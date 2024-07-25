package com.rgk.uploadfilessdk.domain.usecase

import com.rgk.uploadfilessdk.domain.model.UploadResult
import com.rgk.uploadfilessdk.domain.repository.FileRepository
import com.rgk.uploadfilessdk.util.EncryptionUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class UploadPhotoUseCase @Inject constructor(
    private val repository: FileRepository
) {
    operator fun invoke(file: File, description: String, userId: String): Flow<UploadResult> = flow {
        try {
            repository.uploadPhoto(file, description, userId).collect { uploadResult ->
                val decryptedUrl = uploadResult.url?.let { EncryptionUtils.decrypt(it) }
                emit(uploadResult.copy(url = decryptedUrl))
            }
        } catch (e: Exception) {
            emit(UploadResult(progress = 0, error = e))
        }
    }
}