package com.rgk.uploadfilessdk.data.repository

import com.rgk.uploadfilessdk.data.network.ApiService
import com.rgk.uploadfilessdk.domain.model.UploadResult
import com.rgk.uploadfilessdk.domain.repository.FileRepository
import com.rgk.uploadfilessdk.util.ProgressRequestBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import javax.inject.Inject

class RemoteFileRepository @Inject constructor(
    private val apiService: ApiService
) : FileRepository {

    override fun uploadDocument(
        file: File,
        description: String,
        userId: String
    ): Flow<UploadResult> = channelFlow {
        val progressChannel = Channel<Int>(Channel.UNLIMITED)
        val mediaType = "application/pdf".toMediaTypeOrNull()
        val requestBody = ProgressRequestBody(file, mediaType,progressChannel)
        val multipartBodyPart = MultipartBody.Part.createFormData("file", file.name, requestBody)

        launch {
            try {
                for (progress in progressChannel) {
                    trySend(UploadResult(progress = progress))

                    if (progress==100){
                        break
                    }
                }
            }finally {
                progressChannel.close()
            }
        }

        withContext(Dispatchers.IO) {
            val response = apiService.uploadDocument(multipartBodyPart, description, userId)

            if (response.isSuccessful) {
                trySend(UploadResult(progress = 100, url = response.body()?.url))
            } else {
                trySend(UploadResult(progress = 0, error = Exception(response.message())))
            }
        }
        awaitClose {
            progressChannel.close()
        }

    }.flowOn(Dispatchers.IO)

    override fun uploadPhoto(
        file: File,
        description: String,
        userId: String
    ): Flow<UploadResult> = channelFlow  {
        val progressChannel = Channel<Int>(Channel.UNLIMITED)
        val mediaType = "image/*".toMediaTypeOrNull()
        val requestBody = ProgressRequestBody(file, mediaType,progressChannel)
        val multipartBodyPart = MultipartBody.Part.createFormData("file", file.name, requestBody)

        launch(Dispatchers.IO) {
            try {
                for (progress in progressChannel) {
                    trySend(UploadResult(progress = progress)).isSuccess
                    if (progress==100){
                        break
                    }
                }
            }finally {
                progressChannel.close()
            }
        }

        withContext(Dispatchers.IO) {
            val descriptionBody = description.toRequestBody("text/plain".toMediaTypeOrNull())
            val userIdBody = userId.toRequestBody("text/plain".toMediaTypeOrNull())
            val response = apiService.uploadPhoto(multipartBodyPart, descriptionBody, userIdBody)

            if (response.isSuccessful) {
                trySend(UploadResult(progress = 100, url = response.body()?.url))
            } else {
                trySend(UploadResult(progress = 0, error = Exception(response.message())))
            }
        }

        awaitClose {
            progressChannel.close()
        }
    }.flowOn(Dispatchers.IO)

}