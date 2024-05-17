package com.grapevineindustries.easyweather

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_CARD
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_DAY
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_HIGH
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_ICON
import com.grapevineindustries.easyweather.ForecastSectionTestTags.FORECAST_LOW
import com.grapevineindustries.easyweather.HourTestTags.HOUR_CONDITION
import com.grapevineindustries.easyweather.HourTestTags.HOUR_TEMP
import com.grapevineindustries.easyweather.HourTestTags.HOUR_TIME
import com.grapevineindustries.easyweather.data.Forecast
import com.grapevineindustries.easyweather.data.ForecastDay
import com.grapevineindustries.easyweather.data.Hour
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
                    ForecastDay(
                        date = "2024-04-15",
                        hour = arrayListOf(
                            Hour(
                                time = "2024-05-07 00:00",
                                temp_f = 49f
                            ),
                            Hour(time = "2024-05-07 01:00"),
                            Hour(time = "2024-05-07 02:00"),
                            Hour(time = "2024-05-07 03:00"),
                            Hour(time = "2024-05-07 04:00"),
                            Hour(time = "2024-05-07 05:00"),
                            Hour(time = "2024-05-07 06:00"),
                            Hour(time = "2024-05-07 07:00"),
                            Hour(time = "2024-05-07 08:00"),
                            Hour(time = "2024-05-07 09:00"),
                            Hour(time = "2024-05-07 10:00"),
                        )
                    ),
                    ForecastDay("2024-04-16"),
                    ForecastDay("2024-04-17"),
                )
            )
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ForecastSection(forecast: Forecast) {
    val selected = remember { mutableIntStateOf(0) }

    Column {
        Card(
            modifier = Modifier.fillMaxWidth()
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

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceEvenly
                ) {
                    forecast.forecastday.forEachIndexed { index, item ->
                        ForecastCard(
                            onClick = {
                                selected.intValue = index
                            },
                            modifier = Modifier.weight(1f),
                            forecast = item,
                            isSelected = selected.intValue == index,
                            index = index
                        )
                    }
                }
                Spacer(Modifier.height(8.dp))

                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    forecast.forecastday[selected.intValue].hour.forEachIndexed { index, hour ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(if (index % 2 == 0) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant)
                                .padding(horizontal = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                modifier = Modifier
                                    .testTag(HOUR_TIME)
                                    .weight(1f),
                                text = formatTime(hour.time),
                                textAlign = TextAlign.Start
                            )

                            Row(
                                modifier = Modifier.weight(2f),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                GlideImage(
                                    modifier = Modifier
                                        .testTag(CurrentWeatherSectionTestTags.CONDITION_ICON)
                                        .size(50.dp),
                                    model = "https:" + hour.condition.icon,
                                    contentDescription = null,
                                )
                                Text(
                                    modifier = Modifier
                                        .testTag(HOUR_CONDITION),
                                    text = hour.condition.text
                                )
                            }


                            Text(
                                modifier = Modifier
                                    .testTag(HOUR_TEMP)
                                    .weight(1f),
                                text = "${hour.temp_f} $degreesF",
                                textAlign = TextAlign.End
                            )
                        }
                    }
                }
            }
        }

        Spacer(Modifier.height(8.dp))
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ForecastCard(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    forecast: ForecastDay,
    isSelected: Boolean,
    index: Int
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.primaryContainer
            else MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = modifier
            .padding(8.dp)
            .testTag(FORECAST_CARD + index.toString())
            .clickable { onClick() }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            Text(
                modifier = Modifier
                    .testTag(FORECAST_DAY),
                text = formatDate(date = forecast.date),
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.testTag(FORECAST_HIGH),
                text = "${forecast.day.maxtemp_f} $degreesF"
            )
            GlideImage(
                modifier = Modifier
                    .testTag(FORECAST_ICON)
                    .size(50.dp),
                model = "https:" + forecast.day.condition.icon,
                contentDescription = null
            )
            Text(
                modifier = Modifier.testTag(FORECAST_LOW),
                text = "${forecast.day.mintemp_f} $degreesF"
            )
        }
    }
}

@SuppressLint("SimpleDateFormat")
fun formatDate(
    date: String,
    fullDay: Boolean = false
): String {
    val format = SimpleDateFormat("yyyy-MM-dd")
    try {
        val cal: Calendar = Calendar.getInstance()
        format.parse(date)?.let { cal.setTime(it) }
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)
        val dateNumbers = "${cal.get(Calendar.MONTH) + 1}/${cal.get(Calendar.DAY_OF_MONTH)}"
        return when (dayOfWeek) {
            1 -> if (fullDay) "Sunday $dateNumbers" else "Sun"
            2 -> if (fullDay) "Monday $dateNumbers" else "Mon"
            3 -> if (fullDay) "Tuesday $dateNumbers" else "Tues"
            4 -> if (fullDay) "Wednesday $dateNumbers" else "Wed"
            5 -> if (fullDay) "Thursday $dateNumbers" else "Thurs"
            6 -> if (fullDay) "Friday $dateNumbers" else "Fri"
            7 -> if (fullDay) "Saturday $dateNumbers" else "Sat"
            else -> {
                "Something went wrong"
            }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

fun formatTime(time: String): String {
    val split = time.split(" ", ":")
    val backend = if (split[1].toInt() < 12) "AM" else "PM"
    var num = split[1].toInt() % 12
    if (num == 0) num = 12
    return "$num$backend"
}

object ForecastSectionTestTags {
    const val FORECAST_ICON = "FORECAST_ICON"
    const val FORECAST_DAY = "FORECAST_DAY"
    const val FORECAST_HIGH = "FORECAST_HIGH"
    const val FORECAST_LOW = "FORECAST_LOW"
    const val FORECAST_CARD = "FORECAST_CARD"
}

object HourTestTags {
    const val HOUR_TIME = "HOUR_TIME"
    const val HOUR_TEMP = "HOUR_TEMP"
    const val HOUR_CONDITION = "HOUR_CONDITION"
}