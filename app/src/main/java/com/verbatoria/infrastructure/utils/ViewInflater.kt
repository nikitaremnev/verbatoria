package com.verbatoria.infrastructure.utils

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * @author n.remnev
 */

object ViewInflater {

    fun inflate(@LayoutRes layoutId: Int, parent: ViewGroup): View =
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

}