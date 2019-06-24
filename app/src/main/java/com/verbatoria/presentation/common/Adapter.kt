package com.verbatoria.presentation.common

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/***
 * @author n.remnev
 */

open class Adapter(
    private val viewBinders: List<ItemAdapter<*, *>>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), DataUpdater {

    protected var allData: List<Any> = listOf()
    protected var recyclerView: RecyclerView? = null

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        this.recyclerView = null
        super.onDetachedFromRecyclerView(recyclerView)
    }

    override fun getItemCount(): Int = allData.size

    override fun getItemViewType(position: Int): Int =
        viewBinders.indexOfFirst { it.isType(allData[position]) }
            .let { index ->
                if (index == -1) {
                    throw IllegalStateException("ItemAdapter of item (${allData[position]}) not registered")
                } else {
                    index
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        viewBinders[viewType].createViewHolder(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = allData[position]
        val type = viewBinders.first { it.isType(data) }
        type.viewBinder.bindView(holder, data, position)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        if (holder.adapterPosition == -1) return
        val type = viewBinders.first { it.isType(allData[holder.adapterPosition]) }
        type.viewBinder.detachViewHolder(holder)
        super.onViewDetachedFromWindow(holder)
    }

    override fun move(index1: Int, index2: Int) {
        notifyItemMoved(index1, index2)
    }

    override fun update(itemsData: List<Any>) {
        allData = itemsData
        recyclerView?.post {
            notifyDataSetChanged()
        }
    }

    override fun update(index: Int) {
        notifyItemChanged(index)
    }

    override fun updateInserted(itemsData: List<Any>, index: Int, itemCount: Int) {
        allData = itemsData
        notifyItemRangeInserted(index, itemCount)
    }

    override fun updateRemoved(itemsData: List<Any>, index: Int, itemCount: Int) {
        allData = itemsData
        notifyItemRangeRemoved(index, itemCount)
    }
}