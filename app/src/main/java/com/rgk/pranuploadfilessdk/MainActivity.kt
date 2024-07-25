package com.rgk.pranuploadfilessdk

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rgk.pranuploadfilessdk.ui.theme.PranuploadfilessdkTheme
import com.rgk.uploadfilessdk.domain.model.UploadResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PranuploadfilessdkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    PhotoUploadScreen()
                }
            }
        }
    }
}

@Composable
fun PhotoUploadScreen(/*viewModel: MainViewModel = hiltViewModel()*/) {
    //val uploadState by viewModel.uploadState.collectAsState()

    Column(modifier = Modifier.padding(16.dp)) {
        ///PhotoPickerButton(viewModel)
        Text(text = "Subir foto")
        Spacer(modifier = Modifier.height(16.dp))
        /*when {
            uploadState.error != null -> Text(text = "Error: ${uploadState.error!!.message}")
            uploadState.progress > 0 -> Text(text = "Progreso: ${uploadState.progress}%")
            uploadState.url != null -> Text(text = "Foto subida en: ${uploadState.url}")
        }*/
    }
}

@Composable
fun PhotoPickerButton(viewModel: MainViewModel) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadPhoto(context, it) }
    }

    Button(onClick = { launcher.launch("image/*") }) {
        Text(text = "Seleccionar Foto")
    }
}
