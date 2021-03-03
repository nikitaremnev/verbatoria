package com.verbatoria.ui.dashboard.settings

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import com.remnev.verbatoria.R
import com.verbatoria.business.dashboard.LocalesAvailable
import com.verbatoria.infrastructure.extensions.hide

/**
 * @author n.remnev
 */

private const val IS_RUSSIAN_LANGUAGE_AVAILABLE_EXTRA = "is_russian_language_available"
private const val IS_ENGLISH_LANGUAGE_AVAILABLE_EXTRA = "is_english_language_available"
private const val IS_HONG_KONG_LANGUAGE_AVAILABLE_EXTRA = "is_hong_kong_language_available"
private const val IS_UKRAINIAN_LANGUAGE_AVAILABLE_EXTRA = "is_ukrainian_language_available"
private const val IS_BULGARIAN_LANGUAGE_AVAILABLE_EXTRA = "is_bulgarian_language_available"
private const val IS_TURKEY_LANGUAGE_AVAILABLE_EXTRA = "is_turkey_language_available"
private const val IS_ARABIC_LANGUAGE_AVAILABLE_EXTRA = "is_arabic_language_available"
private const val IS_BOSNIAN_LANGUAGE_AVAILABLE_EXTRA = "is_bosnian_language_available"

private const val CURRENT_LOCALE_EXTRA = "current_locale"

class AppLanguagesDialog : DialogFragment() {

    companion object {

        fun build(init: Builder.() -> Unit): AppLanguagesDialog = Builder(init).build()

    }

    private var onLanguageSelectedListener: OnLanguageSelectedDialogListener? =
        null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        onLanguageSelectedListener =
            (activity?.supportFragmentManager?.fragments?.firstOrNull() ?: activity) as? OnLanguageSelectedDialogListener
        val rootView = LayoutInflater.from(context).inflate(R.layout.dialog_app_languages, null)
        val russianLanguageView = rootView.findViewById<View>(R.id.russian_language_container)
        val englishLanguageView = rootView.findViewById<View>(R.id.english_language_container)
        val hongKongLanguageView = rootView.findViewById<View>(R.id.hong_kong_language_container)
        val ukrainianLanguageView = rootView.findViewById<View>(R.id.ukrainian_language_container)
        val bulgarianLanguageView = rootView.findViewById<View>(R.id.bulgarian_language_container)
        val turkeyLanguageView = rootView.findViewById<View>(R.id.turkey_language_container)
        val arabicLanguageView = rootView.findViewById<View>(R.id.arabic_language_container)
        val bosnianLanguageView = rootView.findViewById<View>(R.id.bosnia_language_container)

        val currentLocale = arguments?.get(CURRENT_LOCALE_EXTRA)

