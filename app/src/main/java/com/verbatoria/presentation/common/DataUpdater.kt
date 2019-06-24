package com.verbatoria.presentation.common

/**
 * @author n.remnev
 */

interface DataUpdater {

    fun update(itemsData: List<Any>)

    fun update(index: Int)

    fun updateInserted(itemsData: List<Any>, index: Int, itemCount: Int)

    fun updateRemoved(itemsData: List<Any>, index: Int, itemCount: Int)

    fun move(index1: Int, index2: Int)

}