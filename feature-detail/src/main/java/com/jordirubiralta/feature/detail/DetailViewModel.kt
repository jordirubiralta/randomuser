package com.jordirubiralta.feature.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jordirubiralta.domain.usecase.GetUserByEmailUseCase
import com.jordirubiralta.feature.detail.mapper.UserDetailUIMapper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getUserByEmailUseCase: GetUserByEmailUseCase
) : ViewModel() {

    private val _state: MutableStateFlow<DetailScreenState> =
        MutableStateFlow(DetailScreenState.Empty)
    val state: StateFlow<DetailScreenState> = _state.asStateFlow()

    fun getUserByEmail(email: String) {
        viewModelScope.launch {
            try {
                _state.value = DetailScreenState.Loading
                val user = getUserByEmailUseCase(email = email)
                _state.value = user?.let {
                    DetailScreenState.Success(
                        user = UserDetailUIMapper.fromUserModelToUserDetailUIModel(model = it)
                    )
                } ?: DetailScreenState.Empty
            } catch (e: Exception) {
                _state.value = DetailScreenState.Error(message = e.message)
            }
        }
    }

}
