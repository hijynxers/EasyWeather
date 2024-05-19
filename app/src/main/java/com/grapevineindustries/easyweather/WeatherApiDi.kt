package com.grapevineindustries.easyweather

import com.grapevineindustries.easyweather.networking.RetrofitInstance
import com.grapevineindustries.easyweather.networking.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WeatherApiDi {
    @Singleton
    @Provides
    fun provideWeatherApi(retrofit: Retrofit): WeatherApi =
        retrofit.create(WeatherApi::class.java)

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit = RetrofitInstance.retrofit
}