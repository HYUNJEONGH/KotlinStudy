package com.yjmocha.nationinfo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_nation_detail.*
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Created by yunjeonghwang on 2018. 2. 16..
 */
class NationDetailActivity : AppCompatActivity() {
    companion object {
        val EXTRA_NATION_NAME = "name"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nation_detail)
        val nationName = intent.getStringExtra(EXTRA_NATION_NAME)
        val data: NationDetailData?  = getDataFromName(nationName)
        imgDetailFlag.setImageResource(getResourceId(nationName))
        initView(data)
    }

    private fun initView(data: NationDetailData?) {
        tvName.text = data?.name
        tvCapital.text = data?.capital
        tvVol.text = data?.volume
        tvLoc.text = data?.location
        tvLanguage.text = data?.language
        tvWeather.text = data?.weather
    }

    private fun getResourceId(name: String): Int {
        var resourceId: Int = 0
        when(name) {
            "벨기에" -> {
                resourceId = R.drawable.l_flag_belgium
            }
            "아르헨티나" -> {
                resourceId = R.drawable.l_flag_argentina
            }
            "브라질" -> {
                resourceId = R.drawable.l_flag_brazil
            }
            "캐나다" -> {
                resourceId = R.drawable.l_flag_canada
            }
            "중국" -> {
                resourceId = R.drawable.l_flag_china
            }
            else -> {
                resourceId  = 0
            }
        }
        return resourceId
    }


    private fun getDataFromName(name: String): NationDetailData? {
        val gson: Gson = GsonBuilder().create()
        val inputStream: InputStream = assets.open("nation_data.json")
        val reader: InputStreamReader = InputStreamReader(inputStream)
        val detailData = gson.fromJson(reader, GsonData::class.java)

        for (data in detailData.data) {
            if (name.equals(data.name)) {
                return data
            }
        }
        return null
    }

}