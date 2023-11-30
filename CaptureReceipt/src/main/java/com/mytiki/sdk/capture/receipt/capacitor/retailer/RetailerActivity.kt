package com.mytiki.sdk.capture.receipt.capacitor.retailer

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.mytiki.sdk.capture.receipt.capacitor.R
import com.mytiki.sdk.capture.receipt.capacitor.databinding.PhysicalActivityBinding
import com.mytiki.sdk.capture.receipt.capacitor.databinding.RetailerActivityBinding

class RetailerActivity: AppCompatActivity() {

    private var _binding: RetailerActivityBinding? = null
    private val binding get() = _binding!!
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = RetailerActivityBinding.inflate(layoutInflater)
        val rootView = binding.root
        setContentView(rootView)
        val webView = Retailer.webView.value
        binding.webContainer.removeAllViews()
        binding.webContainer.addView(webView)
    }
}