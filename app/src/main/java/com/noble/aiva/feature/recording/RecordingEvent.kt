package com.noble.aiva.feature.recording

sealed interface RecordingEvent {
    data object StartRecoding: RecordingEvent
    data object FinishRecoding: RecordingEvent
}