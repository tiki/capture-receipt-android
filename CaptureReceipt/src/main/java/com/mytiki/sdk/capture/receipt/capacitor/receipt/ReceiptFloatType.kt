/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.microblink.core.FloatType
import org.json.JSONObject

/**
 * Represents a response (Rsp) for a floating-point value extracted from a receipt.
 *
 * @property confidence The confidence level of the extracted floating-point value.
 * @property value The extracted floating-point value.
 */
class ReceiptFloatType(floatType: FloatType) {
    private val confidence: Float = floatType.confidence()
    private val value: Float = floatType.value()

    /**
     * Converts the ReceiptFloatType object to a JSON representation.
     *
     * @return A JSONObject containing the confidence and value of the floating-point type.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("confidence", confidence)
            .put("value", value)

    companion object {
        /**
         * Creates an ReceiptFloatType instance from a FloatType object, if it is not null.
         *
         * @param floatType The FloatType object to convert to ReceiptFloatType.
         * @return An ReceiptFloatType instance or null if the input floatType is null.
         */
        fun opt(floatType: FloatType?): ReceiptFloatType? =
            if (floatType != null) ReceiptFloatType(floatType) else null
    }
}
