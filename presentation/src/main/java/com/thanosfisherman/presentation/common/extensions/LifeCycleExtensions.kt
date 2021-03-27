package com.thanosfisherman.presentation.common.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, body: (T) -> Unit = {}) {
    liveData.observe(this, { it?.let { t -> body(t) } })
}

fun <T> LifecycleOwner.observeStateFlow(stateFlow: StateFlow<T>, body: suspend (T) -> Unit = {}) {
    stateFlow.onEach { it?.let { t -> body(t) } }.launchIn(lifecycleScope)
}

fun <T> LifecycleOwner.observeSharedFlow(stateFlow: SharedFlow<T>, body: (T) -> Unit = {}) {
    stateFlow.onEach { it?.let { t -> body(t) } }.launchIn(lifecycleScope)
}

@Suppress("detekt.UnsafeCast")
fun <T> MutableLiveData<T>.toLiveData() = this as LiveData<T>
