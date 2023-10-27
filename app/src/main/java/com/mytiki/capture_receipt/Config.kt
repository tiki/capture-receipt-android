package com.mytiki.capture_receipt

data class Config(
    val company: Company,
    val key: Key,
){
    fun gmail(apiKey: String): Config{
        return this
    }

    fun outlook(apiKey: String): Config{
        return this
    }
    
}
