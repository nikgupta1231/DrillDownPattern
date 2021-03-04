package com.example.drilldownpattern

import com.example.drilldownpattern.models.Metric

interface OnRVItemClickListener {
    fun onRVMetricHeaderClick()                 //recycler-view header click callback
    fun onRVMetricItemClick(metric: Metric)     //recycler-view item click callback
}