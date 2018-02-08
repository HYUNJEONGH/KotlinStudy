package com.yjmocha.weathercast

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.yjmocha.weathercast.data.WeatherForecast

/**
 * Created by yunjeonghwang on 2018. 2. 8..
 */

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val descript: TextView = itemView.findViewById(R.id.descript) as TextView
    val weatherIcon: ImageView = itemView.findViewById(R.id.weatherIcon) as ImageView
    val currentTemp: TextView = itemView.findViewById(R.id.currentTemp) as TextView
    val highLowTemp: TextView = itemView.findViewById(R.id.highLowTemp) as TextView
    val humidity: TextView = itemView.findViewById(R.id.humidity) as TextView
    val cloudy: TextView = itemView.findViewById(R.id.cloudy) as TextView
    val wind: TextView = itemView.findViewById(R.id.wind) as TextView
    val cityName: TextView = itemView.findViewById(R.id.detailDescript) as TextView
    val forecast: ForecastView = itemView.findViewById(R.id.forecast) as ForecastView
    val delbtn: ImageButton = itemView.findViewWithTag(R.id.btnDel) as ImageButton

    fun bindHolder(context: Context, data: WeatherForecast, delClick: View.OnClickListener?) {
        descript.text = data.current[0].description
        weatherIcon.loadUrl(data.iconUrl + data.current[0].icon + ".png")
        currentTemp.text = String.format("%s \\u2103", data.current.main.temp)

        val format: String = "%s \\u2103 / %s \\u2103"
        highLowTemp.text = String.format(format, data.current.main.temp_min, data.current.main.temp_max)
        cityName.text = data.current.cityName
        cloudy.text = String.format("%s %%", data.current.clouds.all)
        humidity.text = String.format("%s %%", data.current.main.humidity)
        wind.text = data.current.wind.speed
        forecast.setView(data.week.list, data.iconUrl)
        delbtn.setOnClickListener(delClick)
        delbtn.tag = data.current.apiId
    }

}
    fun ImageView.loadUrl(url: String)
    {
        Picasso.with(context).load(url).into(this)
    }


    class WeatherListViewAdapter(val context: Context, val data: ArrayList<WeatherForecast>)
        : RecyclerView.Adapter<ViewHolder>() {

        var mWeatherData = ArrayList<WeatherForecast>(data)
        var delBtnClickListener: View.OnClickListener? = null

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as
                    LayoutInflater
            val mainView = inflater.inflate(R.layout.layout_card, parent, false)
            val viewHolder: ViewHolder = ViewHolder(mainView)
            return viewHolder
        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
            val data = mWeatherData[position]
            holder?.bindHolder(context, data, delBtnClickListener)
        }
//      함수를 인자로 받음
        fun setDeleteClickListener(onClick: (View)->Unit){
            delBtnClickListener = object : View.OnClickListener {
                override fun onClick(view: View?) {
                    onClick(view)
                }
            }
        }

        fun updateData(newData: ArrayList<WeatherForecast>)
        {
            mWeatherData.clear()
            mWeatherData.addAll(newData)
            notifyDataSetChanged()
        }

        fun removeData(apiId: String)
        {
            for (i in mWeatherData)
            {
                if(i.current.apiId.equals(apiId)) {
                    mWeatherData.remove(i)
                    break
                }
            }
            notifyDataSetChanged()
        }
        override fun getItemCount(): Int = mWeatherData.size
    }