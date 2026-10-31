package com.example.notas.util

/** Resultado de una operación de la capa de datos. */
sealed class Resource<out T> {
    data class Success<T>(val data: T) : Resource<T>()
    data class Error(val type: ErrorType, val serverMessage: String? = null) : Resource<Nothing>()
}

enum class ErrorType {
    NETWORK,
    UNAUTHORIZED,
    SERVER,
    UNKNOWN
}