package com.jordirubiralta.data.datasource

import com.jordirubiralta.data.database.dao.UserDao
import com.jordirubiralta.data.database.entity.DeletedUserEntity
import com.jordirubiralta.data.mapper.UserMapper
import com.jordirubiralta.domain.model.UserListModel
import com.jordirubiralta.domain.model.UserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSource @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun getAllUsers(): UserListModel {
        val modelList = UserMapper.fromUserListEntityToModel(list = userDao.getAllUsers())
        return UserListModel(userList = modelList)
    }

    suspend fun insertAllUsers(userList: List<UserModel>) {
        val entityList = UserMapper.fromUserListModelToEntity(list = userList)
        userDao.insertUsers(entityList)
    }

    suspend fun deleteUser(email: String) {
        userDao.deleteUser(email = email)
        userDao.insertDeletedUser(user = DeletedUserEntity(email = email))
    }

    suspend fun getDeletedUsers(): List<String> =
        userDao.getAllDeletedUsers().map { it.email }

    suspend fun getUserById(email: String): UserModel? =
        userDao.getUserByEmail(email = email)?.let(UserMapper::fromUserEntityToModel)
}
