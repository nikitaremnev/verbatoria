package com.verbatoria.business.recovery_password

import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

interface RecoveryPasswordInteractor {


}

class RecoveryPasswordInteractorImpl(
    private val authorizationManager: AuthorizationManager,
    private val schedulersFactory: RxSchedulersFactory
) : RecoveryPasswordInteractor {

    private val logger = LoggerFactory.getLogger("RecoveryPasswordInteractor")

}