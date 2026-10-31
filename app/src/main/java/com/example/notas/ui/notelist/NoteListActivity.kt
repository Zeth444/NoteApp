package com.example.notas.ui.notelist

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notas.databinding.ActivityNoteListBinding
import com.example.notas.di.ServiceLocator
import com.example.notas.util.applySystemBarsPadding
import com.example.notas.util.toMessageRes

class NoteListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNoteListBinding
    private val adapter = NoteAdapter()
    private val viewModel: NoteListViewModel by viewModels {
        viewModelFactory {
            initializer { NoteListViewModel(ServiceLocator.notesRepository) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNoteListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter

        viewModel.uiState.observe(this) { state ->
            when (state) {
                is NoteListUiState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                    binding.tvEmpty.visibility = View.GONE
                }
                is NoteListUiState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    if (state.notes.isEmpty()) {
                        binding.tvEmpty.visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE
                    } else {
                        binding.tvEmpty.visibility = View.GONE
                        binding.recyclerView.visibility = View.VISIBLE
                        adapter.submitList(state.notes)
                    }
                }
                is NoteListUiState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, state.type.toMessageRes(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}