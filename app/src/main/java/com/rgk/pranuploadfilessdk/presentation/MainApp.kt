package com.rgk.pranuploadfilessdk.presentation

import android.app.Application
import com.rgk.uploadfilessdk.util.PranUploadFile
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp : Application(){
    override fun onCreate() {
        super.onCreate()

        PranUploadFile.create()
                .baseUrl("http://192.168.0.88/upload_files_sdk/api/")
                .build()

    }
}