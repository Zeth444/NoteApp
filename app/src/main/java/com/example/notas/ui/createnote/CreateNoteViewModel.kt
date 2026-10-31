package com.example.notas.ui.createnote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notas.data.repository.NotesRepository
import com.example.notas.util.ErrorType
import com.example.notas.util.Resource
import kotlinx.coroutines.launch

sealed class CreateNoteUiState {
    object Idle : CreateNoteUiState()
    object Loading : CreateNoteUiState()
    object Success : CreateNoteUiState()
    data class Error(val type: ErrorType) : CreateNoteUiState()
}

class CreateNoteViewModel(private val notesRepository: NotesRepository) : ViewModel() {

    private val _uiState = MutableLiveData<CreateNoteUiState>(CreateNoteUiState.Idle)
    val uiState: LiveData<CreateNoteUiState> get() = _uiState

    fun saveNote(title: String, content: String) {
        if (title.isBlank() || content.isBlank()) {
            _uiState.value = CreateNoteUiState.Error(ErrorType.UNKNOWN)
            return
        }

        _uiState.value = CreateNoteUiState.Loading
        viewModelScope.launch {
            val result = notesRepository.createNote(title, content)
            when (result) {
                is Resource.Success -> _uiState.value = CreateNoteUiState.Success
                is Resource.Error -> _uiState.value = CreateNoteUiState.Error(result.type)
            }
        }
    }
}