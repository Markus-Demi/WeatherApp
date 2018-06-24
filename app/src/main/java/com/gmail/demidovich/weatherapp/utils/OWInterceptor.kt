package com.gmail.demidovich.weatherapp.utils

import com.gmail.demidovich.weatherapp.BuildConfig
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Response

class OWInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain?): Response {
        val url: HttpUrl = chain!!.request().url().newBuilder()
                .addQueryParameter("APPID", BuildConfig.OPEN_WEATHER_API_KEY)
                .addQueryParameter("mode", "json")
                .addQueryParameter("units", "metric")
                .build()
        return chain.proceed(chain.request().newBuilder().addHeader("Accept", "application/json").url(url).build())
    }
}