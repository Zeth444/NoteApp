package com.example.notas.ui.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.notas.R
import com.example.notas.databinding.ActivityDashboardBinding
import com.example.notas.di.ServiceLocator
import com.example.notas.ui.login.LoginActivity
import com.example.notas.util.applySystemBarsPadding

class DashboardActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDashboardBinding

    private val viewModel: DashboardViewModel by viewModels {
        viewModelFactory {
            initializer { DashboardViewModel(ServiceLocator.authRepository) }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.applySystemBarsPadding()

        // Saludo con el nombre del usuario
        binding.tvWelcome.text = getString(R.string.dashboard_welcome, viewModel.getUserName())

        // Botón para ir a Crear Nota
        binding.btnCreateNote.setOnClickListener {
            val intent = Intent(this, com.example.notas.ui.createnote.CreateNoteActivity::class.java)
            startActivity(intent)
        }

        // Botón para ir a Listar Notas
        binding.btnListNotes.setOnClickListener {
            val intent = Intent(this, com.example.notas.ui.notelist.NoteListActivity::class.java)
            startActivity(intent)
        }

        // Botón para Cerrar Sesión
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}