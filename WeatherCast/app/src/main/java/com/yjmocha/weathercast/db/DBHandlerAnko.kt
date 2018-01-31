package com.yjmocha.weathercast.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.yjmocha.weathercast.data.CityData
import org.jetbrains.anko.db.INTEGER
import org.jetbrains.anko.db.PRIMARY_KEY
import org.jetbrains.anko.db.TEXT
import org.jetbrains.anko.db.createTable

/**
 * Created by yunjeonghwang on 2018. 2. 1..
 */

class DBHandlerAnko(context: Context) : SQLiteOpenHelper(context, DB_Name, null, DB_Version) {

    companion object {
        val DB_Name = "user.db"
        val DB_Version = 1

        val TABLE_NAME = "city"
        val ID = "_id"
        val NAME = "name"
        val API_ID = "api_id"
    }

    fun getCityDataAll() : ArrayList<CityData> {
        val data = ArrayList<CityData>()
        val cursor = readableDatabase.query(TABLE_NAME,
                arrayOf(ID, NAME, API_ID), null, null, null, null, null)
        if (cursor.count == 0)
            return data

        cursor.moveToFirst()
        do {
            val city: CityData = CityData(cursor.getString(2), cursor.getString(1))
            data.add(city)
        }while (cursor.moveToNext())
        return data
    }

    fun saveCity(city: CityData)
    {
        writableDatabase.use {
            writableDatabase.insert(TABLE_NAME, null, ContentValues().apply {
                put(NAME, city.name)
                put(API_ID, city._id)
            })
        }
    }

    fun deleteCity(id: String)
    {
        writableDatabase.use {
            writableDatabase.delete(TABLE_NAME, API_ID + "=?", arrayOf(id))
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(TABLE_NAME, true,
                Pair(ID, INTEGER + PRIMARY_KEY),
                Pair(NAME, TEXT),
                Pair(API_ID, TEXT))
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }
}