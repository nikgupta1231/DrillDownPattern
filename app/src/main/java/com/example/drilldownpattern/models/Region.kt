package com.example.drilldownpattern.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  usage:  data class for Region metric, used to store Region-Metric-Info in below defined schema to Database
 *  @author Nikhil Gupta
 */
@Entity(tableName = "region_info")
data class Region(
    override var name: String,
    override var territory: String,
    override var parentTerritory: String
) : Metric() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}