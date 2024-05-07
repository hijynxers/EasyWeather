package com.grapevineindustries.easyweather.data

import SPECIAL_KEY
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grapevineindustries.easyweather.data.RetrofitInstance.weatherFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WeatherApi {
    @GET("current.json?key=${SPECIAL_KEY}&q=99501")
    suspend fun fetchCurrentWeather(): CurrentWeatherResponse

    @GET("current.json?hey=${SPECIAL_KEY}&q=99501&days=3&aqi=no&alerts=no")
    suspend fun fetchForecast(): ForecastResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val weatherFactory: WeatherApi by lazy {
        retrofit.create(WeatherApi::class.java)
    }
}

class WeatherRepository(
    private val weatherApi: WeatherApi
) {
    suspend fun fetchCurrentWeather(): CurrentWeatherResponse {
        return weatherApi.fetchCurrentWeather()
    }

    suspend fun fetchForecast(): ForecastResponse {
        return weatherApi.fetchForecast()
    }
}

class HomeViewModel(
    private val repository:WeatherRepository = WeatherRepository(weatherFactory)
) : ViewModel() {

    private val _weather = MutableStateFlow(CurrentWeatherResponse())
    val weather: StateFlow<CurrentWeatherResponse> = _weather

    private val _forecast = MutableStateFlow(ForecastResponse())
    val forecast: StateFlow<ForecastResponse> = _forecast

    fun fetchCurrentWeather() {
        viewModelScope.launch {
            try {
                val cards = repository.fetchCurrentWeather()
                _weather.value = cards
            } catch (e: Exception) {
                val a = e
            }
        }
    }

    fun fetchForecast() {
        viewModelScope.launch {
            try {
                val forecast = repository.fetchForecast()
                _forecast.value = forecast
            } catch (e: Exception) {
                val a = e
            }
        }
    }
}