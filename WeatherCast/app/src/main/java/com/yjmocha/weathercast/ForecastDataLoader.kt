package com.yjmocha.weathercast

import android.content.Context
import android.support.v4.content.AsyncTaskLoader
import com.google.gson.Gson
import com.yjmocha.weathercast.data.CityData
import com.yjmocha.weathercast.data.DayData
import com.yjmocha.weathercast.data.WeatherForecast
import com.yjmocha.weathercast.data.WeekData
import java.io.InputStreamReader
import java.net.URL

/**
 * Created by yunjeonghwang on 2018. 2. 5..
 */
class ForecastDataLoader(context: Context, val cities: ArrayList<CityData>)
    : AsyncTaskLoader<ArrayList<WeatherForecast>>(context) {

    val API_KEY: String = "7f8bd5fe18c59a45df36783efd578a2b"
    val Current_URL : String = "http://api.openweathermap.org/data/2.5/weather?id="
    val Forcast_URL : String = "http://api.openweathermap.org/data/2.5/forecast?id="
    val ICON_URL : String = "http://openweathermap.org/img/w/"

    override fun loadInBackground(): ArrayList<WeatherForecast> {
        val cityWeather = ArrayList<WeatherForecast>()
        cities.forEach {
            val cur_url = Current_URL+it._id+"&units=metric&APPID=$API_KEY"
            val readData = URL(cur_url).readText()
            val current: DayData = Gson().fromJson(readData, DayData::class.java)
            current.cityName = it.name
            current.apiId = it._id

            val foreUrl = Forcast_URL+it._id+"&units=metric&APPID=$API_KEY"
            val url = URL(foreUrl)
            val inputStream = InputStreamReader(url.openStream())

            val week: WeekData = Gson().fromJson(inputStream, WeekData::class.java)
            val forecast: WeatherForecast = WeatherForecast(current, week, ICON_URL)
            cityWeather.add(forecast)
        }
        return cityWeather
    }
}