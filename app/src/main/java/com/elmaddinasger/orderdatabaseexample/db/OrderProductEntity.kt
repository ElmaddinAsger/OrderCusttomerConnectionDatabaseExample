package com.elmaddinasger.orderdatabaseexample.db

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "OrderProductEntity",
    foreignKeys = [
        ForeignKey(
            entity = ProductEntity::class,
            parentColumns = ["productId"],
            childColumns = ["productId"],
            ForeignKey.CASCADE
        ),
    ForeignKey(
        entity = OrderEntity::class,
        parentColumns = ["orderId"],
        childColumns = ["orderId"],
        ForeignKey.CASCADE
    )
    ])
data class OrderProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val orderId: Long,
    val productId: Long,
)
