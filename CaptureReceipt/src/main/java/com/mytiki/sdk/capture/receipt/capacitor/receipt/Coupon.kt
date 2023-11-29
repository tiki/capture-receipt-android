package com.mytiki.sdk.capture.receipt.capacitor.receipt

data class Coupon(
    val couponType: CouponType,
    val amount: FloatType,
    val sku: StringType,
    val description: StringType,
    val relatedProductIndex: Int,
)
