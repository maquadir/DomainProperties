package com.example.domainproperties.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.example.domainproperties.model.PropertyModel
import com.example.domainproperties.model.SearchResult
import com.example.domainproperties.repository.PropertiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainViewModel(private val propertiesRepository: PropertiesRepository) : ViewModel() {

    private val propsMutableState: MutableLiveData<PropertyModel?> = MutableLiveData<PropertyModel?>()
    val propsImmutableState: LiveData<List<SearchResult>>
        get() = propsMutableState.map { it?.search_results ?: emptyList()  }

    private val errorMutableState: MutableLiveData<String> = MutableLiveData()
    val errorImmutableState: LiveData<String>
        get() = errorMutableState

    //fetch properties through Repository
    fun fetchProperties(search_mode: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val buyProperty =
                    withContext(Dispatchers.IO) { propertiesRepository.fetchProperties(search_mode) }
                propsMutableState.value = buyProperty.body()
            } catch (e: Exception) {
                propsMutableState.value = null
                errorMutableState.value = e.message.toString()
            }
        }
    }
}