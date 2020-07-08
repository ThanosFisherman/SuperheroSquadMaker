package com.thanosfisherman.presentation.common.utils

import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

object FragmentUtils {

    fun showDialog(dialogFragment: DialogFragment, fragmentManager: FragmentManager?, tag: String = "dialog") {
        if (fragmentManager == null) return

        try {
            cancelDialog(
                fragmentManager,
                tag
            )
            fragmentManager.beginTransaction().add(dialogFragment, tag).commit()
        } catch (e: IllegalStateException) {
        }
    }

    fun showDialogWithState(dialogFragment: DialogFragment, fragmentManager: FragmentManager?, tag: String = "dialog") {
        if (fragmentManager == null) return

        try {
            val prevFrag = fragmentManager.findFragmentByTag(tag)
            prevFrag?.let {
                if (it is DialogFragment) it.dialog?.show()
                return
            }

            fragmentManager.beginTransaction().add(dialogFragment, tag).commit()
        } catch (e: IllegalStateException) {
        }
    }

    fun cancelDialog(fragmentManager: FragmentManager?, tag: String = "dialog") {
        if (fragmentManager == null) return
        val prevFrag = fragmentManager.findFragmentByTag(tag)
        prevFrag?.let {
            if (it is DialogFragment) it.dismiss()
        }
    }

    fun <T : Fragment?> getFragmentByTag(fm: FragmentManager?, tag: String?): T? {
        return if (fm == null) null else fm.findFragmentByTag(tag) as T?
    }
}