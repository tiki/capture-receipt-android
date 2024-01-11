/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.capture.receipt.physical

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager.*
import android.os.Build
import androidx.annotation.RequiresApi
import com.microblink.BlinkReceiptSdk
import com.microblink.core.InitializeCallback
import com.mytiki.capture.receipt.receipt.Receipt
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.MainScope
import java.lang.Exception


/**
 * This class provides functionality for scanning physical receipts using Microblink's SDK.
 */
class Physical {

    private var receipt = CompletableDeferred<Receipt?>()

    /**
     * Initializes the Microblink SDK with the provided configuration.
     *
     * @param req The initialization request parameters.
     * @param context The Android application context.
     * @param onError Callback function to handle initialization errors.
     * @return A [CompletableDeferred] indicating the initialization status.
     */
    fun initialize(
        context: Context,
        licenseKey: String,
        productKey: String,
        onError: (error: Throwable) -> Unit,
    ): CompletableDeferred<Unit> {
        val isInitialized = CompletableDeferred<Unit>()
        BlinkReceiptSdk.productIntelligenceKey(productKey)
        BlinkReceiptSdk.initialize(
            context,
            licenseKey,
            object : InitializeCallback {
                override fun onComplete() {
                    isInitialized.complete(Unit)
                }

                override fun onException(ex: Throwable) {
                    onError(ex)
                }
            }
        )
        return isInitialized
    }

    /**
     * Initiates the receipt scanning process.
     *
     * @param call The plugin call associated with the scanning operation.
     * @param plugin The Capacitor plugin instance.
     * @param context The Android application context.
     * @param reqPermissionsCallback Callback to request camera permissions if needed.
     */
    @RequiresApi(Build.VERSION_CODES.M)
    suspend fun scan(activity: Activity): Receipt? {
        receipt = CompletableDeferred<Receipt?>()
        if (activity.checkSelfPermission(Manifest.permission.CAMERA) == PERMISSION_DENIED) {
            val requestPermissionCode = 98734763
            activity.requestPermissions(
                listOf(Manifest.permission.CAMERA).toTypedArray(),
                requestPermissionCode
            )
        } else {
            activity.startActivity(Intent(activity, PhysicalActivity::class.java))
        }
        return receipt.await()
    }

    fun onScan(_receipt: Receipt?){
        if (_receipt != null) {
            receipt.complete(_receipt)
        } else {
            receipt.completeExceptionally(
                Exception("error scanning the receipt. please try again")
            )
        }
    }
}