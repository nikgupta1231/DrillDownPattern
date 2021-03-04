package com.example.drilldownpattern.models

abstract class Metric {
    abstract var name: String
    abstract var territory: String
    abstract var parentTerritory: String
}