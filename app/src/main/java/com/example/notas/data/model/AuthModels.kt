package com.example.notas.data.model

import com.google.gson.annotations.SerializedName

// CONTRATO PROVISIONAL: se ajusta en una sola línea por campo cuando definamos el backend.

data class LoginRequest(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

data class User(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("email") val email: String
)

data class LoginResponse(
    @SerializedName("token") val token: String,
    @SerializedName("user") val user: User
)