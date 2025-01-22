package com.jordirubiralta.data.datasource

import android.util.Log
import com.jordirubiralta.data.Mocks
import com.jordirubiralta.data.api.UserApi
import com.jordirubiralta.data.mapper.UserMapper
import com.jordirubiralta.domain.model.UserListModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.mockkStatic
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class UserNetworkDataSourceTest {

    //SUT
    lateinit var dataSource: UserNetworkDataSource

    @MockK
    lateinit var userApi: UserApi

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)

        dataSource = UserNetworkDataSource(userApi = userApi)
    }

    @Test
    fun `GIVEN results and page WHEN getUsers is called THEN UserListModel is returned`() =
        runTest {
            // given
            val results = 20
            val page = 1
            val mockUserListResponse = Mocks.UserListResponse
            val userListModel =
                UserMapper.fromUserListResponseToModel(response = mockUserListResponse)

            coEvery {
                userApi.getUsers(results = results, page = page)
            } returns mockUserListResponse

            // when
            val result = dataSource.getUsers(results = results, page = page)

            // then
            Assert.assertEquals(userListModel, result)
            coVerify(exactly = 1) { userApi.getUsers(results = results, page = page) }
            confirmVerified(userApi)
        }

    @Test
    fun `GIVEN results and page WHEN getUsers is called but there is an exception THEN empty model is returned`() =
        runTest {
            // given
            val results = 20
            val page = 1
            val emptyModel = UserListModel()
            mockkStatic(Log::class)

            coEvery {
                userApi.getUsers(results = results, page = page)
            } throws RuntimeException()

            coEvery {
                Log.e(any(), any())
            } returns 0

            // when
            val result = dataSource.getUsers(results = results, page = page)

            // then
            Assert.assertEquals(emptyModel, result)
            coVerify(exactly = 1) { userApi.getUsers(results = results, page = page) }
            confirmVerified(userApi)
        }
}
