package com.example.tedmobchallenge.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("select * from databaseproduct")
    fun getProducts(): Flow<List<DatabaseProduct>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(vararg repos: DatabaseProduct)
}

@Database(entities = [DatabaseProduct::class], version = 1)
abstract class ProductsDatabase : RoomDatabase() {
    abstract val productDao: ProductDao
}

private lateinit var INSTANCE: ProductsDatabase

fun getDatabase(context: Context): ProductsDatabase {
    synchronized(ProductsDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                ProductsDatabase::class.java,
                "repositories")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
    return INSTANCE
}
