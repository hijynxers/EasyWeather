package com.grapevineindustries.easyweather.networking

import SPECIAL_KEY
import com.grapevineindustries.easyweather.data.CurrentWeatherResponse
import com.grapevineindustries.easyweather.data.ForecastResponse
import retrofit2.http.GET

interface WeatherApi {
    @GET("current.json?key=${SPECIAL_KEY}&q=99501")
    suspend fun fetchCurrentWeather(): CurrentWeatherResponse

    @GET("forecast.json?key=${SPECIAL_KEY}&q=99501&days=3&aqi=no&alerts=no")
    suspend fun fetchForecast(): ForecastResponse
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