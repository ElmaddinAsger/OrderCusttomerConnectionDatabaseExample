package com.elmaddinasger.orderdatabaseexample.adabter

data class ProductModel(
    val productId: Long,
    val productName: String,
    val productQuantity: Long,
    var productState: Boolean,
    var productCount: Long = 0
)
