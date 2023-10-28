package com.mytiki.capture_receipt.receipt

data class Survey(
    val clientUserId: String? = null,
    val serverId: Int = 0,
    val slug: String? = null,
    val rewardValue: String? = null,
    val startDate: String? = null,
    val endDate: String? = null,
    val questions: List<SurveyQuestion>? = null
)
