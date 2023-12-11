/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.capture.receipt.receipt

import com.microblink.core.Survey
import org.json.JSONArray
import org.json.JSONObject

/**
 * Represents a survey response for the TIKI SDK.
 *
 * @param survey The [Survey] object containing survey data.
 */
class ReceiptSurvey(survey: Survey) {

    /**
     * The client's user ID for the survey.
     */
    private val clientUserId: String?

    /**
     * The server ID of the survey.
     */
    private val serverId: Int

    /**
     * The slug associated with the survey.
     */
    private val slug: String?

    /**
     * The reward value associated with the survey.
     */
    private val rewardValue: String?

    /**
     * The start date of the survey.
     */
    private val startDate: String?

    /**
     * The end date of the survey.
     */
    private val endDate: String?

    /**
     * The list of survey questions.
     */
    private val questions: List<ReceiptSurveyQuestion>

    init {
        clientUserId = survey.clientUserId()
        serverId = survey.serverId()
        slug = survey.slug()
        rewardValue = survey.rewardValue()
        startDate = survey.startDate()
        endDate = survey.endDate()
        questions =
            survey.questions()?.map { question -> ReceiptSurveyQuestion(question) } ?: emptyList()
    }

    /**
     * Converts the survey response to a JSON representation.
     *
     * @return A [JSONObject] containing the survey response data.
     */
    fun toJS(): JSONObject =
        JSONObject()
            .put("clientUserId", clientUserId)
            .put("serverId", serverId)
            .put("slug", slug)
            .put("rewardValue", rewardValue)
            .put("startDate", startDate)
            .put("endDate", endDate)
            .put("questions", JSONArray(questions.map { question -> question.toJS() }))

    companion object {

        /**
         * Creates an instance of [Survey] from a [Survey] object.
         *
         * @param survey The [Survey] object to convert.
         * @return An [Survey] object representing the survey response, or `null` if the input is `null`.
         */
        fun opt(survey: Survey?): ReceiptSurvey? =
            if (survey != null) ReceiptSurvey(survey) else null
    }
}
