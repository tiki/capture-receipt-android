/*
 * Copyright (c) TIKI Inc.
 * MIT license. See LICENSE file in root directory.
 */

package com.mytiki.sdk.capture.receipt.capacitor

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.result.ActivityResult
import androidx.annotation.RequiresApi
import com.microblink.BlinkReceiptSdk
import com.microblink.FrameCharacteristics
import com.microblink.ScanOptions
import com.microblink.camera.ui.CameraScanActivity
import com.microblink.core.InitializeCallback
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
        onError: (error: Exception) -> Unit,
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
                    onError(ex as Exception)
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
    fun scan(context: Context, permissionsCallback: () -> Unit) {
//        if (context.checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
//            permissionsCallback()
//        } else {
//            val intent: Intent = open(context)
//            startActivityForResult( intent, SCAN_RECEIPT_REQUEST );
//        }
    }

    /**
     * Creates an intent to open the camera for receipt scanning.
     *
     * @param context The Android application context.
     * @return An [Intent] for opening the camera scanner activity.
     */
    fun open(context: Context): Intent {
        val scanOptions = ScanOptions.newBuilder()
            .detectDuplicates(true)
            .frameCharacteristics(
                FrameCharacteristics.newBuilder()
                    .storeFrames(true)
                    .compressionQuality(100)
                    .externalStorage(false).build()
            )
            .logoDetection(true)
            .build()
        val bundle = Bundle()
        bundle.putParcelable(CameraScanActivity.SCAN_OPTIONS_EXTRA, scanOptions)
        return Intent(context, CameraScanActivity::class.java)
            .putExtra(CameraScanActivity.BUNDLE_EXTRA, bundle)
    }

    /**
     * Handles the scanning result and appropriately resolves or rejects the plugin call based on the outcome.
     *
     * This method is responsible for processing the scanning result and responding to the plugin call accordingly.
     *
     * @param call The plugin call associated with the scanning operation.
     * @param result The result of the scanning activity, typically obtained from an activity result callback.
     *
     * If the scanning operation is successful (resultCode == Activity.RESULT_OK), it extracts scan results and media data
     * from the result and resolves the plugin call with a JSON representation of the scan results.
     *
     * If the scanning operation fails (resultCode != Activity.RESULT_OK), it rejects the plugin call with an error message.
     */
    fun onResult(result: ActivityResult) {
//        if (result.resultCode == Activity.RESULT_OK) {
//            val scanResults: ScanResults? =
//                result.data?.getParcelableExtra(CameraScanActivity.DATA_EXTRA)
//            val media: Media? = result.data?.getParcelableExtra(CameraScanActivity.MEDIA_EXTRA)
//            val rsp = RspScan(scanResults, null, false)
//            call.resolve(JSObject.fromJSONObject(rsp.toJson()))
//        } else {
//            call.reject("Physical failed.")
//        }
    }

}
