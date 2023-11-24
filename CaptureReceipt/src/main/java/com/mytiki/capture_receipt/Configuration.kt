package com.mytiki.capture_receipt

data class Configuration(
    val tikiPublishingID: String,
    val microblinkLicenseKey: String,
    val productIntelligenceKey: String,
    val terms: String,
    val gmailAPIKey: String? = null,
    val outlookAPIKey: String? = null,
)
