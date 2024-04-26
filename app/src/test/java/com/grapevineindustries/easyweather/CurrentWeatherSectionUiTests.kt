package com.grapevineindustries.easyweather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CONDITION_ICON
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CONDITION_TEXT
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_HIGH
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_LOW
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_TEMP
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.LOCATION_CITY
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.LOCATION_OTHER
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CurrentWeatherSectionUiTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private fun launchWithCompose() {
        composeTestRule.setContent {
            CurrentWeatherSection()
        }
    }

    @Test
    fun displayCurrentWeatherConditions() {
        launchWithCompose()

        composeTestRule.onNodeWithTag(LOCATION_CITY)
            .assertIsDisplayed()
            .assertTextEquals("St. Charles")
        composeTestRule.onNodeWithTag(LOCATION_OTHER)
            .assertIsDisplayed()
            .assertTextEquals("Tuesday, April 12, 4:10 AM")
        composeTestRule.onNodeWithTag(CONDITION_ICON)
            .assertIsDisplayed()
        composeTestRule.onNodeWithTag(CONDITION_TEXT)
            .assertIsDisplayed()
            .assertTextEquals("Cloudy")
        composeTestRule.onNodeWithTag(CURRENT_TEMP)
            .assertIsDisplayed()
            .assertTextEquals("-8\u2109")
        composeTestRule.onNodeWithTag(CURRENT_HIGH)
            .assertIsDisplayed()
            .assertTextEquals("High: 87\u2109")
        composeTestRule.onNodeWithTag(CURRENT_LOW)
            .assertIsDisplayed()
            .assertTextEquals("Low: 57\u2109")
    }
}