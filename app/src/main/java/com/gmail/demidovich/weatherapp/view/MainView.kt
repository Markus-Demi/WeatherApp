package com.gmail.demidovich.weatherapp.view

import com.gmail.demidovich.weatherapp.Errors

interface MainView {
    fun showSpinner()
    fun hideSpinner()
    fun updateForecast(forecasts: List<WeatherItemModel>)
    fun showErrorToast(errorType: Errors)
}