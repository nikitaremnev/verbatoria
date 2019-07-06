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

class LanguageSelectionBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {

        fun build(init: Builder.() -> Unit): LanguageSelectionBottomSheetDialog =
            Builder(init).build()

    }

    private var title: String = ""

    private var onLanguageSelectedListener: LanguageSelectedListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onLanguageSelectedListener = (parentFragment ?: activity) as? LanguageSelectedListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_language_selection, container, false)

        val titleView: TextView = view.findViewById(R.id.title_text_view)
        title = arguments?.getString(TITLE_EXTRA) ?: ""
        titleView.text = title

        view.findViewById<View>(R.id.russian_language_item_container).setOnClickListener {
            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.country_russia)
        }
        view.findViewById<View>(R.id.ukraine_language_item_container).setOnClickListener {
            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.country_ukraine)
        }
        view.findViewById<View>(R.id.azerbaijan_language_item_container).setOnClickListener {
            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.country_azerbaijan)
        }
        view.findViewById<View>(R.id.uae_language_item_container).setOnClickListener {
            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.country_uae)
        }
        view.findViewById<View>(R.id.thailand_language_item_container).setOnClickListener {
            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.country_thailand)
        }
        view.findViewById<View>(R.id.israel_language_item_container).setOnClickListener {
            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.country_israel)
        }
        view.findViewById<View>(R.id.belarus_language_item_container).setOnClickListener {
            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.country_belarus)
        }
        view.findViewById<View>(R.id.uzbekistan_language_item_container).setOnClickListener {
            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.country_uzbekistan)
        }
        view.findViewById<View>(R.id.hong_kong_language_item_container).setOnClickListener {
            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.country_hong_kong)
        }
//        view.findViewById<View>(R.id.swiss_language_item_container).setOnClickListener {
//            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.country_swiss)
//        }

        return view
    }

    override fun onDetach() {
        onLanguageSelectedListener = null
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
            LanguageSelectionBottomSheetDialog().apply {
                arguments = Bundle()
                    .also { args ->
                        args.putString(TITLE_EXTRA, titleText)
                    }
            }
    }

    interface LanguageSelectedListener {

        fun onLanguageSelected(tag: String?, languageStringResource: Int)

    }

}