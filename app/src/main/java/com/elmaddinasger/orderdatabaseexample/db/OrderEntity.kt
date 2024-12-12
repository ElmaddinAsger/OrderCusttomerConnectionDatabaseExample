package com.elmaddinasger.orderdatabaseexample.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "OrderTable",
    foreignKeys = [
        ForeignKey(
            entity =CustomerEntity::class,
            parentColumns = ["customerId"],
            childColumns = ["customerId"],
            ForeignKey.CASCADE
        )
    ]
)
data class OrderEntity(
    @PrimaryKey(autoGenerate = true)
    val orderId: Long,
    val customerId: Long,
)
