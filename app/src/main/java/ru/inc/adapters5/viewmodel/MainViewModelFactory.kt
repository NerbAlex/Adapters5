package ru.inc.adapters5.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val interactor: MainInteractor) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainViewModel::class.java)) MainViewModel(interactor) as T
        else throw IllegalArgumentException("ViewModel: [$modelClass] class not found")
    }
}