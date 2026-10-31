package com.example.notas.data.model

import com.google.gson.annotations.SerializedName

data class CreateNoteRequest(
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String
)

data class Note(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("content") val content: String,
    @SerializedName("createdAt") val createdAt: String
)

data class ErrorResponse(
    @SerializedName("message") val message: String?
)