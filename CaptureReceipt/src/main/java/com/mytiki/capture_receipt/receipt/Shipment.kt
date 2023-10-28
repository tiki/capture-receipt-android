package com.mytiki.capture_receipt.receipt

data class Shipment(
    val status: String,
    val products: List<Product>
)
