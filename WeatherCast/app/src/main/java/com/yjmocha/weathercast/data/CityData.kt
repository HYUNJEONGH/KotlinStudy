package com.yjmocha.weathercast.data

import java.util.*;

/**
 * Created by yunjeonghwang on 2018. 1. 31..
 */

data class CityArray(val city:ArrayList<CityData>)
data class CityData(val _id:String, val name:String)