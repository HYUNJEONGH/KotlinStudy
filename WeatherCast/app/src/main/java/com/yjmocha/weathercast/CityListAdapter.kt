package com.yjmocha.weathercast

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.yjmocha.weathercast.data.CityArray
import com.yjmocha.weathercast.data.CityData

/**
 * Created by yunjeonghwang on 2018. 2. 1..
 */
class CityListAdapter(context: Context, cityData: ArrayList<CityData>)
    : ArrayAdapter<CityData>(context, R.layout.city_list_item, cityData) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var mainView = convertView
        //mainView null인 경우 레이아웃을 inflate
        mainView = mainView?:inflater.inflate(R.layout.city_list_item, parent, false)
        val name : TextView = mainView!!.findViewById(R.id.tvName) as TextView
        val data : CityData = getItem(position)
        name.text = data.name
        mainView.tag = data._id

        return mainView!!
    }
}