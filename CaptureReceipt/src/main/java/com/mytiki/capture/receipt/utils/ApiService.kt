package com.mytiki.capture.receipt.utils

import com.mytiki.capture.receipt.receipt.Receipt
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.IOException

private const val BASE_URL = "https://ingest.mytiki.com/api/"

object ApiService {
    private fun client(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private fun postRequest(requestBody: RequestBody, path: String) = Request.Builder()
        .url(BASE_URL + path)
        .post(requestBody)
        .build()

    fun publishReceipts(receipt: Receipt?, onReceipt: (Receipt?) -> Unit, onError: (String) -> Unit){
        if (receipt != null) {
            client().newCall(
                postRequest(
                    receipt.toJS().toString().toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull()),
                    "latest/microblink-receipt")
            ).enqueue(
                object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        onError(e.message.toString())
                    }
                    override fun onResponse(call: Call, response: Response) {
                        if (response.code !in 200..299) {
                            onError("Status: response.code")
                        } else {
                            onReceipt(receipt)
                        }
                    }
                }
            )
        }
    }
}