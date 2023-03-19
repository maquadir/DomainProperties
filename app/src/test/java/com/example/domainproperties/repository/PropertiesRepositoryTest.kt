package com.example.domainproperties.repository

import androidx.annotation.RawRes
import com.example.domainproperties.UtilsTest
import com.example.domainproperties.model.PropertyModel
import com.example.domainproperties.network.ApiService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response
import java.net.HttpURLConnection

internal class PropertiesRepositoryTest {

    private lateinit var propertiesRepository: PropertiesRepository
    private lateinit var apiService: ApiService
    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiService = mock()
        propertiesRepository = PropertiesRepository(apiService)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchBuyPropertiesFromApi() = runTest {
        whenever(apiService.fetchProperties(search_mode = "buy"))
            .thenReturn(Response.success(UtilsTest.results))
        val response = propertiesRepository.fetchProperties("buy")
        assertEquals(UtilsTest.results, response.body())
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun fetchRentPropertiesFromApi() = runTest {
        whenever(apiService.fetchProperties(search_mode = "rent"))
            .thenReturn(Response.success(UtilsTest.results))
        val response = propertiesRepository.fetchProperties("rent")
        assertEquals(UtilsTest.results, response.body())
    }

    @Test
    fun `for no buy properties, repository must return empty with http code 200`() = runTest {
        whenever(apiService.fetchProperties(search_mode = "buy"))
            .thenReturn(Response.success(UtilsTest.emptyResults))

        val response = propertiesRepository.fetchProperties("buy")
        assertEquals(response.body()?.search_results?.size, 0)
    }

    @Test
    fun `for no rent properties, repository must return empty with http code 200`() = runTest {
        whenever(apiService.fetchProperties(search_mode = "rent"))
            .thenReturn(Response.success(UtilsTest.emptyResults))

        val response = propertiesRepository.fetchProperties("rent")
        assertEquals(response.body()?.search_results?.size, 0)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for multiple buy properties, repository must return all properties with http code 200`() = runTest {
        whenever(apiService.fetchProperties(search_mode = "buy"))
            .thenReturn(Response.success(UtilsTest.results))

        val response = propertiesRepository.fetchProperties(search_mode = "buy")
        val properties = UtilsTest.results.search_results
        assertEquals(response.body()?.search_results, properties)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for multiple rent properties, repository must return all properties with http code 200`() = runTest {
        whenever(apiService.fetchProperties(search_mode = "rent"))
            .thenReturn(Response.success(UtilsTest.results))

        val response = propertiesRepository.fetchProperties(search_mode = "rent")
        val properties = UtilsTest.results.search_results
        assertEquals(response.body()?.search_results, properties)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for server error, repository for buy must return with http code 5xx`() = runTest {
        whenever(apiService.fetchProperties(search_mode = "buy"))
            .thenReturn(Response.error(HttpURLConnection.HTTP_INTERNAL_ERROR, "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())))

        val response = propertiesRepository.fetchProperties(search_mode = "buy")
        assertEquals(response.code(), HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for server error, repository for rent must return with http code 5xx`() = runTest {
        whenever(apiService.fetchProperties(search_mode = "rent"))
            .thenReturn(Response.error(HttpURLConnection.HTTP_INTERNAL_ERROR, "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())))

        val response = propertiesRepository.fetchProperties(search_mode = "rent")
        assertEquals(response.code(), HttpURLConnection.HTTP_INTERNAL_ERROR)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for server error, repository for buy must return server error message with http code 5xx`() = runTest {
        whenever(apiService.fetchProperties(search_mode = "buy"))
            .thenReturn(Response.error(HttpURLConnection.HTTP_INTERNAL_ERROR, "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())))

        val response = propertiesRepository.fetchProperties(search_mode = "buy")
        assertEquals(response.code(), HttpURLConnection.HTTP_INTERNAL_ERROR)
        assertEquals(response.message(), "Response.error()")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for server error, repository for rent must return server error message with http code 5xx`() = runTest {
        whenever(apiService.fetchProperties(search_mode = "rent"))
            .thenReturn(Response.error(HttpURLConnection.HTTP_INTERNAL_ERROR, "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())))

        val response = propertiesRepository.fetchProperties(search_mode = "rent")
        assertEquals(response.code(), HttpURLConnection.HTTP_INTERNAL_ERROR)
        assertEquals(response.message(), "Response.error()")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for properties not available, repository for buy must return with http code 404 and null properties`() = runTest {
        whenever(apiService.fetchProperties(search_mode = "buy"))
            .thenReturn(Response.error(HttpURLConnection.HTTP_NOT_FOUND, "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())))

        val response = propertiesRepository.fetchProperties(search_mode = "buy")
        assertEquals(response.code(), HttpURLConnection.HTTP_NOT_FOUND)
        assertEquals(response.body(), null)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for properties not available, repository for rent must return with http code 404 and null properties`() = runTest {
        whenever(apiService.fetchProperties(search_mode = "rent"))
            .thenReturn(Response.error(HttpURLConnection.HTTP_NOT_FOUND, "{\"key\":[\"somestuff\"]}"
                .toResponseBody("application/json".toMediaTypeOrNull())))

        val response = propertiesRepository.fetchProperties(search_mode = "rent")
        assertEquals(response.code(), HttpURLConnection.HTTP_NOT_FOUND)
        assertEquals(response.body(), null)
    }


}