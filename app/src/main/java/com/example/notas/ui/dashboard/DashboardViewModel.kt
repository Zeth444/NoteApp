package com.example.notas.ui.dashboard

import androidx.lifecycle.ViewModel
import com.example.notas.data.repository.AuthRepository

class DashboardViewModel(private val authRepository: AuthRepository) : ViewModel() {
    fun getUserName(): String {
        return authRepository.getUserName() ?: "Usuario"
    }
    fun logout() {
        authRepository.logout()
    }
}