package com.example.notas.ui.createnote

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notas.R
import com.example.notas.databinding.ActivityCreateNoteBinding
import com.example.notas.di.ServiceLocator
import com.example.notas.util.applySystemBarsPadding
import com.example.notas.util.toMessageRes

class CreateNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNoteBinding
    private val viewModel: CreateNoteViewModel by viewModels {
        viewModelFactory {
            initializer { CreateNoteViewModel(ServiceLocator.notesRepository) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.btnSave.setOnClickListener {
            val title = binding.etNoteTitle.text.toString().trim()
            val content = binding.etNoteContent.text.toString().trim()
            viewModel.saveNote(title, content)
        }

        viewModel.uiState.observe(this) { state ->
            when (state) {
                is CreateNoteUiState.Idle -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSave.isEnabled = true
                }
                is CreateNoteUiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.btnSave.isEnabled = false
                }
                is CreateNoteUiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSave.isEnabled = true
                    Toast.makeText(this, R.string.create_note_success, Toast.LENGTH_SHORT).show()
                    finish()
                }
                is CreateNoteUiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.btnSave.isEnabled = true
                    Toast.makeText(this, state.type.toMessageRes(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}