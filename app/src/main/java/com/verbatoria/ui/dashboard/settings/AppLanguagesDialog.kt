package com.verbatoria.ui.dashboard.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import com.remnev.verbatoria.R
import com.verbatoria.infrastructure.extensions.hide

/**
 * @author n.remnev
 */

private const val IS_RUSSIAN_LANGUAGE_AVAILABLE_EXTRA = "is_russian_language_available"
private const val IS_ENGLISH_LANGUAGE_AVAILABLE_EXTRA = "is_english_language_available"
private const val IS_HONG_KONG_LANGUAGE_AVAILABLE_EXTRA = "is_hong_kong_language_available"

class AppLanguagesDialog : DialogFragment() {

    companion object {

        fun build(init: Builder.() -> Unit): AppLanguagesDialog = Builder(init).build()

    }

    private var onLanguageSelectedListener: AppLanguagesDialog.OnLanguageSelectedDialogListener? =
        null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        onLanguageSelectedListener =
            (activity?.supportFragmentManager?.fragments?.firstOrNull() ?: activity) as? OnLanguageSelectedDialogListener
        val rootView = LayoutInflater.from(context).inflate(R.layout.dialog_app_languages, null)
        val russianLanguageView = rootView.findViewById<View>(R.id.russian_language_container)
        val englishLanguageView = rootView.findViewById<View>(R.id.english_language_container)
        val hongKongLanguageView = rootView.findViewById<View>(R.id.hong_kong_language_container)

        if (arguments?.get(IS_RUSSIAN_LANGUAGE_AVAILABLE_EXTRA) == true) {
            russianLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onRussianLanguageSelected()
                dismiss()
            }
        } else {
            russianLanguageView.hide()
        }
        if (arguments?.get(IS_ENGLISH_LANGUAGE_AVAILABLE_EXTRA) == true) {
            englishLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onEnglishLanguageSelected()
                dismiss()
            }
        } else {
            englishLanguageView.hide()
        }
        if (arguments?.get(IS_HONG_KONG_LANGUAGE_AVAILABLE_EXTRA) == true) {
            hongKongLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onHongKongLanguageSelected()
                dismiss()
            }
        } else {
            hongKongLanguageView.hide()
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

        var isRussianLanguageAvailable: Boolean? = null

        var isEnglishLanguageAvailable: Boolean? = null

        var isHongKongLanguageAvailable: Boolean? = null

        init {
            init()
        }

        fun build() =
            AppLanguagesDialog().apply {
                arguments = Bundle()
                    .also { args ->
                        args.putBoolean(
                            IS_RUSSIAN_LANGUAGE_AVAILABLE_EXTRA,
                            isRussianLanguageAvailable ?: false
                        )
                        args.putBoolean(
                            IS_ENGLISH_LANGUAGE_AVAILABLE_EXTRA,
                            isEnglishLanguageAvailable ?: false
                        )
                        args.putBoolean(
                            IS_HONG_KONG_LANGUAGE_AVAILABLE_EXTRA,
                            isHongKongLanguageAvailable ?: false
                        )
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