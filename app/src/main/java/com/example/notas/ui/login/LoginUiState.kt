package com.example.notas.ui.login

import com.example.notas.util.ErrorType

sealed class LoginUiState {
    object Idle : LoginUiState()
    object Loading : LoginUiState()
    object Success : LoginUiState()
    data class Error(val type: ErrorType) : LoginUiState()
}