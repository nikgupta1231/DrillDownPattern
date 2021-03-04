package com.example.drilldownpattern

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.drilldownpattern.models.*

@Dao
interface Dao {

    @Insert
    fun addCountries(countries: List<Country>)

    @Query("SELECT * FROM country_info ORDER BY name ASC")
    fun getAllCountriesInAsc(): LiveData<List<Country>>


    @Insert
    fun addZones(zones: List<Zone>)

    @Query("SELECT * FROM zone_info WHERE parentTerritory = :name ORDER BY name ASC")
    fun getAllZonesByCountryNameInAsc(name: String): LiveData<List<Zone>>


    @Insert
    fun addRegions(regions: List<Region>)

    @Query("SELECT * FROM region_info WHERE parentTerritory = :name ORDER BY name ASC")
    fun getAllRegionsByZoneNameInAsc(name: String): LiveData<List<Region>>


    @Insert
    fun addAreas(areas: List<Area>)

    @Query("SELECT * FROM area_info WHERE parentTerritory = :name ORDER BY name ASC")
    fun getAllAreasByRegionNameInAsc(name: String): LiveData<List<Area>>


    @Insert
    fun addEmployees(employees: List<Employee>)

    @Query("SELECT * FROM employee_info WHERE parentTerritory = :areaName ORDER BY name ASC")
    fun getAllEmployeesByAreaNameInAsc(areaName: String): LiveData<List<Employee>>

}