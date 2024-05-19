package com.grapevineindustries.easyweather.networking

import com.grapevineindustries.easyweather.SPECIAL_KEY
import com.grapevineindustries.easyweather.data.CurrentWeatherResponse
import com.grapevineindustries.easyweather.data.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface WeatherApi {
        @GET("current.json?key=$SPECIAL_KEY")
    suspend fun fetchCurrentWeather(
        @Query("q") code: String
    ): CurrentWeatherResponse

    @GET("forecast.json?key=$SPECIAL_KEY&days=3&aqi=no&alerts=no")
    suspend fun fetchForecast(
        @Query("q") code: String
    ): ForecastResponse
}

class WeatherRepository @Inject constructor(
    private val weatherApi: WeatherApi
) {
    suspend fun fetchCurrentWeather(): CurrentWeatherResponse {
        return weatherApi.fetchCurrentWeather("99501")
    }

    suspend fun fetchForecast(): ForecastResponse {
        return weatherApi.fetchForecast("99501")
    }
}