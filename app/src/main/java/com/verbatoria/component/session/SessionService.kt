package com.verbatoria.component.session

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.support.v4.content.LocalBroadcastManager
import android.util.Log
import com.verbatoria.VerbatoriaApplication
import com.verbatoria.component.session.SessionAction.SESSION_EXPIRED
import com.verbatoria.component.session.SessionAction.SESSION_STARTED
import com.verbatoria.domain.authorization.model.Authorization
import com.verbatoria.domain.session.SessionProvider
import com.verbatoria.domain.session.model.Session
import com.verbatoria.ui.login.LoginActivity
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import javax.inject.Inject

/**
 * @author n.remnev
 */

class SessionService : Service() {

    private val logger: Logger = LoggerFactory.getLogger("SessionService")

    companion object {

        const val STOP_SELF_DELAY = 10 * 1000L

        const val ACTION_START_SESSION = "ru.tcsbank.agentref.action.START_SESSION"
        const val ACTION_EXTEND_SESSION = "ru.tcsbank.agentref.action.EXTEND_SESSION"
        const val ACTION_CLOSE_SESSION = "ru.tcsbank.agentref.action.CLOSE_SESSION"

        const val EXTRA_AUTHORIZATION = "extra_authorization"
        const val EXTRA_LIFETIME = "extra_lifetime"

        fun createIntent(context: Context, action: String) =
            Intent(action).apply {
                setClass(context, SessionService::class.java)
            }

        fun createIntent(context: Context, action: String, authorization: Authorization, lifetime: Long?) =
            Intent(action).apply {
                setClass(context, SessionService::class.java)
                putExtra(EXTRA_AUTHORIZATION, authorization)
                putExtra(EXTRA_LIFETIME, lifetime)
            }

    }

//    @Inject
//    lateinit var authorizationManager: AuthorizationManager

    @Inject
    lateinit var sessionProvider: SessionProvider

    private lateinit var localBroadcastManager: LocalBroadcastManager

    private val mainThreadHandler = Handler()

    //region runnables

    private val sessionExpiredRunnable = Runnable {
        Log.e("test", "SessionService sessionExpiredRunnable")

        logger.info("Session expired clean token")
        createSessionExpiredIntent().let { sessionExpiredIntent ->
            logger.debug("Try send session expired intent")
            if (!sendToAuthBroadcast(sessionExpiredIntent)) {
                logger.info("Can't send session expired intent")
                sessionProvider.markSessionAsRemoved()
            } else {
                logger.info("Session expired intent sent")
                sessionProvider.removeSession()
            }
        }
        stopSelf()
    }

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
        VerbatoriaApplication.getInjector().inject(this)
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
            ACTION_START_SESSION -> {}

//            startSession(
//                intent.getSerializableExtra(EXTRA_AUTHORIZATION) as Authorization
//                intent.getLongExtra(
//                    EXTRA_LIFETIME,
//                    resources.getInteger(R.integer.session_default_lifetime).toLong()
//                )
//            )
            ACTION_CLOSE_SESSION -> closeSession()
            ACTION_EXTEND_SESSION -> extendSession()
            else -> if (!sessionProvider.hasSession()) closeSession()
        }
    }

    private fun startSession(
        authorization: Authorization,
        lifetime: Long
    ) {
        Log.e("test", "SessionService startSession")

        sessionProvider.setSession(
            Session(
                authorization,
                lifetime,
                "login",
                Date().time,
                false
            )
        )
        extendSession()

        localBroadcastManager.sendBroadcast(Intent(SESSION_STARTED))
    }
    private fun extendSession() {
        Log.e("test", "SessionService extendSession")
        if (sessionProvider.hasSession()) {
            sessionProvider.getSession()?.let { session ->
//                val lifetime = resources.getInteger(R.integer.session_default_lifetime).toLong()
//                mainThreadHandler.removeCallbacksAndMessages(null)
//                mainThreadHandler.postDelayed(sessionExpiredRunnable, lifetime)
//                logger.debug("Extend session to $lifetime millis")
            } ?: let {
                stopService()
            }
        } else {
            closeSession()
        }
    }

    private fun closeSession() {
        Log.e("test", "SessionService closeSession")

        mainThreadHandler.removeCallbacksAndMessages(null)
        mainThreadHandler.post(sessionExpiredRunnable)
    }

    private fun sendToAuthBroadcast(intent: Intent): Boolean = localBroadcastManager.sendBroadcast(intent)

    private fun createSessionExpiredIntent(): Intent =
        LoginActivity.createIntent(this).apply {
            action = SESSION_EXPIRED
        }

    private fun stopService() {
        logger.warn("Can't extend session, active session not found")
        mainThreadHandler.removeCallbacksAndMessages(null)
        mainThreadHandler.postDelayed(stopSelfRunnable, STOP_SELF_DELAY)
    }

}