package com.rgk.pranuploadfilessdk.presentation

import android.app.Application
import com.rgk.uploadfilessdk.util.PranUploadFile
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApp : Application(){
    override fun onCreate() {
        super.onCreate()

        PranUploadFile.create()
                .baseUrl("http://demo2464891.mockable.io/")
                .secretKey("mysecretkey12345")
                .build()

    }
}