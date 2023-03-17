package com.example.domainproperties.repository

import com.example.domainproperties.network.ApiService

//repository pattern to fetch from data sources
class PropertiesRepository(private val apiService: ApiService) {

    //get properties
    suspend fun fetchProperties(search_mode: String) =
        apiService.fetchProperties(search_mode = search_mode)
}
