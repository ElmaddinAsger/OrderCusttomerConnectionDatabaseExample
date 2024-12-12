package com.elmaddinasger.orderdatabaseexample.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ProductDao {
    @Query("Select * from ProductTable")
    fun getAll () : List<ProductEntity>
    @Insert
    fun insert(product: ProductEntity): Long
}