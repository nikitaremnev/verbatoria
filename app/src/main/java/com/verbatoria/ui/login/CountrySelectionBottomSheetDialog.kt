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
        view.findViewById<View>(R.id.china_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.CHINA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.macau_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.MACAU_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.macedonia_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.MACEDONIA_COUNTRY_KEY)
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
        view.findViewById<View>(R.id.france_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.FRANCE_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.uganda_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.UGANDA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.saudi_arabia_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.SAUDI_ARABIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.serbia_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.SERBIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.mongolia_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.MONGOLIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.montenegro_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.MONTENEGRO_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.mozambique_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.MOZAMBIQUE_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.kazakhstan_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.KAZAKHSTAN_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.jordan_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.JORDAN_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.estonia_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.ESTONIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.turkey_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.TURKEY_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.tanzania_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.TANZANIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.usa_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.USA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.cyprus_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.CYPRUS_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.bosnia_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.BOSNIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.nepal_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.NEPAL_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.new_zealand_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.NEW_ZEALAND_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.argentina_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.ARGENTINA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.greece_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.GREECE_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.slovakia_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.SLOVAKIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.slovenia_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.SLOVENIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.croatia_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.CROATIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.japan_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.JAPAN_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.kenya_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.KENYA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.latvia_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.LATVIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.australia_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.AUSTRALIA_COUNTRY_KEY)
        }
        view.findViewById<View>(R.id.mexico_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, CountryHelper.MEXICO_COUNTRY_KEY)
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
