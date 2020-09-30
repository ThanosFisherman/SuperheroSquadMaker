package com.thanosfisherman.presentation.common.utils

import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.thanosfisherman.presentation.R


object RapidSnack {
    private var snackbar: Snackbar? = null

    fun success(view: View, @StringRes message: Int = R.string.success) {
        snackbar?.dismiss()
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setAction(android.R.string.ok) { dismiss() }
            setActionTextColor(Color.WHITE)
            setBackgroundRes(R.drawable.bg_success)
            setTextStyle(R.drawable.ic_success, Color.WHITE)
            show()
        }
    }

    fun error(view: View, @StringRes message: Int = R.string.try_again) {
        snackbar?.dismiss()
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setAction(android.R.string.ok) { dismiss() }
            setActionTextColor(Color.WHITE)
            setBackgroundRes(R.drawable.bg_error)
            setTextStyle(R.drawable.ic_error, Color.WHITE)
            show()
        }
    }

    fun fire(view: View, @StringRes message: Int = R.string.try_again) {
        snackbar?.dismiss()
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setAction(android.R.string.ok) { dismiss() }
            setActionTextColor(Color.WHITE)
            setBackgroundRes(R.drawable.bg_error)
            setTextStyle(R.drawable.ic_fire, Color.WHITE)
            show()
        }
    }

    fun error(view: View, message: String) {
        snackbar?.dismiss()
        snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG).apply {
            animationMode = Snackbar.ANIMATION_MODE_SLIDE
            setAction(android.R.string.ok) { dismiss() }
            setActionTextColor(Color.WHITE)
            setBackgroundRes(R.drawable.bg_error)
            setTextStyle(R.drawable.ic_error, Color.WHITE)
            show()
        }
    }

    private fun Snackbar.setTextStyle(@DrawableRes resId: Int, @ColorInt textColor: Int) {
        val textView = view.findViewById<TextView>(R.id.snackbar_text)
        val textAction = view.findViewById<TextView>(com.google.android.material.R.id.snackbar_action)
        //textView.textSize = 16F
        textView.maxLines = 1
        textView.setSingleLine()
        textView.gravity = Gravity.CENTER
        textView.setTextColor(textColor)
        textView.setCompoundDrawablesWithIntrinsicBounds(resId, 0, 0, 0)
        textView.compoundDrawablePadding = textView.resources.getDimensionPixelOffset(R.dimen.snack_icon_padding)
        textAction.gravity = Gravity.CENTER

    }

    private fun Snackbar.setBackgroundRes(@DrawableRes resId: Int) {
        view.background = ContextCompat.getDrawable(view.context, resId)
    }
}