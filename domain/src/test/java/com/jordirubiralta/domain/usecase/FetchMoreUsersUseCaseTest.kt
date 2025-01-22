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

class FetchMoreUsersUseCaseTest {

    // SUT
    private lateinit var useCase: FetchMoreUsersUseCase

    @MockK
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        useCase = FetchMoreUsersUseCase(userRepository = userRepository)
    }

    @Test
    fun `GIVEN page WHEN fetchMoreUsersUseCase is called THEN UserListModel is returned`() = runTest {
        // given
        val page = 1
        val mockUserList = Mocks.UserListModel

        coEvery {
            userRepository.fetchUsersFromNetwork(page = page)
        } returns mockUserList

        // when
        val result = useCase(page = page)

        // then
        Assert.assertEquals(mockUserList, result)
        coVerify(exactly = 1) {
            userRepository.fetchUsersFromNetwork(page = 1)
        }
        confirmVerified(userRepository)
    }
}
