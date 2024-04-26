package com.grapevineindustries.easyweather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_DAY
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_HIGH_LOW
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_ICON
import com.grapevineindustries.easyweather.data.Forecast

@Composable
fun ForecastSection(forecasts: List<Forecast>) {
    Column{
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "3-Day Forecast")
                Spacer(
                    modifier = Modifier
                        .height(1.dp)
                        .fillMaxWidth()
                        .background(Color.Cyan)
                )
                ForecastRow(forecasts[0])
                ForecastRow(forecasts[1])
                ForecastRow(forecasts[2])
            }

        }
    }
}

@Composable
fun ForecastRow(forecast: Forecast) {
    Row {
        Image(
            modifier = Modifier.testTag(FORECAST_ICON),
            painter = painterResource(id = R.drawable.cloud_24),
            contentDescription = null
        )
        Text(
            modifier = Modifier.testTag(FORECAST_DAY),
            text = forecast.day
        )

        Spacer(modifier = Modifier.width(60.dp))

        Text(
            modifier = Modifier.testTag(FORECAST_HIGH_LOW),
            text = "${forecast.high} / ${forecast.low}"
        )
    }
}

object ForecastSectionTestTags {
    const val FORECAST_ICON = "FORECAST_ICON"
    const val FORECAST_DAY = "FORECAST_DAY"
    const val FORECAST_HIGH_LOW = "FORECAST_HIGH_LOW"
}