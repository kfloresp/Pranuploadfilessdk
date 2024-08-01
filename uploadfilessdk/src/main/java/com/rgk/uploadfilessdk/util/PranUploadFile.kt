package com.rgk.uploadfilessdk.util

class PranUploadFile private constructor() {
    private var baseUrl: String = ""

    fun baseUrl(url: String) = apply {
        this.baseUrl = url
    }


    fun build() {
        require(baseUrl.isNotEmpty()) { "Base URL must be provided" }

        Configuration.initialize(baseUrl)
    }

    companion object {
        @JvmStatic
        fun create(): PranUploadFile {
            return PranUploadFile()
        }
    }

    object Configuration {
        var baseUrl: String = ""

        fun initialize(baseUrl: String) {
            this.baseUrl = baseUrl
        }
    }
}