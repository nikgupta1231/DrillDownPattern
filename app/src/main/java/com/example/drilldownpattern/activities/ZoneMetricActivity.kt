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
import com.example.drilldownpattern.models.Zone
import com.example.drilldownpattern.viewmodels.ZoneViewModel
import kotlinx.android.synthetic.main.activity_common_ui_metric.*

/**
 * Usage: This activity is used to display the Zone-Metric
 * @author Nikhil Gupta
 */


class ZoneMetricActivity : AppCompatActivity(), OnRVItemClickListener {

    private lateinit var viewModel: ZoneViewModel
    private lateinit var adapter: MetricViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_common_ui_metric)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val metricName = intent.getStringExtra(Utils.KEY_SELECTED_METRIC_NAME) ?: ""
        val territoryName = intent.getStringExtra(Utils.KEY_SELECTED_TERRITORY_NAME) ?: ""

        if (metricName.isBlank() || territoryName.isBlank()) {
            Toast.makeText(this, "Invalid metric selected, Please try again!", Toast.LENGTH_LONG).show()
            finish()
        }
        metric_name.text = getString(R.string.metric_performance, metricName)

        viewModel = ViewModelProvider(this).get(ZoneViewModel::class.java)

        recycler_view.layoutManager = LinearLayoutManager(this)

        adapter = MetricViewAdapter(this)
        adapter.setUpMetricName(MetricName.Zone.name)
        adapter.setUpMetricType(MetricType.ZONE)
        recycler_view.adapter = adapter

        if (viewModel.zoneList == null) {
            viewModel.getZonesFromRepo(territoryName).observe(this, {
                viewModel.zoneList = it
                adapter.setUpList(it)
            })
        } else {
            adapter.setUpList(viewModel.zoneList!!)
        }

    }

    override fun onRVMetricHeaderClick() {
        viewModel.getReverseOrderedZones()?.let { adapter.setUpList(it) }
    }

    override fun onRVMetricItemClick(metric: Metric) {
        val zone: Zone = metric as Zone
        val intent = Intent(this, RegionMetricActivity::class.java)
        intent.putExtra(Utils.KEY_SELECTED_METRIC_NAME, Utils.capitalize(zone.name))
        intent.putExtra(Utils.KEY_SELECTED_TERRITORY_NAME, zone.territory)
        startActivity(intent)
    }

}