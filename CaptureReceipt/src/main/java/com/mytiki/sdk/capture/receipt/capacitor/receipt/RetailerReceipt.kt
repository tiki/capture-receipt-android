/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.microblink.core.Retailer
import org.json.JSONObject

/**
 * Represents a Retailer for the Receipt Scanning Plugin (RSP).
 *
 * @param retailer The retailer object to be wrapped by RetailerReceipt.
 */
class RetailerReceipt(retailer: Retailer) {
    private val id: Int
    private val bannerId: Int

    init {
        id = retailer.id()
        bannerId = retailer.bannerId()
    }

    /**
     * Converts the RetailerReceipt object to a JSON representation.
     *
     * @return A JSON object representing the RetailerReceipt.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("id", id)
            .put("bannerId", bannerId)

    companion object {
        /**
         * Create an optional RetailerReceipt object from a Retailer object.
         *
         * @param retailer The Retailer object to be converted.
         * @return An RetailerReceipt object if the input Retailer is not null, otherwise null.
         */
        fun opt(retailer: Retailer?): RetailerReceipt? =
            if (retailer != null) RetailerReceipt(retailer) else null
    }
}
