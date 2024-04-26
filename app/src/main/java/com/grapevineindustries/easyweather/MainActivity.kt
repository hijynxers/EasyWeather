package com.grapevineindustries.easyweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grapevineindustries.easyweather.ui.theme.EasyWeatherTheme
import java.time.LocalDateTime
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EasyWeatherTheme {
                HomeScreen()
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun GreetingPreview() {
    EasyWeatherTheme {
        HomeScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val calendar = Calendar.getInstance()

    val current = LocalDateTime.of(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_WEEK),
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        calendar.get(Calendar.SECOND)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(
                        top = paddingValues.calculateTopPadding(),
                        start = 16.dp,
                        end = 16.dp
                    )
                    .fillMaxWidth()
                    .testTag("Stuff")
            ) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                ) {
                    Text(
                        text = "St. Charles",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = "${current.dayOfWeek}, " +
                                "${current.month} " +
                                "${current.dayOfMonth}, " +
                                "${current.hour % 12}:" +
                                current.minute.toString()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row {
                        Column {
                            Icon( // condition:icon
                                painter = painterResource(id = R.drawable.cloud_24),
                                contentDescription = null
                            )
                            Text(
                                text = "Coludy" // condition:text
                            )
                        }

                        Text(  // temp_f
                            text = "-8\u2109"
                        )
                    }

                    Row {
                        Text("High: 87\u2109")
                        Text("Low: 57\u2109")
                    }
                }

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
                        ForecastRow("Today")
                        ForecastRow("Tomorrow")
                        ForecastRow("Tuesday")
                    }

                }

                Row {
                    Text(text = "Powered by ")
                    Text(
                        modifier = Modifier.clickable {  },
                        text = "WeatherApi.com"
                    )
                }

            }
        }
    )
}

@Composable
fun ForecastRow(day: String) {
    Row {
        Icon(
            painter = painterResource(id = R.drawable.cloud_24),
            contentDescription = null
        )
        Text(
            text = day
        )

        Spacer(modifier = Modifier.width(60.dp))

        Text(text = "80 / 45")
    }
}