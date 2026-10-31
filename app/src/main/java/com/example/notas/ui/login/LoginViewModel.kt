package com.example.notas.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notas.data.repository.AuthRepository
import com.example.notas.util.ErrorType
import com.example.notas.util.Resource
import kotlinx.coroutines.launch

class LoginViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _uiState = MutableLiveData<LoginUiState>(LoginUiState.Idle)
    val uiState: LiveData<LoginUiState> get() = _uiState

    private var isLoggingIn = false

    fun login(email: String, password: String) {
        if (isLoggingIn) return

        if (email.isBlank() || password.isBlank()) {
            _uiState.value = LoginUiState.Error(ErrorType.UNKNOWN)
            return
        }

        isLoggingIn = true
        _uiState.value = LoginUiState.Loading

        viewModelScope.launch {
            val result = authRepository.login(email, password)
            when (result) {
                is Resource.Success -> _uiState.value = LoginUiState.Success
                is Resource.Error -> _uiState.value = LoginUiState.Error(result.type) // <-- CORREGIDO AQUÍ
            }
            isLoggingIn = false
        }
    }
}