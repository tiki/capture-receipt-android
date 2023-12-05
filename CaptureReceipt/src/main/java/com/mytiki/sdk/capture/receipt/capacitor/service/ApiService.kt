package com.mytiki.sdk.capture.receipt.capacitor.service

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mytiki.sdk.capture.receipt.capacitor.service.Constants.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

object APIModule {

    fun client() = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

    @Singleton
    fun provideInjestApi(): InjestApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(InjestApi::class.java)
    }
}