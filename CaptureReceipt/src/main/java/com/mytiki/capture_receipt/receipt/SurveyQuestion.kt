package com.mytiki.capture_receipt.receipt

data class SurveyQuestion(
    val myIndex: Int,
    val isLastQuestion: Boolean,
    val nextQuestionIndex: Int,
    val serverId: Int,
    val text: String,
    val type: SurveyQuestionType,
    val answers: List<SurveyAnswer>,
    val multipleAnswers: Boolean,
    val totalNumberOfQuestions: Int,
)