package com.rgk.uploadfilessdk.data.model

import com.google.gson.annotations.SerializedName
import com.rgk.uploadfilessdk.util.EncryptionUtils

data class UploadResponse(
    @SerializedName("url")
    val encryptedUrl: String = ""
) {
    val url: String
        get() = EncryptionUtils.decrypt(encryptedUrl)
}