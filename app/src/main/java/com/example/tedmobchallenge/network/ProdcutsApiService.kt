package com.example.tedmobchallenge.network

import com.example.tedmobchallenge.BuildConfig
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request

class MyOkHttpClient {

    private val client = OkHttpClient()

    fun fetchData(endpoint: String, callback: Callback): Call {
        val request = Request.Builder()
            .url(BuildConfig.BASE_URL + endpoint)
            .build()

        val call = client.newCall(request)
        call.enqueue(callback)
        return call
    }
}

object ProductApi {

    private val myOkHttpClient = MyOkHttpClient()

    fun fetchProducts(callback: Callback) = myOkHttpClient.fetchData("products", callback = callback)
}