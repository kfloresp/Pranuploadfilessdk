package com.rgk.uploadfilessdk.util
import kotlinx.coroutines.channels.Channel
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import okio.buffer
import okio.source
import java.io.File

class ProgressRequestBody(
    private val file: File,
    private val contentType:  MediaType?,
    private val progressChannel: Channel<Int>
) : RequestBody() {

    override fun contentType(): MediaType? = contentType

    override fun writeTo(sink: BufferedSink) {
        val source = file.source().buffer()
        val totalBytes = file.length()
        var bytesWritten: Long = 0

        while (true) {
            val readCount = source.read(sink.buffer, 2048)
            if (readCount == -1L) break
            bytesWritten += readCount
            sink.flush()
            val progress = (100 * bytesWritten / totalBytes).toInt()
            progressChannel.trySend(progress)
        }
    }

    override fun contentLength(): Long = file.length()
}