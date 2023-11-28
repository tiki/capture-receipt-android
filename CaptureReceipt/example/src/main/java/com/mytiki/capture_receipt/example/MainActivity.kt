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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mytiki.capture_receipt.example.ui.theme.CaptureReceiptTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    var username by remember{
        mutableStateOf("")
    }
    var password by remember{
        mutableStateOf("")
    }

    Column(
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Tiki Example",
        )
        Spacer(modifier = Modifier.height(50.dp))
        MainButton(text = "Initialize") {

        }
        Spacer(modifier = Modifier.height(50.dp))

        Input(tile = "Email", text = username, isShow = true, onChange = {username = it})
        Spacer(modifier = Modifier.height(20.dp))
        Input(tile = "Password", text = password, isShow = false, onChange = {password = it})

        Spacer(modifier = Modifier.height(50.dp))

        MainButton(text = "Scan Email") {

        }
        Spacer(modifier = Modifier.height(20.dp))
        MainButton(text = "Scan Retailer") {

        }
        Spacer(modifier = Modifier.height(20.dp))
        MainButton(text = "Scan Physical") {

        }
    }
}
