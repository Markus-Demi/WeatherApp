package com.gmail.demidovich.weatherapp.model

import com.gmail.demidovich.weatherapp.model.dagger.OWApiMod
import com.gmail.demidovich.weatherapp.view.MainPresenter
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [OWApiMod::class])
interface OpenWeatherAPIComponent {
    fun inject(presenter: MainPresenter)
}