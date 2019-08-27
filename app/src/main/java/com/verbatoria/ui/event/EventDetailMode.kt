package com.verbatoria.ui.event

/**
 * @author n.remnev
 */

enum class EventDetailMode {

    CREATE_NEW, START, VIEW_ONLY;

    fun isCreateNew() = this == CREATE_NEW

    fun isStart() = this == START

    fun isViewOnly() = this == VIEW_ONLY

    companion object {

        fun valueOf(ordinal: Int) =
            when (ordinal) {
                CREATE_NEW.ordinal -> CREATE_NEW
                START.ordinal -> START
                VIEW_ONLY.ordinal -> VIEW_ONLY
                else -> throw IllegalArgumentException("Wrong ordinal for EventDetailMode")
            }

    }

}