package com.elmaddinasger.orderdatabaseexample.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderProductDao {
    @Query("Select * from OrderProductEntity")
    fun getAll() : List<OrderProductEntity>
    @Insert
    fun insert(order: OrderProductEntity): Long
}