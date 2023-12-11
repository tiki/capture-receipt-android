/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.capture.receipt.receipt

import com.microblink.core.Shipment
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a Receipt ReceiptShipment for response purposes.
 *
 * This class encapsulates information about a shipment from a receipt, including its status
 * and the list of receiptProducts in the shipment.
 *
 * @param shipment The [Shipment] object to be converted to an ReceiptShipment.
 */
class ReceiptShipment(shipment: Shipment) {
    private val status: String? = shipment.status()
    private val receiptProducts: List<ReceiptProduct> = shipment.products().map { product -> ReceiptProduct(product) }

    /**
     * Converts the ReceiptShipment object to a JSON representation.
     *
     * @return A [JSONObject] containing the status and receiptProducts of the shipment.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("status", status)
            .put("receiptProducts", JSONArray(receiptProducts.map { prd -> prd.toJS() }))

    companion object {
        /**
         * Creates an ReceiptShipment object if the provided [shipment] is not null.
         *
         * @param shipment The [Shipment] object to be converted to an ReceiptShipment.
         * @return An [Shipment] object if [shipment] is not null; otherwise, null.
         */
        fun opt(shipment: Shipment?): ReceiptShipment? =
            if (shipment != null) ReceiptShipment(shipment) else null
    }
}
