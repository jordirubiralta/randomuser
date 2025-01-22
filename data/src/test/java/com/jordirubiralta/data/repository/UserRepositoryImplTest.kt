package com.jordirubiralta.data.repository

import com.jordirubiralta.data.Mocks
import com.jordirubiralta.data.datasource.UserLocalDataSource
import com.jordirubiralta.data.datasource.UserNetworkDataSource
import com.jordirubiralta.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserRepositoryImplTest {

    // SUT
    private lateinit var repository: UserRepository

    @MockK
    lateinit var networkDataSource: UserNetworkDataSource

    @MockK
    lateinit var localDataSource: UserLocalDataSource

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(testDispatcher)
        repository = UserRepositoryImpl(
            ioDispatcher = testDispatcher,
            networkDataSource = networkDataSource,
            localDataSource = localDataSource
        )
    }

    @Test
    fun `GIVEN DB without users WHEN fetchUsers is called THEN UserListModel from network is returned`() =
        runTest {
            // given
            val mockUserListModel = Mocks.UserListModel
            val mockEmptyUserList = spyk(mockUserListModel).apply {
                every { userList } returns emptyList()
            }
            coEvery {
                localDataSource.getAllUsers()
            } returns mockEmptyUserList

            coEvery {
                networkDataSource.getUsers(any(), any())
            } returns mockUserListModel

            coEvery {
                localDataSource.getDeletedUsers()
            } returns emptyList()

            // when
            val result = repository.fetchUsers()

            // then
            Assert.assertEquals(mockUserListModel, result)
            coVerify(exactly = 1) {
                localDataSource.getAllUsers()
                localDataSource.insertAllUsers(any())
                localDataSource.getDeletedUsers()
                networkDataSource.getUsers(any(), any())
            }
            confirmVerified(localDataSource, networkDataSource)
        }

    @Test
    fun `GIVEN DB with users WHEN fetchUsers is called THEN UserListModel from local is returned`() =
        runTest {
            // given
            val mockUserListModel = Mocks.UserListModel

            coEvery {
                localDataSource.getAllUsers()
            } returns mockUserListModel

            // when
            val result = repository.fetchUsers()

            // then
            Assert.assertEquals(mockUserListModel, result)
            coVerify(exactly = 1) {
                localDataSource.getAllUsers()
            }
            coVerify(exactly = 0) {
                localDataSource.insertAllUsers(any())
                localDataSource.getDeletedUsers()
                networkDataSource.getUsers(any(), any())
            }
            confirmVerified(localDataSource, networkDataSource)
        }

    @Test
    fun `GIVEN page WHEN fetchUsersFromNetwork is called THEN UserListModel from local is returned`() =
        runTest {
            // given
            val mockUserListModel = Mocks.UserListModel

            coEvery {
                networkDataSource.getUsers(any(), any())
            } returns mockUserListModel

            coEvery {
                localDataSource.getDeletedUsers()
            } returns emptyList()

            // when
            val result = repository.fetchUsersFromNetwork(1)

            // then
            Assert.assertEquals(mockUserListModel, result)
            coVerify(exactly = 1) {
                localDataSource.insertAllUsers(any())
                localDataSource.getDeletedUsers()
                networkDataSource.getUsers(any(), any())
            }
            coVerify(exactly = 0) {
                localDataSource.getAllUsers()
            }
            confirmVerified(localDataSource, networkDataSource)
        }

    @Test
    fun `GIVEN userlist with duplicated users WHEN fetchUsersFromNetwork is called THEN UserListModel without duplicated Users is returned`() =
        runTest {
            // given
            val mockUserListModel = Mocks.DuplicatedUserListModel

            coEvery {
                networkDataSource.getUsers(any(), any())
            } returns mockUserListModel

            coEvery {
                localDataSource.getDeletedUsers()
            } returns emptyList()

            // when
            val result = repository.fetchUsersFromNetwork(1)

            // then
            Assert.assertEquals(1, result.userList.size)
            coVerify(exactly = 1) {
                localDataSource.insertAllUsers(any())
                localDataSource.getDeletedUsers()
                networkDataSource.getUsers(any(), any())
            }
            coVerify(exactly = 0) {
                localDataSource.getAllUsers()
            }
            confirmVerified(localDataSource, networkDataSource)
        }

    @Test
    fun `GIVEN deletedUsers list WHEN fetchUsersFromNetwork is called THEN UserListModel without deletedUsers is returned`() =
        runTest {
            // given
            val mockUserListModel = Mocks.UserListModel
            val deletedUsers = listOf(mockUserListModel.userList.first().email)

            coEvery {
                networkDataSource.getUsers(any(), any())
            } returns mockUserListModel

            coEvery {
                localDataSource.getDeletedUsers()
            } returns deletedUsers

            // when
            val result = repository.fetchUsersFromNetwork(1)

            // then
            Assert.assertEquals(1, result.userList.size)
            Assert.assertTrue(result.userList.none { it.email == deletedUsers.first() })
            coVerify(exactly = 1) {
                localDataSource.insertAllUsers(any())
                localDataSource.getDeletedUsers()
                networkDataSource.getUsers(any(), any())
            }
            coVerify(exactly = 0) {
                localDataSource.getAllUsers()
            }
            confirmVerified(localDataSource, networkDataSource)
        }

    @Test
    fun `GIVEN email WHEN deleteUser is called THEN user is deleted from DB`() = runTest {
        // given
        val email = "john@example.com"

        coEvery {
            localDataSource.deleteUser(email = email)
        } returns Unit

        // when
        val result = repository.deleteUser(email = email)

        // then
        Assert.assertEquals(Unit, result)
        coVerify(exactly = 1) {
            localDataSource.deleteUser(email = email)
        }
        confirmVerified(localDataSource, networkDataSource)
    }

    @Test
    fun `GIVEN email WHEN getUserByEmail is called THEN UserModel is returned`() = runTest {
        // given
        val mockUser = Mocks.UserModelList.first()
        val email = mockUser.email

        coEvery {
            localDataSource.getUserById(email = email)
        } returns mockUser

        // when
        val result = repository.getUserByEmail(email = email)

        // then
        Assert.assertEquals(email, result?.email)
        coVerify(exactly = 1) {
            localDataSource.getUserById(email = email)
        }
        confirmVerified(localDataSource, networkDataSource)
    }
}
