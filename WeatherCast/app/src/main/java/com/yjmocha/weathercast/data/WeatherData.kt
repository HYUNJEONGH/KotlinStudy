package com.yjmocha.weathercast.data

import com.google.gson.annotations.SerializedName

/**
 * Created by yunjeonghwang on 2018. 2. 3..
 */
data class DayData(val weather: ArrayList<WeatherData>, val main: MainData, val wind: WindData,
                   val clouds: CloudData, val rain: RainData, var cityName: String,
                   var apiId: String)
{
    operator fun get(position: Int):WeatherData = weather[position]
}
data class WeekData(val list: ArrayList<WeekList>)

data class WeekList(val dt: String, val weather: ArrayList<WeatherData>, val main:
                    MainData, val clouds: CloudData, val dtTxt: String)

data class WeatherData(val main: String, val description: String, val icon: String)
data class MainData(val temp: String, val temp_min: String, val temp_max: String,
                    val humidity: String)
data class CloudData(val all: String)
data class RainData(@SerializedName("3h") val rain: Int)
data class WindData(val speed: String)
data class WeatherForecast(val current: DayData, val week: WeekData, val iconUrl: String)

