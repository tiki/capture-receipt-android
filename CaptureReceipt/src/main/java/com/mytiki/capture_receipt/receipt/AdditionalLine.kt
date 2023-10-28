package com.mytiki.capture_receipt.receipt

data class AdditionalLine(
     val type: StringType? = null,
     val text: StringType? = null,
     val lineNumber: Int = 0
)
