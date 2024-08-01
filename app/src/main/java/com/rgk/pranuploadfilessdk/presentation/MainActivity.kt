package com.rgk.pranuploadfilessdk.presentation

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.rgk.pranuploadfilessdk.R
import com.rgk.pranuploadfilessdk.ui.theme.PranuploadfilessdkTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PranuploadfilessdkTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    PhotoUploadScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun PhotoUploadScreen(viewModel: MainViewModel) {
    val uploadState by viewModel.uploadState.collectAsState()

    Column(modifier = Modifier.padding(16.dp).fillMaxSize(), verticalArrangement = Arrangement.Center,horizontalAlignment = Alignment.CenterHorizontally) {
        PhotoPickerButton(viewModel)
        Text(text = "Subir foto")
        Spacer(modifier = Modifier.height(16.dp))
        when {
            uploadState.error != null -> Text(text = "Error: ${uploadState.error!!.message}")
            uploadState.progress in 1..99 ->
                Text(text = "Progreso: ${uploadState.progress}%")
            uploadState.progress == 100 && uploadState.url != null ->
            LoadImageFromUrl(
                url = uploadState.url,
                modifier = Modifier,
                contentDescription = "Foto subida",
                placeholderId = R.drawable.ic_launcher_background
            )
        }
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
