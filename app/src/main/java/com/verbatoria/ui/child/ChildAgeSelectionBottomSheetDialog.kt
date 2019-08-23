package com.verbatoria.ui.child

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomSheetDialogFragment
import android.support.v4.app.FragmentManager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

private const val START_AGE = 4
private const val SCHOOL_MODE_END_AGE = 17
private const val END_AGE = 65
private const val COLUMN_COUNT = 4

private const val IS_SCHOOL_MODE_EXTRA = "is_school_mode_extra"

class ChildAgeSelectionBottomSheetDialog : BottomSheetDialogFragment() {

    companion object {

        fun build(init: Builder.() -> Unit): ChildAgeSelectionBottomSheetDialog =
            Builder(init).build()

    }

    private var isSchoolMode: Boolean = false

    private var onChildAgeSelectionListener: ChildAgeSelectionListener? = null

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        onChildAgeSelectionListener = (parentFragment ?: activity) as? ChildAgeSelectionListener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_child_age_selection, container, false)

        isSchoolMode = arguments?.getBoolean(IS_SCHOOL_MODE_EXTRA) ?: false

        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(context, COLUMN_COUNT)
        recyclerView.adapter = AgeAdapter(
            (if (isSchoolMode) SCHOOL_MODE_END_AGE else END_AGE) - START_AGE + 1
        )

        return view
    }

    override fun onDetach() {
        onChildAgeSelectionListener = null
        super.onDetach()
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        if (manager?.isStateSaved == false) {
            super.show(manager, tag)
        }
    }

    class Builder(init: Builder.() -> Unit) {

        var isSchoolMode: Boolean? = null

        init {
            init()
        }

        fun build() =
            ChildAgeSelectionBottomSheetDialog().apply {
                arguments = Bundle()
                    .also { args ->
                        args.putBoolean(IS_SCHOOL_MODE_EXTRA, isSchoolMode)
                    }
            }

    }

    interface ChildAgeSelectionListener {

        fun onChildAgeSelected(tag: String?, age: Int)

    }


    private inner class AgeItemViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.item_child_age, parent, false)) {

        val ageTextView: TextView = itemView.findViewById(R.id.text)

        init {
            ageTextView.setOnClickListener { v ->
                onChildAgeSelectionListener?.onChildAgeSelected(tag, START_AGE + adapterPosition)
                dismiss()
            }
        }

    }

    private inner class AgeAdapter(
        private val itemsCount: Int
    ) : RecyclerView.Adapter<AgeItemViewHolder>() {

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): AgeItemViewHolder = AgeItemViewHolder(LayoutInflater.from(parent.context), parent)

        override fun onBindViewHolder(viewHolder: AgeItemViewHolder, position: Int) {
            viewHolder.ageTextView.text = (START_AGE + position).toString()
        }

        override fun getItemCount(): Int = itemsCount

    }

}