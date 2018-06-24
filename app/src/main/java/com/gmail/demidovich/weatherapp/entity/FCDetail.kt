package com.gmail.demidovich.weatherapp.entity

import com.google.gson.annotations.SerializedName

data class FCDetail(
        @SerializedName("dt")
        var date: Long,

        @SerializedName("temp")
        var temperature: Temperature,

        @SerializedName("weather")
        var description: List<WeatherDescription>,

        @SerializedName("pressure")
        var pressure: Double,

        @SerializedName("humidity")
        var humidity: Double
)