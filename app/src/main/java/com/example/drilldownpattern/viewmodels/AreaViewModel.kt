package com.example.drilldownpattern.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.drilldownpattern.DataRepository
import com.example.drilldownpattern.MyDatabase
import com.example.drilldownpattern.models.Area

/**
 *  usage:  view model for Area-Metric, applies business logic to data retrieved from DataRepo
 *  @author Nikhil Gupta
 */
class AreaViewModel(application: Application) : AndroidViewModel(application) {

    private val metricRepo: DataRepository

    init {
        val dao = MyDatabase.getInstance(application).getDao()
        metricRepo = DataRepository.getInstance()
        metricRepo.setDao(dao)
    }

    fun getAreasFromRepo(regionName: String): LiveData<List<Area>> {
        return metricRepo.getAllAreaInAsc(regionName)
    }

    var areasList: List<Area>? = null

    fun getReverseOrderedAreaList(): List<Area>? {
        areasList = areasList?.asReversed() ?: areasList
        return areasList
    }

}