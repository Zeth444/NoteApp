package com.example.notas.di

import android.content.Context
import com.example.notas.data.local.SessionManager
import com.example.notas.data.remote.ApiService
import com.example.notas.data.remote.RetrofitClient
import com.example.notas.data.repository.AuthRepository
import com.example.notas.data.repository.NotesRepository

/** Punto único de construcción de dependencias (sin librerías de DI). */
object ServiceLocator {

    private lateinit var appContext: Context

    fun init(context: Context) {
        appContext = context.applicationContext
    }

    val sessionManager: SessionManager by lazy { SessionManager(appContext) }

    private val apiService: ApiService by lazy { RetrofitClient.create(sessionManager) }

    val authRepository: AuthRepository by lazy { AuthRepository(apiService, sessionManager) }

    val notesRepository: NotesRepository by lazy { NotesRepository(apiService) }
}