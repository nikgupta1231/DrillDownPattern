package com.example.drilldownpattern.models

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 *  usage:  data class for Area metric, used to store Area-Metric-Info in below defined schema to Database
 *  @author Nikhil Gupta
 */
@Entity(tableName = "area_info")
data class Area(
    override var name: String,
    override var territory: String,
    override var parentTerritory: String
) : Metric() {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
