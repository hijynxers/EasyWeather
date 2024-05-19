package com.grapevineindustries.easyweather

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grapevineindustries.easyweather.HomeScreenTestTags.ADD_LOCATION_CONFIRM
import com.grapevineindustries.easyweather.HomeScreenTestTags.ADD_LOCATION_TEXT
import com.grapevineindustries.easyweather.HomeScreenTestTags.ADD_LOCATION_TEXT_FIELD
import com.grapevineindustries.easyweather.HomeScreenTestTags.TOP_NAVBAR_UPDATE_LOCATION
import com.grapevineindustries.easyweather.HomeScreenTestTags.WEATHER_API_LINK
import com.grapevineindustries.easyweather.data.Condition
import com.grapevineindustries.easyweather.data.Current
import com.grapevineindustries.easyweather.data.CurrentWeatherResponse
import com.grapevineindustries.easyweather.data.Day
import com.grapevineindustries.easyweather.data.Forecast
import com.grapevineindustries.easyweather.data.ForecastDay
import com.grapevineindustries.easyweather.data.ForecastResponse
import com.grapevineindustries.easyweather.data.HomeViewModel
import com.grapevineindustries.easyweather.data.Location
import com.grapevineindustries.easyweather.ui.theme.EasyWeatherTheme

@Preview(showSystemUi = true)
@Composable
fun HomeScreenContentPreview() {
    EasyWeatherTheme {
        HomeScreenContent(
            current = CurrentWeatherResponse(
                location = Location(
                    name = "Anchorage",
                    localtime = "2024-04-15 21:58"
                ),
                current = Current(
                    temp_f = 37.4f,
                )
            ),
            forecast = ForecastResponse(
                forecast = Forecast(
                    forecastday = arrayListOf(
                        ForecastDay(
                            day = Day(
                                maxtemp_f = 37.7f,
                                mintemp_f = 33.6f,
                                condition = Condition(
                                    text = "Overcast",
                                    icon = ""
                                )
                            )
                        ),
                        ForecastDay(),
                        ForecastDay(),
                    )
                )
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = HomeViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.fetchCurrentWeather()
        viewModel.fetchForecast()
    }
    val showAddLocationDialog = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                },
                actions = {
                    IconButton(
                        modifier = Modifier.testTag(TOP_NAVBAR_UPDATE_LOCATION),
                        onClick = {
                            showAddLocationDialog.value = true
                        },
                        content = {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null
                            )
                        }
                    )
                }
            )
        },
        containerColor = MaterialTheme.colorScheme.background,
        content = { paddingValues ->
            Column(
                modifier = Modifier.padding(paddingValues)

            ) {
                if (viewModel.isFinishedLoading.collectAsState().value) {
                    HomeScreenContent(
                        current = viewModel.weather.collectAsState().value,
                        forecast = viewModel.forecast.collectAsState().value,
                    )
                } else {
                    Column(
                        Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

            if (showAddLocationDialog.value) {
                AlertDialog(
                    onDismissRequest = { showAddLocationDialog.value = false },
                    confirmButton = {
                        TextButton(
                            modifier = Modifier.testTag(ADD_LOCATION_CONFIRM),
                            onClick = { showAddLocationDialog.value = false },
                            content = {
                                Text(
                                    text = "Submit"
                                )
                            }
                        )
                    },
                    title = {
                        Text(
                            modifier = Modifier.testTag(ADD_LOCATION_TEXT),
                            text = "Enter Zip Code:"
                        )
                    },
                    text = {
                        TextField(
                            modifier = Modifier.testTag(ADD_LOCATION_TEXT_FIELD),
                            value = "",
                            onValueChange = {}
                        )
                    },
                )
            }
        }
    )
}

@Composable
fun HomeScreenContent(
    current: CurrentWeatherResponse,
    forecast: ForecastResponse,
) {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        CurrentWeatherSection(
            weather = current,
            highTemp = forecast.forecast.forecastday[0].day.maxtemp_f,
            lowTemp = forecast.forecast.forecastday[0].day.mintemp_f
        )
        Spacer(modifier = Modifier.height(8.dp))
        ForecastSection(forecast.forecast)

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .testTag(WEATHER_API_LINK)
                .clickable {
                    uriHandler.openUri("https://www.weatherapi.com/")
                }
        ) {
            Text(
                text = "Powered by ",
                style = MaterialTheme.typography.labelSmall
            )
            Text(
                text = "WeatherApi.com",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                textDecoration = TextDecoration.Underline
            )
        }

        Spacer(Modifier.height(16.dp))
    }
}

object HomeScreenTestTags {
    const val WEATHER_API_LINK = "HOME_SCREEN_WEATHER_API_LINK"
    const val TOP_NAVBAR_UPDATE_LOCATION = "TOP_NAVBAR_UPDATE_LOCATION"
    const val ADD_LOCATION_TEXT = "ADD_LOCATION_TEXT"
    const val ADD_LOCATION_TEXT_FIELD = "ADD_LOCATION_TEXT_FIELD"
    const val ADD_LOCATION_CONFIRM = "ADD_LOCATION_CONFIRM"
}