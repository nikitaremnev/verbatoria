package com.verbatoria.component.session

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.verbatoria.domain.authorization.model.Authorization

/**
 * @author n.remnev
 */

interface SessionServiceController {

    fun startSession(authorization: Authorization)

    fun closeSession()

}

class SessionServiceControllerImpl(
    val context: Context
) : SessionServiceController {

    override fun startSession(authorization: Authorization) {
        Log.e("test", "SessionServiceController startSession")
        runOnMainThread {
            context.startService(
                SessionService.createIntent(
                    context,
                    SessionService.ACTION_START_SESSION,
                    authorization
                )
            )
        }
    }

    override fun closeSession() {
        Log.e("test", "SessionServiceController closeSession")
        runOnMainThread {
            context.startService(
                SessionService.createIntent(
                    context,
                    SessionService.ACTION_CLOSE_SESSION
                )
            )
        }

    }

    private fun runOnMainThread(operation: () -> Unit) {
        if (Looper.getMainLooper().thread != Thread.currentThread()) {
            Handler(Looper.getMainLooper()).post {
                operation()
            }
        } else {
            operation()
        }
    }

}
