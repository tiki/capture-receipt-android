/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.receipt

import com.microblink.core.SurveyQuestion
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a survey question in the RSP (Receipt CaptureSurvey Processor) module.
 *
 * @param surveyQuestion The [SurveyQuestion] object from the Microblink SDK.
 */
class CaptureSurveyQuestion(surveyQuestion: SurveyQuestion) {
    private val myIndex: Int
    private val lastQuestion: Boolean
    private val nextQuestionIndex: Int
    private val serverId: Int
    private val text: String?
    private val type: String?
    private val answers: List<CaptureSurveyAnswer>
    private val multipleAnswers: Boolean
    private val totalNumberOfQuestions: Int

    init {
        myIndex = surveyQuestion.myIndex()
        lastQuestion = surveyQuestion.lastQuestion()
        nextQuestionIndex = surveyQuestion.nextQuestionIndex()
        serverId = surveyQuestion.serverId()
        text = surveyQuestion.text()
        type = surveyQuestion.type()?.a
        answers = surveyQuestion.answers()?.map { answer -> CaptureSurveyAnswer(answer) } ?: emptyList()
        multipleAnswers = surveyQuestion.multipleAnswers()
        totalNumberOfQuestions = surveyQuestion.totalNumberOfQuestions()
    }

    /**
     * Converts the CaptureSurveyQuestion object to a JSON representation.
     *
     * @return A [JSONObject] representing the CaptureSurveyQuestion.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("myIndex", myIndex)
            .put("lastQuestion", lastQuestion)
            .put("nextQuestionIndex", nextQuestionIndex)
            .put("serverId", serverId)
            .put("text", text)
            .put("type", type)
            .put("answers", JSONArray(answers.map { answer -> answer.toJS() }))
            .put("multipleAnswers", multipleAnswers)
            .put("totalNumberOfQuestions", totalNumberOfQuestions)

    companion object {
        /**
         * Creates an CaptureSurveyQuestion instance from a [SurveyQuestion] object.
         *
         * @param surveyQuestion The [SurveyQuestion] object to convert.
         * @return An [SurveyQuestion] instance or null if the input is null.
         */
        fun opt(surveyQuestion: SurveyQuestion?): CaptureSurveyQuestion? =
            if (surveyQuestion != null) CaptureSurveyQuestion(
                surveyQuestion
            ) else null
    }
}
