package com.example.drilldownpattern

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MetricViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val metricNameTextView: TextView = itemView.findViewById(R.id.metric_name)
}