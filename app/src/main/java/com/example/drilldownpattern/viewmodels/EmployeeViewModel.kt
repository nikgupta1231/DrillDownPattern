package com.example.drilldownpattern.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.drilldownpattern.DataRepository
import com.example.drilldownpattern.MyDatabase
import com.example.drilldownpattern.models.Employee

/**
 *  usage:  view model for Employee-Metric, applies business logic to data retrieved from DataRepo
 *  @author Nikhil Gupta
 */
class EmployeeViewModel(application: Application) : AndroidViewModel(application) {

    private val metricRepo: DataRepository

    init {
        val dao = MyDatabase.getInstance(application).getDao()
        metricRepo = DataRepository.getInstance()
        metricRepo.setDao(dao)
    }

    fun getEmployeesFromRepo(areaName: String): LiveData<List<Employee>> {
        return metricRepo.getAllEmployeeInAsc(areaName)
    }

    var originalEmployeeList: List<Employee>? = null
    var filteredEmployeeList: List<Employee>? = null

    fun getReverseOrderedEmployeeList(): List<Employee>? {
        filteredEmployeeList = filteredEmployeeList?.asReversed() ?: filteredEmployeeList
        return filteredEmployeeList
    }

}