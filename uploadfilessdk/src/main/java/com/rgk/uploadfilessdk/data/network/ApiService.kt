package com.rgk.uploadfilessdk.data.network

import com.rgk.uploadfilessdk.data.model.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @Multipart
    @POST("upload/photo")
    suspend fun uploadPhoto(
        @Part file: MultipartBody.Part,
        @Part("description") description: String,
        @Part("userId") userId: String
    ): Response<UploadResponse>

    @Multipart
    @POST("upload/document")
    suspend fun uploadDocument(
        @Part file: MultipartBody.Part,
        @Part("description") description: String,
        @Part("userId") userId: String
    ): Response<UploadResponse>
}