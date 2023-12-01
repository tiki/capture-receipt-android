/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

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
class Receipt(scanResults: ScanResults){

    private val receiptDate: CaptureStringType?
    private val receiptTime: CaptureStringType?
    private val retailerId: RetailerReceipt
    private val products: List<Product>
    private val coupons: List<Coupon>
    private val total: CaptureFloatType?
    private val tip: CaptureFloatType?
    private val subtotal: CaptureFloatType?
    private val taxes: CaptureFloatType?
    private val storeNumber: CaptureStringType?
    private val merchantName: CaptureStringType?
    private val storeAddress: CaptureStringType?
    private val storeCity: CaptureStringType?
    private val blinkReceiptId: String?
    private val storeState: CaptureStringType?
    private val storeZip: CaptureStringType?
    private val storeCountry: CaptureStringType?
    private val storePhone: CaptureStringType?
    private val cashierId: CaptureStringType?
    private val transactionId: CaptureStringType?
    private val registerId: CaptureStringType?
    private val paymentMethods: List<PaymentMethod>
    private val taxId: CaptureStringType?
    private val mallName: CaptureStringType?
    private val last4cc: CaptureStringType?
    private val ocrConfidence: Float
    private val merchantSource: String?
    private val foundTopEdge: Boolean
    private val foundBottomEdge: Boolean
    private val eReceiptOrderNumber: String?
    private val eReceiptOrderStatus: String?
    private val eReceiptRawHtml: String?
    private val eReceiptShippingAddress: String?
    private val shipments: List<CaptureShipment>
    private val longTransactionId: CaptureStringType?
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
    private val merchantMatchGuess: CaptureStringType?
    private val productsPendingLookup: Int
    private val qualifiedPromotions: List<CapturePromotion>
    private val unqualifiedPromotions: List<CapturePromotion>
    private val extendedFields: JSONObject?
    private val eReceiptAdditionalFees: JSONObject?
    private val purchaseType: CaptureStringType?
    private val loyaltyForBanner: Boolean
    private val channel: CaptureStringType?
    private val submissionDate: Long?
    private val eReceiptFulfilledBy: String?
    private val eReceiptShippingStatus: String?
    private val eReceiptPOSSystem: String?
    private val eReceiptSubMerchant: String?
    private val qualifiedSurveys: List<CaptureSurvey>
    private val barcode: String?
    private val eReceiptMerchantEmail: String?
    private val eReceiptEmailSubject: String?
    private val eReceiptShippingCosts: Float
    private val currencyCode: String?
    private val clientMerchantName: CaptureStringType?
    private val loyaltyProgram: Boolean
    private val merchantSources: List<Int>
    private val paymentTerminalId: CaptureStringType?
    private val paymentTransactionId: CaptureStringType?
    private val combinedRawText: CaptureStringType?

    init {
        // Initialize properties with data from scanResults
        receiptDate = CaptureStringType.opt(scanResults.receiptDate())
        receiptTime = CaptureStringType.opt(scanResults.receiptTime())
        retailerId = RetailerReceipt(scanResults.retailerId())
        products = scanResults.products()?.map { product -> Product(product) } ?: emptyList()
        coupons = scanResults.coupons()?.map { coupon -> Coupon(coupon) } ?: emptyList()
        total = CaptureFloatType.opt(scanResults.total())
        tip = CaptureFloatType.opt(scanResults.tip())
        subtotal = CaptureFloatType.opt(scanResults.subtotal())
        taxes = CaptureFloatType.opt(scanResults.taxes())
        storeNumber = CaptureStringType.opt(scanResults.storeNumber())
        merchantName = CaptureStringType.opt(scanResults.merchantName())
        storeAddress = CaptureStringType.opt(scanResults.storeAddress())
        storeCity = CaptureStringType.opt(scanResults.storeCity())
        blinkReceiptId = scanResults.blinkReceiptId()
        storeState = CaptureStringType.opt(scanResults.storeState())
        storeZip = CaptureStringType.opt(scanResults.storeZip())
        storeCountry = CaptureStringType.opt(scanResults.storeCountry())
        storePhone = CaptureStringType.opt(scanResults.storePhone())
        cashierId = CaptureStringType.opt(scanResults.cashierId())
        transactionId = CaptureStringType.opt(scanResults.transactionId())
        registerId = CaptureStringType.opt(scanResults.registerId())
        paymentMethods =
            scanResults.paymentMethods()?.map { paymentMethod -> PaymentMethod(paymentMethod) }
                ?: emptyList()
        taxId = CaptureStringType.opt(scanResults.taxId())
        mallName = CaptureStringType.opt(scanResults.mallName())
        last4cc = CaptureStringType.opt(scanResults.last4cc())
        ocrConfidence = scanResults.ocrConfidence()
        merchantSource = scanResults.merchantSource()
        foundTopEdge = scanResults.foundTopEdge()
        foundBottomEdge = scanResults.foundBottomEdge()
        eReceiptOrderNumber = scanResults.eReceiptOrderNumber()
        eReceiptOrderStatus = scanResults.eReceiptOrderStatus()
        eReceiptRawHtml = scanResults.eReceiptRawHtml()
        eReceiptShippingAddress = scanResults.eReceiptShippingAddress()
        shipments =
            scanResults.shipments()?.map { shipment -> CaptureShipment(shipment) } ?: emptyList()
        longTransactionId = CaptureStringType.opt(scanResults.longTransactionId())
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
        merchantMatchGuess = CaptureStringType.opt(scanResults.merchantMatchGuess())
        productsPendingLookup = scanResults.productsPendingLookup()
        qualifiedPromotions =
            scanResults.qualified()?.map { promotion -> CapturePromotion(promotion) } ?: emptyList()
        unqualifiedPromotions =
            scanResults.unqualified()?.map { promotion -> CapturePromotion(promotion) } ?: emptyList()
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
        purchaseType = CaptureStringType.opt(scanResults.purchaseType())
        loyaltyForBanner = scanResults.loyaltyForBanner()
        channel = CaptureStringType.opt(scanResults.channel())
        submissionDate = scanResults.submissionDate()?.time
        eReceiptFulfilledBy = scanResults.eReceiptFulfilledBy()
        eReceiptShippingStatus = scanResults.eReceiptShippingStatus()
        eReceiptPOSSystem = scanResults.eReceiptPOSSystem()
        eReceiptSubMerchant = scanResults.eReceiptSubMerchant()
        qualifiedSurveys =
            scanResults.qualifiedSurveys()?.map { survey -> CaptureSurvey(survey) } ?: emptyList()
        barcode = scanResults.barcode()
        eReceiptMerchantEmail = scanResults.eReceiptMerchantEmail()
        eReceiptEmailSubject = scanResults.eReceiptEmailSubject()
        eReceiptShippingCosts = scanResults.eReceiptShippingCosts()
        currencyCode = scanResults.currencyCode()
        clientMerchantName = CaptureStringType.opt(StringType(scanResults.clientMerchantName()))
        loyaltyProgram = scanResults.loyaltyProgram()
        merchantSources = scanResults.merchantSources() ?: emptyList()
        paymentTerminalId = CaptureStringType.opt(scanResults.paymentTerminalId())
        paymentTransactionId = CaptureStringType.opt(scanResults.paymentTransactionId())
        combinedRawText = CaptureStringType.opt(scanResults.combinedRawText())
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
        .put("products", JSONArray(products.map { prd -> prd.toJS() }))
        .put("coupons", JSONArray(coupons.map { coupon -> coupon.toJS() }))
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
        .put("paymentMethods", JSONArray(paymentMethods.map { method -> method.toJS() }))
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
