package com.mytiki.capture.receipt.license

import android.content.Context
import com.mytiki.tiki_sdk_android.TikiSdk
import com.mytiki.tiki_sdk_android.trail.LicenseRecord
import com.mytiki.tiki_sdk_android.trail.Tag
import com.mytiki.tiki_sdk_android.trail.TagCommon
import com.mytiki.tiki_sdk_android.trail.TitleRecord
import com.mytiki.tiki_sdk_android.trail.Use
import com.mytiki.tiki_sdk_android.trail.Usecase
import com.mytiki.tiki_sdk_android.trail.UsecaseCommon
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import java.util.Date
import kotlin.jvm.Throws

class LicenseService(
    var userId: String = "",
    var providerId: String = "",
    var terms: String = "",
    var expiry: Date? = null
) {
    @Throws(Exception::class)
    suspend fun create(
        context: Context,
        userId: String = "",
        terms: String = "",
        expiry: Date? = null
    ): String {
        val license = checkLicense(context, userId, terms, expiry)
        val titleRecord = checkInitialization(context, license).await()
        return TikiSdk.trail.license.create(
            titleRecord.id,
            listOf(Use(listOf(Usecase(UsecaseCommon.ATTRIBUTION)))),
            license.terms,
            license.expiry,
            "Receipt data"
        ).await().id
    }
    @Throws(Exception::class)
    suspend fun get(
        context: Context,
        userId: String = "",
        terms: String = "",
        expiry: Date? = null
    ): String? {
        val license = checkLicense(context, userId, terms, expiry)
        val titleRecord = checkInitialization(context, license).await()
        val list = TikiSdk.trail.license.all(titleRecord.id).await()
        return if(list.lastIndex !=-1) list[list.lastIndex].id else null
    }

    @Throws(Exception::class)
    private fun checkLicense(
        context: Context,
        userId: String = "",
        terms: String = "",
        expiry: Date? = null
    ): LicenseService {
        val license = LicenseService(this.userId,this.providerId,this.terms,this.expiry)

        if (userId.isNotEmpty()){
            license.userId = userId
        }
        if (terms.isNotEmpty()){
            license.terms = terms
        }
        if (expiry != null){
            license.expiry = expiry
        }
        if (license.userId.isEmpty() || license.providerId.isEmpty() || license.terms.isEmpty() || license.expiry == null){
            throw IllegalArgumentException("Please set userId, providerId, terms and expiry parameters")
        } else {
            if (!TikiSdk.isInitialized || TikiSdk.id != license.userId) {

                TikiSdk.initialize(license.userId, license.providerId, context) {}
            }
        }
        return license
    }
    @Throws(Exception::class)
    private fun checkInitialization(
        context: Context,
        license: LicenseService,
    ): CompletableDeferred<TitleRecord> {
        val title = CompletableDeferred<TitleRecord>()
        if (license.userId.isEmpty() || license.providerId.isEmpty() || license.terms.isEmpty() || license.expiry == null) {
            throw IllegalArgumentException("Please set userId, providerId, terms and expiry parameters")
        } else {
            if (!TikiSdk.isInitialized || TikiSdk.id != license.userId) {
                var titleRecord: TitleRecord? = null

                MainScope().async {
                    val deferredTikiSdk =
                        TikiSdk.initialize(userId, license!!.providerId, context)
                    deferredTikiSdk.await()

                    try {
                        titleRecord = TikiSdk.trail.title.get(userId).await()
                        title.complete(titleRecord!!)
                    } catch (ex: Exception) {
                        throw ex
                    }

                    if (titleRecord == null) {
                        try {
                            titleRecord = TikiSdk.trail.title.create(
                                userId,
                                listOf(Tag(TagCommon.PURCHASE_HISTORY))
                            ).await()
                            title.complete(titleRecord!!)
                        } catch (ex: Exception) {
                            throw ex
                        }
                    }
                }
            }
            return title
        }
    }
}