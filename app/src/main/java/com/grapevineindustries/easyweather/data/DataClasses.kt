package com.grapevineindustries.easyweather.data

data class CurrentWeatherResponse(
    val location: Location = Location(),
    val current: Current = Current()
) {
    companion object
}

data class ForecastResponse(
    val location: Location = Location(),
    val current: Current = Current(),
    val forecast: Forecast = Forecast()
)

data class Forecast(
    val forecastday: ArrayList<ForecastDay> = arrayListOf(),
) {
    companion object
}

data class ForecastDay(
    val date: String = "",
    val day: Day = Day(),
    val astro: Astro = Astro(),
    val hour: ArrayList<Hour> = arrayListOf()
) {
    companion object
}

data class Day(
    val maxtemp_c: Float = -1f,
    val maxtemp_f: Float = -1f,
    val mintemp_c: Float = -1f,
    val mintemp_f: Float = -1f,
    val condition: Condition = Condition()
) {
    companion object
}

data class Astro(
    val sunrise: String = "",
    val sunset: String = "",
) {
    companion object
}

data class Hour(
    val time: String = "",
    val temp_c: Float = -1f,
    val temp_f: Float = -1f,
    val condition: Condition = Condition()
) {
    companion object
}

data class Location(
    val name: String = "",
    val region: String = "",
    val country: String = "",
    val lat: Float = -1f,
    val lon: Float = -1f,
    val tz_id: String = "",
    val localtime_epoch: Int = 0,
    val localtime: String = "",
) {
    companion object
}

data class Condition(
    val text: String = "",
    val icon: String = "",
    val code: Int = -1
) {
    companion object
}

data class Current(
    val last_updated: String = "",
    val temp_c: Float = -1f,
    val temp_f: Float = -1f,
    val is_day: Int = 1,
    val condition: Condition = Condition(),
    val feelslike_c: Float = -1f,
    val feelslike_f: Float = -1f,
) {
    companion object
}