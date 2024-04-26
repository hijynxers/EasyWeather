package com.grapevineindustries.easyweather.data

data class Forecast(
    val icon: String = "",
    val day: String = "",
    val high: Int = 87,
    val low: Int = 57
) {
    companion object
}