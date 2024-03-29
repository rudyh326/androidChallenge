package com.example.tedmobchallenge.network

import com.example.tedmobchallenge.database.DatabaseProduct
import com.example.tedmobchallenge.domain.Product
import kotlinx.serialization.Serializable

@Serializable
data class NetworkProduct(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: String,
    val image: String
)

fun List<NetworkProduct>.asDomainModel(): List<Product> {
    return map {
        Product(
            id = it.id,
            title = it.title,
            price = it.price,
            description = it.description,
            category = it.category,
            image = it.image
        )
    }
}

fun List<NetworkProduct>.asDatabaseModel(): Array<DatabaseProduct> {
    return map {
        DatabaseProduct(
            id = it.id,
            title = it.title,
            price = it.price,
            description = it.description,
            category = it.category,
            image = it.image)
    }.toTypedArray()
}
