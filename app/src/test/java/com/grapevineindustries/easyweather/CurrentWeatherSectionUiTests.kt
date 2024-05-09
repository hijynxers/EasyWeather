package com.grapevineindustries.easyweather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CONDITION_TEXT
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_HIGH
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_LOW
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_TEMP
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.LOCATION_CITY
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.LOCATION_OTHER
import com.grapevineindustries.easyweather.data.Condition
import com.grapevineindustries.easyweather.data.Current
import com.grapevineindustries.easyweather.data.CurrentWeatherResponse
import com.grapevineindustries.easyweather.data.Location
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CurrentWeatherSectionUiTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val expectedWeather = CurrentWeatherResponse(
        Location(
            name = "name",
            localtime = "localtime"
        ),
        Current(
            temp_f = 50f,
            condition = Condition(
                text = "Cloudy",
                icon = "//cdn.weatherapi.com/weather/64x64/day/302.png"
            )
        )
    )
    private val expectedHighTemp = 90f
    private val expectedLowTemp = 50f

    private fun launchWithCompose() {
        composeTestRule.setContent {
            CurrentWeatherSection(
                weather = expectedWeather,
                highTemp = expectedHighTemp,
                lowTemp = expectedLowTemp
            )
        }
    }

    @Test
    fun displayCurrentWeatherConditions() {
        launchWithCompose()

        composeTestRule.onNodeWithTag(LOCATION_CITY)
            .assertIsDisplayed()
            .assertTextEquals(expectedWeather.location.name)
        composeTestRule.onNodeWithTag(LOCATION_OTHER)
            .assertIsDisplayed()
            .assertTextEquals(expectedWeather.location.localtime)
//        composeTestRule.onNodeWithTag(CONDITION_ICON)
//            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(CONDITION_TEXT)
            .assertIsDisplayed()
            .assertTextEquals(expectedWeather.current.condition.text)
        composeTestRule.onNodeWithTag(CURRENT_TEMP)
            .assertIsDisplayed()
            .assertTextEquals("${expectedWeather.current.temp_f}\u2109")
        composeTestRule.onNodeWithTag(CURRENT_HIGH)
            .assertIsDisplayed()
            .assertTextEquals("High: ${expectedHighTemp}\u2109")
        composeTestRule.onNodeWithTag(CURRENT_LOW)
            .assertIsDisplayed()
            .assertTextEquals("Low: ${expectedLowTemp}\u2109")
    }
}