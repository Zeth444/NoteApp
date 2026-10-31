package com.example.notas.data.repository

import com.example.notas.data.model.CreateNoteRequest
import com.example.notas.data.model.Note
import com.example.notas.data.remote.ApiService
import com.example.notas.util.Resource
import com.example.notas.util.safeApiCall

class NotesRepository(private val api: ApiService) {

    suspend fun createNote(title: String, content: String): Resource<Note> =
        safeApiCall { api.createNote(CreateNoteRequest(title, content)) }

    suspend fun getNotes(): Resource<List<Note>> =
        safeApiCall { api.getNotes() }
}