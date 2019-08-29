package com.verbatoria.business.child

import com.verbatoria.domain.child.model.Child
import com.verbatoria.domain.child.manager.ChildManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface ChildInteractor {

    fun createNewChild(clientId: String, child: Child): Single<String>

    fun editChild(clientId: String, child: Child): Completable

}

class ChildInteractorImpl(
    private val childManager: ChildManager,
    private val schedulersFactory: RxSchedulersFactory
) : ChildInteractor {

    override fun createNewChild(clientId: String, child: Child): Single<String> =
        Single.fromCallable {
            childManager.createNewChild(clientId, child)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun editChild(clientId: String, child: Child): Completable =
        Completable.fromCallable {
            childManager.editChild(clientId, child)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}