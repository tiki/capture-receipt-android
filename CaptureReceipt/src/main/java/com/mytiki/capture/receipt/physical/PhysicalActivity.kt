package com.mytiki.capture.receipt.physical

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

const val SCAN_RECEIPT_REQUEST = 420

class PhysicalActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @Deprecated(
        "Deprecated in Java", ReplaceWith(
            "super.onActivityResult(requestCode, resultCode, data)",
            "androidx.appcompat.app.AppCompatActivity"
        )
    )
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        this@PhysicalActivity.finish()
    }
}