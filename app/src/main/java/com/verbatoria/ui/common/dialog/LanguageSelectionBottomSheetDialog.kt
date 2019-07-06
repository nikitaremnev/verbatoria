package com.verbatoria.ui.common.dialog

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
            onLanguageSelectedListener?.onLanguageSelected(tag, R.string.language_russian)
        }

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