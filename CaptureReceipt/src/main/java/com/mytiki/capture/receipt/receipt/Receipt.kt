/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.capture.receipt.receipt

import com.microblink.core.ScanResults
import com.microblink.core.StringType
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a Receipt Scan Processor (RSP) Receipt.
 *
 * This class encapsulates various data extracted from a scanned receipt using the RSP library.
 *
 * @param scanResults The scan results containing the data to populate this receipt.
 */
class Receipt(scanResults: ScanResults) {

    private val receiptDate: ReceiptStringType?
    private val receiptTime: ReceiptStringType?
    private val retailerId: ReceiptRetailer
    private val receiptProducts: List<ReceiptProduct>
    private val receiptCoupons: List<ReceiptCoupon>
    private val total: ReceiptFloatType?
    private val tip: ReceiptFloatType?
    private val subtotal: ReceiptFloatType?
    private val taxes: ReceiptFloatType?
    private val storeNumber: ReceiptStringType?
    private val merchantName: ReceiptStringType?
    private val storeAddress: ReceiptStringType?
    private val storeCity: ReceiptStringType?
    private val blinkReceiptId: String?
    private val storeState: ReceiptStringType?
    private val storeZip: ReceiptStringType?
    private val storeCountry: ReceiptStringType?
    private val storePhone: ReceiptStringType?
    private val cashierId: ReceiptStringType?
    private val transactionId: ReceiptStringType?
    private val registerId: ReceiptStringType?
    private val receiptPaymentMethods: List<ReceiptPaymentMethod>
    private val taxId: ReceiptStringType?
    private val mallName: ReceiptStringType?
    private val last4cc: ReceiptStringType?
    private val ocrConfidence: Float
    private val merchantSource: String?
    private val foundTopEdge: Boolean
    private val foundBottomEdge: Boolean
    private val eReceiptOrderNumber: String?
    private val eReceiptOrderStatus: String?
    private val eReceiptRawHtml: String?
    private val eReceiptShippingAddress: String?
    private val shipments: List<ReceiptShipment>
    private val longTransactionId: ReceiptStringType?
    private val subtotalMatches: Boolean
    private val eReceiptEmailProvider: String?
    private val eReceiptEmailId: String?
    private val eReceiptAuthenticated: Boolean
    private val instacartShopper: Boolean
    private val eReceipt: Boolean
    private val eReceiptComponentEmails: List<Receipt>
    private val duplicate: Boolean
    private val fraudulent: Boolean
    private val receiptDateTime: Long?
    private val duplicateBlinkReceiptIds: List<String>
    private val merchantMatchGuess: ReceiptStringType?
    private val productsPendingLookup: Int
    private val qualifiedPromotions: List<ReceiptPromotion>
    private val unqualifiedPromotions: List<ReceiptPromotion>
    private val extendedFields: JSONObject?
    private val eReceiptAdditionalFees: JSONObject?
    private val purchaseType: ReceiptStringType?
    private val loyaltyForBanner: Boolean
    private val channel: ReceiptStringType?
    private val submissionDate: Long?
    private val eReceiptFulfilledBy: String?
    private val eReceiptShippingStatus: String?
    private val eReceiptPOSSystem: String?
    private val eReceiptSubMerchant: String?
    private val qualifiedSurveys: List<ReceiptSurvey>
    private val barcode: String?
    private val eReceiptMerchantEmail: String?
    private val eReceiptEmailSubject: String?
    private val eReceiptShippingCosts: Float
    private val currencyCode: String?
    private val clientMerchantName: ReceiptStringType?
    private val loyaltyProgram: Boolean
    private val merchantSources: List<Int>
    private val paymentTerminalId: ReceiptStringType?
    private val paymentTransactionId: ReceiptStringType?
    private val combinedRawText: ReceiptStringType?

