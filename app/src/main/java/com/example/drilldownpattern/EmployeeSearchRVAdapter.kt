package com.example.drilldownpattern

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.drilldownpattern.emuns.MetricName
import com.example.drilldownpattern.emuns.MetricViewType
import com.example.drilldownpattern.models.Employee
import java.util.*
import kotlin.collections.ArrayList

class EmployeeSearchRVAdapter(private val listener: OnRVItemClickListener) : RecyclerView.Adapter<MetricViewHolder>(), Filterable {

    private var employeeList: ArrayList<Employee> = ArrayList()
    private var filteredEmployeeList: ArrayList<Employee> = ArrayList()

    fun setUpLists(filteredList: List<Employee>, originalList: List<Employee>) {
        this.employeeList.clear()
        this.employeeList.addAll(originalList)
        this.filteredEmployeeList.clear()
        this.filteredEmployeeList.addAll(filteredList)
        notifyDataSetChanged()
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
                viewHolder.itemView.setOnClickListener { listener.onRVMetricItemClick(filteredEmployeeList[viewHolder.adapterPosition - 1]) }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: MetricViewHolder, position: Int) {
        when (position) {
            0 -> {
                holder.metricNameTextView.text = MetricName.Employee.name
            }
            else -> {
                holder.metricNameTextView.text = filteredEmployeeList[position - 1].name
            }
        }
    }

    override fun getItemCount(): Int {
        return filteredEmployeeList.size + 1
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) return MetricViewType.HEADER.type
        return MetricViewType.METRIC_ITEM.type
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charInput = constraint.toString()
                filteredEmployeeList = if (charInput.isEmpty()) employeeList
                else {
                    val resultList = ArrayList<Employee>()
                    for (employee in employeeList) {
                        if (employee.name.toLowerCase(Locale.ROOT).contains(charInput.toLowerCase(Locale.ROOT))) {
                            resultList.add(employee)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = filteredEmployeeList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredEmployeeList = results?.values as ArrayList<Employee>
                notifyDataSetChanged()
                filteredListChangeListener.onListChange(filteredEmployeeList)
            }
        }
    }

    lateinit var filteredListChangeListener: OnFilteredListChangeListener
    fun setOnFilteredListPublishListener(listener: OnFilteredListChangeListener) {
        filteredListChangeListener = listener
    }

    interface OnFilteredListChangeListener {
        fun onListChange(filteredList: List<Employee>)
    }

}