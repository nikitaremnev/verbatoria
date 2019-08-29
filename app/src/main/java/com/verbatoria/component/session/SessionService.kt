package com.verbatoria.component.session

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.verbatoria.VerbatoriaKtApplication
import com.verbatoria.domain.authorization.model.Authorization
import com.verbatoria.domain.session.repository.SessionProvider
import com.verbatoria.domain.session.model.Session
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Inject

/**
 * @author n.remnev
 */

class SessionService : Service() {

    private val logger: Logger = LoggerFactory.getLogger("SessionService")

    companion object {

        const val STOP_SELF_DELAY = 10 * 1000L

        const val ACTION_START_SESSION = "com.verbatoria.component.session.START_SESSION"
        const val ACTION_CLOSE_SESSION = "com.verbatoria.component.session.CLOSE_SESSION"

        const val EXTRA_AUTHORIZATION = "extra_authorization"

        fun createIntent(context: Context, action: String) =
            Intent(action).apply {
                setClass(context, SessionService::class.java)
            }

        fun createIntent(context: Context, action: String, authorization: Authorization) =
            Intent(action).apply {
                setClass(context, SessionService::class.java)
                putExtra(EXTRA_AUTHORIZATION, authorization)
            }

    }

//    @Inject
//    lateinit var authorizationManager: AuthorizationManager

    @Inject
    lateinit var sessionProvider: SessionProvider

    private lateinit var localBroadcastManager: LocalBroadcastManager

    private val mainThreadHandler = Handler()

    //region runnables

    private val stopSelfRunnable = Runnable {
        Log.e("test", "SessionService stopSelfRunnable")
        stopSelf()
    }

    //endregion

    //region service

    override fun onCreate() {
        super.onCreate()
        Log.e("test", "SessionService onCreate")
        logger.debug("Create session service")
        localBroadcastManager = LocalBroadcastManager.getInstance(this)
        (application as VerbatoriaKtApplication).injector.inject(this)
    }

    override fun onDestroy() {
        Log.e("test", "SessionService onDestroy")
        logger.debug("Destroy session service")
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e("test", "SessionService onStartCommand")
        handleCommand(intent)
        return START_STICKY
    }

    //endregion

    //region session commands

    private fun handleCommand(intent: Intent?) {
        when (intent?.action) {
            ACTION_START_SESSION -> startSession(intent.getSerializableExtra(EXTRA_AUTHORIZATION) as Authorization)
            ACTION_CLOSE_SESSION -> closeSession()
            else -> if (!sessionProvider.hasSession()) closeSession()
        }
    }

    private fun startSession(authorization: Authorization) {
        Log.e("test", "SessionService startSession")
        sessionProvider.setSession(Session(authorization, false))
//        localBroadcastManager.sendBroadcast(Intent(SESSION_STARTED))
    }

    private fun closeSession() {
        Log.e("test", "SessionService closeSession")
        mainThreadHandler.removeCallbacksAndMessages(null)
        stopService()
    }

    private fun stopService() {
        logger.warn("Can't extend session, active session not found")
        mainThreadHandler.removeCallbacksAndMessages(null)
        mainThreadHandler.postDelayed(stopSelfRunnable, STOP_SELF_DELAY)
    }

}