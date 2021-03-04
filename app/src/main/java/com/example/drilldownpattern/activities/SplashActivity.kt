package com.example.drilldownpattern.activities

import android.content.Intent
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import com.example.drilldownpattern.DataDownloadListener
import com.example.drilldownpattern.DataRepository
import com.example.drilldownpattern.R
import kotlinx.android.synthetic.main.activity_splash.*

/**
 *   usage: First screen of the app.
 *          Fetching the data on initialization and then showing the use to begin
 *          Alerting the user if data download is not successful and prompting the use to retry
 *          User can't proceed until the data is downloaded.
 *   @author Nikhil Gupta
 */

class SplashActivity : AppCompatActivity(), DataDownloadListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val repository = DataRepository.getInstance()
        repository.setDataDownloadListener(this)

        if (repository.isDataAvailable(this)) {
            showInitSuccessUI()
        } else {
            showDataDownloadingUI()
            repository.fetchData(application)
        }

        imageView.setOnClickListener {
            val intent = Intent(this, CountryMetricActivity::class.java)
            startActivity(intent)
            finish()
        }

        retry_btn.setOnClickListener {
            repository.fetchData(application)
        }
    }


    private fun showInitSuccessUI() {
        progress_bar.visibility = GONE
        message_text_view.visibility = GONE
        textView.visibility = VISIBLE
        imageView.visibility = VISIBLE
        retry_btn.visibility = GONE
    }

    private fun showDataDownloadingUI() {
        progress_bar.visibility = VISIBLE
        message_text_view.visibility = VISIBLE
        message_text_view.text = getString(R.string.initialising_app)
        textView.visibility = GONE
        imageView.visibility = GONE
        retry_btn.visibility = GONE
    }

    private fun showDataDownloadingFailedUI() {
        progress_bar.visibility = GONE
        message_text_view.visibility = VISIBLE
        message_text_view.text = getString(R.string.initialising_app_failed)
        retry_btn.visibility = VISIBLE
        textView.visibility = GONE
        imageView.visibility = GONE
    }

    override fun onDataDownloadSuccess() {
        showInitSuccessUI()
    }

    override fun onDataDownloadFailed() {
        showDataDownloadingFailedUI()
    }

}