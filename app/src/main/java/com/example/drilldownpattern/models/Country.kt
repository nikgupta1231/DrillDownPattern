package com.example.drilldownpattern.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  usage:  data class for Country metric, used to store Country-Metric-Info in below defined schema to Database
 *  @author Nikhil Gupta
 */
@Entity(tableName = "country_info")
data class Country(
    override var name: String,
    override var territory: String,
    override var parentTerritory: String
) : Metric() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}