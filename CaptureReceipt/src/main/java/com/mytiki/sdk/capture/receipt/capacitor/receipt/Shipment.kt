package com.mytiki.sdk.capture.receipt.capacitor.receipt

data class Shipment(
    val status: String,
    val products: List<Product>
)
