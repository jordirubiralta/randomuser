package com.jordirubiralta.domain.usecase

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

class DeleteUserUseCaseTest {

    // SUT
    private lateinit var useCase: DeleteUserUseCase

    @MockK
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        useCase = DeleteUserUseCase(userRepository = userRepository)
    }

    @Test
    fun `GIVEN email WHEN deleteUserUseCase is called THEN it returns Unit`() = runTest {
        // given
        val email = "john@example.com"
        coEvery {
            userRepository.deleteUser(email = email)
        } returns Unit

        // when
        val result = useCase(email)

        // then
        Assert.assertEquals(Unit, result)
        coVerify(exactly = 1) {
            userRepository.deleteUser(email = email)
        }
        confirmVerified(userRepository)
    }

}