        if (arguments?.get(IS_RUSSIAN_LANGUAGE_AVAILABLE_EXTRA) == true) {
            if (currentLocale == LocalesAvailable.RUSSIAN_LOCALE) {
                russianLanguageView.findViewById<View>(R.id.russian_selected_image_view).visibility = View.VISIBLE
            }
            russianLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onRussianLanguageSelected()
                dismiss()
            }
        } else {
            russianLanguageView.hide()
        }
        if (arguments?.get(IS_ENGLISH_LANGUAGE_AVAILABLE_EXTRA) == true) {
            if (currentLocale == LocalesAvailable.ENGLISH_LOCALE) {
                englishLanguageView.findViewById<View>(R.id.english_selected_image_view).visibility = View.VISIBLE
            }
            englishLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onEnglishLanguageSelected()
                dismiss()
            }
        } else {
            englishLanguageView.hide()
        }
        if (arguments?.get(IS_HONG_KONG_LANGUAGE_AVAILABLE_EXTRA) == true) {
            if (currentLocale == LocalesAvailable.HONG_KONG_LOCALE_FROM_SERVER) {
                hongKongLanguageView.findViewById<View>(R.id.hong_kong_selected_image_view).visibility = View.VISIBLE
            }
            hongKongLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onHongKongLanguageSelected()
                dismiss()
            }
        } else {
            hongKongLanguageView.hide()
        }
        if (arguments?.get(IS_UKRAINIAN_LANGUAGE_AVAILABLE_EXTRA) == true) {
            if (currentLocale == LocalesAvailable.UKRAINE_LOCALE_FROM_SERVER) {
                ukrainianLanguageView.findViewById<View>(R.id.ukrainian_selected_image_view).visibility = View.VISIBLE
            }
            ukrainianLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onUkrainianLanguageSelected()
                dismiss()
            }
        } else {
            ukrainianLanguageView.hide()
        }

        if (arguments?.get(IS_BULGARIAN_LANGUAGE_AVAILABLE_EXTRA) == true) {
            if (currentLocale == LocalesAvailable.BULGARIAN_LOCALE) {
                bulgarianLanguageView.findViewById<View>(R.id.bulgarian_selected_image_view).visibility = View.VISIBLE
            }
            bulgarianLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onBulgarianLanguageSelected()
                dismiss()
            }
        } else {
            bulgarianLanguageView.hide()
        }

        if (arguments?.get(IS_TURKEY_LANGUAGE_AVAILABLE_EXTRA) == true) {
            if (currentLocale == LocalesAvailable.TURKEY_LOCALE) {
                turkeyLanguageView.findViewById<View>(R.id.turkey_selected_image_view).visibility = View.VISIBLE
            }
            turkeyLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onTurkeyLanguageSelected()
                dismiss()
            }
        } else {
            turkeyLanguageView.hide()
        }

        if (arguments?.get(IS_ARABIC_LANGUAGE_AVAILABLE_EXTRA) == true) {
            if (currentLocale == LocalesAvailable.ARABIC_LOCALE) {
                arabicLanguageView.findViewById<View>(R.id.arabic_selected_image_view).visibility = View.VISIBLE
            }
            arabicLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onArabicLanguageSelected()
                dismiss()
            }
        } else {
            arabicLanguageView.hide()
        }

        if (arguments?.get(IS_BOSNIAN_LANGUAGE_AVAILABLE_EXTRA) == true) {
            if (currentLocale == LocalesAvailable.BOSNIA_LOCALE || currentLocale == LocalesAvailable.BOSNIAN_LOCALE_FROM_SERVER) {
                bosnianLanguageView.findViewById<View>(R.id.bosnia_selected_image_view).visibility = View.VISIBLE
            }
            bosnianLanguageView.setOnClickListener {
                onLanguageSelectedListener?.onBosnianLanguageSelected()
                dismiss()
            }
        } else {
            bosnianLanguageView.hide()
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

        var isUkrainianLanguageAvailable: Boolean? = null

        var isBulgarianLanguageAvailable: Boolean? = null

        var isTurkeyLanguageAvailable: Boolean? = null

        var isArabicLanguageAvailable: Boolean? = null

        var isBosniaLanguageAvailable: Boolean? = null

        var currentLocale: String? = null

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
                        args.putBoolean(
                            IS_UKRAINIAN_LANGUAGE_AVAILABLE_EXTRA,
                            isUkrainianLanguageAvailable ?: false
                        )
                        args.putBoolean(
                            IS_BULGARIAN_LANGUAGE_AVAILABLE_EXTRA,
                            isBulgarianLanguageAvailable ?: false
                        )
                        args.putBoolean(
                            IS_TURKEY_LANGUAGE_AVAILABLE_EXTRA,
                            isTurkeyLanguageAvailable ?: false
                        )
                        args.putBoolean(
                            IS_ARABIC_LANGUAGE_AVAILABLE_EXTRA,
                            isArabicLanguageAvailable ?: false
                        )
                        args.putBoolean(
                            IS_BOSNIAN_LANGUAGE_AVAILABLE_EXTRA,
                            isBosniaLanguageAvailable ?: false
                        )
                        args.putString(
                            CURRENT_LOCALE_EXTRA,
                            currentLocale
                        )
                        isCancelable = true
                    }
            }
    }

    interface OnLanguageSelectedDialogListener {

        fun onRussianLanguageSelected()

        fun onEnglishLanguageSelected()

        fun onHongKongLanguageSelected()

        fun onUkrainianLanguageSelected()

        fun onBulgarianLanguageSelected()

        fun onTurkeyLanguageSelected()

        fun onArabicLanguageSelected()

        fun onBosnianLanguageSelected()

    }

}