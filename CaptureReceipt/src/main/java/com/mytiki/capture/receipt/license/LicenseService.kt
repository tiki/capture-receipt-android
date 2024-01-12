package com.mytiki.capture.receipt.license

import android.content.Context
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.trail.LicenseRecord
import com.mytiki.tiki_sdk_android.trail.Tag
import com.mytiki.tiki_sdk_android.trail.TagCommon
import com.mytiki.tiki_sdk_android.trail.Use
import com.mytiki.tiki_sdk_android.trail.Usecase
import com.mytiki.tiki_sdk_android.trail.UsecaseCommon
import java.util.Date

class LicenseService(
    var userId: String = "",
    var providerId: String = "",
    var terms: String = "",
    var expiry: Date? = null
) {
    suspend fun create(
        context: Context,
        _userId: String = "",
        _providerId: String = "",
        _terms: String = "",
        _expiry: Date? = null
    ): LicenseRecord {
        checkUp(context, _userId, _providerId, _terms, _expiry)
        val titleRecord = TikiSdk.trail.title.create(userId, listOf(Tag(TagCommon.PURCHASE_HISTORY))).await()
        return TikiSdk.trail.license.create(
            titleRecord.id,
            listOf(Use(listOf(Usecase(UsecaseCommon.ATTRIBUTION)))),
            terms,
            expiry,
            "Receipt data"
        ).await()
    }
    suspend fun get(
        context: Context,
        _userId: String = "",
        _providerId: String = "",
        _terms: String = "",
        _expiry: Date? = null
    ): LicenseRecord? {
        checkUp(context, _userId, _providerId, _terms, _expiry)
        val list = TikiSdk.trail.license.all(TikiSdk.trail.title.get(userId).await().id).await()
        return if(list.lastIndex !=-1) list[list.lastIndex] else null
    }

    private fun checkUp(
        context: Context,
        userId: String = "",
        providerId: String = "",
        terms: String = "",
        expiry: Date? = null
    ){
        if (userId.isNotEmpty()){
            this.userId = userId
        }
        if (providerId.isNotEmpty()){
            this.providerId = providerId
        }
        if (terms.isNotEmpty()){
            this.terms = terms
        }
        if (expiry != null){
            this.expiry = expiry
        }
        if (this.userId.isEmpty() || this.providerId.isEmpty() || this.terms.isEmpty() || this.expiry == null){
            throw Exception("Please set userId, providerId, terms and expiry parameters before calling any function")
        } else {
            if (!TikiSdk.isInitialized) {
                TikiSdk.initialize(this.userId, this.providerId, context) {}
            }
        }
    }
}