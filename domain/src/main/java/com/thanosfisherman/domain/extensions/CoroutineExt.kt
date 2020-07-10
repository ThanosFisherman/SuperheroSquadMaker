package com.thanosfisherman.domain.extensions

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun Job.cancelIfActive() {
    if (isActive)
        cancel()
}

@ExperimentalCoroutinesApi
fun <T> MutableStateFlow<T>.asStateFlow() = object : StateFlow<T> by this {}

