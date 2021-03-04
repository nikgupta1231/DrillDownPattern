package com.example.drilldownpattern.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.drilldownpattern.DataRepository
import com.example.drilldownpattern.MyDatabase
import com.example.drilldownpattern.models.Country

/**
 *  usage:  view model for Country-Metric, applies business logic to data retrieved from DataRepo
 *  @author Nikhil Gupta
 */
class CountryViewModel(application: Application) : AndroidViewModel(application) {

    private val metricRepo: DataRepository

    init {
        val dao = MyDatabase.getInstance(application).getDao()
        metricRepo = DataRepository.getInstance()
        metricRepo.setDao(dao)
    }

    fun getCountriesFromRepo(): LiveData<List<Country>> {
        return metricRepo.getAllCountriesInAsc()
    }

    var countryList: List<Country>? = null

    fun getReverseOrderedCountryList(): List<Country>? {
        countryList = countryList?.asReversed() ?: countryList
        return countryList
    }

}