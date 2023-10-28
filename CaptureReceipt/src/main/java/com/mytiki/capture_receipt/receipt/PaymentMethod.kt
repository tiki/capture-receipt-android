package com.mytiki.capture_receipt.receipt

data class PaymentMethod(
    val paymentMethod: StringType,
    val cardType: StringType,
    val cardIssuer: StringType,
    val amount: FloatType,
)

