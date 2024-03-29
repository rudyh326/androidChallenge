package com.example.tedmobchallenge.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tedmobchallenge.domain.Product

@Entity
data class DatabaseProduct constructor(
    @PrimaryKey
    val id : Int,
    val title : String,
    val price : Double,
    val description : String,
    val category : String,
    val image: String)

fun List<DatabaseProduct>.asDomainModel(): List<Product> {
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

