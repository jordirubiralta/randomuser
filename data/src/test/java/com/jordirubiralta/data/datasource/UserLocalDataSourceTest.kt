package com.jordirubiralta.data.datasource

import com.jordirubiralta.data.Mocks
import com.jordirubiralta.data.database.dao.UserDao
import com.jordirubiralta.data.database.entity.DeletedUserEntity
import com.jordirubiralta.data.mapper.UserMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserLocalDataSourceTest {

    // SUT
    private lateinit var dataSource: UserLocalDataSource

    @MockK
    lateinit var userDao: UserDao


    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        dataSource = UserLocalDataSource(userDao = userDao)
    }

    @Test
    fun `GIVEN userDao WHEN getAllUsers is called THEN UserListModel is returned`() = runTest {
        // given
        val mockUsers = Mocks.UserEntityList
        val userModelList = UserMapper.fromUserListEntityToModel(list = mockUsers)
        coEvery {
            userDao.getAllUsers()
        } returns mockUsers

        // when
        val result = dataSource.getAllUsers()

        // then
        Assert.assertEquals(userModelList, result.userList)
        coVerify(exactly = 1) {
            userDao.getAllUsers()
            UserMapper.fromUserListEntityToModel(mockUsers)
        }
        confirmVerified(userDao)
    }

    @Test
    fun `GIVEN userDao and UserModel list WHEN insertAllUsers is called THEN list is added to DB`() =
        runTest {
            // given
            val mockUsers = Mocks.UserModelList
            val userEntityList = UserMapper.fromUserListModelToEntity(list = mockUsers)
            coEvery {
                userDao.insertUsers(userEntityList)
            } returns Unit

            // when
            val result = dataSource.insertAllUsers(userList = mockUsers)

            // then
            coVerify(exactly = 1) { userDao.insertUsers(userEntityList) }
            confirmVerified(userDao)
        }

    @Test
    fun `GIVEN userDao and email WHEN deleteUser is called THEN user is deleted from user DB and added to deleted_user DB`() =
        runTest {
            // given
            val email = "test@example.com"
            val deletedUser = DeletedUserEntity(email = email)
            coEvery {
                userDao.deleteUser(email)
            } returns Unit
            coEvery {
                userDao.insertDeletedUser(deletedUser)
            } returns Unit

            // when
            val result = dataSource.deleteUser(email)

            // then
            coVerify(exactly = 1) {
                userDao.deleteUser(email)
                userDao.insertDeletedUser(deletedUser)
            }
            confirmVerified(userDao)
        }

    @Test
    fun `GIVEN userDao WHEN getDeletedUsers is called THEN email list is returned`() = runTest {
        // given
        val deletedUsers = listOf(
            DeletedUserEntity(email = "example1@test.com"),
            DeletedUserEntity(email = "example2@test.com")
        )
        val emails = deletedUsers.map { it.email }
        coEvery {
            userDao.getAllDeletedUsers()
        } returns deletedUsers

        // when
        val result = dataSource.getDeletedUsers()

        // then
        Assert.assertEquals(emails, result)
        coVerify(exactly = 1) { userDao.getAllDeletedUsers() }
        confirmVerified(userDao)
    }

    @Test
    fun `GIVEN userDao and email WHEN getUserById is called THEN user is returned`() = runTest {
        // given
        val email = "example1@test.com"
        val mockUserEntity = Mocks.UserEntityList.first()
        val userModel = UserMapper.fromUserEntityToModel(entity = mockUserEntity)

        coEvery {
            userDao.getUserByEmail(email)
        } returns mockUserEntity

        // when
        val result = dataSource.getUserById(email)

        // then
        Assert.assertEquals(userModel, result)
        coVerify(exactly = 1) { userDao.getUserByEmail(email) }
        confirmVerified(userDao)
    }
}