    init {
        // Initialize properties with data from scanResults
        receiptDate = ReceiptStringType.opt(scanResults.receiptDate())
        receiptTime = ReceiptStringType.opt(scanResults.receiptTime())
        retailerId = ReceiptRetailer(scanResults.retailerId())
        receiptProducts =
            scanResults.products()?.map { product -> ReceiptProduct(product) } ?: emptyList()
        receiptCoupons =
            scanResults.coupons()?.map { coupon -> ReceiptCoupon(coupon) } ?: emptyList()
        total = ReceiptFloatType.opt(scanResults.total())
        tip = ReceiptFloatType.opt(scanResults.tip())
        subtotal = ReceiptFloatType.opt(scanResults.subtotal())
        taxes = ReceiptFloatType.opt(scanResults.taxes())
        storeNumber = ReceiptStringType.opt(scanResults.storeNumber())
        merchantName = ReceiptStringType.opt(scanResults.merchantName())
        storeAddress = ReceiptStringType.opt(scanResults.storeAddress())
        storeCity = ReceiptStringType.opt(scanResults.storeCity())
        blinkReceiptId = scanResults.blinkReceiptId()
        storeState = ReceiptStringType.opt(scanResults.storeState())
        storeZip = ReceiptStringType.opt(scanResults.storeZip())
        storeCountry = ReceiptStringType.opt(scanResults.storeCountry())
        storePhone = ReceiptStringType.opt(scanResults.storePhone())
        cashierId = ReceiptStringType.opt(scanResults.cashierId())
        transactionId = ReceiptStringType.opt(scanResults.transactionId())
        registerId = ReceiptStringType.opt(scanResults.registerId())
        receiptPaymentMethods =
            scanResults.paymentMethods()
                ?.map { paymentMethod -> ReceiptPaymentMethod(paymentMethod) }
                ?: emptyList()
        taxId = ReceiptStringType.opt(scanResults.taxId())
        mallName = ReceiptStringType.opt(scanResults.mallName())
        last4cc = ReceiptStringType.opt(scanResults.last4cc())
        ocrConfidence = scanResults.ocrConfidence()
        merchantSource = scanResults.merchantSource()
        foundTopEdge = scanResults.foundTopEdge()
        foundBottomEdge = scanResults.foundBottomEdge()
        eReceiptOrderNumber = scanResults.eReceiptOrderNumber()
        eReceiptOrderStatus = scanResults.eReceiptOrderStatus()
        eReceiptRawHtml = scanResults.eReceiptRawHtml()
        eReceiptShippingAddress = scanResults.eReceiptShippingAddress()
        shipments =
            scanResults.shipments()?.map { shipment -> ReceiptShipment(shipment) } ?: emptyList()
        longTransactionId = ReceiptStringType.opt(scanResults.longTransactionId())
        subtotalMatches = scanResults.subtotalMatches()
        eReceiptEmailProvider = scanResults.eReceiptEmailProvider()
        eReceiptEmailId = scanResults.eReceiptEmailId()
        eReceiptAuthenticated = scanResults.eReceiptAuthenticated()
        instacartShopper = scanResults.isInstacartShopper
        eReceipt = scanResults.eReceipt()
        eReceiptComponentEmails =
            scanResults.eReceiptComponentEmails()?.map { res -> Receipt(res) }
                ?: emptyList()
        duplicate = scanResults.duplicate()
        fraudulent = scanResults.fraudulent()
        receiptDateTime = scanResults.receiptDateTime()?.time
        duplicateBlinkReceiptIds = scanResults.duplicateBlinkReceiptIds() ?: emptyList()
        merchantMatchGuess = ReceiptStringType.opt(scanResults.merchantMatchGuess())
        productsPendingLookup = scanResults.productsPendingLookup()
        qualifiedPromotions =
            scanResults.qualified()?.map { promotion -> ReceiptPromotion(promotion) } ?: emptyList()
        unqualifiedPromotions =
            scanResults.unqualified()?.map { promotion -> ReceiptPromotion(promotion) }
                ?: emptyList()
        extendedFields = if (scanResults.extendedFields() != null) {
            val extendedFields = JSONObject()
            scanResults.extendedFields()
                ?.forEach { entry -> extendedFields.put(entry.key, entry.value) }
            extendedFields
        } else null
        eReceiptAdditionalFees = if (scanResults.eReceiptAdditionalFees() != null) {
            val additionalFees = JSONObject()
            scanResults.eReceiptAdditionalFees()
                ?.forEach { entry -> additionalFees.put(entry.key, entry.value) }
            additionalFees
        } else null
        purchaseType = ReceiptStringType.opt(scanResults.purchaseType())
        loyaltyForBanner = scanResults.loyaltyForBanner()
        channel = ReceiptStringType.opt(scanResults.channel())
        submissionDate = scanResults.submissionDate()?.time
        eReceiptFulfilledBy = scanResults.eReceiptFulfilledBy()
        eReceiptShippingStatus = scanResults.eReceiptShippingStatus()
        eReceiptPOSSystem = scanResults.eReceiptPOSSystem()
        eReceiptSubMerchant = scanResults.eReceiptSubMerchant()
        qualifiedSurveys =
            scanResults.qualifiedSurveys()?.map { survey -> ReceiptSurvey(survey) } ?: emptyList()
        barcode = scanResults.barcode()
        eReceiptMerchantEmail = scanResults.eReceiptMerchantEmail()
        eReceiptEmailSubject = scanResults.eReceiptEmailSubject()
        eReceiptShippingCosts = scanResults.eReceiptShippingCosts()
        currencyCode = scanResults.currencyCode()
        clientMerchantName = ReceiptStringType.opt(StringType(scanResults.clientMerchantName()))
        loyaltyProgram = scanResults.loyaltyProgram()
        merchantSources = scanResults.merchantSources() ?: emptyList()
        paymentTerminalId = ReceiptStringType.opt(scanResults.paymentTerminalId())
        paymentTransactionId = ReceiptStringType.opt(scanResults.paymentTransactionId())
        combinedRawText = ReceiptStringType.opt(scanResults.combinedRawText())
    }

