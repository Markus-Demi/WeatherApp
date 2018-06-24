package com.gmail.demidovich.weatherapp.utils

import com.gmail.demidovich.weatherapp.entity.WResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface OWApi {
    @GET("forecast/daily")
    fun dailyForecast(@Query("q") cityName: String, @Query("cnt") dayCount: Int): Call<WResponse>

    companion object {
        const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    }
}