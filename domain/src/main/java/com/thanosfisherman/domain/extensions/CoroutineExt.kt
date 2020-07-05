package com.thanosfisherman.domain.extensions

import kotlinx.coroutines.Job

fun Job.cancelIfActive() {
    if (isActive)
        cancel()
}