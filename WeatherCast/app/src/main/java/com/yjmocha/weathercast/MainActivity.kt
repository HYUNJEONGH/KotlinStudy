package com.yjmocha.weathercast

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.LoaderManager
import android.support.v4.content.Loader
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.yjmocha.weathercast.data.CityData
import com.yjmocha.weathercast.data.WeatherForecast
import com.yjmocha.weathercast.db.DBHandlerAnko
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), LoaderManager.LoaderCallbacks<ArrayList<WeatherForecast>> {

    val LOADER_ID = 101010
    var adapter: WeatherListViewAdapter? = null
    var mWeatherData: ArrayList<WeatherForecast>? = null
    val mCityArray = ArrayList<CityData>()
    val mDb = DBHandlerAnko(this)

    companion object {
        val SELECTED_CITY = 1100
        val REQUEST_CITY = 1101
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mCityArray.addAll(mDb.getCityDataAll())
        supportLoaderManager.initLoader(LOADER_ID, null, this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId)
        {
            R.id.addCity -> {
                val intent = Intent(this, SelectCityActivity::class.java)
                startActivityForResult(intent, SELECTED_CITY)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onLoadFinished(loader: Loader<ArrayList<WeatherForecast>>?, data: ArrayList<WeatherForecast>?) {
        adapter?:let{
            adapter = WeatherListViewAdapter(applicationContext, data)
            adapter?.setDeleteClickListener(){
                view -> val db = DBHandlerAnko(applicationContext)
                db.deleteCity(view.tag as String)
                adapter?.removeData(view.tag as String)
            }
            weatherList.adapter = adapter
            weatherList.layoutManager = LinearLayoutManager(applicationContext)
        }
        mWeatherData?.addAll(data)
        adapter?.updateData(data)
        progressbar.visibility = View.GONE
    }

//    로더가 리셋되면 호출되는 함수로 기존에 있던 데이터가 리셋되고 새로운 값으로 다시 로드된다.
    override fun onLoaderReset(loader: Loader<ArrayList<WeatherForecast>>?) {
    }

//    AsyncTaskLoader가 성공적으로 만들었다고 알려주는 콜백 함수
    override fun onCreateLoader(id: Int, args: Bundle?): Loader<ArrayList<WeatherForecast>>? {
        val loader: ForecastDataLoader = ForecastDataLoader(this, mCityArray)
        loader.forceLoad();//실행 함수
        progressbar.visibility = View.VISIBLE
        return loader
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(resultCode)
        {
            SELECTED_CITY -> {
                mCityArray.clear();
                mCityArray.addAll(mDb.getCityDataAll())
                supportLoaderManager.restartLoader(LOADER_ID, null, this)
            }
        }
    }
}
