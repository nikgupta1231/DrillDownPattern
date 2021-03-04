package com.example.drilldownpattern

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.drilldownpattern.models.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject

/**
 *  usage:  Main class for managing data. It downloads the data and then save to the Database.
 *          It is the medium between view-model and  database
 */
class DataRepository {

    companion object {
        @Volatile
        private var INSTANCE: DataRepository? = null
        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: DataRepository().also {
                INSTANCE = it
            }
        }
    }

    private lateinit var dao: Dao
    fun setDao(dao: Dao) {
        this.dao = dao
    }

    fun getAllCountriesInAsc(): LiveData<List<Country>> {
        return dao.getAllCountriesInAsc()
    }

    fun getAllZoneInAsc(countryName: String): LiveData<List<Zone>> {
        return dao.getAllZonesByCountryNameInAsc(countryName)
    }

    fun getAllRegionInAsc(countryName: String): LiveData<List<Region>> {
        return dao.getAllRegionsByZoneNameInAsc(countryName)
    }

    fun getAllAreaInAsc(countryName: String): LiveData<List<Area>> {
        return dao.getAllAreasByRegionNameInAsc(countryName)
    }

    fun getAllEmployeeInAsc(countryName: String): LiveData<List<Employee>> {
        return dao.getAllEmployeesByAreaNameInAsc(countryName)
    }


    lateinit var listener: DataDownloadListener
    fun setDataDownloadListener(listener: DataDownloadListener) {
        this.listener = listener
    }

    fun isDataAvailable(context: Context): Boolean {
        val pref = context.getSharedPreferences(Utils.SHARED_PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        return pref.getBoolean(Utils.SHARED_PREF_IS_DOWNLOADED_DATA_AVAILABLE, false)
    }

    private fun setDataAvailable(context: Context) {
        val pref = context.getSharedPreferences(Utils.SHARED_PREF_NAME, AppCompatActivity.MODE_PRIVATE)
        pref.edit().putBoolean(Utils.SHARED_PREF_IS_DOWNLOADED_DATA_AVAILABLE, true).apply()
    }

    fun fetchData(application: Application) {
        val request = JsonObjectRequest(
            Request.Method.GET,
            Utils.DATA_API,
            null,
            { response ->
                GlobalScope.launch(Dispatchers.IO) {
                    parseResponseAndSaveToDb(response, application)
                    withContext(Dispatchers.Main) {
                        setDataAvailable(application)
                        listener.onDataDownloadSuccess()
                    }
                }
            }
        ) { listener.onDataDownloadFailed() }

        NetworkQueue.getInstance(application).addToQueue(request)
    }

    private fun parseResponseAndSaveToDb(response: JSONObject, application: Application) {
        val responseData: JSONObject = response.getJSONObject(Utils.JSON_KEY_RESPONSE_DATA)

        val countryJsonArray: JSONArray = responseData.getJSONArray(Utils.JSON_KEY_COUNTRY)
        val zoneJsonArray: JSONArray = responseData.getJSONArray(Utils.JSON_KEY_ZONE)
        val regionJsonArray: JSONArray = responseData.getJSONArray(Utils.JSON_KEY_REGION)
        val areaJsonArray: JSONArray = responseData.getJSONArray(Utils.JSON_KEY_AREA)
        val employeeJsonArray: JSONArray = responseData.getJSONArray(Utils.JSON_KEY_EMPLOYEE)

        val countryList: ArrayList<Country> = ArrayList()
        for (i in 0 until countryJsonArray.length()) {
            val jsonObject: JSONObject = countryJsonArray.getJSONObject(i)
            countryList.add(Country(jsonObject.getString(Utils.JSON_KEY_COUNTRY).trim(), jsonObject.getString(Utils.JSON_KEY_TERRITORY).trim(), ""))
        }

        val zoneList: ArrayList<Zone> = ArrayList()
        for (i in 0 until zoneJsonArray.length()) {
            val jsonObject: JSONObject = zoneJsonArray.getJSONObject(i)
            val strArr = jsonObject.getString(Utils.JSON_KEY_TERRITORY).split("::")
            zoneList.add(Zone(jsonObject.getString(Utils.JSON_KEY_ZONE), strArr[1].trim(), strArr[0].trim()))
        }

        val regionList: ArrayList<Region> = ArrayList()
        for (i in 0 until regionJsonArray.length()) {
            val jsonObject: JSONObject = regionJsonArray.getJSONObject(i)
            val strArr = jsonObject.getString(Utils.JSON_KEY_TERRITORY).split("::")
            regionList.add(Region(jsonObject.getString(Utils.JSON_KEY_REGION), strArr[2], strArr[1]))
        }

        val areaList: ArrayList<Area> = ArrayList()
        for (i in 0 until areaJsonArray.length()) {
            val jsonObject: JSONObject = areaJsonArray.getJSONObject(i)
            val strArr = jsonObject.getString(Utils.JSON_KEY_TERRITORY).split("::")
            areaList.add(Area(jsonObject.getString(Utils.JSON_KEY_AREA), strArr[3], strArr[2]))
        }

        val employeeList: ArrayList<Employee> = ArrayList()
        for (i in 0 until employeeJsonArray.length()) {
            try {
                val jsonObject: JSONObject = employeeJsonArray.getJSONObject(i)
                val strArr = jsonObject.getString(Utils.JSON_KEY_TERRITORY).split("::")
                employeeList.add(Employee(jsonObject.getString(Utils.JSON_KEY_NAME), "", strArr[3]))
            } catch (e: Exception) {
                //
            }
        }

        val db = MyDatabase.getInstance(application)
        db.getDao().addCountries(countryList)
        db.getDao().addZones(zoneList)
        db.getDao().addRegions(regionList)
        db.getDao().addAreas(areaList)
        db.getDao().addEmployees(employeeList)
    }

}

interface DataDownloadListener {
    fun onDataDownloadSuccess()

    fun onDataDownloadFailed()
}