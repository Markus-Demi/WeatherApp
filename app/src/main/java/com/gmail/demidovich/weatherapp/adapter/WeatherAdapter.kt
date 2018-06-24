package com.gmail.demidovich.weatherapp.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.gmail.demidovich.weatherapp.R
import com.gmail.demidovich.weatherapp.view.WeatherItemModel
import kotlinx.android.synthetic.main.weather_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class WeatherAdapter : RecyclerView.Adapter<WeatherAdapter.WeatherViewHolder>() {
    var weatherList = mutableListOf<WeatherItemModel>()

    fun addForecast(list: List<WeatherItemModel>) {
        weatherList.clear()
        weatherList.addAll(list)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
        weatherList[position].let {
            holder.bind(weatherElement = it)
        }
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

    class WeatherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(weatherElement: WeatherItemModel) {
            itemView.degreeText.text = "${weatherElement.degreeDay} °C ${weatherElement.description}"
            itemView.dateText.text = getDate(weatherElement.date)
            Glide.with(itemView.context)
                    .load("http://openweathermap.org/img/w/${weatherElement.icon}.png")
                    .into(itemView.weatherIcon)
        }

        private fun getDate(date: Long): String {
            val timeFormatter = SimpleDateFormat("dd.MM.yyyy")
            return timeFormatter.format(Date(date * 1000L))
        }
    }
}