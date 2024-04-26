package com.grapevineindustries.easyweather

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.grapevineindustries.easyweather.HomeScreenTestTags.WEATHER_API_LINK
import com.grapevineindustries.easyweather.data.Forecast
import com.grapevineindustries.easyweather.ui.theme.EasyWeatherTheme

@Preview(showSystemUi = true)
@Composable
fun HomeScreenContentPreview() {
    EasyWeatherTheme {
        HomeScreenContent()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
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
                modifier = Modifier.padding(paddingValues)

            ) {
                HomeScreenContent()
            }
        }
    )
}

@Composable
fun HomeScreenContent() {
    val uriHandler = LocalUriHandler.current

    Column(
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        CurrentWeatherSection()
        ForecastSection(
            forecasts = listOf(
                Forecast(),
                Forecast(),
                Forecast(),
            )
        )

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
    }
}

object HomeScreenTestTags {
    const val WEATHER_API_LINK = "HOME_SCREEN_WEATHER_API_LINK"
}