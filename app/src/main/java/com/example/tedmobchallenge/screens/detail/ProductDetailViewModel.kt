package com.example.tedmobchallenge.screens.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tedmobchallenge.utils.ParcelableProduct

class ProductDetailViewModel(parcelableProduct: ParcelableProduct, application: Application) : AndroidViewModel(application) {
    private val _selectedProduct = MutableLiveData<ParcelableProduct>()

    val selectedProduct: LiveData<ParcelableProduct>
        get() = _selectedProduct

    init {
        _selectedProduct.value = parcelableProduct
    }
}