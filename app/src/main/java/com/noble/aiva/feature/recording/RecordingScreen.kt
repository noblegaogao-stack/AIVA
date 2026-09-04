package com.noble.aiva.feature.recording

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import androidx.core.util.TimeUtils.formatDuration
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.noble.aiva.domain.model.Recording
import java.time.Duration

@Composable()
fun RecordingScreen(viewModel: RecordingViewModel,
                    onRecordingFinish: () -> Unit) {

    val context = LocalContext.current
    val isRecording by viewModel.isRecording.collectAsStateWithLifecycle()
    val recordingFile by viewModel.recordingFile.collectAsStateWithLifecycle()
    val recordings by viewModel.recordings.collectAsStateWithLifecycle()

    val uiState by viewModel.uiState.collectAsState()

    /**
     * Androdi 申请录音权限
     * 保存权限，不用每次重组的时候，重新创建一个新的权限请求器
     * Launcher 与 Compose 生命周期正确连接起来。
     * “以后我要向 Android 系统请求某种结果”的工具。
     */
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            viewModel.startRecording()
        } else {
            viewModel.onMicrophonePermissionDenied()
        }
    }

    // 这里产生audioId
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (uiState.isRecording){
                "正在录音"
            } else {
                "未录音"
            }
        )

        Text(
            text = "${uiState.duration} 秒"
        )

        // Recording 正在录音，有一个 录音计时器 时间跳动，一个按钮“停止录音”，点击停止计时器
        Button(onClick = {
            if (uiState.isRecording){
                viewModel.stopRecording()
            } else {
                /**
                 * 当前没有录音，
                 * 先检查权限
                 */
                val hasPermission = ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) == PackageManager.PERMISSION_GRANTED

                if (hasPermission){
                    viewModel.startRecording()
                } else {
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
        }) {
            Text(
                text = if (uiState.isRecording){
                    "停止录音"
                } else {
                    "开始录音"
                }
            )
        }

        recordingFile?.let{filePath ->
            Text(
                text = "录音文件路径：$filePath"
            )
        }


        // 触发导航函数，
        Button(onRecordingFinish) {
            Text("进入下一页")
        }

    }


}


@Composable
fun RecordingItem(
    recording: Recording
){
    Column {
        Text(
            text = recording.fileName
        )

        Text(
            text = formatDuration(
                recording.duration
            )
        )

        Text(text = recording.status.name)
    }
}

fun formatDuration(duration: Long): String{
    val totalSeconds = duration /1000
    val minutes = totalSeconds / 60
    val seconds = totalSeconds % 60

    return String.format(
        "%0.2:%02d",
        minutes,
        seconds
    )
}
