package com.example.drilldownpattern.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  usage:  data class for Zone metric, used to store Zone-Metric-Info in below defined schema to Database
 *  @author Nikhil Gupta
 */
@Entity(tableName = "zone_info")
data class Zone(
    override var name: String,
    override var territory: String,
    override var parentTerritory: String
) : Metric() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}