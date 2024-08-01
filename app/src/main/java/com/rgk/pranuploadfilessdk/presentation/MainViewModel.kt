package com.rgk.pranuploadfilessdk.presentation

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

    private val _uploadState = MutableStateFlow(UploadResult(progress = 0))
    val uploadState: StateFlow<UploadResult> get() = _uploadState

    fun uploadPhoto(context: Context, uri: Uri) {
        viewModelScope.launch {
            try {
                val file = uri.toFile(context)
                uploadPhotoUseCase.invoke(file, "test_", "kflores")
                    .collect { result ->
                        _uploadState.value = result
                    }
            } catch (e: Exception) {
                _uploadState.value = UploadResult(progress = 0, error = e)
            }
        }
    }

    private fun Uri.toFile(context: Context): File {
        val fileName = getFileName(context) ?: "default_filename.jpg"
        val file = File(context.cacheDir, fileName)

        context.contentResolver.openInputStream(this)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }

        return file
    }

    private fun Uri.getFileName(context: Context): String? {
        var name: String? = null
        context.contentResolver.query(this, null, null, null, null)?.use { cursor ->
            val nameIndex = cursor.getColumnIndex(android.provider.MediaStore.Images.Media.DISPLAY_NAME)
            if (nameIndex != -1 && cursor.moveToFirst()) {
                name = cursor.getString(nameIndex)
            }
        }
        return name
    }
}