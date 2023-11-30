/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in the root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor.retailer

import android.content.Context
import android.content.Intent
import android.os.Build
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.microblink.core.InitializeCallback
import com.microblink.core.ScanResults
import com.microblink.core.Timberland
import com.microblink.linking.AccountLinkingClient
import com.microblink.linking.AccountLinkingException
import com.microblink.linking.BlinkReceiptLinkingSdk
import com.microblink.linking.PasswordCredentials
import com.microblink.linking.VERIFICATION_NEEDED
import com.mytiki.sdk.capture.receipt.capacitor.R
import com.mytiki.sdk.capture.receipt.capacitor.account.Account
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import com.microblink.linking.Account as MbAccount

typealias OnReceiptCallback = ((receipt: ScanResults?) -> Unit)

/**
 * This class represents the Retailer functionality for account linking and order retrieval.
 */
class Retailer {
    companion object {
        val webView = mutableStateOf<View?>(null)
    }

    /**
     * Initializes the Retailer SDK.
     *
     * @param context The Android application context.
     * @param licenseKey The license key.
     * @param productKey The product key.
     * @param onError A callback to handle initialization errors.
     * @return A [CompletableDeferred] indicating whether the initialization was successful.
     */
    fun initialize(
        context: Context,
        licenseKey: String,
        productKey: String,
        onError: (error: Exception) -> Unit,
    ): CompletableDeferred<Unit> {
        val isLinkInitialized = CompletableDeferred<Unit>()
        BlinkReceiptLinkingSdk.licenseKey = licenseKey
        BlinkReceiptLinkingSdk.productIntelligenceKey = productKey
        BlinkReceiptLinkingSdk.initialize(context, object : InitializeCallback {
            override fun onComplete() {
                isLinkInitialized.complete(Unit)
            }

            override fun onException(ex: Throwable) {
                onError(ex as Exception)
            }

        })
        return isLinkInitialized
    }

