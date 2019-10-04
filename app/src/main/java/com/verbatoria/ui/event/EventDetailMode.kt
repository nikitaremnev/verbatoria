package com.verbatoria.ui.event

/**
 * @author n.remnev
 */

enum class EventDetailMode {

    CREATE_NEW, CHILD_REQUIRED, START, VIEW_ONLY;

    fun isCreateNew() = this == CREATE_NEW

    fun isStart() = this == START

    fun isViewOnly() = this == VIEW_ONLY

    fun isChildRequired() = this == CHILD_REQUIRED

    companion object {

        fun valueOf(ordinal: Int) =
            when (ordinal) {
                CREATE_NEW.ordinal -> CREATE_NEW
                START.ordinal -> START
                VIEW_ONLY.ordinal -> VIEW_ONLY
                CHILD_REQUIRED.ordinal -> CHILD_REQUIRED
                else -> throw IllegalArgumentException("Wrong ordinal for EventDetailMode")
            }

    }

}