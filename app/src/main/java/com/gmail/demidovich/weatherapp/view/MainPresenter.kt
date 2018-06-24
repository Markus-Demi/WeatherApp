package com.gmail.demidovich.weatherapp.view

import com.gmail.demidovich.weatherapp.BuildConfig
import com.gmail.demidovich.weatherapp.Errors
import com.gmail.demidovich.weatherapp.entity.FCDetail
import com.gmail.demidovich.weatherapp.entity.WResponse
import com.gmail.demidovich.weatherapp.utils.OWApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MainPresenter(val view: MainView) {
    @Inject
    lateinit var api: OWApi

    private var key: String = "3b8ceec7d8263efe09f0c17025b8f791"

    fun getForecastForSevenDays(cityName: String) {
        if (BuildConfig.OPEN_WEATHER_API_KEY == key) {
            view.showErrorToast(Errors.MISSING_API_KEY)
            return
        }
        view.showSpinner()
        api.dailyForecast(cityName, 7).enqueue(object : Callback<WResponse> {

            override fun onResponse(call: Call<WResponse>, response: Response<WResponse>) {
                response.body()?.let {
                    createListForView(it)
                    view.hideSpinner()
                } ?: view.showErrorToast(Errors.RESULT_NOT_FOUND)
            }

            override fun onFailure(call: Call<WResponse>?, t: Throwable) {
                view.showErrorToast(Errors.CALL_ERROR)
                t.printStackTrace()
            }
        })
    }

    private fun createListForView(weatherResponse: WResponse) {
        val forecasts = mutableListOf<WeatherItemModel>()
        for (forecastDetail: FCDetail in weatherResponse.forecast) {
            val dayTemp = forecastDetail.temperature.dayTemperature
            val forecastItem = WeatherItemModel(degreeDay = dayTemp.toString(),
                    date = forecastDetail.date,
                    icon = forecastDetail.description[0].icon,
                    description = forecastDetail.description[0].description)
            forecasts.add(forecastItem)
        }
        view.updateForecast(forecasts)
    }
}