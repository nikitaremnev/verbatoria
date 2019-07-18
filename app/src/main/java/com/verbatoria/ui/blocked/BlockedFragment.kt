package com.verbatoria.ui.blocked

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

class BlockedFragment : Fragment() {

    companion object {

        fun createFragment(): BlockedFragment = BlockedFragment()

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_blocked, container, false)
    }

}