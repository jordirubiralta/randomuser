package com.jordirubiralta.domain.model

data class UserListModel(
    val userList: List<UserModel> = emptyList(),
    val page: Int? = null
) {
    fun filterList(search: String): UserListModel {
        val filteredList = userList.filter { user ->
            user.name.contains(search, ignoreCase = true)
                    || user.surname.contains(search, ignoreCase = true)
                    || user.email.contains(search, ignoreCase = true)
        }
        return this.copy(userList = filteredList)
    }
}
