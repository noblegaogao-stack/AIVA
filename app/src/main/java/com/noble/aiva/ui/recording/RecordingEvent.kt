package com.noble.aiva.ui.recording

sealed interface RecordingEvent {
    data object StartClicked: RecordingEvent
    data object StopClicked: RecordingEvent
}