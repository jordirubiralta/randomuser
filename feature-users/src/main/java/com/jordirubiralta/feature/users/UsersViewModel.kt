package com.jordirubiralta.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordirubiralta.domain.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase
) : ViewModel() {

    fun getUsers() {
        viewModelScope.launch {
            getUserListUseCase.invoke()
        }
    }
}
