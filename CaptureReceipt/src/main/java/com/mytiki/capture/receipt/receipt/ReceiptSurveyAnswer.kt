/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.capture.receipt.receipt

import com.microblink.core.SurveyAnswer
import org.json.JSONObject

/**
 * Represents a response (RSP) for a survey answer.
 *
 * @property SurveyAnswer The underlying survey answer to be represented.
 */
class ReceiptSurveyAnswer(surveyAnswer: SurveyAnswer) {
    private val id: Int
    private val text: String?
    private val nextQuestionIndex: Int?

    init {
        id = surveyAnswer.serverId()
        text = surveyAnswer.text()
        nextQuestionIndex = surveyAnswer.nextQuestionIndex()
    }

    /**
     * Converts the RSP survey answer object to a JSON object.
     *
     * @return The JSON representation of the RSP survey answer.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("id", id)
            .put("text", text)
            .put("nextQuestionIndex", nextQuestionIndex)

    companion object {
        /**
         * Creates an optional RSP survey answer from a given survey answer.
         *
         * @param surveyAnswer The survey answer to create an RSP survey answer from.
         * @return An optional RSP survey answer, or null if the input is null.
         */
        fun opt(surveyAnswer: SurveyAnswer?): ReceiptSurveyAnswer? =
            if (surveyAnswer != null) ReceiptSurveyAnswer(
                surveyAnswer
            ) else null
    }
}
