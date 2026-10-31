package com.example.notas.data.remote

import com.example.notas.data.model.CreateNoteRequest
import com.example.notas.data.model.LoginRequest
import com.example.notas.data.model.LoginResponse
import com.example.notas.data.model.Note
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST(ApiRoutes.LOGIN)
    suspend fun login(@Body body: LoginRequest): Response<LoginResponse>

    @POST(ApiRoutes.NOTES)
    suspend fun createNote(@Body body: CreateNoteRequest): Response<Note>

    @GET(ApiRoutes.NOTES)
    suspend fun getNotes(): Response<List<Note>>
}