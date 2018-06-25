package com.example.malar.todolists.viewmodels

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            val mainViewModel = MainViewModel(application)
            return mainViewModel as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}