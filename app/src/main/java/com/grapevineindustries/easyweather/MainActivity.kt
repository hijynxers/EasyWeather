package com.grapevineindustries.easyweather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.grapevineindustries.easyweather.ui.theme.EasyWeatherTheme

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