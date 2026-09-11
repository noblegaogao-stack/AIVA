package com.noble.aiva.ui.recording

import android.Manifest
import android.R.attr.text
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.noble.aiva.domain.model.Recording
import com.noble.aiva.domain.model.RecordingStatus

@SuppressLint("MissingPermission")
@Composable()
fun RecordingScreen(
    viewModel: RecordingViewModel,
    onNavigateToResult : (Long) -> Unit) {

    val context = LocalContext.current


    /**
     * ============================================================
     * 1. 观察录音列表
     * ============================================================
     */
    val recordings by viewModel.recordings.collectAsStateWithLifecycle()

    /**
     * ============================================================
     * 2. 观察上传状态
     * ============================================================
     */
    val uploadState by viewModel.uploadUiState.collectAsStateWithLifecycle()
//    val uiState by viewModel.uiState.collectAsState()

    /**
     * ============================================================
     * 3. 当前是否正在录音
     * ============================================================
     */
    val isRecording by viewModel.isRecording.collectAsStateWithLifecycle()
//    var isRecording by remember{ mutableStateOf(false) }

    /**
     * ============================================================
     * 4. 当前录音文件路径
     *
     * 开始录音时保存
     * 停止录音时使用
     * ============================================================
     */
    val recordingFile by viewModel.recordingFile.collectAsStateWithLifecycle()

    /**
     * ============================================================
     * 5. 麦克风权限申请器
     *      * Android 申请录音权限
     *      * 保存权限，不用每次重组的时候，重新创建一个新的权限请求器
     *      * Launcher 与 Compose 生命周期正确连接起来。
     *      * “以后我要向 Android 系统请求某种结果”的工具。
     * ============================================================
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
        modifier = Modifier.fillMaxSize().padding(16.dp)
    ) {
        Text(
            text = "AIVA 录音",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        /**
         * ========================================================
         * 录音按钮
         * ========================================================
         */
        Button(onClick = {
            if (isRecording){
                /**
                 * 停止录音
                 */
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
                    // 没有权限，申请权限
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }
        }) {
            Text(
                text = if (isRecording){
                    "停止录音"
                } else {
                    "开始录音"
                }
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        /**
         * ========================================================
         * 当前录音状态
         * ========================================================
         */
        if (isRecording){
            Text(
                text = "正在录音...",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(
                modifier = Modifier.height(8.dp)
            )
            CircularProgressIndicator()
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )


//        recordingFile?.let{filePath ->
//            Text(
//                text = "录音文件路径：$filePath"
//            )
//        }

        /**
         * ========================================================
         * 上传进度
         * ========================================================
         */

        if (uploadState.isUploading){
            Text(
                text = "正在上传...",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            LinearProgressIndicator(
                progress = {
                    uploadState.progress / 100f
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "${uploadState.progress} %"
            )
            Spacer(modifier = Modifier.height(16.dp))
        }


        /**
         * ========================================================
         * 上传错误
         * ========================================================
         */
        uploadState.errorMessage?.let{errorMessage ->
            Text(
                text = errorMessage,
                color = MaterialTheme.colorScheme.error
            )

            Spacer(modifier = Modifier.height(8.dp))
        }

        /**
         * ========================================================
         * 录音列表
         * ========================================================
         */
        Text(
            text= "我的录音",
            style= MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            items(
                items = recordings,
                key ={ recording ->
                    recording.id
                }
            ) { recording ->

                RecordingItem(
                    recording = recording,
                    /**
                     * 点击录音
                     */
                    onClick = {
                        onNavigateToResult(recording.id)
                    },

                    // 上传
                    onUploadClick = { id ->
                        viewModel.uploadRecording(
                            recordingId = id
                        )
                    },
                )
            }
        }


        // 触发导航函数，
//        Button(onRecordingFinish) {
//            Text("进入下一页")
//        }

    }

}
//
//Button(
//onClick = {
//    viewModel.uploadRecording(
//        recording.id
//    )
//},
//enabled = !uploadState.isUploading
//) {
//
//    if (uploadState.isUploading) {
//
//        Text("上传中 ${uploadState.progress}%")
//
//    } else {
//
//        Text("上传")
//    }
//}


/**
 * ================================================================
 * RecordingItem
 *
 * 一个录音文件对应一个 Item
 *
 *   onClick: () -> Unit, 录音文件支持上传就行了
 * ================================================================
 */
@Composable
fun RecordingItem(
    recording: Recording,
    onClick: () -> Unit,
    onUploadClick: (Long) -> Unit
) {
    Column(
        modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
    ) {
        /**
         * 文件名
         */
        Text(
            text = recording.fileName,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        //  创建时间
        Text(text = "创建时间：${recording.createdAt}")

        Spacer(
            modifier = Modifier.height(4.dp)
        )

        // 状态
        Text(
            text = "状态： ${getStatusText(recording.status)}"
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        /**
         * ========================================================
         * 操作按钮
         * ========================================================
         */
        Row(
            modifier = Modifier.fillMaxWidth()
        ){
            /*
            *  查看结果
            */
            TextButton(onClick = onClick) {
                Text("查看")
            }

            Spacer(
                modifier = Modifier.weight(1f)
            )
        }

        //上传按钮
        when (recording.status) {

            RecordingStatus.LOCAL ,
            RecordingStatus.ASR_FAILED -> {

                Button(
                    onClick = {
                        onUploadClick(recording.id)
                    }
                ) {
                    if (recording.status == RecordingStatus.UPLOAD_FAILED){
                        Text("重试")
                    } else {
                        Text("上传")
                    }
                }
            }

            RecordingStatus.UPLOADING -> {

                Button(
                    onClick = {},
                    enabled = false
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp)
                    )

                    Spacer(
                        modifier = Modifier.width(8.dp)
                    )

                    Text("上传中...")
                }
            }

            RecordingStatus.UPLOADED -> {
                Button(
                    onClick = {},
                    enabled = false
                ) {
                    Text(
                        text = "已上传"
                    )
                }
            }

            RecordingStatus.PROCESSING -> {

                Button(
                    onClick = {},
                    enabled = false
                ) {
                    Text("识别中")
                }
            }

            RecordingStatus.COMPLETED -> {
                Button(onClick = onClick) {
                    Text("查看结果")
                }
            }

            else -> {
                Text(
                    text = recording.status.name
                )
            }
        }
    }
}


/**
 * ================================================================
 * RecordingStatus → 中文
 * ================================================================
 */
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
