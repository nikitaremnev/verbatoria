package com.verbatoria.business.submit

import com.remnev.verbatoria.R
import com.verbatoria.domain.submit.SubmitManager
import com.verbatoria.domain.submit.SubmitProgress
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

/**
 * @author n.remnev
 */

interface SubmitInteractor {

    fun submitData(eventId: String): Observable<SubmitProgress>

}

class SubmitInteractorImpl(
    private val submitManager: SubmitManager,
    private val schedulersFactory: RxSchedulersFactory
) : SubmitInteractor {

    override fun submitData(eventId: String): Observable<SubmitProgress> =
        BehaviorSubject.create<SubmitProgress> { emitter ->
            emitter.onNext(SubmitProgress(R.string.session_connect))
            submitManager.sendData(eventId)

            emitter.onNext(SubmitProgress(R.string.session_connect))
            submitManager.finishSession(eventId)

            emitter.onNext(SubmitProgress(R.string.session_connect))
            submitManager.cleanData(eventId)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}