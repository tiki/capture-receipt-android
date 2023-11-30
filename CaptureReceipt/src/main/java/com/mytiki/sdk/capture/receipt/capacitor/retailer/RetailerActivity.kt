package com.mytiki.sdk.capture.receipt.capacitor.retailer

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.microblink.FrameCharacteristics
import com.microblink.Media
import com.microblink.ScanOptions
import com.microblink.camera.ui.CameraScanActivity
import com.microblink.core.ScanResults
import com.mytiki.sdk.capture.receipt.capacitor.databinding.RetailerActivityBinding

const val SCAN_RECEIPT_REQUEST = 420
class RetailerActivity: AppCompatActivity() {

    private var _binding: RetailerActivityBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = RetailerActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}