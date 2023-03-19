package com.example.domainproperties.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.*
import com.example.domainproperties.model.PropertyModel
import com.example.domainproperties.model.SearchResult
import com.example.domainproperties.repository.PropertiesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.lang.Exception

class MainViewModel(private val propertiesRepository: PropertiesRepository) : ViewModel() {

    private val propsMutableState: MutableLiveData<PropertyModel?> =
        MutableLiveData<PropertyModel?>()
    val propsImmutableState: LiveData<PropertyModel?>
        get() = propsMutableState

    private val errorMutableState: MutableLiveData<String> = MutableLiveData()
    val errorImmutableState: LiveData<String>
        get() = errorMutableState

    //fetch properties through Repository
    fun fetchProperties(search_mode: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val buyProperty =
                    withContext(Dispatchers.IO) { propertiesRepository.fetchProperties(search_mode) }
                updatePropertyState(buyProperty)
            } catch (e: Exception) {
                updatePropertyState(null)
                updateError(e)
            }
        }
    }

    private fun updateError(e: Exception) {
        errorMutableState.value = e.message.toString()
    }

    private fun updatePropertyState(buyProperty: Response<PropertyModel>?) {
        if (buyProperty?.body() != null) {
            propsMutableState.value = buyProperty.body()
        } else {
            propsMutableState.value = null
        }
    }
}