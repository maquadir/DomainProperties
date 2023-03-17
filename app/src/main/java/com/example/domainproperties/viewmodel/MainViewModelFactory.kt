package com.example.domainproperties.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domainproperties.repository.PropertiesRepository

//viewmodel factory for creating viewmodel with dependencies
class MainViewModelFactory(
    private val propertiesRepository: PropertiesRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(propertiesRepository) as T
    }
}