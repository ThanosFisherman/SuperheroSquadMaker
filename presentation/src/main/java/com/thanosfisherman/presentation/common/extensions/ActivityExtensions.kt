package com.thanosfisherman.presentation.common.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment

inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(createIntent<T>())
}

inline fun <reified T : Activity> Fragment.startActivity() {
    startActivity(context?.createIntent<T>())
}

inline fun <reified T : Activity> Fragment.startActivityForResult(requestCode: Int, bundle: Bundle? = null) {
    startActivityForResult(context?.createIntent<T>(), requestCode, bundle)
}

inline fun <reified T : Activity> Context.startActivity() {
    startActivity(createIntent<T>())
}

inline fun <reified T : Activity> Context.createIntent() =
    Intent(this, T::class.java)