    /**
     * Converts the RSP receipt data to a JSON object.
     *
     * @return A JSONObject containing the RSP receipt data.
     */
    fun toJS(): JSONObject = JSONObject()
        .put("receiptDate", receiptDate?.toJS())
        .put("receiptTime", receiptTime?.toJS())
        .put("retailerId", retailerId.toJS())
        .put("receiptProducts", JSONArray(receiptProducts.map { prd -> prd.toJS() }))
        .put("receiptCoupons", JSONArray(receiptCoupons.map { coupon -> coupon.toJS() }))
        .put("total", total?.toJS())
        .put("tip", tip?.toJS())
        .put("subtotal", subtotal?.toJS())
        .put("taxes", taxes?.toJS())
        .put("storeNumber", storeNumber?.toJS())
        .put("merchantName", merchantName?.toJS())
        .put("storeAddress", storeAddress?.toJS())
        .put("storeCity", storeCity?.toJS())
        .put("blinkReceiptId", blinkReceiptId)
        .put("storeState", storeState?.toJS())
        .put("storeZip", storeZip?.toJS())
        .put("storeCountry", storeCountry?.toJS())
        .put("storePhone", storePhone?.toJS())
        .put("cashierId", cashierId?.toJS())
        .put("transactionId", transactionId?.toJS())
        .put("registerId", registerId?.toJS())
        .put(
            "receiptPaymentMethods",
            JSONArray(receiptPaymentMethods.map { method -> method.toJS() })
        )
        .put("taxId", taxId?.toJS())
        .put("mallName", mallName?.toJS())
        .put("last4cc", last4cc?.toJS())
        .put("ocrConfidence", ocrConfidence)
        .put("merchantSource", merchantSource)
        .put("foundTopEdge", foundTopEdge)
        .put("foundBottomEdge", foundBottomEdge)
        .put("eReceiptOrderNumber", eReceiptOrderNumber)
        .put("eReceiptOrderStatus", eReceiptOrderStatus)
        .put("eReceiptRawHtml", eReceiptRawHtml)
        .put("eReceiptShippingAddress", eReceiptShippingAddress)
        .put("shipments", JSONArray(shipments.map { shipment -> shipment.toJS() }))
        .put("longTransactionId", longTransactionId?.toJS())
        .put("subtotalMatches", subtotalMatches)
        .put("eReceiptEmailProvider", eReceiptEmailProvider)
        .put("eReceiptEmailId", eReceiptEmailId)
        .put("eReceiptAuthenticated", eReceiptAuthenticated)
        .put("instacartShopper", instacartShopper)
        .put("eReceipt", eReceipt)
        .put(
            "eReceiptComponentEmails",
            JSONArray(eReceiptComponentEmails.map { email -> email.toJS() })
        )
        .put("duplicate", duplicate)
        .put("fraudulent", fraudulent)
        .put("receiptDateTime", receiptDateTime)
        .put("duplicateBlinkReceiptIds", JSONArray(duplicateBlinkReceiptIds))
        .put("merchantMatchGuess", merchantMatchGuess?.toJS())
        .put("productsPendingLookup", productsPendingLookup)
        .put(
            "qualifiedPromotions",
            JSONArray(qualifiedPromotions.map { promo -> promo.toJS() })
        )
        .put(
            "unqualifiedPromotions",
            JSONArray(unqualifiedPromotions.map { promo -> promo.toJS() })
        )
        .put("extendedFields", extendedFields)
        .put("eReceiptAdditionalFees", eReceiptAdditionalFees)
        .put("purchaseType", purchaseType?.toJS())
        .put("loyaltyForBanner", loyaltyForBanner)
        .put("channel", channel?.toJS())
        .put("submissionDate", submissionDate)
        .put("eReceiptFulfilledBy", eReceiptFulfilledBy)
        .put("eReceiptShippingStatus", eReceiptShippingStatus)
        .put("eReceiptPOSSystem", eReceiptPOSSystem)
        .put("eReceiptSubMerchant", eReceiptSubMerchant)
        .put(
            "qualifiedSurveys",
            JSONArray(qualifiedSurveys.map { survey -> survey.toJS() })
        )
        .put("barcode", barcode)
        .put("eReceiptMerchantEmail", eReceiptMerchantEmail)
        .put("eReceiptEmailSubject", eReceiptEmailSubject)
        .put("eReceiptShippingCosts", eReceiptShippingCosts)
        .put("currencyCode", currencyCode)
        .put("clientMerchantName", clientMerchantName?.toJS())
        .put("loyaltyProgram", loyaltyProgram)
        .put("merchantSources", JSONArray(merchantSources))
        .put("paymentTerminalId", paymentTerminalId?.toJS())
        .put("paymentTransactionId", paymentTransactionId?.toJS())
        .put("combinedRawText", combinedRawText?.toJS())

    companion object {
        /**
         * Creates an optional RSP receipt from the provided scan results.
         *
         * @param scanResults The scan results to create an RSP receipt from.
         * @return An optional RSPReceipt instance, or null if scanResults is null.
         */
        fun opt(scanResults: ScanResults?): Receipt? =
            if (scanResults != null) Receipt(scanResults) else null
    }
}
