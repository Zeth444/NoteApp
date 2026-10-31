package com.example.notas.data.remote

/** Rutas relativas a BuildConfig.API_BASE_URL. Único lugar donde viven los paths. */
object ApiRoutes {
    const val LOGIN = "auth/login"
    const val NOTES = "notes"
}

/** Parámetros de red compartidos. */
object ApiConfig {
    const val HEADER_AUTHORIZATION = "Authorization"
    const val TOKEN_PREFIX = "Bearer "
    const val TIMEOUT_SECONDS = 30L
}