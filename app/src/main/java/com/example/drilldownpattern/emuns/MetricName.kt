package com.example.drilldownpattern.emuns

/**
 * Usage: enum for setting the metric name in performance header
 * @author Nikhil Gupta
 */
enum class MetricName(val type: String) {
    Country("Country"),
    Zone("Zone"),
    Region("Region"),
    Area("Area"),
    Employee("Employee")
}