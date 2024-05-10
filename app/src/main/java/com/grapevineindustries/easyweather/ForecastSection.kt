package com.grapevineindustries.easyweather

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_DAY
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_HIGH
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_ICON
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_LOW
import com.grapevineindustries.easyweather.data.Forecast
import com.grapevineindustries.easyweather.data.ForecastDay
import com.grapevineindustries.easyweather.data.degreesF
import com.grapevineindustries.easyweather.ui.theme.EasyWeatherTheme
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Calendar

@Preview
@Composable
fun ForecastSectionPreview() {
    EasyWeatherTheme {
        ForecastSection(
            forecast = Forecast(
                forecastday = arrayListOf(
                    ForecastDay("2024-04-15"),
                    ForecastDay("2024-04-16"),
                    ForecastDay("2024-04-17"),
                )
            )
        )
    }
}

@Composable
fun ForecastSection(forecast: Forecast) {
    Column {
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
                        .background(MaterialTheme.colorScheme.outline)
                )
                forecast.forecastday.forEach { item ->
                    ForecastRow(forecast = item)
                }
            }

        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ForecastRow(forecast: ForecastDay) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        GlideImage(
            modifier = Modifier.testTag(FORECAST_ICON),
            model = "https:" + forecast.day.condition.icon,
            contentDescription = null
        )

        Text(
            modifier = Modifier
                .testTag(FORECAST_DAY)
                .weight(1f),
            text = formatDate(forecast.date)
        )

        Spacer(modifier = Modifier.width(60.dp))

        Row {

            Text(
                modifier = Modifier.testTag(FORECAST_HIGH),
                text = "${forecast.day.maxtemp_f} $degreesF"
            )
            Text(
                text = " / "
            )
            Text(
                modifier = Modifier.testTag(FORECAST_LOW),
                text = "${forecast.day.mintemp_f} $degreesF"
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun formatDate(date: String): String {
    val format = SimpleDateFormat("yyyy-MM-dd")
    try {
        val cal: Calendar = Calendar.getInstance()
        format.parse(date)?.let { cal.setTime(it) }
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        return when (dayOfWeek) {
            1 -> "Sunday"
            2 -> "Monday"
            3 -> "Tuesday"
            4 -> "Wednesday"
            5 -> "Thursday"
            6 -> "Friday"
            7 -> "Saturday"
            else -> {
                "Something went wrong"
            }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

object ForecastSectionTestTags {
    const val FORECAST_ICON = "FORECAST_ICON"
    const val FORECAST_DAY = "FORECAST_DAY"
    const val FORECAST_HIGH = "FORECAST_HIGH"
    const val FORECAST_LOW = "FORECAST_LOW"
}