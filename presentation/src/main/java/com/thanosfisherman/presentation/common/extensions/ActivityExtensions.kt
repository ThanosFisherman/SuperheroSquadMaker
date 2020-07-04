package com.thanosfisherman.presentation.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(createIntent<T>())
}

inline fun <reified T : Activity> Context.createIntent() =
    Intent(this, T::class.java)



