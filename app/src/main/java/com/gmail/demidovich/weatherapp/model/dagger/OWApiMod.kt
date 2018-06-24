package com.gmail.demidovich.weatherapp.model.dagger

import com.gmail.demidovich.weatherapp.utils.OWApi
import com.gmail.demidovich.weatherapp.utils.OWInterceptor
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [GsonMod::class])
class OWApiMod {
    @Provides
    @Singleton
    fun provideApi(gson: Gson): OWApi {

        val apiClient = OkHttpClient.Builder().addInterceptor(OWInterceptor()).build()

        return Retrofit.Builder().apply {
            baseUrl(OWApi.BASE_URL)
            addConverterFactory(GsonConverterFactory.create(gson))
            client(apiClient)
        }.build().create(OWApi::class.java)
    }
}