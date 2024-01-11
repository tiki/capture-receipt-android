package com.mytiki.capture.receipt.utils

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor

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
//        .addHeader("token", TikiSdk.idp.token().toString())
        .post(requestBody)
        .build()


}