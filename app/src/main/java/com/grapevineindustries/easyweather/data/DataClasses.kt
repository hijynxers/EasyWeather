package com.grapevineindustries.easyweather.data

data class CurrentWeatherResponse(
    val location: Location,
    val current: Current
) {
    companion object
}

data class ForecastResponse(
    val forecastDay: ForecastDay,
) {
    companion object
}

data class ForecastDay(
    val date: String,
    val day: Day,
    val astro: Astro,
    val hour: Hour
) {
    companion object
}

data class Day(
    val maxtemp_c: Float,
    val maxtemp_f: Float,
    val mintemp_c: Float,
    val mintemp_f: Float,
    val condition: Condition
) {
    companion object
}

data class Astro(
    val sunrise: String,
    val sunset: String,
) {
    companion object
}

data class Hour(
    val time: String,
    val temp_c: Float,
    val temp_f: Float,
    val condition: Condition
) {
    companion object
}

data class Location(
    val name: String,
    val region: String,
    val country: String,
    val lat: Float,
    val lon: Float,
    val tz_id: String,
    val localtime_epoch: Int,
    val localtime: String,
) {
    companion object
}

data class Condition(
    val text: String,
    val icon: String,
    val code: Int
) {
    companion object
}

data class Current(
    val last_updated: String,
    val temp_c: Float,
    val temp_f: Float,
    val is_day: Int,
    val condition: Condition,
    val feelslike_c: Float,
    val feelslike_f: Float,
) {
    companion object
}