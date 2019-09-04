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

    fun getGroupedActivities(eventId: String): Single<GroupedActivities>

    fun saveActivity(
        eventId: String,
        activityCode: ActivityCode,
        startTime: Long,
        endTime: Long
    ): Completable

    fun saveBCIDataBlock(eventId: String, bciData: List<BCIData>): Completable

}

class WritingInteractorImpl(
    private val activitiesManager: ActivitiesManager,
    private val bciDataManager: BCIDataManager,
    private val schedulersFactory: RxSchedulersFactory
) : WritingInteractor {

    override fun getGroupedActivities(eventId: String): Single<GroupedActivities> =
        Single.fromCallable {
            activitiesManager.getByEventId(eventId)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveActivity(
        eventId: String,
        activityCode: ActivityCode,
        startTime: Long,
        endTime: Long
    ): Completable =
        Completable.fromCallable {
            activitiesManager.saveActivity(eventId, activityCode, startTime, endTime)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun saveBCIDataBlock(eventId: String, bciData: List<BCIData>): Completable =
        Completable.fromCallable {
            bciData.forEach { bciDataItem ->
                bciDataItem.eventId = eventId
            }
            bciDataManager.save(bciData)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}