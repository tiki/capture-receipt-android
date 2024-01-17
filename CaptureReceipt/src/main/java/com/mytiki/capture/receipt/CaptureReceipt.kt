/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.capture.receipt

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.mytiki.capture.receipt.email.Email
import com.mytiki.capture.receipt.email.EmailType
import com.mytiki.capture.receipt.license.LicenseService
import com.mytiki.capture.receipt.physical.Physical
import com.mytiki.tiki_sdk_android.trail.License
import com.mytiki.tiki_sdk_android.trail.TitleRecord
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
/**
 * The CaptureReceipt object provides methods to interact with the TIKI Capture Receipt SDK.
 */
object CaptureReceipt {

    val email: Email = Email()
    val physical: Physical = Physical()
    val license: LicenseService = LicenseService()


    /**
     * Initialize the Capture Receipt SDK.
     *
     * @param userId The user's unique identifier.
     * @param options Configuration options for the SDK.
     */
    fun initialize(userId: String, context: Context, onException: (Throwable) -> Unit) {

    }

    /**
     * Initiates a scan for a physical receipt using the device's camera.
     *
     * @param context The Android application context.
     * @param onReceipt A callback function to execute when a receipt is successfully scanned.
     *                 It provides the scanned Receipt object as a parameter.
     * @param onError A callback function to execute if there is an error during the scanning process.
     *                It provides an Error object as a parameter.
     * @param onComplete A callback function to execute when the scanning process is completed,
     *                   whether successful or not.
     *
     * @see Receipt An example of a Receipt object.
     * @see Error An example of an Error object.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    fun scan(
        activity: Activity,
    ){
        MainScope().async {
                physical.scan(activity)
        }
    }


    /**
     * Log in to an account for receipt data retrieval.
     *
     * @param context The Android application context.
     * @param username The username for the account.
     * @param password The password for the account.
     * @param accountType The type of account, e.g., Gmail or Retailer.
     * @param onSuccess A callback function to execute on successful login, providing the account information.
     * @param onError A callback function to execute if there is an error during login.
     */
    fun login(
        emailType: EmailType,
        onSuccess: (String) -> Unit,
        onError: (String) -> Unit
    ) {
           email.login(emailType, { onSuccess(it) }) { onError(it) }
    }

    /**
     * Retrieve a list of connected accounts.
     *
     * @return A list of connected accounts.
     */
    fun accounts(
        context: Context,
        onError: (msg: String) -> Unit,
    ): List<String>? {
        return email.accounts(context){ onError(it) }
    }

    /**
     * Log out of an account.
     *
     * @param context The Android application context.
     * @param username The username of the account to log out from.
     * @param onSuccess A callback function to execute on successful logout.
     * @param onError A callback function to execute if there is an error during logout.
     */
    fun logout(
        context: Context,
        emailType: EmailType,
        username: String,
        onError: (String) -> Unit
    ): Boolean {
         return email.logout(context, emailType, username) { onError(it) }
    }

    fun logout(
        context: Context,
        onError: (String) -> Unit
    ): Boolean {
        return email.flush(context) { onError(it) }
    }

    /**
     * Retrieve digital receipt data for a specific account type.
     *
     * This function allows you to retrieve receipt data from all accounts of an account type,
     * such as email or retailer accounts, from the user's connected accounts.
     *
     * @param context The Android application context.
     * @param accountCommon The type of account for which to retrieve receipt data.
     * @param onReceipt A callback function to execute for each retrieved receipt.
     * It provides the retrieved Receipt object as a parameter.
     * @param onError A callback function to execute if there is an error during data retrieval.
     * It provides an Error object as a parameter, allowing you to handle errors gracefully.
     * @param onComplete A callback function to execute when the data retrieval process is completed.
     * This function can be used to perform any cleanup or additional actions
     * once the retrieval process is finished.
     */
    fun scrape(
        context: Context,
        onEmail: () -> Unit,
        onError: (msg: String) -> Unit,
        onComplete: () -> Unit
    ) {
        email.scrape(context, onEmail, onError, onComplete)
    }


    /**
     * Retrieve digital receipt data for a specific account.
     *
     * This function allows you to retrieve digital scrape associated with a specific account
     * from the user's connected accounts.
     *
     * @param context The Android application context.
     * @param account The account for which you wish to retrieve receipt data.
     * @param onReceipt A callback function executed for each retrieved receipt,
     * providing the retrieved Receipt object as a parameter.
     * @param onError A callback function executed in case of an error during data retrieval,
     * offering an Error object for error details.
     * @param onComplete A callback function executed upon the completion of the data retrieval process.
     */
    fun scrape(
        context: Context,
        emailType: EmailType,
        username: String,
        onEmail: () -> Unit,
        onError: (String) -> Unit,
        onComplete: () -> Unit
    ) {
        email.scrape(context, emailType, username, onEmail, onError, onComplete)
    }



}
