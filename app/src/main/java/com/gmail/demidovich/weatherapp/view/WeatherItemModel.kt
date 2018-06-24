package com.gmail.demidovich.weatherapp.view

data class WeatherItemModel(
        val degreeDay: String,
        val icon: String = "01d",
        val date: Long = System.currentTimeMillis(),
        val description: String = "No description"
)