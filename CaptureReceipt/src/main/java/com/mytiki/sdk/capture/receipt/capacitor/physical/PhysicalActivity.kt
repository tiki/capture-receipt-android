package com.mytiki.sdk.capture.receipt.capacitor.physical

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.microblink.FrameCharacteristics
import com.microblink.Media
import com.microblink.ScanOptions
import com.microblink.camera.ui.CameraScanActivity
import com.microblink.core.ScanResults
import com.mytiki.sdk.capture.receipt.capacitor.databinding.PhysicalActivityBinding

const val SCAN_RECEIPT_REQUEST = 420
class PhysicalActivity: AppCompatActivity() {

    private var _binding: PhysicalActivityBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = PhysicalActivityBinding.inflate(layoutInflater)
        val scanOptions = ScanOptions.newBuilder()
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

        val intent = Intent(this, CameraScanActivity::class.java)
            .putExtra(CameraScanActivity.BUNDLE_EXTRA, bundle)
        this.startActivityForResult(intent, SCAN_RECEIPT_REQUEST)
    }

    @Deprecated("Deprecated in Java", ReplaceWith(
        "super.onActivityResult(requestCode, resultCode, data)",
        "androidx.appcompat.app.AppCompatActivity"
    ))
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SCAN_RECEIPT_REQUEST && resultCode == Activity.RESULT_OK) {
            val scanResults: ScanResults? = data?.getParcelableExtra(CameraScanActivity.DATA_EXTRA)
            val media: Media? = data?.getParcelableExtra(CameraScanActivity.MEDIA_EXTRA)
        }
        this@PhysicalActivity.finish()
    }
}