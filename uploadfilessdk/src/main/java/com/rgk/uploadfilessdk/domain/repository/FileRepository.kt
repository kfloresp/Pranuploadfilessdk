package com.rgk.uploadfilessdk.domain.repository
import com.rgk.uploadfilessdk.domain.model.UploadResult
import kotlinx.coroutines.flow.Flow
import java.io.File

interface FileRepository {
    fun uploadPhoto(file: File, description: String, userId: String): Flow<UploadResult>
    fun uploadDocument(file: File, description: String, userId: String): Flow<UploadResult>
}