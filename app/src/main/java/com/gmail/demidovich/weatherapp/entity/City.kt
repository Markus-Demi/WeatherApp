package com.gmail.demidovich.weatherapp.entity

import com.google.gson.annotations.SerializedName

data class City(
        @SerializedName("city")
        var cityName: String,

        @SerializedName("country")
        var country: String
)