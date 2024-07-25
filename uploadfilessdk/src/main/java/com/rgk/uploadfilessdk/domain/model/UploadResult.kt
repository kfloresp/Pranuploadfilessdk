package com.rgk.uploadfilessdk.domain.model

data class UploadResult(
    val progress: Int,
    val url: String? = null,
    val error: Throwable? = null
)