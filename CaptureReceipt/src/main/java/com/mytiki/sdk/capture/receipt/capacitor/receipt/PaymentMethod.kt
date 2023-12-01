/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.microblink.core.PaymentMethod
import org.json.JSONObject

/**
 * Represents a Receipt Scanning Plugin Payment Method for TIKI SDK.
 *
 * This class wraps the [PaymentMethod] class provided by MicroBlink
 * and provides a structured representation for payment-related information
 * extracted from a receipt.
 *
 * @param paymentMethod The [PaymentMethod] instance to be wrapped.
 */
class PaymentMethod(paymentMethod: PaymentMethod) {
    private val paymentMethod: CaptureStringType?
    private val cardType: CaptureStringType?
    private val cardIssuer: CaptureStringType?
    private val amount: CaptureFloatType?

    init {
        this.paymentMethod = CaptureStringType.opt(paymentMethod.paymentMethod())
        cardType = CaptureStringType.opt(paymentMethod.cardType())
        cardIssuer = CaptureStringType.opt(paymentMethod.cardIssuer())
        amount = CaptureFloatType.opt(paymentMethod.amount())
    }

    /**
     * Converts the [PaymentMethod] instance to a JSON representation.
     *
     * @return A [JSONObject] containing the payment method information.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("paymentMethod", paymentMethod?.toJS())
            .put("cardType", cardType?.toJS())
            .put("cardIssuer", cardIssuer?.toJS())
            .put("amount", amount?.toJS())

    companion object {
        /**
         * Creates an [PaymentMethod] instance from a [PaymentMethod] instance,
         * or returns null if the input is null.
         *
         * @param paymentMethod The [PaymentMethod] instance to be converted.
         * @return An [PaymentMethod] instance if [paymentMethod] is not null; otherwise, null.
         */
        fun opt(paymentMethod: PaymentMethod?): com.mytiki.sdk.capture.receipt.capacitor.receipt.PaymentMethod? =
            if (paymentMethod != null) PaymentMethod(paymentMethod) else null
    }
}
