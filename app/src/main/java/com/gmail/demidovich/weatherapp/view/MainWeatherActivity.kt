package com.gmail.demidovich.weatherapp.view

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.View
import android.widget.Toast
import com.gmail.demidovich.weatherapp.Errors
import com.gmail.demidovich.weatherapp.R
import com.gmail.demidovich.weatherapp.adapter.WeatherAdapter
import com.gmail.demidovich.weatherapp.model.OpenWeatherAPIComponent
import com.gmail.demidovich.weatherapp.model.dagger.OWApiMod
import kotlinx.android.synthetic.main.activity_main_weather.*

class MainWeatherActivity : AppCompatActivity(), MainView {

    private val presenter = MainPresenter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectDI()
        setContentView(R.layout.activity_main_weather)
        initWeatherList()
    }

    private fun initWeatherList() {
        weatherList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = WeatherAdapter()
        }
    }
    private fun injectDI() {
        OpenWeatherAPIComponent
                .builder()
                .openWeatherAPIModule(OWApiMod())
                .build()
                .inject(presenter)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_item, menu)

        val menuItem = menu?.findItem(R.id.search_button)
        val searchMenuItem = menuItem?.actionView

        if (searchMenuItem is SearchView) {
            searchMenuItem.queryHint = getString(R.string.menu_search_hint)
            searchMenuItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    getForecast(query)
                    menuItem.collapseActionView()
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
        return true
    }

    private fun getForecast(query: String) = presenter.getForecastForSevenDays(query)

    override fun showSpinner() {
        weatherList.visibility = View.GONE
        emptyStateText.visibility = View.GONE
        loadingSpinner.visibility = View.VISIBLE
    }

    override fun hideSpinner() {
        weatherList.visibility = View.VISIBLE
        loadingSpinner.visibility = View.GONE
    }

    override fun updateForecast(forecasts: List<WeatherItemModel>) {
        if (forecasts.isEmpty()) emptyStateText.visibility = View.VISIBLE
        weatherList.adapter.safeCast<WeatherAdapter>()?.addForecast(forecasts)
    }

    private inline fun <reified T> Any.safeCast() = this as? T

    override fun showErrorToast(errorType: Errors) {
        when (errorType) {
            Errors.CALL_ERROR -> toast(getString(R.string.connection_error_message))
            Errors.MISSING_API_KEY -> toast(getString(R.string.missing_api_key_message))
            Errors.RESULT_NOT_FOUND -> toast(getString(R.string.city_not_found_toast_message))
        }
        loadingSpinner.visibility = View.GONE
        emptyStateText.visibility = View.VISIBLE
    }

    private fun Activity.toast(toastMessage: String, duration: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, toastMessage, duration).show()
    }
}
