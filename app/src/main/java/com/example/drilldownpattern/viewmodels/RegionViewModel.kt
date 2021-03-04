package com.example.drilldownpattern.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.drilldownpattern.DataRepository
import com.example.drilldownpattern.MyDatabase
import com.example.drilldownpattern.models.Region

/**
 *  usage:  view model for Region-Metric, applies business logic to data retrieved from DataRepo
 *  @author Nikhil Gupta
 */
class RegionViewModel(application: Application) : AndroidViewModel(application) {
    private val metricRepo: DataRepository

    init {
        val dao = MyDatabase.getInstance(application).getDao()
        metricRepo = DataRepository.getInstance()
        metricRepo.setDao(dao)
    }

    fun getRegionsFromRepo(zoneName: String): LiveData<List<Region>> {
        return metricRepo.getAllRegionInAsc(zoneName)
    }

    var regionsList: List<Region>? = null

    fun getReverseOrderedRegionList(): List<Region>? {
        regionsList = regionsList?.asReversed() ?: regionsList
        return regionsList
    }

}