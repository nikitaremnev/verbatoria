package com.verbatoria.infrastructure.extensions

import android.app.ProgressDialog
import android.content.Context
import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

fun Context.createProgressDialog(text: String): ProgressDialog = ProgressDialog(this).apply {
    setMessage(text)
    isIndeterminate = true
    setCancelable(false)
    setCanceledOnTouchOutside(false)
}

fun showProgressDialogFragment(
    @StringRes messageResId: Int, fragmentManager: FragmentManager,
    tag: String
) {
    if (!fragmentManager.isStateSaved && fragmentManager.findFragmentByTag(tag) == null) {
        AlertDialogFragment.newInstance(messageResId).show(fragmentManager, tag)
    }
}

fun hideProgressDialogFragment(fragmentManager: FragmentManager, tag: String) {
    if (!fragmentManager.isStateSaved) {
        (fragmentManager.findFragmentByTag(tag) as? AlertDialogFragment)?.dismiss()
    }
}

fun Context.getColorFromRes(@ColorRes colorRes: Int) =
    ContextCompat.getColor(this, colorRes)

fun Context.getDimensionFromRes(@DimenRes dimenRes: Int) =
    resources.getDimensionPixelSize(dimenRes)

fun Context.getDrawableFromRes(@DrawableRes drawableRes: Int): Drawable =
    ContextCompat.getDrawable(this, drawableRes)
        ?: throw Resources.NotFoundException(
            "Drawable ${this.resources.getResourceName(
                drawableRes
            )} is not found!"
        )

class AlertDialogFragment : DialogFragment() {

    companion object {
        private const val MESSAGE_RES_ID_KEY = "message_res_id"

        fun newInstance(@StringRes messageResId: Int): AlertDialogFragment =
            AlertDialogFragment().apply {
                arguments = Bundle().apply {
                    putInt(MESSAGE_RES_ID_KEY, messageResId)
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.dialog_loading_progress, container, false)?.apply {
            arguments?.let {
                findViewById<TextView>(R.id.dialog_loading_progress_message)
                    .setText(it.getInt(MESSAGE_RES_ID_KEY))
            }
            isCancelable = false
        }

}