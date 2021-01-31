package com.buchi.listdetail.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.buchi.listdetail.data.repository.MainRepository
import com.buchi.listdetail.data.repository.MainRepositoryImpl
import com.buchi.listdetail.presentation.MainViewModel

class MainViewModelFactory(private val repository: MainRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}