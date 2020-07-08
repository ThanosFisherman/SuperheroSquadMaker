package com.thanosfisherman.presentation.fragments

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment

class AlertFragment : AppCompatDialogFragment(), DialogInterface.OnClickListener {

    private lateinit var childInteractor: FragmentInteractionListener

    companion object {
        val TAG = AlertFragment::class.java.simpleName
        fun newInstance(title: String, text: String) =
            AlertFragment().apply {
                arguments = Bundle().apply {
                    putString("title", title)
                    putString("text", text)
                }
            }
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        if (which == DialogInterface.BUTTON_POSITIVE)
            childInteractor.messageFromChildToParent()
        dismiss()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentInteractionListener) {
            childInteractor = context
        } else {
            throw ClassCastException("The parent fragment/activity must implement ChildFragmentInteractionListener")
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val alertDialog = AlertDialog.Builder(requireContext()).apply {
            setTitle(arguments?.getString("title"))
            setMessage(arguments?.getString("text"))
            setPositiveButton(android.R.string.ok, this@AlertFragment)
            setNegativeButton(android.R.string.cancel, this@AlertFragment)
        }
        return alertDialog.create()
    }
}