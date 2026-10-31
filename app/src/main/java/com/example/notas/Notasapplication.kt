package com.example.notas

import android.app.Application
import com.example.notas.di.ServiceLocator

class NotasApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init(this)
    }
}