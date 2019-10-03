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
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_russia)
        }
        view.findViewById<View>(R.id.ukraine_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_ukraine)
        }
        view.findViewById<View>(R.id.azerbaijan_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_azerbaijan)
        }
        view.findViewById<View>(R.id.uae_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_uae)
        }
        view.findViewById<View>(R.id.thailand_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_thailand)
        }
        view.findViewById<View>(R.id.israel_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_israel)
        }
        view.findViewById<View>(R.id.belarus_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_belarus)
        }
        view.findViewById<View>(R.id.uzbekistan_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_uzbekistan)
        }
        view.findViewById<View>(R.id.hong_kong_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_hong_kong)
        }
        view.findViewById<View>(R.id.swiss_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_swiss)
        }
        view.findViewById<View>(R.id.indonesia_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_indonesia)
        }
        view.findViewById<View>(R.id.malaysia_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_malaysia)
        }
        view.findViewById<View>(R.id.india_country_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_india)
        }
        view.findViewById<View>(R.id.bulgaria_item_container).setOnClickListener {
            dismiss()
            onCountrySelectionListener?.onCountrySelected(tag, R.string.country_bulgaria)
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

        fun onCountrySelected(tag: String?, languageStringResource: Int)

    }

}