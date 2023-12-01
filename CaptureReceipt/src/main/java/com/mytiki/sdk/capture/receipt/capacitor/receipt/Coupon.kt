/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.microblink.core.Coupon
import org.json.JSONObject

/**
 * Represents a parsed coupon extracted from a receipt during the RSP (Receipt Scanning Processor) process.
 *
 * @property coupon The [Coupon] object containing coupon information.
 */
class Coupon(coupon: Coupon) {
    private val type: String?
    private val amount: CaptureFloatType?
    private val sku: CaptureStringType?
    private val description: CaptureStringType?
    private val relatedProductIndex: Int

    init {
        type = coupon.typeToString()
        amount = CaptureFloatType.opt(coupon.amount())
        sku = CaptureStringType.opt(coupon.sku())
        description = CaptureStringType.opt(coupon.description())
        relatedProductIndex = coupon.relatedProductIndex()
    }

    /**
     * Converts the [Coupon] object to a JSON representation.
     *
     * @return A [JSONObject] containing the coupon information.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("type", type)
            .put("amount", amount?.toJS() ?: JSONObject())
            .put("sku", sku?.toJS())
            .put("description", description?.toJS())
            .put("relatedProductIndex", relatedProductIndex)

    companion object {
        /**
         * Creates an optional [Coupon] object from a [Coupon].
         *
         * @param coupon The [Coupon] object to convert.
         * @return An [Coupon] object if [coupon] is not null, otherwise null.
         */
        fun opt(coupon: Coupon?): com.mytiki.sdk.capture.receipt.capacitor.receipt.Coupon? =
            if (coupon != null) Coupon(coupon) else null
    }
}
