package com.example.tedmobchallenge.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.tedmobchallenge.database.ProductsDatabase
import com.example.tedmobchallenge.database.asDomainModel
import com.example.tedmobchallenge.domain.Product
import com.example.tedmobchallenge.network.NetworkProduct
import com.example.tedmobchallenge.network.ProductApi
import com.example.tedmobchallenge.network.asDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException

class ProductsRepository(private val database: ProductsDatabase) {

    val freshProducts: Flow<List<Product>> =
        database.productDao.getProducts().map {
            it.asDomainModel()
        }

    val error = MutableStateFlow<String?>("")

    suspend fun getNewProducts(){
        withContext(Dispatchers.IO) {
            ProductApi.fetchProducts(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    Log.e(this.toString(), e.printStackTrace().toString())
                    error.value = e.message
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData = response.body?.string()
                    val jsonParser = Json { ignoreUnknownKeys = true }
                    val products = responseData?.let { jsonParser.decodeFromString<List<NetworkProduct>>(it) }
                    if (products != null) {
                        database.productDao.insertProduct(*products.asDatabaseModel())
                    }
                }
            })
        }
    }

}
