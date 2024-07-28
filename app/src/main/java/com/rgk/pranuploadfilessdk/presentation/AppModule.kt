package com.rgk.pranuploadfilessdk.presentation

import com.rgk.uploadfilessdk.domain.repository.FileRepository
import com.rgk.uploadfilessdk.domain.usecase.UploadPhotoUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUploadPhotoUseCase(fileRepository: FileRepository): UploadPhotoUseCase {
        return UploadPhotoUseCase(fileRepository)
    }

}