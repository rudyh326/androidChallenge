package com.example.tedmobchallenge.screens.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.tedmobchallenge.database.getDatabase
import com.example.tedmobchallenge.domain.Product
import com.example.tedmobchallenge.repository.ProductsRepository
import com.example.tedmobchallenge.utils.ParcelableProduct
import com.example.tedmobchallenge.utils.toParcelable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductListViewModel(application: Application)  : AndroidViewModel(application) {

    private val _products = MutableStateFlow<List<Product>>(emptyList())

    val products: StateFlow<List<Product>>
        get() = _products

    val error = MutableStateFlow<String?>("")

    private val _navigateToSelectedProduct = MutableLiveData<ParcelableProduct?>()

    val navigateToSelectedProduct: LiveData<ParcelableProduct?>
        get() = _navigateToSelectedProduct

    private val database = getDatabase(application)
    private val productsRepository = ProductsRepository(database)

    init {
        refreshProducts()
        getFreshProducts()
        handleErrors()
    }

    private fun refreshProducts() {
        viewModelScope.launch {
            productsRepository.getNewProducts()
        }
    }

    private fun getFreshProducts() {
        viewModelScope.launch {
            productsRepository.freshProducts.collect{
                _products.value = it
            }
        }
    }

    private fun handleErrors() {
        viewModelScope.launch {
            productsRepository.error.collect{
                if (products.value.isEmpty()) {
                    error.value = it
                }
                else error.value = ""
            }
        }
    }

    fun displayProductDetails(product: Product) {
        _navigateToSelectedProduct.value = product.toParcelable()
    }

    fun displayProductDetailsComplete() {
        _navigateToSelectedProduct.value = null
    }

}