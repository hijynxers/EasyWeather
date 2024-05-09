package com.grapevineindustries.easyweather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_DAY
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_HIGH_LOW
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_ICON
import com.grapevineindustries.easyweather.data.Forecast
import com.grapevineindustries.easyweather.data.ForecastDay
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForecastSectionUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val forecast = Forecast(
        arrayListOf(
            ForecastDay(date = "2024-04-15"),
            ForecastDay(date = "2024-04-16"),
            ForecastDay(date = "2024-04-17"),
        )
    )

    private fun launchWithCompose() {
        composeTestRule.setContent {
            ForecastSection(forecast)
        }
    }

    @Test
    fun displayForecastSection() {
        launchWithCompose()

        val icons = composeTestRule.onAllNodesWithTag(FORECAST_ICON)
        val days = composeTestRule.onAllNodesWithTag(FORECAST_DAY)
        val highLows = composeTestRule.onAllNodesWithTag(FORECAST_HIGH_LOW)

        forecast.forecastday.forEachIndexed { index, forecast ->
            icons[index].assertIsDisplayed()
            days[index].assertIsDisplayed()
                .assertTextEquals(forecast.date)
            highLows[index].assertIsDisplayed()
                .assertTextEquals("${forecast.day.maxtemp_f} / ${forecast.day.mintemp_f}")
        }
    }
}