package com.verbatoria.ui.common

import android.content.Context
import android.util.AttributeSet
import android.widget.EditText
import android.text.TextWatcher

/**
 * @author n.remnev
 */

class ExtendedEditText : EditText {

    private var textWatchersList: MutableList<TextWatcher> = mutableListOf()

    constructor(ctx: Context) : super(ctx)

    constructor(ctx: Context, attrs: AttributeSet) : super(ctx, attrs)

    constructor(ctx: Context, attrs: AttributeSet, defStyle: Int) : super(ctx, attrs, defStyle)


    override fun addTextChangedListener(watcher: TextWatcher) {
        textWatchersList.add(watcher)
        super.addTextChangedListener(watcher)
    }

    override fun removeTextChangedListener(watcher: TextWatcher) {
        textWatchersList.remove(watcher)
        super.removeTextChangedListener(watcher)
    }

    fun clearTextChangedListeners() {
        textWatchersList.clear()
    }

}