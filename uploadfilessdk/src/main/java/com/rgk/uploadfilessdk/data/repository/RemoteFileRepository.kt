package com.rgk.uploadfilessdk.data.repository

import com.rgk.uploadfilessdk.data.network.ApiService
import com.rgk.uploadfilessdk.domain.model.UploadResult
import com.rgk.uploadfilessdk.domain.repository.FileRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class RemoteFileRepository @Inject constructor(
    private val apiService: ApiService
) : FileRepository {

    override fun uploadPhoto(file: File, description: String, userId: String): Flow<UploadResult> = flow{
        val requestBody = file.asRequestBody("image/*".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)

        val response = apiService.uploadPhoto(multipartBody, description, userId)
        if (response.isSuccessful) {
            emit(UploadResult(progress = 100, url = response.body()?.url))
        } else {
            emit(UploadResult(progress = 100, error = Exception(response.message())))
        }
    }

    override fun uploadDocument(file: File, description: String, userId: String): Flow<UploadResult> = flow {
        val requestBody = file.asRequestBody("application/pdf".toMediaTypeOrNull())
        val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
        val response = apiService.uploadDocument(multipartBody, description, userId)
        if (response.isSuccessful) {
            emit(UploadResult(progress = 100, url = response.body()?.url))
        } else {
            emit(UploadResult(progress = 100, error = Exception(response.message())))
        }
    }
}