package com.verbatoria.business.writing

import com.verbatoria.domain.activities.manager.ActivitiesManager
import com.verbatoria.domain.activities.model.ActivityCode
import com.verbatoria.domain.activities.model.GroupedActivities
import com.verbatoria.domain.bci_data.manager.BCIDataManager
import com.verbatoria.domain.bci_data.model.BCIData
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface WritingInteractor {

    fun getGroupedActivities(sessionId: String): Single<GroupedActivities>

    fun saveActivity(
        sessionId: String,
        activityCode: ActivityCode,
        startTime: Long,
        endTime: Long
    ): Completable

    fun saveBCIDataBlock(sessionId: String, bciData: List<BCIData>): Completable

}

class WritingInteractorImpl(
    private val activitiesManager: ActivitiesManager,
    private val bciDataManager: BCIDataManager,
    private val schedulersFactory: RxSchedulersFactory
) : WritingInteractor {

    override fun getGroupedActivities(sessionId: String): Single<GroupedActivities> =
        Single.fromCallable {
            activitiesManager.getBySessionId(sessionId)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveActivity(
        sessionId: String,
        activityCode: ActivityCode,
        startTime: Long,
        endTime: Long
    ): Completable =
        Completable.fromCallable {
            activitiesManager.saveActivity(sessionId, activityCode, startTime, endTime)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveBCIDataBlock(sessionId: String, bciData: List<BCIData>): Completable =
        Completable.fromCallable {
            bciData.forEach { bciDataItem ->
                bciDataItem.sessionId = sessionId
            }
            bciDataManager.save(bciData)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}