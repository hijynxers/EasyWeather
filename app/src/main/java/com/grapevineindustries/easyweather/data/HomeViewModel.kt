package com.grapevineindustries.easyweather.data

import SPECIAL_KEY
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.grapevineindustries.easyweather.data.RetrofitInstance.weatherFactory
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import kotlin.properties.Delegates

interface WeatherApi {
    @GET("current.json?key=${SPECIAL_KEY}&q=99501")
    suspend fun fetchCurrentWeather(): CurrentWeatherResponse

    @GET("forecast.json?key=${SPECIAL_KEY}&q=99501&days=3&aqi=no&alerts=no")
    suspend fun fetchForecast(): ForecastResponse
}

object RetrofitInstance {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val OK_HTTP_CLIENT: OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(logging)
            .build()
    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OK_HTTP_CLIENT)
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
                val a = e
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