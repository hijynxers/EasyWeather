package com.grapevineindustries.easyweather

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.grapevineindustries.easyweather.CurrentWeatherSectionTestTags.CONDITION_ICON
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
import com.grapevineindustries.easyweather.data.degreesF
import com.grapevineindustries.easyweather.ui.theme.EasyWeatherTheme

@Preview(showSystemUi = true)
@Composable
fun CurrentWeatherSectionPreview() {
    EasyWeatherTheme {
        CurrentWeatherSection(
            weather = CurrentWeatherResponse(
                location = Location(
                    name = "Anchorage",
                    localtime = "2024-04-15 21:58"
                ),
                current = Current(
                    temp_f = 37.4f,
                    condition = Condition(
                        text = "Overcast",
                        icon = "//cdn.weatherapi.com/weather/64x64/day/302.png"
                    )
                )
            ),
            highTemp = 37.7f,
            lowTemp = 33.6f,
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun CurrentWeatherSection(
    weather: CurrentWeatherResponse,
    highTemp: Float,
    lowTemp: Float
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.testTag(LOCATION_CITY),
            text = weather.location.name,
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            modifier = Modifier.testTag(LOCATION_OTHER),
            text = formatDate(weather.location.localtime, fullDay = true)
        )

        GlideImage(
            modifier = Modifier
                .testTag(CONDITION_ICON)
                .size(100.dp),
            model = "https:" + weather.current.condition.icon,
            contentDescription = null,
        )

        Text(
            modifier = Modifier.testTag(CONDITION_TEXT),
            text = weather.current.condition.text
        )

        Text(
            modifier = Modifier.testTag(CURRENT_TEMP),
            text = "${weather.current.temp_f} $degreesF",
            style = MaterialTheme.typography.headlineSmall
        )

        Row {
            Text(
                modifier = Modifier.testTag(CURRENT_HIGH),
                text = "High: $highTemp $degreesF"
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                modifier = Modifier.testTag(CURRENT_LOW),
                text = "Low: $lowTemp $degreesF"
            )
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