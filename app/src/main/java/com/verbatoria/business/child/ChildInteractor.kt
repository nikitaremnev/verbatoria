package com.verbatoria.business.child

import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory

/**
 * @author n.remnev
 */

interface ChildInteractor {



}

class ChildInteractorImpl(
    private val authorizationManager: AuthorizationManager,
    private val schedulersFactory: RxSchedulersFactory
) : ChildInteractor {

}