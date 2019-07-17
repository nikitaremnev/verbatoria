package com.verbatoria.ui.common.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager

/**
 * @author n.remnev
 */

private const val TITLE_EXTRA = "title"
private const val MESSAGE_EXTRA = "message"
private const val POSITIVE_TITLE_BUTTON_EXTRA = "positive_title_button"
private const val NEGATIVE_TITLE_BUTTON_EXTRA = "negative_title_button"

class SuggestDialog : DialogFragment() {

    companion object {

        fun build(init: Builder.() -> Unit): SuggestDialog = Builder(init).build()

    }

    private var clickListener: OnClickSuggestDialogListener? = null

    private var cancelListener: OnCancelSuggestDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(activity)
            .apply {
                clickListener = (activity?.supportFragmentManager?.fragments?.firstOrNull() ?: activity) as? OnClickSuggestDialogListener
                cancelListener = (activity?.supportFragmentManager?.fragments?.firstOrNull() ?: activity) as? OnCancelSuggestDialogListener
                if (arguments?.containsKey(TITLE_EXTRA) == true) setTitle(
                    arguments?.getString(
                        TITLE_EXTRA
                    )
                )
                if (arguments?.containsKey(MESSAGE_EXTRA) == true) setMessage(
                    arguments?.getString(
                        MESSAGE_EXTRA
                    )
                )
                if (arguments?.containsKey(POSITIVE_TITLE_BUTTON_EXTRA) == true) {

                    setPositiveButton(arguments?.getString(POSITIVE_TITLE_BUTTON_EXTRA)) { _, _ ->
                        clickListener?.onPositiveClicked(tag)
                        dismiss()
                    }
                }
                if (arguments?.containsKey(NEGATIVE_TITLE_BUTTON_EXTRA) == true) {
                    setNegativeButton(arguments?.getString(NEGATIVE_TITLE_BUTTON_EXTRA)) { _, _ ->
                        clickListener?.onNegativeClicked(tag)
                        dismiss()
                    }
                }
            }
            .create()

    override fun onDestroy() {
        cancelListener?.onCancelDialog(tag)
        cancelListener = null
        clickListener = null
        super.onDestroy()
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        if (manager?.isStateSaved == false) {
            super.show(manager, tag)
        }
    }

    class Builder(init: Builder.() -> Unit) {

        var title: String? = null

        var message: String? = null

        var cancelable: Boolean? = null

        var positiveTitleBtn: String? = null

        var negativeTitleBtn: String? = null

        init {
            init()
        }

        fun build() =
            SuggestDialog().apply {
                arguments = Bundle()
                    .also { args ->
                        if (title != null) args.putString(TITLE_EXTRA, title)
                        if (message != null) args.putString(MESSAGE_EXTRA, message)
                        if (positiveTitleBtn != null) args.putString(
                            POSITIVE_TITLE_BUTTON_EXTRA,
                            positiveTitleBtn
                        )
                        if (negativeTitleBtn != null) args.putString(
                            NEGATIVE_TITLE_BUTTON_EXTRA,
                            negativeTitleBtn
                        )
                        isCancelable = cancelable ?: true
                    }
            }
    }

    interface OnClickSuggestDialogListener {

        fun onPositiveClicked(tag: String?)

        fun onNegativeClicked(tag: String?)

    }

    interface OnCancelSuggestDialogListener {

        fun onCancelDialog(tag: String?)

    }

}