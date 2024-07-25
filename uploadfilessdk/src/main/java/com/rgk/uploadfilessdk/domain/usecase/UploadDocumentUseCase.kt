package com.rgk.uploadfilessdk.domain.usecase

import com.rgk.uploadfilessdk.domain.model.UploadResult
import com.rgk.uploadfilessdk.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class UploadDocumentUseCase @Inject constructor(
    private val repository: FileRepository
) {
    operator fun invoke(file: File, description: String, userId: String): Flow<UploadResult> = flow {
        try {
            repository.uploadDocument(file, description, userId).collect { uploadResult ->
                emit(uploadResult)
            }
        } catch (e: Exception) {
            emit(UploadResult(progress = 0, error = e))
        }
    }
}