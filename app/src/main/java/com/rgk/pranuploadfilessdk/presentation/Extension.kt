package com.rgk.pranuploadfilessdk.presentation

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation

@Composable
fun LoadImageFromUrl(
    url: String?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    placeholderId: Int? = null
) {
    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .data(url)
                .apply {
                    transformations(CircleCropTransformation())
                    crossfade(true)
                    placeholderId?.let { placeholder(placeholderId) }
                }
                .build()
        ),
        contentDescription = contentDescription,
        modifier = modifier
    )
}