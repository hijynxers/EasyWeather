package com.grapevineindustries.easyweather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_CARD
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_DAY
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_HIGH
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_LOW
import com.grapevineindustries.easyweather.HourTestTags.HOUR_CONDITION
import com.grapevineindustries.easyweather.HourTestTags.HOUR_TEMP
import com.grapevineindustries.easyweather.HourTestTags.HOUR_TIME
import com.grapevineindustries.easyweather.data.Condition
import com.grapevineindustries.easyweather.data.Day
import com.grapevineindustries.easyweather.data.Forecast
import com.grapevineindustries.easyweather.data.ForecastDay
import com.grapevineindustries.easyweather.data.Hour
import com.grapevineindustries.easyweather.data.degreesF
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ForecastSectionUiTests {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val expectedDays = listOf("Mon", "Tues", "Wed")
    private val expectedHours = listOf("12AM", "1AM", "2AM", "12PM", "1PM")
    private val expectedTemps = listOf("1.0", "2.0", "3.0", "4.0", "5.0")
    private val expectedConditions = listOf("Cloudy", "Cloudy", "Cloudy", "Cloudy", "Cloudy")
    private val forecast = Forecast(
        arrayListOf(
            ForecastDay(
                date = "2024-04-15",
                day = Day(
                    maxtemp_f = 80f,
                    mintemp_c = 40f
                ),
                hour = arrayListOf(
                    Hour(
                        time = "2024-05-07 00:00", temp_f = 1f,
                        condition = Condition(text = "Cloudy")
                    ),
                    Hour(
                        time = "2024-05-07 01:00", temp_f = 2f,
                        condition = Condition(text = "Cloudy")
                    ),
                    Hour(
                        time = "2024-05-07 02:00", temp_f = 3f,
                        condition = Condition(text = "Cloudy")
                    ),
                    Hour(
                        time = "2024-05-07 12:00", temp_f = 4f,
                        condition = Condition(text = "Cloudy")
                    ),
                    Hour(
                        time = "2024-05-07 13:00", temp_f = 5f,
                        condition = Condition(text = "Cloudy")
                    ),
                    Hour(
                        time = "2024-05-07 14:00", temp_f = 6f,
                        condition = Condition(text = "Cloudy")
                    ),
                )
            ),
            ForecastDay(
                date = "2024-04-16",
                day = Day(
                    maxtemp_f = 81f,
                    mintemp_c = 41f
                ),
                hour = arrayListOf(
                    Hour(
                        time = "2024-05-07 00:00", temp_f = 1f,
                        condition = Condition(text = "Rainy")
                    ),
                    Hour(
                        time = "2024-05-07 01:00", temp_f = 2f,
                        condition = Condition(text = "Rainy")
                    ),
                    Hour(
                        time = "2024-05-07 02:00", temp_f = 3f,
                        condition = Condition(text = "Rainy")
                    ),
                    Hour(
                        time = "2024-05-07 12:00", temp_f = 4f,
                        condition = Condition(text = "Rainy")
                    ),
                    Hour(
                        time = "2024-05-07 13:00", temp_f = 5f,
                        condition = Condition(text = "Rainy")
                    ),
                    Hour(
                        time = "2024-05-07 14:00", temp_f = 6f,
                        condition = Condition(text = "Rainy")
                    ),
                )
            ),
            ForecastDay(
                date = "2024-04-17",
                day = Day(
                    maxtemp_f = 82f,
                    mintemp_c = 42f
                ),
                hour = arrayListOf(
                    Hour(
                        time = "2024-05-07 00:00", temp_f = 1f,
                        condition = Condition(text = "Sunny")
                    ),
                    Hour(
                        time = "2024-05-07 01:00", temp_f = 2f,
                        condition = Condition(text = "Sunny")
                    ),
                    Hour(
                        time = "2024-05-07 02:00", temp_f = 3f,
                        condition = Condition(text = "Sunny")
                    ),
                    Hour(
                        time = "2024-05-07 12:00", temp_f = 4f,
                        condition = Condition(text = "Sunny")
                    ),
                    Hour(
                        time = "2024-05-07 13:00", temp_f = 5f,
                        condition = Condition(text = "Sunny")
                    ),
                    Hour(
                        time = "2024-05-07 14:00", temp_f = 6f,
                        condition = Condition(text = "Sunny")
                    ),
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

        val days = composeTestRule.onAllNodesWithTag(FORECAST_DAY, useUnmergedTree = true)
        val highs = composeTestRule.onAllNodesWithTag(FORECAST_HIGH, useUnmergedTree = true)
        val lows = composeTestRule.onAllNodesWithTag(FORECAST_LOW, useUnmergedTree = true)

        forecast.forecastday.forEachIndexed { index, forecast ->
            days[index].assertIsDisplayed()
                .assertTextEquals(expectedDays[index])
            highs[index].assertIsDisplayed()
                .assertTextEquals("${forecast.day.maxtemp_f} $degreesF")
            lows[index].assertIsDisplayed()
                .assertTextEquals("${forecast.day.mintemp_f} $degreesF")
        }
    }

    @Test
    fun displayHourSection() {
        launchWithCompose()

        val time = composeTestRule.onAllNodesWithTag(HOUR_TIME)
        val temp = composeTestRule.onAllNodesWithTag(HOUR_TEMP)
        val condition = composeTestRule.onAllNodesWithTag(HOUR_CONDITION)

        for (index in expectedHours.indices) {
            time[index].assertIsDisplayed()
                .assertTextEquals(expectedHours[index])
            temp[index].assertIsDisplayed()
                .assertTextEquals("${expectedTemps[index]} $degreesF")
            condition[index].assertIsDisplayed()
                .assertTextEquals(expectedConditions[index])
        }
    }

    @Test
    fun clickHourCards() {
        launchWithCompose()

        composeTestRule.onAllNodesWithTag(HOUR_CONDITION).onFirst()
            .assertTextEquals("Cloudy")

        composeTestRule.onNodeWithTag("${FORECAST_CARD}1")
            .performClick()
        composeTestRule.onAllNodesWithTag(HOUR_CONDITION).onFirst()
            .assertTextEquals("Rainy")

        composeTestRule.onNodeWithTag("${FORECAST_CARD}2")
            .performClick()
        composeTestRule.onAllNodesWithTag(HOUR_CONDITION).onFirst()
            .assertTextEquals("Sunny")

        composeTestRule.onNodeWithTag("${FORECAST_CARD}0")
            .performClick()
        composeTestRule.onAllNodesWithTag(HOUR_CONDITION).onFirst()
            .assertTextEquals("Cloudy")
    }

    @Test
    fun verifyFormatDate() {
        assert("Mon" == formatDate("2024-04-15"))
        assert("Tues" == formatDate("2024-04-16"))
        assert("Wed" == formatDate("2024-04-17"))
        assert("Thurs" == formatDate("2024-04-18"))
        assert("Fri" == formatDate("2024-04-19"))
        assert("Sat" == formatDate("2024-04-20"))
        assert("Sun" == formatDate("2024-04-21"))
        assert("Monday 4/15" == formatDate("2024-04-15", true))
        assert("Tuesday 4/16" == formatDate("2024-04-16", true))
        assert("Wednesday 4/17" == formatDate("2024-04-17", true))
        assert("Thursday 4/18" == formatDate("2024-04-18", true))
        assert("Friday 4/19" == formatDate("2024-04-19", true))
        assert("Saturday 4/20" == formatDate("2024-04-20", true))
        assert("Sunday 4/21" == formatDate("2024-04-21", true))
    }

    @Test
    fun verifyFormatTime() {
        val formattedTime1 = formatTime("2024-04-15 00:00")
        assert("12AM" == formattedTime1)

        val formattedTime2 = formatTime("2024-04-15 05:00")
        assert("5AM" == formattedTime2)

        val formattedTime3 = formatTime("2024-04-15 12:00")
        assert("12PM" == formattedTime3)

        val formattedTime4 = formatTime("2024-04-15 15:00")
        assert("3PM" == formattedTime4)
    }
}