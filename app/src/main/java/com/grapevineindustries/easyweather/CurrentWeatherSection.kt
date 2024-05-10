package com.grapevineindustries.easyweather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CONDITION_ICON
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CONDITION_TEXT
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_HIGH
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_LOW
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CURRENT_TEMP
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.LOCATION_CITY
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.LOCATION_OTHER
import com.grapevineindustries.easyweather.data.CurrentWeatherResponse
import com.grapevineindustries.easyweather.data.degreesF

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CurrentWeatherSection(
    weather: CurrentWeatherResponse,
    highTemp: Float,
    lowTemp: Float
) {
    Column {
        Text(
            modifier = Modifier.testTag(LOCATION_CITY),
            text = weather.location.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            modifier = Modifier.testTag(LOCATION_OTHER),
            text = weather.location.localtime
//            text = "Tuesday, April 12, 4:10 AM"
        )
        Row {
            Column {
                GlideImage(
                    modifier = Modifier.testTag(CONDITION_ICON),
                    model = "https:" + weather.current.condition.icon,
                    contentDescription = null
                )
                Text(
                    modifier = Modifier.testTag(CONDITION_TEXT),
                    text = weather.current.condition.text
                )
            }

            Text(
                modifier = Modifier.testTag(CURRENT_TEMP),
                text = "${weather.current.temp_f}$degreesF"
            )

            Row {
                Text(
                    modifier = Modifier.testTag(CURRENT_HIGH),
                    text = "High: $highTemp$degreesF"
                )
                Text(
                    modifier = Modifier.testTag(CURRENT_LOW),
                    text = "Low: $lowTemp$degreesF"
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