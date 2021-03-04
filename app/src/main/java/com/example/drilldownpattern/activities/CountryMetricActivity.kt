package com.example.drilldownpattern.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.drilldownpattern.R
import com.example.drilldownpattern.Utils
import com.example.drilldownpattern.models.Country
import com.example.drilldownpattern.viewmodels.CountryViewModel
import kotlinx.android.synthetic.main.activity_country_metric.*

/**
 * Usage: This activity is used to display the Country-Metric, It doesn't contain the RV because of the business logic
 * @author Nikhil Gupta
 */
class CountryMetricActivity : AppCompatActivity() {

    private lateinit var viewModel: CountryViewModel
    lateinit var country: Country
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_metric)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel = ViewModelProvider(this).get(CountryViewModel::class.java)

        viewModel.getCountriesFromRepo().observe(this, {
            country = it[0]
        })

        performByZoneBtn.setOnClickListener {
            val intent = Intent(this, ZoneMetricActivity::class.java)
            intent.putExtra(Utils.KEY_SELECTED_METRIC_NAME, Utils.capitalize(country.name))
            intent.putExtra(Utils.KEY_SELECTED_TERRITORY_NAME, country.territory)
            startActivity(intent)
        }

    }

}