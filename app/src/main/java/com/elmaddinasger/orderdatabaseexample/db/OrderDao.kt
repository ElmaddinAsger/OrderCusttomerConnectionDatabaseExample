package com.elmaddinasger.orderdatabaseexample.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface OrderDao {
    @Query("Select * from ORDERTABLE")
    fun getAll(): List<OrderEntity>
    @Insert
    fun insert(order: OrderEntity): Long
}