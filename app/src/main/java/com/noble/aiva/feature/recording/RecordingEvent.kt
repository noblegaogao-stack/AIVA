package com.noble.aiva.feature.recording

sealed interface RecordingEvent {
    data object StartClicked: RecordingEvent
    data object StopClicked: RecordingEvent
}