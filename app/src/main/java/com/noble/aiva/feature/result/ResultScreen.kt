package com.noble.aiva.feature.result

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.noble.aiva.domain.model.RecordingStatus

@Composable
fun ResultScreen(
    viewModel: ResultViewModel,
    onBack: () -> Unit) {

    /**
     * 观察当前录音
     */
    val recording by viewModel.recording.collectAsStateWithLifecycle()

    /**
     * Room 数据还没回来
     */
    if (recording == null) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {

            CircularProgressIndicator()
        }

        return
    }

    val currentRecording = recording!!

    /**
     * ==========================================================
     * 页面
     * ==========================================================
     */
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "AI 语音识别",
            style =
                MaterialTheme.typography.headlineMedium
        )
        Spacer(
            modifier = Modifier.height(24.dp)
        )
        Text(
            text =
                "文件：${currentRecording?.fileName}"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text =
                "状态：${getStatusText(currentRecording.status)}"
        )


        Spacer(
            modifier = Modifier.height(24.dp)
        )


//        when (currentRecording.status) {
//
//            RecordingStatus.UPLOADED,
//            RecordingStatus.PROCESSING -> {
//
//                ProcessingView()
//            }
//
//
//            RecordingStatus.COMPLETED -> {
//
//                TranscriptView(
//                    transcript =
//                        currentRecording.transcript
//                            ?: "没有识别结果"
//                )
//            }
//
//
//            RecordingStatus.ASR_FAILED -> {
//
//                ErrorView()
//            }
//
//
//            else -> {
//
//                Text(
//                    text = "当前状态无法进行语音识别"
//                )
//            }
//        }

        Button(onClick = onBack) {
            Text("返回")
        }
    }

}

@Composable
private fun ProcessingView() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        CircularProgressIndicator()

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Text(
            text = "AI 正在识别语音..."
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text = "请稍候"
        )
    }
}

@Composable
private fun TranscriptView(
    transcript: String
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Text(
            text = "识别结果",
            style =
                MaterialTheme.typography.titleLarge
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = transcript,
            style =
                MaterialTheme.typography.bodyLarge
        )
    }
}

@Composable
private fun ErrorView() {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment =
            Alignment.CenterHorizontally
    ) {

        Text(
            text = "AI 语音识别失败",
            color =
                MaterialTheme.colorScheme.error
        )
    }
}

private fun getStatusText(
    status: RecordingStatus
): String {

    return when (status) {

        RecordingStatus.LOCAL ->
            "本地录音"

        RecordingStatus.UPLOADING ->
            "上传中"

        RecordingStatus.UPLOADED ->
            "上传完成"

        RecordingStatus.PROCESSING ->
            "AI 处理中"

        RecordingStatus.COMPLETED ->
            "识别完成"

        RecordingStatus.UPLOAD_FAILED ->
            "上传失败"

        else -> "null"
    }
}