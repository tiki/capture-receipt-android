/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.capture.receipt.email

import android.content.Context
import com.microblink.core.ScanResults
import com.microblink.digital.ImapClient

typealias OnReceiptCallback = ((receipt: ScanResults?) -> Unit)

/**
 * Class that handles all email-related logic.
 *
 * @constructor Creates an empty Email.
 */
class Email(
) {
    private var googleAPIKey: String? = null
    private var outlookAPIKey: String? = null


    /**
     * Logs in to the email provider using [ImapClient].
     *
     * @param username The username.
     * @param password The password.
     * @param id The id (email provider).
     * @param supportFragmentManager The fragment manager.
     * @param onComplete Callback called when the login is completed successfully.
     * @param onError Callback called when an error occurs during login.
     */
    fun login(
        emailType: EmailType,
        onComplete: ((String) -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
    }


    /**
     * Scrapes receipts from the email using [ImapClient].
     *
     * @param context The application context.
     * @param onReceipt Callback called for each collected receipt.
     * @param onError Callback called when an error occurs during scraping.
     * @param onComplete Callback called when scrap is com=
     */
    fun scrape(
        context: Context,
        onEmail: () -> Unit,
        onError: (msg: String) -> Unit,
        onComplete: () -> Unit
    ) {

    }

    fun scrape(
        context: Context,
        emailType: EmailType,
        username: String,
        onEmail: () -> Unit,
        onError: (String) -> Unit,
        onComplete: () -> Unit
    ) {

    }

    /**
     * Retrieves a list of email accounts logged using [ImapClient].
     *
     * @param context The application context.
     * @param onAccount Callback called for each retrieved email account.
     * @param onError Callback called when an error occurs during account retrieval.
     */
    fun accounts(
        context: Context,
        onError: ((msg: String) -> Unit),
    ): List<String>? {
        return null
    }

    /**
     * Removes an email account.
     *
     * This function allows the removal of an specific email account in [ImapClient].
     *
     * @param context The application context.
     * @param account The email account information to be removed.
     * @param onRemove Callback called when the account is successfully removed.
     * @param onError Callback called when an error occurs during account removal.
     */
    fun logout(
        context: Context,
        emailType: EmailType,
        username: String,
        onError: (String) -> Unit
    ): Boolean {
        return true
    }

    /**
     * Logs out of all email accounts.
     *
     * @param context The application context.
     * @param onComplete Callback called when the logout is completed successfully.
     * @param onError Callback called when an error occurs during logout.
     */
    fun flush(context: Context, onError: (msg: String) -> Unit): Boolean {
        return true
    }
}
