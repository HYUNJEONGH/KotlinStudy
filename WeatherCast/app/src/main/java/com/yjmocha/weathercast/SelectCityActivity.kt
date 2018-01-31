package com.yjmocha.weathercast

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.yjmocha.weathercast.data.CityArray
import com.yjmocha.weathercast.data.CityData
import com.yjmocha.weathercast.db.DBHandlerAnko
import kotlinx.android.synthetic.main.activity_select_city.*
import kotlinx.android.synthetic.main.city_list_item.*
import java.io.InputStreamReader

/**
 * Created by yunjeonghwang on 2018. 1. 31..
 */
class SelectCityActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_city)
        val inputStream = InputStreamReader(assets.open("areaCode"))
        val cityData: CityArray = Gson().fromJson(inputStream, CityArray::class.java)
        val adapter = CityListAdapter(this, cityData.city)
        cityList.adapter = adapter
        cityList.setOnItemClickListener { adapterView, view, i, l ->
            saveData(view.tag as String, tvName.text as String)
            setResult(MainActivity.SELECTED_CITY)
            finish()
        }
    }

    fun saveData(api_id: String, name: String)
    {
        val db = DBHandlerAnko(this)
        db.saveCity(CityData(api_id, name))
    }
}