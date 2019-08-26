package com.verbatoria.ui.event

/**
 * @author n.remnev
 */

enum class EventDetailMode {

    CREATE_NEW, START, EDIT;

    fun isCreateNew() = this == CREATE_NEW

    fun isStart() = this == START

    fun isEdit() = this == EDIT

    companion object {

        fun valueOf(ordinal: Int) =
            when (ordinal) {
                CREATE_NEW.ordinal -> CREATE_NEW
                START.ordinal -> START
                EDIT.ordinal -> EDIT
                else -> throw IllegalArgumentException("Wrong ordinal for EventDetailMode")
            }

    }

}