package com.example.tedmobchallenge.utils

import android.content.Context
import android.os.Parcelable
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tedmobchallenge.domain.Product
import kotlinx.parcelize.Parcelize

fun Product.toParcelable(): ParcelableProduct {
    return ParcelableProduct(
        id,
        title,
        price,
        description,
        category,
        image
    )
}

@Parcelize
data class ParcelableProduct(
    val id : Int,
    val title : String,
    val price : Double,
    val description : String,
    val category : String,
    val image: String) : Parcelable


fun hideKeyboard(view: View) {
    val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun String.onlyLettersAndNumbers() = all { it.isLetterOrDigit() }