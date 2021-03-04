package com.example.drilldownpattern.activities

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drilldownpattern.EmployeeSearchRVAdapter
import com.example.drilldownpattern.OnRVItemClickListener
import com.example.drilldownpattern.R
import com.example.drilldownpattern.Utils
import com.example.drilldownpattern.models.Employee
import com.example.drilldownpattern.models.Metric
import com.example.drilldownpattern.viewmodels.EmployeeViewModel
import kotlinx.android.synthetic.main.activity_employee_metric.*


/**
 * Usage:   This activity is used to display the Employee-Metric, it takes area name as parameter.
 *          Search is implemented with filterable.
 *          Employee names can be searched in Search view, to view all the employees close btn should be pressed.
 *          Configuration is retained for the original employee list and filtered employee list with the help of EmployeeViewModel
 * @author Nikhil Gupta
 */

class EmployeeMetricActivity : AppCompatActivity(), OnRVItemClickListener, SearchView.OnQueryTextListener, EmployeeSearchRVAdapter.OnFilteredListChangeListener, View.OnClickListener {

    private lateinit var viewModel: EmployeeViewModel
    private lateinit var adapter: EmployeeSearchRVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_metric)

        //configuring custom toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        //configuring Area name
        val metricName = intent.getStringExtra(Utils.KEY_SELECTED_METRIC_NAME) ?: ""
        val territoryName: String = intent.getStringExtra(Utils.KEY_SELECTED_TERRITORY_NAME) ?: ""
        if (metricName.isBlank() or territoryName.isBlank()) {
            Toast.makeText(this, "Invalid metric selected, Please try again!", Toast.LENGTH_LONG).show()
            finish()
        }
        metric_name.text = getString(R.string.metric_performance, metricName)

        viewModel = ViewModelProvider(this).get(EmployeeViewModel::class.java)

        recycler_view.layoutManager = LinearLayoutManager(this)
        adapter = EmployeeSearchRVAdapter(this)
        recycler_view.adapter = adapter
        adapter.setOnFilteredListPublishListener(this)

        if (viewModel.filteredEmployeeList == null) {
            viewModel.getEmployeesFromRepo(territoryName).observe(this, {
                viewModel.filteredEmployeeList = it
                viewModel.originalEmployeeList = it
                adapter.setUpLists(it, it)
            })
        } else {
            viewModel.originalEmployeeList?.let { adapter.setUpLists(viewModel.filteredEmployeeList!!, it) }
        }

        search_view.setOnQueryTextListener(this)
        val closeButton: ImageView = search_view.findViewById(androidx.appcompat.R.id.search_close_btn) as ImageView
        closeButton.setOnClickListener(this)
    }

    /**
     *  overriding for listening header click, and performing reordering of filtered employee list
     */
    override fun onRVMetricHeaderClick() {
        viewModel.getReverseOrderedEmployeeList()?.let {
            adapter.setUpLists(it, viewModel.originalEmployeeList!!)
        }
    }

    override fun onRVMetricItemClick(metric: Metric) {
        //doing nothing on click as no further hierarchy is present
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return false
    }

    /**
     *  overriding for listening user input, and filtering employee list accordingly
     */
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText?.isNotEmpty() == true) {
            adapter.filter.filter(newText)
        }
        return false
    }

    /**
     *  overriding for listening filtered employee list, so that it can be retained with view-model
     *  so that ui can be retained after configuration change
     */
    override fun onListChange(filteredList: List<Employee>) {
        viewModel.filteredEmployeeList = filteredList
    }

    /**
     *  overriding for listening callback for close btn from searchView to clear the searchView and filtered list
     */
    override fun onClick(v: View?) {
        search_view.setQuery("", false)
        search_view.clearFocus()
        adapter.setUpLists(viewModel.originalEmployeeList!!, viewModel.originalEmployeeList!!)
    }

}