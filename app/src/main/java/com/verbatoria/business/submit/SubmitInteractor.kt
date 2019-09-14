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

    fun submitData(sessionId: String): Observable<SubmitProgress>

}

class SubmitInteractorImpl(
    private val submitManager: SubmitManager,
    private val schedulersFactory: RxSchedulersFactory
) : SubmitInteractor {

    override fun submitData(sessionId: String): Observable<SubmitProgress> =
        BehaviorSubject.create<SubmitProgress> { emitter ->
            emitter.onNext(SubmitProgress(R.string.submit_progress_collect_and_send))
            submitManager.sendData(sessionId)

            emitter.onNext(SubmitProgress(R.string.submit_progress_finish_session))
            submitManager.finishSession(sessionId)

            emitter.onNext(SubmitProgress(R.string.submit_progress_clean_data))
            submitManager.cleanData(sessionId)

            emitter.onComplete()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}