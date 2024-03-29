package com.example.tedmobchallenge.screens.detail

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tedmobchallenge.utils.ParcelableProduct

class DetailProductViewModelFactory(private val parcelableRepo: ParcelableProduct, private val app: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailViewModel(parcelableRepo, app) as T
        }
        throw IllegalArgumentException("Unable to construct viewmodel")
    }
}

