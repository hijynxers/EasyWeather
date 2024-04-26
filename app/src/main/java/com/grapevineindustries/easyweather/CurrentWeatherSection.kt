package com.grapevineindustries.easyweather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CONDITION_ICON
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CONDITION_TEXT
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_HIGH
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_LOW
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_TEMP
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.LOCATION_CITY
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.LOCATION_OTHER

@Composable
fun CurrentWeatherSection() {
    Column {
        Text(
            modifier = Modifier.testTag(LOCATION_CITY),
            text = "St. Charles",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            modifier = Modifier.testTag(LOCATION_OTHER),
            text = "Tuesday, April 12, 4:10 AM"
        )
        Row {
            Column {
                Image( // condition:icon
                    modifier = Modifier.testTag(CONDITION_ICON),
                    painter = painterResource(id = R.drawable.cloud_24),
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.testTag(CONDITION_TEXT),
                    text = "Cloudy" // condition:text
                )
            }

            Text(  // temp_f
                modifier = Modifier.testTag(CURRENT_TEMP),
                text = "-8\u2109"
            )

            Row {
                Text(
                    modifier = Modifier.testTag(CURRENT_HIGH),
                    text = "High: 87\u2109"
                )
                Text(
                    modifier = Modifier.testTag(CURRENT_LOW),
                    text = "Low: 57\u2109"
                )
            }
        }
    }
}

object CurrentWeatherSectionTestTags {
    const val LOCATION_CITY = "CURRENT_WEATHER_LOCATION_CITY"
    const val LOCATION_OTHER = "CURRENT_WEATHER_LOCATION_OTHER"
    const val CONDITION_ICON = "CURRENT_WEATHER_CONDITION_ICON"
    const val CONDITION_TEXT = "CURRENT_WEATHER_CONDITION_TEXT"
    const val CURRENT_TEMP = "CURRENT_WEATHER_CURRENT_TEMP"
    const val CURRENT_HIGH = "CURRENT_WEATHER_CURRENT_HIGH"
    const val CURRENT_LOW = "CURRENT_WEATHER_CURRENT_LOW"
}