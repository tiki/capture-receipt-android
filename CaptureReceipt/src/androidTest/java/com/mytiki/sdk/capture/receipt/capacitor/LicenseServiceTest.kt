package com.mytiki.sdk.capture.receipt.capacitor

import androidx.test.platform.app.InstrumentationRegistry
import com.mytiki.capture.receipt.license.LicenseService
import com.mytiki.tiki_sdk_android.trail.LicenseRecord
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

class LicenseServiceTest {

    @Test
    fun createTest() = runTest {
        val instrumentationContext = InstrumentationRegistry.getInstrumentation().context
        val license = LicenseService()
        Assert.assertThrows(
            "Please set userId, providerId, terms and expiry parameters before calling any function",
            Exception::class.java
        ){
            MainScope().async {
                license.create(instrumentationContext)

            }
        }
    }
}