package com.grapevineindustries.easyweather

import com.grapevineindustries.easyweather.data.Astro
import com.grapevineindustries.easyweather.data.Condition
import com.grapevineindustries.easyweather.data.Current
import com.grapevineindustries.easyweather.data.CurrentWeatherResponse
import com.grapevineindustries.easyweather.data.Day
import com.grapevineindustries.easyweather.data.Forecast
import com.grapevineindustries.easyweather.data.ForecastDay
import com.grapevineindustries.easyweather.data.ForecastResponse
import com.grapevineindustries.easyweather.data.Hour
import com.grapevineindustries.easyweather.data.Location
import com.grapevineindustries.easyweather.networking.WeatherApi
import com.grapevineindustries.easyweather.networking.WeatherRepository
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.kotlin.doReturn

val condition = Condition(
    text = "text",
    icon = "icon",
    code = 13
)

val current = Current(
    last_updated = "last_updated",
    temp_c = 12f,
    temp_f = 13f,
    is_day = 14,
    condition = condition,
    feelslike_c = 24f,
    feelslike_f = 25f,
)

val location = Location(
    name = "name",
    region = "region",
    country = "country",
    lat = 15f,
    lon = 20f,
    tz_id = "tz_id",
    localtime_epoch = 1,
    localtime = "localtime",
)

val expectedCurrentWeather = CurrentWeatherResponse(
    location = location,
    current = current
)

val expectedForecast = ForecastResponse(
    location = location,
    current = current,
    forecast = Forecast(
        forecastday = arrayListOf(
            ForecastDay(
                date = "date",
                day = Day(
                    maxtemp_c = 1f,
                    maxtemp_f = 2f,
                    mintemp_c = 3f,
                    mintemp_f = 4f,
                    condition = condition
                ),
                astro = Astro(
                    sunset = "sunset",
                    sunrise = "sunrise"
                ),
                hour = arrayListOf(
                    Hour(
                        time = "time",
                        temp_c = 5f,
                        temp_f = 6f,
                        condition = condition
                    )
                )
            )
        )
    )
)


class WeatherRepositoryTests {

    private lateinit var testObject: WeatherRepository
    private val weatherApi: WeatherApi = mock()

    @Test
    fun fetchCurrentWeather() {
        runTest {
            Mockito.lenient()
                .`when`(
                    weatherApi.fetchCurrentWeather()
                ) doReturn expectedCurrentWeather

            testObject = WeatherRepository(weatherApi)
            val result = testObject.fetchCurrentWeather()
            assert(result == expectedCurrentWeather)
        }
    }

    @Test
    fun fetchForecast() {
        runTest {
            Mockito.lenient()
                .`when`(
                    weatherApi.fetchForecast()
                ) doReturn expectedForecast

            testObject = WeatherRepository(weatherApi)
            val result = testObject.fetchForecast()
            assert(result == expectedForecast)
        }
    }
}