package com.rgk.uploadfilessdk.util

class PranUploadFile private constructor() {
    private var baseUrl: String = ""
    private var secretKey: String = ""

    fun baseUrl(url: String) = apply {
        this.baseUrl = url
    }

    fun secretKey(key: String) = apply {
        this.secretKey = key
    }

    fun build() {
        require(baseUrl.isNotEmpty()) { "Base URL must be provided" }
        require(secretKey.isNotEmpty()) { "Secret key must be provided" }

        Configuration.initialize(baseUrl, secretKey)
    }

    companion object {
        @JvmStatic
        fun create(): PranUploadFile {
            return PranUploadFile()
        }
    }

    object Configuration {
        var baseUrl: String = ""
        var secretKey: String = ""

        fun initialize(baseUrl: String, secretKey: String) {
            this.baseUrl = baseUrl
            this.secretKey = secretKey
        }
    }
}