package com.jordirubiralta.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordirubiralta.domain.usecase.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor() : ViewModel() {



    fun getUsers() {
        viewModelScope.launch {
        }
    }
}
