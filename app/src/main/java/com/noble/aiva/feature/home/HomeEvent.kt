package com.noble.aiva.feature.home

// 密封接口
sealed interface HomeEvent {
    // 单例数据类
    data object RecordClicked: HomeEvent
}