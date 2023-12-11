/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.capture.receipt.receipt

import com.microblink.core.Retailer
import org.json.JSONObject

/**
 * Represents a Retailer for the Receipt Scanning Plugin (RSP).
 *
 * @param retailer The retailer object to be wrapped by ReceiptRetailer.
 */
class ReceiptRetailer(retailer: Retailer) {
    private val id: Int
    private val bannerId: Int

    init {
        id = retailer.id()
        bannerId = retailer.bannerId()
    }

    /**
     * Converts the ReceiptRetailer object to a JSON representation.
     *
     * @return A JSON object representing the ReceiptRetailer.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("id", id)
            .put("bannerId", bannerId)

    companion object {
        /**
         * Create an optional ReceiptRetailer object from a Retailer object.
         *
         * @param retailer The Retailer object to be converted.
         * @return An ReceiptRetailer object if the input Retailer is not null, otherwise null.
         */
        fun opt(retailer: Retailer?): ReceiptRetailer? =
            if (retailer != null) ReceiptRetailer(retailer) else null
    }
}
