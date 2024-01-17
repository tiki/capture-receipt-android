package com.mytiki.capture.receipt.utils

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File


object ApiService{
    fun postImageRequest(url: String, vararg filesPaths: String): Response{
        val client = client()
        val requestBody = postImageBody(*filesPaths)
        val request = postRequest(requestBody, url)

        val call = client.newCall(request)
        return call.execute()
    }
     private fun client(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    private fun postImageBody(vararg filesPaths: String): MultipartBody{
        val body = MultipartBody.Builder().setType(MultipartBody.FORM)
        filesPaths.forEachIndexed { index, path ->
            body.addFormDataPart(
                    "images[$index]",
                    path.substringAfterLast("/"),
                    File(path).asRequestBody("image/png".toMediaTypeOrNull())
            )
        }
        return body.build()
    }

    private fun postRequest(requestBody: MultipartBody, url: String) = Request.Builder()
        .url(url)
        .post(requestBody)
        .build()
}