package com.mytiki.capture_receipt.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mytiki.capture_receipt.example.ui.theme.CaptureReceiptTheme
import com.mytiki.capture_receipt.CaptureReceipt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CaptureReceipt.configuration
        setContent {
            CaptureReceiptTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Content()
                }
            }
        }
    }
}

@Composable
fun Content() {
    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tiki Example",
        )
        Spacer(modifier = Modifier.height(80.dp))
        MainButton(text = "Initialize") {

        }
    }
}
