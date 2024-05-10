package com.grapevineindustries.easyweather

import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.grapevineindustries.easyweather.HomeScreenTestTags.WEATHER_API_LINK
import com.grapevineindustries.easyweather.data.CurrentWeatherResponse
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeScreenUiTests {
    lateinit var uriHandler: UriHandler

    @get:Rule
    val composeTestRule = createComposeRule()

    private fun launchWithCompose() {
        composeTestRule.setContent {
            uriHandler = LocalUriHandler.current

            HomeScreenContent(
                current = CurrentWeatherResponse(),
                forecast = expectedForecast
            )
        }
    }

    @Test
    fun weatherApiClickOpensSite() {
        launchWithCompose()

        composeTestRule.onNodeWithTag(WEATHER_API_LINK)
            .assertIsDisplayed()
            .assertTextEquals("Powered by ", "WeatherApi.com")
            .performClick()
    }
}