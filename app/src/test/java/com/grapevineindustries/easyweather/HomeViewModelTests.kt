package com.grapevineindustries.easyweather

import com.grapevineindustries.easyweather.data.HomeViewModel
import com.grapevineindustries.easyweather.networking.WeatherRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}

class HomeViewModelTests {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val weatherRepository: WeatherRepository = mock()
    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setup() {
        homeViewModel = HomeViewModel(weatherRepository)
    }

    @Test
    fun fetchCurrentWeatherData() = runTest {
        Mockito.lenient()
            .`when`(
                weatherRepository.fetchCurrentWeather()
            ) doReturn expectedCurrentWeather

        homeViewModel.fetchCurrentWeather()
        assert(expectedCurrentWeather == homeViewModel.weather.value)
    }

    @Test
    fun fetchForecastData() = runTest {
        Mockito.lenient()
            .`when`(
                weatherRepository.fetchForecast()
            ) doReturn expectedForecast

        homeViewModel.fetchForecast()
        assert(expectedForecast == homeViewModel.forecast.value)
    }
}