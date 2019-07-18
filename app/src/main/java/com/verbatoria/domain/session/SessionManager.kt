package com.verbatoria.domain.session

import com.verbatoria.component.session.SessionServiceController
import com.verbatoria.domain.authorization.AuthorizationContext
import com.verbatoria.domain.authorization.model.Authorization
import com.verbatoria.domain.authorization.model.OfflineAuthorization
import com.verbatoria.domain.session.model.Session
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

interface SessionManager : AuthorizationContext {

    fun getSession(): Session?

    fun startSession(authorization: Authorization)

    fun closeSession(cause: String)

    fun hasActiveSession(): Boolean

    fun isExpiredSession(): Boolean

    fun updateSessionProvider(sessionProvider: SessionProvider)

}

class SessionManagerImpl(
    private val sessionServiceController: SessionServiceController,
    private var sessionProvider: SessionProvider
) : SessionManager {

    private val logger = LoggerFactory.getLogger("SessionManager")

    override fun startSession(authorization: Authorization) {
        logger.info("Dispatch start session")
        sessionServiceController.startSession(authorization)
    }

    override fun hasActiveSession() =
        sessionProvider.hasSession()

    override fun isExpiredSession(): Boolean =
        !sessionProvider.hasSession()

    override fun getSession(): Session? =
        sessionProvider.getSession()

    override fun closeSession(cause: String) {
        logger.info("Dispatch close session [$cause]")
        sessionServiceController.closeSession()
    }

    override fun invalidate() {
        if (hasActiveSession()) {
            //extendSession()
        }
    }

    override fun unauthorize() {
        if (sessionProvider.getSession()?.isOffline() == false) {
            closeSession("Server session is expired")
        }
    }

    override fun getAuthorization(): Authorization =
        sessionProvider.getSession()?.authorization ?: OfflineAuthorization()

    override fun updateSessionProvider(sessionProvider: SessionProvider) {
        this.sessionProvider = sessionProvider
    }

}