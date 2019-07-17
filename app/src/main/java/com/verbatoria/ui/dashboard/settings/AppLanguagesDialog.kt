package com.verbatoria.ui.dashboard.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

class AppLanguagesDialog : DialogFragment() {

    companion object {

        fun build(init: Builder.() -> Unit): AppLanguagesDialog = Builder(init).build()

    }

    private var onLanguageSelectedListener: AppLanguagesDialog.OnLanguageSelectedDialogListener? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        onLanguageSelectedListener = (parentFragment ?: activity) as? OnLanguageSelectedDialogListener
        val rootView = LayoutInflater.from(context).inflate(R.layout.dialog_app_languages, null)
        val russianLanguageView = rootView.findViewById<View>(R.id.russian_language_container)
        val englishLanguageView = rootView.findViewById<View>(R.id.english_language_container)
        val hongKongLanguageView = rootView.findViewById<View>(R.id.hong_kong_language_container)
        russianLanguageView.setOnClickListener {
            onLanguageSelectedListener?.onRussianLanguageSelected()
            dismiss()
        }
        englishLanguageView.setOnClickListener {
            onLanguageSelectedListener?.onEnglishLanguageSelected()
            dismiss()
        }
        hongKongLanguageView.setOnClickListener {
            onLanguageSelectedListener?.onHongKongLanguageSelected()
            dismiss()
        }
        return AlertDialog.Builder(activity)
            .setView(rootView)
            .setNegativeButton(R.string.cancel, null)
            .create()
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        if (manager?.isStateSaved == false) {
            super.show(manager, tag)
        }
    }

    class Builder(init: Builder.() -> Unit) {

        init {
            init()
        }

        fun build() =
            AppLanguagesDialog().apply {
                arguments = Bundle()
                    .also {
                        isCancelable = true
                    }
            }
    }

    interface OnLanguageSelectedDialogListener {

        fun onRussianLanguageSelected()

        fun onEnglishLanguageSelected()

        fun onHongKongLanguageSelected()

    }

}