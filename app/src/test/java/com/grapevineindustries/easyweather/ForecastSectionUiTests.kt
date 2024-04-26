package com.grapevineindustries.easyweather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_DAY
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_HIGH_LOW
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_ICON
import com.grapevineindustries.easyweather.data.Forecast
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForecastSectionUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val forecasts = listOf(
        Forecast(icon = "", day = "Tuesday", high = 87, low = 57),
        Forecast(icon = "", day = "Wednesday", high = 87, low = 57),
        Forecast(icon = "", day = "Thursday", high = 87, low = 57),
    )

    private fun launchWithCompose() {
        composeTestRule.setContent {
            ForecastSection(forecasts)
        }
    }

    @Test
    fun displayForecastSection() {
        launchWithCompose()

        val icons = composeTestRule.onAllNodesWithTag(FORECAST_ICON)
        val days = composeTestRule.onAllNodesWithTag(FORECAST_DAY)
        val highLows = composeTestRule.onAllNodesWithTag(FORECAST_HIGH_LOW)

        forecasts.forEachIndexed { index, forecast ->
            icons[index].assertIsDisplayed()
            days[index].assertIsDisplayed()
                .assertTextEquals(forecast.day)
            highLows[index].assertIsDisplayed()
                .assertTextEquals("${forecast.high} / ${forecast.low}")
        }
    }
}