package com.verbatoria.business.late_send

import com.remnev.verbatoria.R
import com.verbatoria.domain.late_send.manager.LateSendManager
import com.verbatoria.domain.late_send.model.LateSend
import com.verbatoria.domain.late_send.model.LateSendState
import com.verbatoria.domain.submit.SubmitManager
import com.verbatoria.domain.submit.SubmitProgress
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.BehaviorSubject


/**
 * @author n.remnev
 */

interface LateSendInteractor {

    fun findAllLateSend(): Single<List<LateSend>>

    fun sendLateSend(lateSend: LateSend): Observable<SubmitProgress>

    fun sendLateSend(sessionId: String): Observable<SubmitProgress>

    fun deleteLateSend(lateSend: LateSend): Completable

}

class LateSendInteractorImpl(
    private val submitManager: SubmitManager,
    private val lateSendManager: LateSendManager,
    private val schedulersFactory: RxSchedulersFactory
) : LateSendInteractor {

    override fun findAllLateSend(): Single<List<LateSend>> =
        Single.fromCallable {
            lateSendManager.findAllLateSend()
        }
            .subscribeOn(schedulersFactory.database)
            .observeOn(schedulersFactory.main)

    override fun sendLateSend(lateSend: LateSend): Observable<SubmitProgress> =
        BehaviorSubject.create<SubmitProgress> { emitter ->
            when {
                lateSend.state == LateSendState.HAS_QUESTIONNAIRE -> {
                    emitter.onNext(SubmitProgress(R.string.submit_progress_collect_and_send))
                    submitManager.sendData(lateSend.sessionId)
                    lateSendManager.updateLateSendState(lateSend.sessionId, LateSendState.DATA_SENT)

                    emitter.onNext(SubmitProgress(R.string.submit_progress_finish_session))
                    submitManager.finishSession(lateSend.sessionId)
                    lateSendManager.updateLateSendState(lateSend.sessionId, LateSendState.SESSION_FINISHED)

                    emitter.onNext(SubmitProgress(R.string.submit_progress_clean_data))
                    submitManager.cleanData(lateSend.sessionId)
                    lateSendManager.updateLateSendState(lateSend.sessionId, LateSendState.DATA_CLEANED)
                }
                lateSend.state == LateSendState.DATA_SENT -> {
                    emitter.onNext(SubmitProgress(R.string.submit_progress_finish_session))
                    submitManager.finishSession(lateSend.sessionId)
                    lateSendManager.updateLateSendState(lateSend.sessionId, LateSendState.SESSION_FINISHED)

                    emitter.onNext(SubmitProgress(R.string.submit_progress_clean_data))
                    submitManager.cleanData(lateSend.sessionId)
                    lateSendManager.updateLateSendState(lateSend.sessionId, LateSendState.DATA_CLEANED)
                }
                lateSend.state == LateSendState.SESSION_FINISHED -> {
                    emitter.onNext(SubmitProgress(R.string.submit_progress_clean_data))
                    submitManager.cleanData(lateSend.sessionId)
                    lateSendManager.updateLateSendState(lateSend.sessionId, LateSendState.DATA_CLEANED)
                }
            }
            emitter.onComplete()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun sendLateSend(sessionId: String): Observable<SubmitProgress> =
        BehaviorSubject.create<SubmitProgress> { emitter ->
            emitter.onNext(SubmitProgress(R.string.submit_progress_collect_and_send))

            submitManager.sendDataFromReadyFile(sessionId)
            lateSendManager.updateLateSendState(sessionId, LateSendState.DATA_SENT)

            emitter.onNext(SubmitProgress(R.string.submit_progress_finish_session))
            submitManager.finishSession(sessionId)
            lateSendManager.updateLateSendState(sessionId, LateSendState.SESSION_FINISHED)

            emitter.onNext(SubmitProgress(R.string.submit_progress_clean_data))
            submitManager.cleanData(sessionId)
            lateSendManager.updateLateSendState(sessionId, LateSendState.DATA_CLEANED)
            emitter.onComplete()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun deleteLateSend(lateSend: LateSend): Completable =
        Completable.fromCallable {
            submitManager.cleanData(lateSend.sessionId)
        }
            .subscribeOn(schedulersFactory.database)
            .observeOn(schedulersFactory.main)

}