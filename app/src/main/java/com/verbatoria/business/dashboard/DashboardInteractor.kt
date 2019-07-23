package com.verbatoria.business.dashboard

import com.verbatoria.domain.dashboard.info.InfoManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface DashboardInteractor {

    fun isBlocked(): Single<Boolean>

    fun updateInfo(): Single<Boolean>

}

class DashboardInteractorImpl(
    private val infoManager: InfoManager,
    private val schedulersFactory: RxSchedulersFactory
) : DashboardInteractor {

    override fun isBlocked(): Single<Boolean> =
        Single.fromCallable {
            infoManager.getStatus().isBlocked()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun updateInfo(): Single<Boolean> =
        Single.fromCallable {
            infoManager.updateInfo().isBlocked()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}