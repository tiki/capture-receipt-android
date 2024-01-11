package com.mytiki.capture.receipt.email

import com.microblink.digital.Provider

/**
 * Enumeration representing common email providers and their corresponding [Provider] values.
 *
 * @property value The [Provider] value associated with the email provider.
 */
enum class EmailType {

    /**
     * Outlook email provider.
     */
    OUTLOOK,

    /**
     * Gmail email provider.
     */
    GMAIL;
}
