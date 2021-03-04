package com.example.drilldownpattern

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class NetworkQueue(context: Context) {

    companion object {
        @Volatile
        var INSTANCE: NetworkQueue? = null
        fun getInstance(context: Context) = INSTANCE ?: synchronized(this) {
            INSTANCE ?: NetworkQueue(context).also {
                INSTANCE = it
            }
        }
    }

    val requestQueue: RequestQueue by lazy {
        Volley.newRequestQueue(context)
    }

    fun <T> addToQueue(req: Request<T>) {
        requestQueue.add(req)
    }

}