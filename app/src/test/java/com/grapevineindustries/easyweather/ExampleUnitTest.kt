package com.grapevineindustries.easyweather

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class ExampleUnitTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displayScreen() {
        composeTestRule.setContent {
            HomeScreen()
        }

        composeTestRule.onNodeWithTag("Stuff")
            .assertIsDisplayed()
    }
}