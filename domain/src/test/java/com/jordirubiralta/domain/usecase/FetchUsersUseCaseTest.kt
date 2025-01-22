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

class FetchUsersUseCaseTest {

    // SUT
    private lateinit var useCase: FetchUsersUseCase

    @MockK
    lateinit var userRepository: UserRepository

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        useCase = FetchUsersUseCase(userRepository = userRepository)
    }

    @Test
    fun `GIVEN null search text WHEN FetchUserUseCase is called then UserListModel without filter is returned`() =
        runTest {
            // given
            val searchText = null
            val mockUserList = Mocks.UserListModel

            coEvery {
                userRepository.fetchUsers()
            } returns mockUserList

            // when
            val result = useCase(search = searchText)

            // then
            Assert.assertEquals(mockUserList, result)
            coVerify(exactly = 1) {
                userRepository.fetchUsers()
            }
            confirmVerified(userRepository)
        }

    @Test
    fun `GIVEN blank search text WHEN FetchUserUseCase is called then UserListModel without filter is returned`() =
        runTest {
            // given
            val searchText = ""
            val mockUserList = Mocks.UserListModel

            coEvery {
                userRepository.fetchUsers()
            } returns mockUserList

            // when
            val result = useCase(search = searchText)

            // then
            Assert.assertEquals(mockUserList, result)
            coVerify(exactly = 1) {
                userRepository.fetchUsers()
            }
            confirmVerified(userRepository)
        }

    @Test
    fun `GIVEN search text WHEN FetchUserUseCase is called then UserListModel without filter is returned`() =
        runTest {
            // given
            val searchText = "John"
            val mockUserList = Mocks.UserListModel

            coEvery {
                userRepository.fetchUsers()
            } returns mockUserList

            // when
            val result = useCase(search = searchText)

            // then
            Assert.assertEquals(1, result.userList.size)
            Assert.assertTrue(
                result.userList.all {
                    it.email == searchText || it.name == searchText || it.surname == searchText
                }
            )
            coVerify(exactly = 1) {
                userRepository.fetchUsers()
            }
            confirmVerified(userRepository)
        }

}
