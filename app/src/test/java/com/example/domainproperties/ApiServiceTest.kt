package com.example.domainproperties

import com.example.domainproperties.network.ApiService
import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.Assert.assertEquals
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiServiceTestUsingMockWebServer {

    private lateinit var mockWebServer: MockWebServer

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val apiService by lazy {
        retrofit.create(ApiService::class.java)
    }


    @Before
    fun setUp() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkBuyPropertiesApiCall() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(Gson().toJson(UtilsTest.results))
                .setResponseCode(200)
        )

        apiService.fetchProperties(search_mode = "buy")
        assertEquals(
            "/search?dwelling_types=%5B%22Apartment%20%2F%20Unit%20%2F%20Flat%22%5D&search_mode=buy",
            mockWebServer.takeRequest().path
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun checkRentPropertiesApiCall() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(Gson().toJson(UtilsTest.results))
                .setResponseCode(200)
        )

        apiService.fetchProperties(search_mode = "rent")
        assertEquals(
            "/search?dwelling_types=%5B%22Apartment%20%2F%20Unit%20%2F%20Flat%22%5D&search_mode=rent",
            mockWebServer.takeRequest().path
        )
    }



    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for multiple buy properties, api must return all properties with http code 200`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(Gson().toJson(UtilsTest.results))
                .setResponseCode(200)
        )

        val response = apiService.fetchProperties(search_mode = "buy")
        val properties = UtilsTest.results.search_results
        assertEquals(response.body()?.search_results, properties)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for multiple rent properties, api must return all properties with http code 200`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setBody(Gson().toJson(UtilsTest.results))
                .setResponseCode(200)
        )

        val response = apiService.fetchProperties(search_mode = "rent")
        val properties = UtilsTest.results.search_results
        assertEquals(response.body()?.search_results, properties)
    }

}