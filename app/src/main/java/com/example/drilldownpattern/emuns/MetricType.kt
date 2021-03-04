package com.example.drilldownpattern.emuns

/**
 *  usage: enum for type in int for each metric
 *  @author Nikhil Gupta
 */
enum class MetricType(val type: Int) {
    Country(1),
    ZONE(2),
    Region(3),
    Area(4),
    Employee(5)
}