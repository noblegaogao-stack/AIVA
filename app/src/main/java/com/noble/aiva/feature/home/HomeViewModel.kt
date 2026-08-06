package com.noble.aiva.feature.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow

class HomeViewModel : ViewModel() {
    private val _count = MutableStateFlow(0) //MutableStateFlow: ViewModel保存状态的一种方式。

    val count: MutableStateFlow<Int>
        get() = _count

    fun increase() {
        _count.value++
    }
}