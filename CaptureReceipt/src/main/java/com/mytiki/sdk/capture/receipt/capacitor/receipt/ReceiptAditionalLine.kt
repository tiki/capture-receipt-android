/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.microblink.core.AdditionalLine
import org.json.JSONObject

/**
 * Represents a parsed additional line from a receipt. This class is part of the TIKI Inc.
 * SDK for receipt capture.
 *
 * @property type The type of the additional line.
 * @property text The text content of the additional line.
 * @property lineNumber The line number of the additional line within the receipt.
 */
class ReceiptAditionalLine(additionalLine: AdditionalLine) {
    private val type: ReceiptStringType? = ReceiptStringType.opt(additionalLine.type())
    private val text: ReceiptStringType? = ReceiptStringType.opt(additionalLine.text())
    private val lineNumber: Int = additionalLine.lineNumber()

    /**
     * Converts this `ReceiptAditionalLine` object to a JSON representation.
     *
     * @return A `JSONObject` representing the additional line.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("type", type?.toJS())
            .put("text", text?.toJS())
            .put("lineNumber", lineNumber)

    companion object {
        /**
         * Creates an `ReceiptAditionalLine` object from an `ReceiptAditionalLine` if the input is not null.
         *
         * @param additionalLine The `ReceiptAditionalLine` to convert.
         * @return An `ReceiptAditionalLine` object, or null if the input is null.
         */
        fun opt(additionalLine: AdditionalLine?): ReceiptAditionalLine? =
            if (additionalLine != null) ReceiptAditionalLine(additionalLine) else null
    }
}
