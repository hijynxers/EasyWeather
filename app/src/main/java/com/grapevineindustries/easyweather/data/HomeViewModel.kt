package com.grapevineindustries.easyweather.data

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grapevineindustries.easyweather.networking.RetrofitInstance.weatherFactory
import com.grapevineindustries.easyweather.networking.WeatherRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

class HomeViewModel(
    private val repository: WeatherRepository = WeatherRepository(weatherFactory)
) : ViewModel() {
    private var currentWeatherLoaded by Delegates.notNull<Boolean>()
    private var forecastLoaded by Delegates.notNull<Boolean>()

    init {
        currentWeatherLoaded = false
        forecastLoaded = false
    }

    private val _weather = MutableStateFlow(CurrentWeatherResponse())
    val weather: StateFlow<CurrentWeatherResponse> = _weather

    private val _forecast = MutableStateFlow(ForecastResponse())
    val forecast: StateFlow<ForecastResponse> = _forecast


    private val _isFinishedLoading = MutableStateFlow(currentWeatherLoaded && forecastLoaded)
    val isFinishedLoading: StateFlow<Boolean> = _isFinishedLoading

    fun fetchCurrentWeather() {
        viewModelScope.launch {
            try {
                val cards = repository.fetchCurrentWeather()
                _weather.value = cards
                currentWeatherLoaded = true
                _isFinishedLoading.update {
                    currentWeatherLoaded && forecastLoaded
                }
            } catch (e: Exception) {
                Log.e("@@", "fetchCurrentWeather: $e")
            }
        }
    }

    fun fetchForecast() {
        viewModelScope.launch {
            try {
                val forecast = repository.fetchForecast()
                _forecast.value = forecast
                forecastLoaded = true
                _isFinishedLoading.update {
                    currentWeatherLoaded && forecastLoaded
                }
            } catch (e: Exception) {
                Log.e("@@", "fetchForecast: $e")
            }
        }
    }
}