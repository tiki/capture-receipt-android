package com.mytiki.capture_receipt.receipt

import java.math.BigDecimal

data class Promotion(
    val id: Long,
    val slug: String,
    val reward: BigDecimal,
    val rewardCurrency: String,
    val errorCode: Int,
    val errorMessage: String,
    val relatedProductIndexes: List<Int>,
    val qualifications: List<List<Int>>,
    val uniqueProductIndexes: Set<Int>,
    val lock: Any,
)
