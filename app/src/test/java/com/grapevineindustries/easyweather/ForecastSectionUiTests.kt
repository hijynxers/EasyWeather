package com.grapevineindustries.easyweather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_DAY
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_HIGH
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_ICON
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_LOW
import com.grapevineindustries.easyweather.data.Day
import com.grapevineindustries.easyweather.data.Forecast
import com.grapevineindustries.easyweather.data.ForecastDay
import com.grapevineindustries.easyweather.data.degreesF
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForecastSectionUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val expectedDays = listOf("Monday", "Tuesday", "Wednesday")
    private val forecast = Forecast(
        arrayListOf(
            ForecastDay(
                date = "2024-04-15", //Wednesday
                day = Day(
                    maxtemp_f = 80f,
                    mintemp_c = 40f
                )
            ),
            ForecastDay(
                date = "2024-04-16", //Thursday
                day = Day(
                    maxtemp_f = 81f,
                    mintemp_c = 41f
                )
            ),
            ForecastDay(
                date = "2024-04-17", //Friday
                day = Day(
                    maxtemp_f = 82f,
                    mintemp_c = 42f
                )
            ),
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
        val highs = composeTestRule.onAllNodesWithTag(FORECAST_HIGH)
        val lows = composeTestRule.onAllNodesWithTag(FORECAST_LOW)

        forecast.forecastday.forEachIndexed { index, forecast ->
//            icons[index].assertIsDisplayed()
            days[index].assertIsDisplayed()
                .assertTextEquals(expectedDays[index])
            highs[index].assertIsDisplayed()
                .assertTextEquals("${forecast.day.maxtemp_f} $degreesF")
            lows[index].assertIsDisplayed()
                .assertTextEquals("${forecast.day.mintemp_f} $degreesF")
        }
    }

    @Test
    fun verifyFormatDate() {
        val formattedDate = formatDate("2024-04-15")
        assert("Monday" == formattedDate)
    }
}