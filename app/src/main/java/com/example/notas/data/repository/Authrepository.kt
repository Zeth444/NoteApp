package com.example.notas.data.repository

import com.example.notas.data.local.SessionManager
import com.example.notas.data.model.LoginRequest
import com.example.notas.data.model.LoginResponse
import com.example.notas.data.remote.ApiService
import com.example.notas.util.Resource
import com.example.notas.util.safeApiCall

class AuthRepository(
    private val api: ApiService,
    private val session: SessionManager
) {

    suspend fun login(email: String, password: String): Resource<LoginResponse> {
        val result = safeApiCall { api.login(LoginRequest(email, password)) }
        if (result is Resource.Success) {
            session.saveSession(result.data.token, result.data.user.name)
        }
        return result
    }

    fun isLoggedIn(): Boolean = session.isLoggedIn()

    fun getUserName(): String? = session.getUserName()

    fun logout() = session.clear()
}