package com.rgk.uploadfilessdk.data.model

import com.google.gson.annotations.SerializedName

data class UploadResponse(
    @SerializedName("url")
    val url: String = ""
) /*{
    val url: String
        get() = EncryptionUtils.decrypt(encryptedUrl)
}*/