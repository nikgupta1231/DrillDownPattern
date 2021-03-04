package com.example.drilldownpattern.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.drilldownpattern.DataRepository
import com.example.drilldownpattern.MyDatabase
import com.example.drilldownpattern.models.Zone

/**
 *  usage:  view model for Zone-Metric, applies business logic to data retrieved from DataRepo
 *  @author Nikhil Gupta
 */
class ZoneViewModel(application: Application) : AndroidViewModel(application) {
    private val metricRepo: DataRepository

    init {
        val dao = MyDatabase.getInstance(application).getDao()
        metricRepo = DataRepository.getInstance()
        metricRepo.setDao(dao)
    }

    fun getZonesFromRepo(countryName: String): LiveData<List<Zone>> {
        return metricRepo.getAllZoneInAsc(countryName)
    }

    var zoneList: List<Zone>? = null

    fun getReverseOrderedZones(): List<Zone>? {
        zoneList = zoneList?.asReversed() ?: zoneList
        return zoneList
    }


}