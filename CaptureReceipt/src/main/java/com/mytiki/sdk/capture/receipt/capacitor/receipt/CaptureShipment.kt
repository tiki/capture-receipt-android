/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.microblink.core.Shipment
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a Receipt CaptureShipment for response purposes.
 *
 * This class encapsulates information about a shipment from a receipt, including its status
 * and the list of products in the shipment.
 *
 * @param shipment The [Shipment] object to be converted to an CaptureShipment.
 */
class CaptureShipment(shipment: Shipment) {
    private val status: String?
    private val products: List<Product>

    /**
     * Initializes an CaptureShipment instance based on the provided [shipment].
     *
     * @param shipment The [CaptureShipment] object to be converted to an CaptureShipment.
     */
    init {
        status = shipment.status()
        products = shipment.products().map { product -> Product(product) }
    }

    /**
     * Converts the CaptureShipment object to a JSON representation.
     *
     * @return A [JSONObject] containing the status and products of the shipment.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("status", status)
            .put("products", JSONArray(products.map { prd -> prd.toJS() }))

    companion object {
        /**
         * Creates an CaptureShipment object if the provided [shipment] is not null.
         *
         * @param shipment The [Shipment] object to be converted to an CaptureShipment.
         * @return An [Shipment] object if [shipment] is not null; otherwise, null.
         */
        fun opt(shipment: Shipment?): CaptureShipment? =
            if (shipment != null) CaptureShipment(shipment) else null
    }
}
