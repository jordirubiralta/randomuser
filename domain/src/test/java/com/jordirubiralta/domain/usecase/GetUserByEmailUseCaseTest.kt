package com.jordirubiralta.domain.usecase

import com.jordirubiralta.domain.Mocks
import com.jordirubiralta.domain.repository.UserRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetUserByEmailUseCaseTest {

    // SUT
    private lateinit var useCase: GetUserByEmailUseCase

    @MockK
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        useCase = GetUserByEmailUseCase(userRepository = userRepository)
    }

    @Test
    fun `GIVEN email WHEN getUserByEmailUseCase is called THEN UserModel is returned`() = runTest {
        // given
        val email = "john.doe@example.com"
        val mockUser = Mocks.UserModelList.first()

        coEvery {
            userRepository.getUserByEmail(email = email)
        } returns mockUser

        // when
        val result = useCase(email = email)

        // then
        Assert.assertEquals(mockUser, result)
        coVerify(exactly = 1) {
            userRepository.getUserByEmail(email = email)
        }
        confirmVerified(userRepository)
    }
}
