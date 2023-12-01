/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.microblink.core.StringType
import org.json.JSONObject

/**
 * Represents a response object for a string type recognized by the TIKI SDK.
 *
 * @param stringType The [StringType] recognized by the TIKI SDK.
 */
class CaptureStringType(stringType: StringType) {
    /**
     * The confidence score of the recognized string.
     */
    private val confidence: Float

    /**
     * The recognized string value.
     */
    private val value: String?

    init {
        confidence = stringType.confidence()
        value = stringType.value()
    }

    /**
     * Converts the [CaptureStringType] object to a JSON representation.
     *
     * @return A [JSONObject] containing the confidence and value of the recognized string.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("confidence", confidence)
            .put("value", value)

    companion object {
        /**
         * Creates an [CaptureStringType] object from the provided [StringType].
         *
         * @param stringType The [StringType] to convert.
         * @return An [CaptureStringType] object representing the provided [StringType], or null if the input is null.
         */
        fun opt(stringType: StringType?): CaptureStringType? =
            if (stringType != null) CaptureStringType(stringType) else null
    }
}
