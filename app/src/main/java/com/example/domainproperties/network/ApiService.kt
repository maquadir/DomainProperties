package com.example.domainproperties.network

import com.example.domainproperties.model.PropertyModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

//background services
interface ApiService {

    @POST("search")
    suspend fun fetchProperties(
        @Header("contenttype") content_type: String = "application/json",
        @Query("dwelling_types") dwelling_types: String = "[\"Apartment / Unit / Flat\"]",
        @Query("search_mode") search_mode: String
    ): Response<PropertyModel>


    companion object {
        val instance: ApiService by lazy {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://domain-adapter-api.domain.com.au/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(ApiService::class.java)
        }
    }
}
