package com.rgk.uploadfilessdk.util

import android.util.Base64
import com.rgk.uploadfilessdk.util.PranUploadFile.Configuration.secretKey
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

object EncryptionUtils {
    private const val ALGORITHM = "AES"
    private const val TRANSFORMATION = "AES/CBC/PKCS5Padding"

    private val secretKeySpec: SecretKeySpec by lazy {
        SecretKeySpec(secretKey.toByteArray(), ALGORITHM)
    }

    fun encrypt(input: String): String {
        val cipher = Cipher.getInstance(TRANSFORMATION)
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec)
        val iv = cipher.iv
        val encryptedBytes = cipher.doFinal(input.toByteArray())
        val encryptedBase64 = Base64.encodeToString(encryptedBytes, Base64.DEFAULT)
        val ivBase64 = Base64.encodeToString(iv, Base64.DEFAULT)
        return "$ivBase64:$encryptedBase64"
    }

    fun decrypt(encrypted: String): String {
        val parts = encrypted.split(":")
        val iv = Base64.decode(parts[0], Base64.DEFAULT)
        val encryptedBytes = Base64.decode(parts[1], Base64.DEFAULT)
        val cipher = Cipher.getInstance(TRANSFORMATION)
        val ivParameterSpec = IvParameterSpec(iv)
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }
}