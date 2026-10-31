package com.example.notas.ui.notelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notas.data.model.Note
import com.example.notas.data.repository.NotesRepository
import com.example.notas.util.ErrorType
import com.example.notas.util.Resource
import kotlinx.coroutines.launch

sealed class NoteListUiState {
    object Loading : NoteListUiState()
    data class Success(val notes: List<Note>) : NoteListUiState()
    data class Error(val type: ErrorType) : NoteListUiState()
}

class NoteListViewModel(private val notesRepository: NotesRepository) : ViewModel() {
    private val _uiState = MutableLiveData<NoteListUiState>()
    val uiState: LiveData<NoteListUiState> get() = _uiState

    init { fetchNotes() }

    fun fetchNotes() {
        _uiState.value = NoteListUiState.Loading
        viewModelScope.launch {
            val result = notesRepository.getNotes()
            when (result) {
                is Resource.Success -> _uiState.value = NoteListUiState.Success(result.data)
                is Resource.Error -> _uiState.value = NoteListUiState.Error(result.type)
            }
        }
    }
}