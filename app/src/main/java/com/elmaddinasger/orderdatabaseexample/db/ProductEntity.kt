package com.elmaddinasger.orderdatabaseexample.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName ="ProductTable")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val productId: Long,
    val productName: String,
    val quantity: Long
)
