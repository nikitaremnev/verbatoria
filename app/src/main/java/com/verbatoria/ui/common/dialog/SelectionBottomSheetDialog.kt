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

private const val ITEMS_EXTRA = "items"
private const val TITLE_EXTRA = "title"

class SelectionBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {

        fun build(init: Builder.() -> Unit): SelectionBottomSheetDialog =
            Builder(init).build()

    }

    private var title: String = ""
    private var items: List<String> = listOf()
    private var onSelectedItemListener: OnSelectedItemListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onSelectedItemListener = (parentFragment ?: activity) as? OnSelectedItemListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        title = arguments?.getString(TITLE_EXTRA) ?: ""
        items = arguments?.getStringArrayList(ITEMS_EXTRA) ?: listOf()

        val view = inflater.inflate(R.layout.dialog_selection_bottom_sheet, container, false)
        val titleView: TextView = view.findViewById(R.id.title_text_view)
        val itemsContainer: ViewGroup = view.findViewById(R.id.container)

        titleView.text = title
        items.forEachIndexed { index, item ->
            val itemView = inflater.inflate(R.layout.item_selection_dialog, null, false) as TextView
            itemView.text = item
            itemView.setOnClickListener {
                onSelectedItemListener?.onSelectionDialogItemSelected(tag, index)
                dismiss()
            }
            itemsContainer.addView(itemView)
        }
        return view
    }

    override fun onDetach() {
        onSelectedItemListener = null
        super.onDetach()
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        if (manager?.isStateSaved == false) {
            super.show(manager, tag)
        }
    }

    class Builder(init: Builder.() -> Unit) {
        var titleText: String? = null
        var itemsArray: ArrayList<String> = arrayListOf()

        init {
            init()
        }

        fun build() =
            SelectionBottomSheetDialog().apply {
                arguments = Bundle()
                    .also { args ->
                        args.putString(TITLE_EXTRA, titleText)
                        args.putStringArrayList(ITEMS_EXTRA, itemsArray)
                    }
            }
    }

    interface OnSelectedItemListener {

        fun onSelectionDialogItemSelected(tag: String?, position: Int)
    }

}