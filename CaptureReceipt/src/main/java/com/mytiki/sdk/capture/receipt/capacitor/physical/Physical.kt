/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.PackageManager.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.microblink.BlinkReceiptSdk
import com.microblink.core.InitializeCallback
import com.microblink.core.ScanResults
import com.mytiki.sdk.capture.receipt.capacitor.physical.PhysicalActivity
import kotlinx.coroutines.CompletableDeferred


/**
 * This class provides functionality for scanning physical receipts using Microblink's SDK.
 */
class Physical {
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
    fun scan(activity: Activity, permissionsCallback: () -> Unit) {
        if (activity.checkSelfPermission(Manifest.permission.CAMERA) == PERMISSION_DENIED) {
            val requestPermissionCode = 98734763
            activity.requestPermissions(listOf(Manifest.permission.CAMERA).toTypedArray(), requestPermissionCode)
        } else {
            activity.startActivity(Intent(activity, PhysicalActivity::class.java))
        }
    }


}