    /**
     * Logs in a user account.
     *
     * @param username The username.
     * @param password The password.
     * @param id The id (email provider).
     * @param activity The [AppCompatActivity] where the login dialog will be displayed.
     * @param onAccount Callback called when the login is completed successfully.
     * @param onError Callback called when an error occurs during login.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun login(
        username: String,
        password: String,
        id: String,
        activity: AppCompatActivity,
        onAccount: ((Account) -> Unit)? = null,
        onError: ((String) -> Unit)? = null
    ) {
        val retailerAccount = MbAccount(
            RetailerEnum.fromString(id).toMbInt(),
            PasswordCredentials(username, password)
        )
        this.client(activity) { client ->
            client.link(retailerAccount)
                .addOnSuccessListener {
                    if (it) {
                        verify(retailerAccount, activity, onAccount, onError)
                    } else {
                        onError?.invoke("Login failed: account $username - $id")
                        client.close()
                    }
                }
                .addOnFailureListener { ex ->
                    onError?.invoke(
                        ex.message ?: "Unknown error on login: account $username - $id: $ex"
                    )
                    client.close()
                }
        }
    }

    /**
     * Removes a user account.
     *
     * @param context The Android application context.
     * @param account The user's account information.
     * @param onComplete Callback called when the account is successfully removed.
     * @param onError Callback called when an error occurs during account removal.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun logout(
        context: Context,
        account: Account,
        onComplete: () -> Unit,
        onError: (msg: String) -> Unit
    ) {
        this.client(context) { client ->
            client.accounts().addOnSuccessListener { accounts ->
                val retailerAccount = accounts?.firstOrNull {
                    it.retailerId == RetailerEnum.fromString(account.accountCommon.id)
                        .toMbInt() &&
                            it.credentials.username() == account.username
                }
                if (retailerAccount != null) {
                    client.resetHistory(
                        RetailerEnum.fromString(account.accountCommon.id).toMbInt()
                    )
                    client.unlink(retailerAccount).addOnSuccessListener {
                        onComplete()
                        client.close()
                    }.addOnFailureListener {
                        onError(it.message ?: it.toString())
                        onComplete()
                        client.close()
                    }
                } else {
                    onError("Logout: Account not found ${account.accountCommon.id} - ${account.username}")
                    onComplete()
                    client.close()
                }
            }
        }
    }

    /**
     * Flushes the order history.
     *
     * @param context The Android application context.
     * @param onComplete Callback called when the history is successfully flushed.
     * @param onError Callback called when an error occurs during flushing.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun flush(context: Context, onComplete: () -> Unit, onError: (String) -> Unit) {
        this.client(context) { client ->
            client.resetHistory()
            client.unlink().addOnSuccessListener {
                onComplete()
                client.close()
            }.addOnFailureListener {
                onError(it.message ?: it.toString())
                onComplete()
                client.close()
            }
        }
    }

    /**
     * Retrieves orders for all verified accounts.
     *
     * @param context The Android application context.
     * @param onReceipt Callback called for each collected receipt.
     * @param onError Callback called when an error occurs during order retrieval.
     * @param daysCutOff The day cutoff limit for order retrieval (optional, default is 7).
     * @param onComplete Callback called when the order retrieval is completed.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun orders(
        context: Context,
        onReceipt: (ScanResults?) -> Unit,
        onError: (msg: String) -> Unit,
        onComplete: () -> Unit
    ) {
        this.client(context) { client ->
            var fetchedAccounts = 0
            client.accounts()
                .addOnSuccessListener { retailerAccountList ->
                    if (retailerAccountList.isNullOrEmpty()) {
                        onComplete()
                        client.close()
                    } else {
                        for (retailerAccount in retailerAccountList) {
                            val account = Account.fromRetailerAccount(retailerAccount)
                            this.orders(
                                context,
                                account,
                                onReceipt,
                                onError,
                            ) {
                                fetchedAccounts++
                                if (fetchedAccounts >= retailerAccountList.size) {
                                    onComplete()
                                    client.close()
                                }
                            }
                        }
                    }
                }
                .addOnFailureListener {
                    Timberland.e(it)
                    onComplete.invoke()
                    client.close()
                }
        }
    }

    /**
     * Retrieves orders for a specific user account.
     *
     * @param context The Android application context.
     * @param account The user's account information.
     * @param onScan Callback called for each collected receipt.
     * @param daysCutOff The day cutoff limit for order retrieval (optional, default is 7).
     * @param onError Callback called when an error occurs during order retrieval.
     * @param onComplete Callback called when the order retrieval is completed.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun orders(
        context: Context,
        account: Account,
        onScan: (ScanResults?) -> Unit,
        onError: (msg: String) -> Unit,
        onComplete: (() -> Unit)? = null
    ) {
        this.client(context) { client ->
            val id = account.accountCommon.id
            val retailerId = RetailerEnum.fromString(id).toMbInt()
            val ordersSuccessCallback: (Int, ScanResults?, Int, String) -> Unit =
                { _: Int, results: ScanResults?, remaining: Int, _: String ->
                    onScan(results)
                    if (remaining == 0) {
                        onComplete?.invoke()
                        client.close()
                    }
                }
            val ordersFailureCallback: (Int, AccountLinkingException) -> Unit =
                { _: Int, exception: AccountLinkingException ->
                    onError(exception.message ?: "Unknown error code: ${exception.code}")
                    onComplete?.invoke()
                    client.close()
                }
            client.orders(
                retailerId,
                ordersSuccessCallback,
                ordersFailureCallback,
            )
        }
    }


    /**
     * Retrieves a list of user accounts from the Retailer SDK.
     *
     * @param context The Android application context.
     * @param onAccount Callback called for each collected user account.
     * @param onError Callback called when an error occurs during account retrieval.
     * @param onComplete Callback called when the retrieval is completed.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun accounts(
        context: Context,
        onAccount: (Account) -> Unit,
        onError: ((msg: String) -> Unit),
        onComplete: (() -> Unit)? = null
    ) {
        this.client(context) { client ->
            client.accounts().addOnSuccessListener { retailerAccountList ->
                if (retailerAccountList.isNullOrEmpty()) {
                    onComplete?.invoke()
                    client.close()
                } else {
                    MainScope().async {
                        var counter = 0
                        retailerAccountList.forEach { retailerAccount ->
                            val account = Account.fromRetailerAccount(retailerAccount)
                            account.isVerified = true
                            onAccount.invoke(account)
                            counter++
                            if (counter == retailerAccountList.size) {
                                onComplete?.invoke()
                                client.close()
                            }
                        }

                    }
                }
            }.addOnFailureListener {
                onError(it.message ?: "Unknown Error in retrieving accounts. $it")
                onComplete?.invoke()
                client.close()
            }
        }
    }


    /**
     * Verifies a user account.
     *
     * @param retailerAccount The user's account in the Retailer SDK.
     * @param activity The [AppCompatActivity] where verification may occur.
     * @param onVerify Callback called when the verification is successful.
     * @param onError Callback called when an error occurs during verification.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun verify(
        retailerAccount: MbAccount,
        activity: AppCompatActivity,
        onVerify: ((Account) -> Unit)?,
        onError: ((msg: String) -> Unit)?
    ) {
        this.client(activity) { client ->
            val account = Account.fromRetailerAccount(retailerAccount)
            client.verify(
                RetailerEnum.fromString(account.accountCommon.id).value,
                success = { isVerified: Boolean, _: String ->
                    if (isVerified) {
                        account.isVerified = true
                        onVerify?.invoke(account)
                        client.close()
                    } else {
                        client.unlink(retailerAccount)
                        onError?.let {
                            it("Please login. Account not verified ${account.username} - ${account.accountCommon.id}")
                        }
                        client.close()
                    }
                },
                failure = { exception ->
                    if (exception.code == VERIFICATION_NEEDED && exception.view != null) {
                        exception.view!!.isFocusableInTouchMode = true
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            exception.view!!.focusable = View.FOCUSABLE
                        }
                        webView.value = exception.view
                        activity.startActivity(Intent(activity, RetailerActivity::class.java))
                    } else {
                        onError?.let {
                            it("Account not verified ${account.username} - ${account.accountCommon.id}: ${exception.message} - $exception")
                        }
                        client.resetHistory(
                            RetailerEnum.fromString(account.accountCommon.id).toMbInt()
                        )
                        client.unlink(retailerAccount)
                        client.close()
                    }
                }
            )
        }
    }

    /**
     * Creates an instance of the AccountLinkingClient with optional configuration parameters.
     *
     * @param context The Android application context.
     * @param dayCutoff The day cutoff for order retrieval (optional, default is 7).
     * @param latestOrdersOnly Indicates whether to retrieve only the latest orders (optional, default is false).
     * @param countryCode The country code for order retrieval (optional, default is "US").
     * @return An instance of the [AccountLinkingClient].
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    private fun client(
        context: Context,
        dayCutoff: Int = 15,
        latestOrdersOnly: Boolean = true,
        countryCode: String = "US",
        onClientReady: (AccountLinkingClient) -> Unit
    ) {
        val client = AccountLinkingClient(context)
        client.dayCutoff = dayCutoff
        client.latestOrdersOnly = latestOrdersOnly
        client.countryCode = countryCode
        onClientReady(client)
    }
}
