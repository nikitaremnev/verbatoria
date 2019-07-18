package com.verbatoria.business.dashboard.calendar

import com.verbatoria.infrastructure.rx.RxSchedulersFactory

/**
 * @author n.remnev
 */

interface CalendarInteractor {


}

class CalendarInteractorImpl(
    private val schedulersFactory: RxSchedulersFactory
) : CalendarInteractor {

//    override fun logout(): Completable =
//        Completable.fromCallable {
//            sessionManager.closeSession("logout")
//        }
//            .subscribeOn(schedulersFactory.io)
//            .observeOn(schedulersFactory.main)

}