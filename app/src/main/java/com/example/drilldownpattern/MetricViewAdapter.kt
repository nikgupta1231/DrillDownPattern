package com.example.drilldownpattern

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.drilldownpattern.emuns.MetricType
import com.example.drilldownpattern.emuns.MetricViewType
import com.example.drilldownpattern.models.*

class MetricViewAdapter(private val listener: OnRVItemClickListener) : RecyclerView.Adapter<MetricViewHolder>() {

    private val items: ArrayList<Metric> by lazy {
        ArrayList()
    }
    private lateinit var metricName: String
    private lateinit var metricType: MetricType

    fun setUpList(items: List<Metric>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    fun setUpMetricName(name: String) {
        metricName = name
    }

    fun setUpMetricType(type: MetricType) {
        metricType = type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetricViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.metric_entity, parent, false)
        val viewHolder = MetricViewHolder(view)
        when (viewType) {
            MetricViewType.HEADER.type -> {
                viewHolder.itemView.setBackgroundColor(Color.BLACK)
                viewHolder.metricNameTextView.setTextColor(Color.WHITE)
                viewHolder.itemView.setOnClickListener { listener.onRVMetricHeaderClick() }
            }
            else -> {
                viewHolder.itemView.setOnClickListener { listener.onRVMetricItemClick(items[viewHolder.adapterPosition - 1]) }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MetricViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.metricNameTextView.text = metricName
            }
            else -> {
                when (metricType) {
                    MetricType.Country -> holder.metricNameTextView.text = (items[position - 1] as Country).name
                    MetricType.ZONE -> holder.metricNameTextView.text = (items[position - 1] as Zone).name
                    MetricType.Region -> holder.metricNameTextView.text = (items[position - 1] as Region).name
                    MetricType.Area -> holder.metricNameTextView.text = (items[position - 1] as Area).name
                    MetricType.Employee -> holder.metricNameTextView.text = (items[position - 1] as Employee).name
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return MetricViewType.HEADER.type
        return MetricViewType.METRIC_ITEM.type
    }

}