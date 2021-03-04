package com.example.drilldownpattern.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  usage:  data class for Employee metric, used to store Employee-Metric-Info in below defined schema to Database
 *  @author Nikhil Gupta
 */
@Entity(tableName = "employee_info")
data class Employee(
    override var name: String,
    override var territory: String,
    override var parentTerritory: String
) : Metric() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
