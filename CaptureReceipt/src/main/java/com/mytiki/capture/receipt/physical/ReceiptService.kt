package com.mytiki.capture.receipt.physical

import android.media.Image
import android.net.Uri
import com.mytiki.capture.receipt.utils.ApiService
import okhttp3.Response
import java.net.URI

class ReceiptService {

    fun upload(vararg imagePaths: String): Int {
        val response = ApiService.postImageRequest("https://postman-echo.com/post", *imagePaths)
        return response.code
    }
}