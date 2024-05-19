package com.grapevineindustries.easyweather.networking

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private const val BASE_URL = "https://api.weatherapi.com/v1/"
    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    private val OK_HTTP_CLIENT: OkHttpClient =
        OkHttpClient.Builder()
            .addNetworkInterceptor(logging)
            .build()
    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OK_HTTP_CLIENT)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}