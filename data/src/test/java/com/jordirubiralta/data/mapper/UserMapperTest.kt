package com.jordirubiralta.data.mapper

import com.jordirubiralta.data.Mocks
import com.jordirubiralta.domain.model.UserModel
import io.mockk.every
import io.mockk.spyk
import org.junit.Assert
import org.junit.Test

class UserMapperTest {

    @Test
    fun `GIVEN UserListResponse WHEN fromUserListResponseToModel is called THEN UserListModel is returned`() {
        // given
        val mockResponse = Mocks.UserListResponse

        // when
        val result = UserMapper.fromUserListResponseToModel(response = mockResponse)

        // then
        Assert.assertEquals(mockResponse.info?.page, result.page)
        Assert.assertEquals(mockResponse.results?.size, result.userList.size)
        result.userList.forEachIndexed { index, userModel ->
            Assert.assertEquals(mockResponse.results?.get(index)?.name?.title, userModel.title)
            Assert.assertEquals(mockResponse.results?.get(index)?.name?.first, userModel.name)
            Assert.assertEquals(mockResponse.results?.get(index)?.name?.last, userModel.surname)
            Assert.assertEquals(mockResponse.results?.get(index)?.gender, userModel.gender)
            Assert.assertEquals(mockResponse.results?.get(index)?.email, userModel.email)
            Assert.assertEquals(mockResponse.results?.get(index)?.phone, userModel.phone)
            Assert.assertEquals(
                mockResponse.results?.get(index)?.picture?.thumbnail,
                userModel.thumbnailImageUrl
            )
            Assert.assertEquals(
                mockResponse.results?.get(index)?.picture?.large,
                userModel.largeImageUrl
            )
            Assert.assertEquals(
                mockResponse.results?.get(index)?.location?.toString(),
                userModel.location
            )
            Assert.assertEquals(
                mockResponse.results?.get(index)?.registered?.date,
                userModel.registeredDate
            )
        }
    }

    @Test
    fun `GIVEN UserListResponse with null params WHEN fromUserListResponseToModel is called THEN UserListModel is returned`() {
        // given
        val mockResponse = Mocks.EmptyUserListResponse

        // when
        val result = UserMapper.fromUserListResponseToModel(response = mockResponse)

        // then
        Assert.assertNull(result.page)
        Assert.assertEquals(mockResponse.results?.size, result.userList.size)
        result.userList.forEachIndexed { index, userModel ->
            Assert.assertEquals("", userModel.name)
            Assert.assertEquals("", userModel.surname)
            Assert.assertEquals("", userModel.email)
            Assert.assertNull(userModel.title)
            Assert.assertNull(userModel.gender)
            Assert.assertNull(userModel.phone)
            Assert.assertNull(userModel.thumbnailImageUrl)
            Assert.assertNull(userModel.largeImageUrl)
            Assert.assertNull(userModel.location)
            Assert.assertNull(userModel.registeredDate)
        }
    }

    @Test
    fun `GIVEN UserListResponse with null results WHEN fromUserListResponseToModel is called THEN UserListModel is returned with empty list`() {
        // given
        val mockResponse = spyk(Mocks.UserListResponse).apply {
            every { results } returns null
        }

        // when
        val result = UserMapper.fromUserListResponseToModel(response = mockResponse)

        // then
        Assert.assertEquals(emptyList<UserModel>(), result.userList)
    }

    @Test
    fun `GIVEN userModel list WHEN fromUserListModelToEntity THEN UserEntity list is returned`() {
        // given
        val mockUserList = Mocks.UserModelList

        // when
        val result = UserMapper.fromUserListModelToEntity(list = mockUserList)

        // then
        result.forEachIndexed { index, model ->
            Assert.assertEquals(mockUserList[index].gender, model.gender)
            Assert.assertEquals(mockUserList[index].title, model.title)
            Assert.assertEquals(mockUserList[index].name, model.name)
            Assert.assertEquals(mockUserList[index].surname, model.surname)
            Assert.assertEquals(mockUserList[index].email, model.email)
            Assert.assertEquals(mockUserList[index].phone, model.phone)
            Assert.assertEquals(mockUserList[index].thumbnailImageUrl, model.thumbnailImageUrl)
            Assert.assertEquals(mockUserList[index].largeImageUrl, model.largeImageUrl)
            Assert.assertEquals(mockUserList[index].location, model.location)
            Assert.assertEquals(mockUserList[index].registeredDate, model.registeredDate)
        }
    }

    @Test
    fun `GIVEN UserEntity list WHEN fromUserListEntityToModel is called THEN UserModel list is returned`() {
        // given
        val mockUserList = Mocks.UserEntityList

        // when
        val result = UserMapper.fromUserListEntityToModel(list = mockUserList)

        // then
        result.forEachIndexed { index, model ->
            Assert.assertEquals(mockUserList[index].gender, model.gender)
            Assert.assertEquals(mockUserList[index].title, model.title)
            Assert.assertEquals(mockUserList[index].name, model.name)
            Assert.assertEquals(mockUserList[index].surname, model.surname)
            Assert.assertEquals(mockUserList[index].email, model.email)
            Assert.assertEquals(mockUserList[index].phone, model.phone)
            Assert.assertEquals(mockUserList[index].thumbnailImageUrl, model.thumbnailImageUrl)
            Assert.assertEquals(mockUserList[index].largeImageUrl, model.largeImageUrl)
            Assert.assertEquals(mockUserList[index].location, model.location)
            Assert.assertEquals(mockUserList[index].registeredDate, model.registeredDate)
        }
    }
}
