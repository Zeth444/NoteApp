package com.example.notas.util

import com.example.notas.data.model.ErrorResponse
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import java.net.HttpURLConnection
import kotlin.coroutines.cancellation.CancellationException

private val gson = Gson()

/** Envuelve una llamada Retrofit y la convierte en Resource, sin lanzar excepciones. */
suspend fun <T> safeApiCall(call: suspend () -> Response<T>): Resource<T> =
    try {
        val response = call()
        val body = response.body()
        when {
            response.isSuccessful && body != null -> Resource.Success(body)
            response.isSuccessful -> Resource.Error(ErrorType.UNKNOWN)
            else -> Resource.Error(response.code().toErrorType(), response.serverMessage())
        }
    } catch (e: CancellationException) {
        throw e
    } catch (e: IOException) {
        Resource.Error(ErrorType.NETWORK)
    } catch (e: Exception) {
        Resource.Error(ErrorType.UNKNOWN)
    }

private fun Int.toErrorType(): ErrorType = when {
    this == HttpURLConnection.HTTP_UNAUTHORIZED ||
            this == HttpURLConnection.HTTP_FORBIDDEN -> ErrorType.UNAUTHORIZED
    this >= HttpURLConnection.HTTP_INTERNAL_ERROR -> ErrorType.SERVER
    else -> ErrorType.UNKNOWN
}

private fun <T> Response<T>.serverMessage(): String? = runCatching {
    gson.fromJson(errorBody()?.string(), ErrorResponse::class.java)?.message
}.getOrNull()