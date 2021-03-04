package com.example.drilldownpattern.activities

/**
 * Usage: This activity is used to display the Area-Metric, It overrides recycler-view-item click listener to listen to RV callbacks
 * @author Nikhil Gupta
 */

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.drilldownpattern.MetricViewAdapter
import com.example.drilldownpattern.OnRVItemClickListener
import com.example.drilldownpattern.R
import com.example.drilldownpattern.Utils
import com.example.drilldownpattern.emuns.MetricName
import com.example.drilldownpattern.emuns.MetricType
import com.example.drilldownpattern.models.Area
import com.example.drilldownpattern.models.Metric
import com.example.drilldownpattern.viewmodels.AreaViewModel
import kotlinx.android.synthetic.main.activity_common_ui_metric.*

class AreaMetricActivity : AppCompatActivity(), OnRVItemClickListener {

    private lateinit var viewModel: AreaViewModel
    private lateinit var adapter: MetricViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_ui_metric)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        viewModel = ViewModelProvider(this).get(AreaViewModel::class.java)

        val metricName = intent.getStringExtra(Utils.KEY_SELECTED_METRIC_NAME) ?: ""
        val territoryName = intent.getStringExtra(Utils.KEY_SELECTED_TERRITORY_NAME) ?: ""

        if (metricName.isBlank() or territoryName.isBlank()) {
            Toast.makeText(this, "Invalid metric selected, Please try again!", Toast.LENGTH_LONG).show()
            finish()
        }

        metric_name.text = getString(R.string.metric_performance, metricName)

        recycler_view.layoutManager = LinearLayoutManager(this)

        adapter = MetricViewAdapter(this)
        adapter.setUpMetricName(MetricName.Area.name)
        adapter.setUpMetricType(MetricType.Area)
        recycler_view.adapter = adapter

        if (viewModel.areasList == null) {
            viewModel.getAreasFromRepo(territoryName).observe(this, {
                viewModel.areasList = it
                adapter.setUpList(it)
            })
        } else {
            adapter.setUpList(viewModel.areasList!!)
        }
    }

    override fun onRVMetricHeaderClick() {
        viewModel.getReverseOrderedAreaList()?.let { adapter.setUpList(it) }
    }

    override fun onRVMetricItemClick(metric: Metric) {
        val area: Area = metric as Area
        val intent = Intent(this, EmployeeMetricActivity::class.java)
        intent.putExtra(Utils.KEY_SELECTED_METRIC_NAME, Utils.capitalize(area.name))
        intent.putExtra(Utils.KEY_SELECTED_TERRITORY_NAME, area.territory)
        startActivity(intent)
    }

}