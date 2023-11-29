/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.mytiki.sdk.capture.receipt.capacitor.account.Account
import com.mytiki.sdk.capture.receipt.capacitor.account.AccountCommon
import com.mytiki.sdk.capture.receipt.capacitor.email.Email
import com.mytiki.sdk.capture.receipt.capacitor.receipt.Receipt
import com.mytiki.sdk.capture.receipt.capacitor.retailer.Retailer
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

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tiki-capture-receipt")
/**
 * The CaptureReceipt object provides methods to interact with the TIKI Capture Receipt SDK.
 */
object CaptureReceipt {
    private var configuration: Configuration? = null

    private var license: License? = null
    private var userId: String? = null
    private var title: TitleRecord? = null

    private val email: Email = Email()
    private val retailer: Retailer = Retailer()
    private val physical: Physical = Physical()



    fun config(config: Configuration, onError: (Throwable) -> Unit) {
        if (config.tikiPublishingID.isBlank()){
            onError(Exception("tikiPublishingID cannot be blank"))
        } else if (config.microblinkLicenseKey.isBlank()) {
            onError(Exception("microblinkLicenseKey cannot be blank"))
        } else if (config.productIntelligenceKey.isBlank()) {
            onError(Exception("productIntelligenceKey cannot be blank"))
        } else if (config.terms.isBlank()) {
            onError(Exception("terms cannot be blank"))
        } else {
            configuration = config
        }
    }

    /**
     * Initialize the Capture Receipt SDK.
     *
     * @param userId The user's unique identifier.
     * @param options Configuration options for the SDK.
     */
    fun initialize(userId: String, context: Context, onException: (Throwable) -> Unit) {
        if (configuration != null) {

            email.initialize(
                context,
                configuration!!.microblinkLicenseKey,
                configuration!!.productIntelligenceKey,
                configuration!!.gmailAPIKey,
                configuration!!.outlookAPIKey,
            ) { onException(it) }
            retailer.initialize(
                context,
                configuration!!.microblinkLicenseKey,
                configuration!!.productIntelligenceKey
            ) { onException(it) }
            physical.initialize(
                context,
                configuration!!.microblinkLicenseKey,
                configuration!!.productIntelligenceKey
            ) { onException(it) }

            MainScope().async {
                val deferredTikiSdk =
                    TikiSdk.initialize(userId, configuration!!.tikiPublishingID, context)
                deferredTikiSdk.await()

                try {
                    title = TikiSdk.trail.title.get(userId).await()
                } catch (ex: Exception) {
                    onException(ex)
                }

                if (title == null) {
                    try {
                        title = TikiSdk.trail.title.create(
                            userId,
                            listOf(Tag(TagCommon.PURCHASE_HISTORY))
                        ).await()
                    } catch (ex: Exception) {
                        onException(ex)
                    }
                }

                TikiSdk.trail.license.create(
                    title!!.id,
                    listOf(Use(listOf(Usecase(UsecaseCommon.ANALYTICS)), listOf("*"))),
                    configuration!!.terms
                )
            }
        } else {
            onException (Exception("Please pass the configuration object through CaptureReceipt.config() before initialize the SDK"))
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
    @RequiresApi(Build.VERSION_CODES.M)
    fun scan(
        activity: AppCompatActivity,
        permissionsCallback: () -> Unit
    ) {
        physical.scan(activity){permissionsCallback()}
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
