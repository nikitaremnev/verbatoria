package com.verbatoria.component.session

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * @author p.o.drozdov
 */

class SessionExpiredBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, data: Intent?) {
        data?.run {
            context.startActivity(this)
        }
    }

}