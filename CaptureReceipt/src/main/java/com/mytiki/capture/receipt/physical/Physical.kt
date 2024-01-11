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
        onError: (error: Throwable) -> Unit,
    ): CompletableDeferred<Unit> {
        val isInitialized = CompletableDeferred<Unit>()

        isInitialized.complete(Unit)

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
    fun scan(activity: Activity) {
        if (activity.checkSelfPermission(Manifest.permission.CAMERA) == PERMISSION_DENIED) {
            val requestPermissionCode = 98734763
            activity.requestPermissions(
                listOf(Manifest.permission.CAMERA).toTypedArray(),
                requestPermissionCode
            )
        } else {
            activity.startActivity(Intent(activity, PhysicalActivity::class.java))
        }
    }

    fun onScan(){
    }
}