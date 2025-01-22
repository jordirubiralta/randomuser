package com.jordirubiralta.feature.users.viewmodel.viewmodel

import android.content.Context
import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.usecase.DeleteUserUseCase
import com.jordirubiralta.domain.usecase.FetchMoreUsersUseCase
import com.jordirubiralta.domain.usecase.FetchUsersUseCase
import com.jordirubiralta.feature.users.UserScreenUIEffect
import com.jordirubiralta.feature.users.UsersScreenState
import com.jordirubiralta.feature.users.UsersViewModel
import com.jordirubiralta.feature.users.mapper.UserUIMapper
import com.jordirubiralta.feature.users.viewmodel.Mocks
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.spyk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UserViewModelTest {

    // SUT
    private lateinit var viewModel: UsersViewModel

    @MockK
    lateinit var fetchUsersUseCase: FetchUsersUseCase

    @MockK
    lateinit var fetchMoreUsersUseCase: FetchMoreUsersUseCase

    @MockK
    lateinit var deleteUserUseCase: DeleteUserUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = UsersViewModel(
            fetchUsersUseCase = fetchUsersUseCase,
            fetchMoreUsersUseCase = fetchMoreUsersUseCase,
            deleteUserUseCase = deleteUserUseCase
        )
    }

    @Test
    fun `GIVEN search WHEN fetchUsers is called THEN state is updated as Success`() = runTest {
        // given
        val search = "john"
        val mockUserList = Mocks.UserListModel
        val resultList = UserUIMapper.fromUserModelListToUIModel(list = mockUserList.userList)

        coEvery {
            fetchUsersUseCase(search = search)
        } returns mockUserList

        // when
        viewModel.fetchUsers(search = search)

        // then
        Assert.assertTrue(viewModel.state.value is UsersScreenState.Success)
        (viewModel.state.value as? UsersScreenState.Success)?.let { state ->
            Assert.assertFalse(state.isLoadingMore)
            Assert.assertFalse(state.showLoadMore)
            Assert.assertEquals(resultList, state.userList)
        }
        coVerify(exactly = 1) {
            fetchUsersUseCase(any())
        }
        confirmVerified(fetchUsersUseCase, fetchMoreUsersUseCase, deleteUserUseCase)
    }

    @Test
    fun `GIVEN search WHEN fetchUsers is called and returns empty user list THEN state is updated as Empty`() =
        runTest {
            // given
            val search = null
            val mockUserList = spyk(mockk<UserListModel>()).apply {
                every { userList } returns emptyList()
            }

            coEvery {
                fetchUsersUseCase(search = search)
            } returns mockUserList

            // when
            viewModel.fetchUsers(search = search)

            // then
            Assert.assertTrue(viewModel.state.value is UsersScreenState.Empty)
            coVerify {
                fetchUsersUseCase(any())
            }
            confirmVerified(fetchUsersUseCase, fetchMoreUsersUseCase, deleteUserUseCase)
        }

    @Test
    fun `GIVEN search WHEN fetchUsers is called and throws Exception THEN state is updated as Error`() =
        runTest {
            // given
            val search = null
            val errorMessage = "Error"

            coEvery {
                fetchUsersUseCase(search = search)
            } throws Exception(errorMessage)

            // when
            viewModel.fetchUsers(search = search)

            // then
            Assert.assertTrue(viewModel.state.value is UsersScreenState.Error)
            Assert.assertEquals(
                errorMessage,
                (viewModel.state.value as? UsersScreenState.Error)?.message
            )
            coVerify(exactly = 1) {
                fetchUsersUseCase(search = any())
            }
            confirmVerified(fetchUsersUseCase, fetchMoreUsersUseCase, deleteUserUseCase)
        }

    @Test
    fun `GIVEN viewModel WHEN fetchMoreUsers is called THEN state has updated user list`() =
        runTest {
            // given
            val mockCurrentList = Mocks.UserListModel
            val mockNewList = listOf(Mocks.UserModel)
            val mockUser = spyk(mockk<UserListModel>()).apply {
                every { userList } returns mockNewList
            }
            val resultList =
                UserUIMapper.fromUserModelListToUIModel(mockCurrentList.userList + mockNewList)

            coEvery {
                fetchUsersUseCase(search = null)
            } returns mockCurrentList

            coEvery {
                fetchMoreUsersUseCase(any())
            } returns mockUser

            // when
            viewModel.fetchUsers(search = null)
            viewModel.fetchMoreUsers()

            // then
            Assert.assertTrue(viewModel.state.value is UsersScreenState.Success)
            (viewModel.state.value as? UsersScreenState.Success)?.let { state ->
                Assert.assertFalse(state.isLoadingMore)
                Assert.assertTrue(state.showLoadMore)
                Assert.assertEquals(resultList, state.userList)
            }
        }

    @Test
    fun `GIVEN viewModel WHEN fetchMoreUsers is called but throws exception THEN state has updated with error message`() =
        runTest {
            // given
            val mockCurrentList = Mocks.UserListModel
            val resultList = UserUIMapper.fromUserModelListToUIModel(mockCurrentList.userList)
            val errorMessage = "Error"

            coEvery {
                fetchUsersUseCase(search = null)
            } returns mockCurrentList

            coEvery {
                fetchMoreUsersUseCase(any())
            } throws Exception(errorMessage)
            // when
            viewModel.fetchUsers(search = null)
            viewModel.fetchMoreUsers()

            // then
            Assert.assertTrue(viewModel.state.value is UsersScreenState.Success)
            (viewModel.state.value as? UsersScreenState.Success)?.let { state ->
                Assert.assertFalse(state.isLoadingMore)
                Assert.assertFalse(state.showLoadMore)
                Assert.assertEquals(errorMessage, state.errorMessage)
                Assert.assertEquals(resultList, state.userList)
            }
        }

    @Test
    fun `GIVEN email and context WHEN deleteUser is called THEN state is Success and snackbar emitted`() =
        runTest {
            // given
            var effect: UserScreenUIEffect? = null
            val collectJob = launch(UnconfinedTestDispatcher()) {
                viewModel.effect.collectLatest {
                    effect = it
                }
            }
            val mockUserList = Mocks.UserListModel
            val email = mockUserList.userList.first().email
            val context = mockk<Context>()
            val snackbarMessage = "User deleted"

            coEvery {
                fetchUsersUseCase(search = null)
            } returns mockUserList

            coEvery {
                deleteUserUseCase(email = email)
            } returns Unit

            coEvery {
                context.getString(any())
            } returns snackbarMessage

            // when
            viewModel.fetchUsers(search = null)
            viewModel.deleteUser(email = email, context = context)

            // then
            Assert.assertTrue(viewModel.state.value is UsersScreenState.Success)
            (viewModel.state.value as? UsersScreenState.Success)?.let { state ->
                Assert.assertFalse(state.userList.any { it.email == email })
            }
            Assert.assertTrue(effect is UserScreenUIEffect.DeleteItemSnackbar)
            Assert.assertEquals(
                snackbarMessage,
                (effect as? UserScreenUIEffect.DeleteItemSnackbar)?.message
            )
            coVerify(exactly = 1) {
                fetchUsersUseCase(search = null)
                deleteUserUseCase(email)
            }
            confirmVerified(fetchUsersUseCase, fetchMoreUsersUseCase, deleteUserUseCase)

            collectJob.cancel()
        }

    @Test
    fun `GIVEN userList with 1 item, email and context WHEN deleteUser is called THEN state is Success and snackbar emitted`() =
        runTest {
            // given
            var effect: UserScreenUIEffect? = null
            val collectJob = launch(UnconfinedTestDispatcher()) {
                viewModel.effect.collectLatest {
                    effect = it
                }
            }

            val mockUserList = UserListModel(
                userList = Mocks.UserListModel.userList.subList(0, 1),
                page = 1
            )
            val email = mockUserList.userList.first().email
            val context = mockk<Context>()
            val snackbarMessage = "User deleted"

            coEvery {
                fetchUsersUseCase(search = null)
            } returns mockUserList

            coEvery {
                deleteUserUseCase(email = email)
            } returns Unit

            coEvery {
                context.getString(any())
            } returns snackbarMessage

            // when
            viewModel.fetchUsers(search = null)
            viewModel.deleteUser(email = email, context = context)

            // then
            Assert.assertTrue(viewModel.state.value is UsersScreenState.Empty)
            Assert.assertTrue(effect is UserScreenUIEffect.DeleteItemSnackbar)
            Assert.assertEquals(
                snackbarMessage,
                (effect as? UserScreenUIEffect.DeleteItemSnackbar)?.message
            )
            coVerify(exactly = 1) {
                fetchUsersUseCase(search = null)
                deleteUserUseCase(email)
            }
            confirmVerified(fetchUsersUseCase, fetchMoreUsersUseCase, deleteUserUseCase)

            collectJob.cancel()
        }
}
