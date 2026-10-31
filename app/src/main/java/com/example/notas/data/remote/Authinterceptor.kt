package com.example.notas.data.remote

import com.example.notas.data.local.SessionManager
import okhttp3.Interceptor
import okhttp3.Response

/** Agrega el token a todas las peticiones cuando existe una sesión activa. */
class AuthInterceptor(private val sessionManager: SessionManager) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val token = sessionManager.getToken()
            ?: return chain.proceed(original)

        val authorized = original.newBuilder()
            .header(ApiConfig.HEADER_AUTHORIZATION, ApiConfig.TOKEN_PREFIX + token)
            .build()
        return chain.proceed(authorized)
    }
}