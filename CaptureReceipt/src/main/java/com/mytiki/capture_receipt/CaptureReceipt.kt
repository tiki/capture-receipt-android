/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.capture_receipt

import android.content.Context
import com.mytiki.capture_receipt.account.Account
import com.mytiki.capture_receipt.account.AccountCommon
import com.mytiki.capture_receipt.config.Configuration
import com.mytiki.capture_receipt.receipt.Receipt
import com.mytiki.capture_receipt.retailer.Retailer
import com.mytiki.sdk.capture.receipt.capacitor.Physical
import com.mytiki.sdk.capture.receipt.capacitor.email.Email
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.trail.License
import com.mytiki.tiki_sdk_android.trail.Tag
import com.mytiki.tiki_sdk_android.trail.TagCommon
import com.mytiki.tiki_sdk_android.trail.TitleRecord
import com.mytiki.tiki_sdk_android.trail.Use
import com.mytiki.tiki_sdk_android.trail.Usecase
import com.mytiki.tiki_sdk_android.trail.UsecaseCommon
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll


/**
 * The CaptureReceipt object provides methods to interact with the TIKI Capture Receipt SDK.
 */
object CaptureReceipt {
    lateinit var configuration: Configuration

    var license: License? = null
    var userId: String? = null
    var title: TitleRecord? = null

//    physical
//    email
//    retailer

    fun config(config: Configuration) {
        configuration = config
    }

    /**
     * Initialize the Capture Receipt SDK.
     *
     * @param userId The user's unique identifier.
     * @param options Configuration options for the SDK.
     */
    fun initialize(userId: String, context: Context, onException: (Exception) -> Unit) {
        val deferredTikiSdk = TikiSdk.initialize(userId, configuration.tikiPublishingID, context)

        val deferredEmail = Email().initialize(
            context,
            configuration.microblinkLicenseKey,
            configuration.productIntelligenceKey
        ) {
            if (it != null) {
                onException(it)
            }
        }
        val deferredRetailer = Retailer().initialize(
            context,
            configuration.microblinkLicenseKey,
            configuration.productIntelligenceKey
        ) {
            if (it != null) {
                onException(it)
            }
        }
        Physical().initialize(
            context,
            configuration.microblinkLicenseKey,
            configuration.productIntelligenceKey
        ) {
            if (it != null) {
                onException(it)
            }
        }
        MainScope().async {
            deferredTikiSdk.await()
            try{
                title = TikiSdk.trail.title.get(userId).await()
            } catch (ex: Exception) {
                onException(ex)
            }

            if (title == null) {
                try {
                    title =
                        TikiSdk.trail.title.create(userId, listOf(Tag(TagCommon.PURCHASE_HISTORY)))
                            .await()
                } catch (ex: Exception) {
                    onException(ex)
                }
            }

            TikiSdk.trail.license.create(
                title!!.id,
                listOf(
                    Use(
                        listOf(Usecase(UsecaseCommon.ANALYTICS)),
                        listOf("*")
                    ),
                ),
                configuration.terms
            )
        }
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
    fun scan(
        context: Context,
        onReceipt: (Receipt) -> Void,
        onError: (Exception) -> Unit,
        onComplete: () -> Void
    ) {
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
        context: Context,
        username: String,
        password: String,
        accountType: AccountCommon,
        onSuccess: (Account) -> Void,
        onError: (Exception) -> Unit
    ) {
    }

    /**
     * Retrieve a list of connected accounts.
     *
     * @return A list of connected accounts.
     */
    fun accounts(): List<Account> {
        return mutableListOf()
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
        username: String,
        onSuccess: () -> Void,
        onError: (Exception) -> Unit
    ) {
    }

    /**
     * Retrieve digital receipt data for a specific account type.
     *
     * This function allows you to retrieve receipt data from all accounts of an account type,
     * such as email or retailer accounts, from the user's connected accounts.
     *
     * @param context The Android application context.
     * @param account The type of account for which to retrieve receipt data.
     * @param onReceipt A callback function to execute for each retrieved receipt.
     * It provides the retrieved Receipt object as a parameter.
     * @param onError A callback function to execute if there is an error during data retrieval.
     * It provides an Error object as a parameter, allowing you to handle errors gracefully.
     * @param onComplete A callback function to execute when the data retrieval process is completed.
     * This function can be used to perform any cleanup or additional actions
     * once the retrieval process is finished.
     */
    fun receipts(
        context: Context,
        account: AccountCommon,
        onReceipt: (Receipt) -> Void,
        onError: (Exception) -> Unit,
        onComplete: () -> Void
    ) {
    }


    /**
     * Retrieve digital receipt data for a specific account.
     *
     * This function allows you to retrieve digital receipts associated with a specific account
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
    fun receipts(
        context: Context,
        account: Account,
        onReceipt: (Receipt) -> Void,
        onError: (Exception) -> Unit,
        onComplete: () -> Void
    ) {
    }


    /**
     * Retrieve digital receipt data for all connected accounts.
     *
     * This function allows you to retrieve digital receipts associated with all of the user's
     * connected accounts. It provides a convenient way to collect receipt data from multiple
     * accounts and simplifies the data retrieval process.
     *
     * @param context The Android application context.
     * @param onReceipt A callback function executed for each retrieved receipt, providing the
     * retrieved Receipt object as a parameter.
     * @param onError A callback function executed in case of an error during data retrieval,
     * offering an Error object for error details.
     * @param onComplete A callback function executed upon the completion of the data retrieval
     * process.
     */
    fun receipts(
        context: Context,
        onReceipt: (Receipt) -> Void,
        onError: (Exception) -> Unit,
        onComplete: () -> Void
    ) {
    }

}
