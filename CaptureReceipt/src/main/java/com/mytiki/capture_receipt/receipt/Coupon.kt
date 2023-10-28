package com.mytiki.capture_receipt.receipt

data class Coupon(
val couponType: CouponType,
val amount: FloatType,
val sku: StringType,
val description: StringType,
val relatedProductIndex: Int,
)
