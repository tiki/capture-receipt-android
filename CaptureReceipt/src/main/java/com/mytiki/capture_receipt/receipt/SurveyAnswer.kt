package com.mytiki.capture_receipt.receipt

data class SurveyAnswer(
    val serverId: Int,
    val text: String?,
    val nextQuestionIndex: Int?
)

