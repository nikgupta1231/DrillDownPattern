package com.example.drilldownpattern

import java.util.*

class Utils {
    companion object {

        const val KEY_SELECTED_METRIC_NAME = "metric_name"
        const val KEY_SELECTED_TERRITORY_NAME = "territory_name"

        const val DATA_API = "http://demo1929804.mockable.io/assignment"

        const val SHARED_PREF_NAME = "METRIC_SP"
        const val SHARED_PREF_IS_DOWNLOADED_DATA_AVAILABLE = "IS_DOWNLOADED_DATA_AVAIL"

        fun capitalize(string: String): String {
            return string[0].toUpperCase().plus(string.subSequence(1, string.length).toString().toLowerCase(Locale.ROOT))
        }


        const val JSON_KEY_RESPONSE_DATA = "ResponseData"
        const val JSON_KEY_COUNTRY = "country"
        const val JSON_KEY_ZONE = "zone"
        const val JSON_KEY_REGION = "region"
        const val JSON_KEY_AREA = "area"
        const val JSON_KEY_EMPLOYEE = "employee"
        const val JSON_KEY_TERRITORY = "territory"
        const val JSON_KEY_NAME = "name"

    }
}