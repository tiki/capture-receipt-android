package com.mytiki.sdk.capture.receipt.capacitor

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mytiki.capture.receipt.CaptureReceipt
import com.mytiki.capture_receipt.example.Input
import com.mytiki.sdk.capture.receipt.capacitor.ui.theme.CaptureReceiptTheme
import java.util.UUID

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var errorOutput by remember {
                mutableStateOf("")
            }
            var loginOutput by remember {
                mutableStateOf("")
            }
            var accountsOutput by remember {
                mutableStateOf("")
            }
            var receiptsOutput by remember {
                mutableStateOf("")
            }
            var username by remember {
                mutableStateOf("")
            }
            var password by remember {
                mutableStateOf("")
            }

            fun onError(error: Throwable) {
                errorOutput = ""
                loginOutput = ""
                accountsOutput = ""
                error.message?.let { errorOutput = it }
            }


            CaptureReceipt.initialize(
                UUID.randomUUID().toString(),
                this@MainActivity
            ) { onError(it) }
            CaptureReceiptTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(40.dp))
                        Text(
                            text = "Tiki Example",
                        )
                        if (errorOutput.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = errorOutput,
                                color = MaterialTheme.colorScheme.error,
                                textAlign = TextAlign.Justify
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Input(
                            tile = "Email",
                            text = username,
                            isShow = true,
                            onChange = { username = it })
                        Spacer(modifier = Modifier.height(20.dp))
                        Input(
                            tile = "Password",
                            text = password,
                            isShow = false,
                            onChange = { password = it })
                        if (loginOutput.isNotBlank()) {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = loginOutput,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Justify
                            )
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        MainButton(text = "Scan Physical") {
                            errorOutput = ""
                            loginOutput = ""
                            accountsOutput = ""

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Login Email") {
                            errorOutput = ""
                            loginOutput = ""
                            accountsOutput = ""
//
                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Login Retailer") {
                            errorOutput = ""
                            loginOutput = ""
                            accountsOutput = ""

                        }
                        if (accountsOutput.isNotBlank()) {
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(
                                text = accountsOutput,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Justify
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                        } else {
                            Spacer(modifier = Modifier.height(60.dp))
                        }

                        MainButton(text = "Accounts") {


                        }

                        if (receiptsOutput.isNotBlank()) {
                            Spacer(modifier = Modifier.height(30.dp))
                            Text(
                                text = receiptsOutput,
                                color = MaterialTheme.colorScheme.primary,
                                textAlign = TextAlign.Justify
                            )
                            Spacer(modifier = Modifier.height(30.dp))
                        } else {
                            Spacer(modifier = Modifier.height(60.dp))
                        }

                        MainButton(text = "Get Receipts accounts Gmail") {

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Get Receipts accounts Amazon") {

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Get Receipts Amazon") {

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Get Receipts Gmail") {

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Get All Receipts") {

                        }

                        Spacer(modifier = Modifier.height(60.dp))
                        MainButton(text = "Logout Gmail") {

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Logout Amazon") {

                        }
                        Spacer(modifier = Modifier.height(20.dp))
                        MainButton(text = "Flush") {

                        }

                        Spacer(modifier = Modifier.height(40.dp))
                    }
                }
            }
        }
    }
}

