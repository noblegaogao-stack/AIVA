package com.noble.aiva.data.remote

import okhttp3.MediaType
import okhttp3.RequestBody
import okio.BufferedSink
import java.io.File

class ProgressRequestBody(
    private val file: File,
    private val contentType: MediaType?,
    private val onProgress: (Int) -> Unit
    ) : RequestBody() {
    override fun contentType(): MediaType? {
        return contentType
    }

    override fun contentLength(): Long {
        return file.length()
    }

    override fun writeTo(sink: BufferedSink) {
        val totalBytes = contentLength()
        var uploadedBytes = 0L
        val buffer = ByteArray(DEFAULT_BUFFER_SIZE)

        file.inputStream().use { inputStream ->
            while (true){
                val read = inputStream.read(buffer)
                if (read == -1){
                    break
                }
                sink.write(buffer,0, read)

                uploadedBytes += read

                val progress =
                    if (totalBytes > 0){
                        (uploadedBytes * 100 / totalBytes).toInt()
                } else {
                    0
                }

                onProgress(progress)
            }
        }

        return
    }
}