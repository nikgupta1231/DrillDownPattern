package com.example.drilldownpattern.activities

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
import com.example.drilldownpattern.models.Metric
import com.example.drilldownpattern.models.Region
import com.example.drilldownpattern.viewmodels.RegionViewModel
import kotlinx.android.synthetic.main.activity_common_ui_metric.*

/**
 * Usage: This activity is used to display the Region-Metric
 * @author Nikhil Gupta
 */

class RegionMetricActivity : AppCompatActivity(), OnRVItemClickListener {

    private lateinit var viewModel: RegionViewModel
    private lateinit var adapter: MetricViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_ui_metric)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val metricName = intent.getStringExtra(Utils.KEY_SELECTED_METRIC_NAME) ?: ""
        val territoryName = intent.getStringExtra(Utils.KEY_SELECTED_TERRITORY_NAME) ?: ""
        if (metricName.isBlank() or territoryName.isBlank()) {
            Toast.makeText(this, "Invalid metric selected, Please try again!", Toast.LENGTH_LONG).show()
            finish()
        }
        metric_name.text = getString(R.string.metric_performance, metricName)

        viewModel = ViewModelProvider(this).get(RegionViewModel::class.java)

        adapter = MetricViewAdapter(this)
        adapter.setUpMetricName(MetricName.Region.name)
        adapter.setUpMetricType(MetricType.Region)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        if (viewModel.regionsList == null) {
            viewModel.getRegionsFromRepo(territoryName).observe(this, {
                viewModel.regionsList = it
                adapter.setUpList(it)
            })
        } else {
            adapter.setUpList(viewModel.regionsList!!)
        }
    }

    override fun onRVMetricHeaderClick() {
        viewModel.getReverseOrderedRegionList()?.let {
            adapter.setUpList(it)
        }
    }

    override fun onRVMetricItemClick(metric: Metric) {
        val region: Region = metric as Region
        val intent = Intent(this, AreaMetricActivity::class.java)
        intent.putExtra(Utils.KEY_SELECTED_METRIC_NAME, Utils.capitalize(region.name))
        intent.putExtra(Utils.KEY_SELECTED_TERRITORY_NAME, region.territory)
        startActivity(intent)
    }

}