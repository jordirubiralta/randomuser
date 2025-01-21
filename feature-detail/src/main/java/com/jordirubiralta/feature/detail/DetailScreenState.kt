package com.jordirubiralta.feature.detail

import com.jordirubiralta.feature.detail.model.UserDetailUIModel

sealed class DetailScreenState {
    data object Loading : DetailScreenState()
    data class Success(val user: UserDetailUIModel) : DetailScreenState()
    data class Error(val message: String?) : DetailScreenState()
    data object Empty : DetailScreenState()
}
