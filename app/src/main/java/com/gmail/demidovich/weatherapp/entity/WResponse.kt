package com.gmail.demidovich.weatherapp.entity

import com.google.gson.annotations.SerializedName

data class WResponse(
        @SerializedName("city")
        var city: City,

        @SerializedName("list")
        var forecast: List<FCDetail>
)