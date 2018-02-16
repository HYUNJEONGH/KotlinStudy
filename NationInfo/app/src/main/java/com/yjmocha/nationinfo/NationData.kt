package com.yjmocha.nationinfo

/**
 * Created by yunjeonghwang on 2018. 2. 16..
 */
data class NationDetailData (val name: String,
                             val capital: String,
                             val location: String,
                             val volume: String,
                             val weather: String,
                             val language: String)
// 이름이 data인 배열
data class GsonData(val data: ArrayList<NationDetailData>)