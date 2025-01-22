package com.jordirubiralta.feature.detail.viewmodel

import com.jordirubiralta.domain.usecase.GetUserByEmailUseCase
import com.jordirubiralta.feature.detail.DetailScreenState
import com.jordirubiralta.feature.detail.DetailViewModel
import com.jordirubiralta.feature.detail.Mocks
import com.jordirubiralta.feature.detail.mapper.UserDetailUIMapper
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class DetailViewModelTest {

    // SUT
    private lateinit var viewModel: DetailViewModel

    @MockK
    lateinit var getUserByEmailUseCase: GetUserByEmailUseCase

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = DetailViewModel(getUserByEmailUseCase = getUserByEmailUseCase)
    }

    @Test
    fun `GIVEN DetailViewModel WHEN it is initialized THEN state is init as Empty`() =
        runTest {
            Assert.assertTrue(viewModel.state.value is DetailScreenState.Empty)
            coVerify(exactly = 0) {
                getUserByEmailUseCase(any())
            }
            confirmVerified(getUserByEmailUseCase)
        }

    @Test
    fun `GIVEN email WHEN getUserByEmail is called THEN state is updated with user information`() =
        runTest {
            // given
            val mockUser = Mocks.UserModel
            val email = mockUser.email
            val resultModel = UserDetailUIMapper.fromUserModelToUserDetailUIModel(model = mockUser)

            coEvery {
                getUserByEmailUseCase(email = email)
            } returns mockUser

            // when
            viewModel.getUserByEmail(email = email)

            // then
            Assert.assertTrue(viewModel.state.value is DetailScreenState.Success)
            (viewModel.state.value as? DetailScreenState.Success)?.let { state ->
                Assert.assertEquals(resultModel.gender, state.user.gender)
                Assert.assertEquals(resultModel.name, state.user.name)
                Assert.assertEquals(resultModel.surname, state.user.surname)
                Assert.assertEquals(resultModel.location, state.user.location)
                Assert.assertEquals(resultModel.registeredDate, state.user.registeredDate)
                Assert.assertEquals(resultModel.email, state.user.email)
                Assert.assertEquals(resultModel.imageUrl, state.user.imageUrl)
                Assert.assertEquals(resultModel.phone, state.user.phone)
            }
            coVerify(exactly = 1) {
                getUserByEmailUseCase(email = email)
            }
            confirmVerified(getUserByEmailUseCase)
        }

    @Test
    fun `GIVEN non-existent email WHEN getUserByEmail is called but THEN state is updated as Empty`() =
        runTest {
            // given
            val email = "john.smith@example.com"

            coEvery {
                getUserByEmailUseCase(email = email)
            } returns null

            // when
            viewModel.getUserByEmail(email = email)

            // then
            Assert.assertTrue(viewModel.state.value is DetailScreenState.Empty)
            coVerify(exactly = 1) {
                getUserByEmailUseCase(email = email)
            }
            confirmVerified(getUserByEmailUseCase)
        }

    @Test
    fun `GIVEN email WHEN getUserByEmail is called but throws Exception THEN state is updated as Error `() =
        runTest {
            // given
            val email = "john.smith@example.com"
            val errorMessage = "Error"

            coEvery {
                getUserByEmailUseCase(email = email)
            } throws Exception(errorMessage)

            // when
            viewModel.getUserByEmail(email = email)

            // then
            Assert.assertTrue(viewModel.state.value is DetailScreenState.Error)
            (viewModel.state.value as? DetailScreenState.Error)?.let { state ->
                Assert.assertEquals(errorMessage, state.message)
            }
            coVerify(exactly = 1) {
                getUserByEmailUseCase(email = email)
            }
            confirmVerified(getUserByEmailUseCase)
        }
}
