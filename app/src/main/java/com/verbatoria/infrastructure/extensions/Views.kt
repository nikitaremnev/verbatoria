package com.verbatoria.infrastructure.extensions

import android.view.View

/**
 * @author n.remnev
 */

fun View.hide() {
    this.visibility = View.GONE
}

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}