package com.verbatoria.ui.login

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.utils.CountryHelper

/**
 * @author n.remnev
 */

private const val TITLE_EXTRA = "title"

class CountrySelectionBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {

        fun build(init: Builder.() -> Unit): CountrySelectionBottomSheetDialog =
            Builder(init).build()

    }

    private var title: String = ""

    private var onCountrySelectionListener: CountrySelectionListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onCountrySelectionListener = (parentFragment ?: activity) as? CountrySelectionListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_country_selection, container, false)

        val titleView: TextView = view.findViewById(R.id.title_text_view)
        title = arguments?.getString(TITLE_EXTRA) ?: ""
        titleView.text = title

        view.findViewById<View>(R.id.russia_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.RUSSIAN_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.ukraine_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.UKRAINE_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.azerbaijan_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.AZERBAIJAN_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.uae_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.UAE_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.thailand_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.THAILAND_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.israel_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.ISRAEL_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.belarus_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.BELARUS_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.uzbekistan_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.UZBEKISTAN_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.hong_kong_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.HONG_KONG_COUNTRY)
        }
        view.findViewById<View>(R.id.swiss_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.SWISS_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.indonesia_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.INDONESIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.malaysia_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.MALAYSIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.india_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.INDIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.bulgaria_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.BULGARIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.macau_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.MACAU_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.singapore_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.SINGAPORE_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.egypt_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.EGYPT_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.ethiopia_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.ETHIOPIA_COUNTRY_KEY)
        }
        return view
    }

    override fun onDetach() {
        onCountrySelectionListener = null
        super.onDetach()
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        if (manager?.isStateSaved == false) {
            super.show(manager, tag)
        }
    }

    class Builder(init: Builder.() -> Unit) {

        var titleText: String? = null

        init {
            init()
        }

        fun build() =
            CountrySelectionBottomSheetDialog().apply {
                arguments = Bundle()
                    .also { args ->
                        args.putString(TITLE_EXTRA, titleText)
                    }
            }
    }

    interface CountrySelectionListener {

        fun onCountrySelected(tag: String?, countryKey: String)

    }

}