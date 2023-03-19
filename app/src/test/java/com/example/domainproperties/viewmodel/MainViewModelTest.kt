package com.example.domainproperties.viewmodel

import android.media.DeniedByServerException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.domainproperties.UtilsTest
import com.example.domainproperties.model.*
import com.example.domainproperties.network.ApiService
import com.example.domainproperties.repository.PropertiesRepository
import com.google.common.truth.ExpectFailure.assertThat
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.*
import retrofit2.Response
import java.net.HttpURLConnection
import kotlin.Metadata

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var apiService: ApiService
    private lateinit var repository: PropertiesRepository
    private lateinit var viewModel: MainViewModel
    private lateinit var buyProperties: PropertyModel
    private lateinit var mockWebServer: MockWebServer
    private lateinit var propertiesObserver: Observer<List<SearchResult>?>
    private lateinit var errorObserver: Observer<String>

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
        apiService = mock()
        repository = PropertiesRepository(apiService)
        viewModel = MainViewModel(repository)
        buyProperties = mock()
        propertiesObserver = mock()
        errorObserver = mock()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    // Reusable JUnit4 TestRule to override the Main dispatcher
    class MainDispatcherRule @OptIn(ExperimentalCoroutinesApi::class) constructor(
        private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
    ) : TestWatcher() {
        @OptIn(ExperimentalCoroutinesApi::class)
        override fun starting(description: Description) {
            Dispatchers.setMain(testDispatcher)
        }

        @OptIn(ExperimentalCoroutinesApi::class)
        override fun finished(description: Description) {
            Dispatchers.resetMain()
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for success resource, buy property data must be fetched and observed`() = runTest {

        whenever(repository.fetchProperties(search_mode = "buy"))
            .thenReturn(Response.success(UtilsTest.results))

        viewModel.fetchProperties("buy") // Uses testDispatcher, runs its coroutine eagerly
        viewModel.propsImmutableState.observeForever {
            assertEquals(it?.search_results, UtilsTest.results.search_results)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for success resource, rent property data must be fetched and observed`() = runTest {

        whenever(repository.fetchProperties(search_mode = "rent"))
            .thenReturn(Response.success(UtilsTest.results))

        viewModel.fetchProperties("rent") // Uses testDispatcher, runs its coroutine eagerly
        viewModel.propsImmutableState.observeForever {
            assertEquals(it?.search_results, UtilsTest.results.search_results)
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for success resource when no data exists in server, buy property data should be null and observed`() = runTest {

        whenever(repository.fetchProperties(search_mode = "buy"))
            .thenReturn(Response.success(null))

        viewModel.fetchProperties("buy") // Uses testDispatcher, runs its coroutine eagerly
        viewModel.propsImmutableState.observeForever {
            assertEquals(it, null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for success resource when no data exists in server, rent property data should be null and observed`() = runTest {

        whenever(repository.fetchProperties(search_mode = "rent"))
            .thenReturn(Response.success(null))

        viewModel.fetchProperties("rent") // Uses testDispatcher, runs its coroutine eagerly
        viewModel.propsImmutableState.observeForever {
            assertEquals(it, null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for server error, fetch for buy data must return with null data`() = runTest {
        whenever(repository.fetchProperties(search_mode = "buy"))
            .thenReturn(
                Response.error(
                    HttpURLConnection.HTTP_INTERNAL_ERROR, "{\"key\":[\"somestuff\"]}"
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
            )

        viewModel.fetchProperties("buy") // Uses testDispatcher, runs its coroutine eagerly
        viewModel.propsImmutableState.observeForever {
            assertEquals(it, null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for server error, fetch for rent data must return with null data`() = runTest {
        whenever(repository.fetchProperties(search_mode = "rent"))
            .thenReturn(
                Response.error(
                    HttpURLConnection.HTTP_INTERNAL_ERROR, "{\"key\":[\"somestuff\"]}"
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
            )

        viewModel.fetchProperties("rent") // Uses testDispatcher, runs its coroutine eagerly
        viewModel.propsImmutableState.observeForever {
            assertEquals(it, null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for server error, fetch for buy data must return with http code 404 and null data`() = runTest {
        whenever(repository.fetchProperties(search_mode = "buy"))
            .thenReturn(
                Response.error(
                    HttpURLConnection.HTTP_NOT_FOUND, "{\"key\":[\"somestuff\"]}"
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
            )

        viewModel.fetchProperties("buy") // Uses testDispatcher, runs its coroutine eagerly
        viewModel.propsImmutableState.observeForever {
            assertEquals(it, null)
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for server error, fetch for rent data must return with http code 404 and null data`() = runTest {
        whenever(repository.fetchProperties(search_mode = "buy"))
            .thenReturn(
                Response.error(
                    HttpURLConnection.HTTP_NOT_FOUND, "{\"key\":[\"somestuff\"]}"
                        .toResponseBody("application/json".toMediaTypeOrNull())
                )
            )

        viewModel.fetchProperties("buy") // Uses testDispatcher, runs its coroutine eagerly
        viewModel.propsImmutableState.observeForever {
            assertEquals(it, null)
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `for server error, fetch for buy data throws a denied server exception`() = runTest {
        whenever(repository.fetchProperties(search_mode = "buy"))
            .then {
                throw Exception("Access Denied")
            }

        viewModel.fetchProperties("buy") // Uses testDispatcher, runs its coroutine eagerly
        viewModel.propsImmutableState.observeForever {
            assertEquals(it, null)
        }
        viewModel.errorImmutableState.observeForever {
            assertEquals(it, "Access Denied")
        }
    }
}