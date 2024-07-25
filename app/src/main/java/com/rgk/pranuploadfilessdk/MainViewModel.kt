package com.rgk.pranuploadfilessdk

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rgk.uploadfilessdk.domain.model.UploadResult
import com.rgk.uploadfilessdk.domain.usecase.UploadPhotoUseCase
import com.rgk.uploadfilessdk.util.PranUploadFile
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val uploadPhotoUseCase: UploadPhotoUseCase
) : ViewModel() {

    init {
        PranUploadFile.create()
            .baseUrl("")
            .secretKey("mysecretkey12345")
            .build()
    }

    private val _uploadState = MutableStateFlow(UploadResult(progress = 0))
    val uploadState: StateFlow<UploadResult> get() = _uploadState

    fun uploadPhoto(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                val file = uri.toFile(context)
                uploadPhotoUseCase.invoke(file, "photo123", "kflores")
                    .collect { result ->
                        _uploadState.value = result
                    }
            } catch (e: Exception) {
                _uploadState.value = UploadResult(progress = 0, error = e)
            }
        }
    }

    private fun Uri.toFile(context: Context): File {
        val inputStream = context.contentResolver.openInputStream(this)
        val file = File(context.cacheDir, "photo.jpg")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.close()
        return file
    }
}