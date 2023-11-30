package com.mytiki.sdk.capture.receipt.capacitor.receipt

data class PaymentMethod(
    val paymentMethod: StringType,
    val cardType: StringType,
    val cardIssuer: StringType,
    val amount: FloatType,
)

