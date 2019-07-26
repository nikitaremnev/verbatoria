package com.verbatoria.business.event

import com.remnev.verbatoria.R
import com.verbatoria.business.event.models.ClientModel
import com.verbatoria.business.event.models.item.*
import com.verbatoria.domain.client.ClientManager
import com.verbatoria.domain.dashboard.info.InfoRepository
import com.verbatoria.infrastructure.extensions.formatToServerTime
import com.verbatoria.infrastructure.retrofit.endpoints.event.EventEndpoint
import com.verbatoria.infrastructure.retrofit.endpoints.event.model.params.CreateNewOrEditEventParamsDto
import com.verbatoria.infrastructure.retrofit.endpoints.event.model.params.EventParamsDto
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.event.EventDetailMode
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

/**
 * @author n.remnev
 */

private const val MINIMUM_HOBBY_AGE = 18

interface EventDetailInteractor {

    fun getCreateNewModeEventDetailItems(): Single<List<EventDetailItem>>

    fun getViewModeEventDetailItems(): Single<List<EventDetailItem>>

    fun getClient(cliendId: String): Single<ClientModel>

    fun createNewEvent(childId: String, startAt: Date, endAt: Date): Completable

}

class EventDetailInteractorImpl(
    private val eventEndpoint: EventEndpoint,
    private val clientManager: ClientManager,
    private val infoRepository: InfoRepository,
    private val schedulersFactory: RxSchedulersFactory
) : EventDetailInteractor {

    override fun getCreateNewModeEventDetailItems(): Single<List<EventDetailItem>> =
        Single.fromCallable {
            listOf(
                EventDetailHeaderItem(
                    mode = EventDetailMode.CREATE_NEW,
                    headerStringResource = R.string.client
                ),
                EventDetailClientItem(
                    EventDetailMode.CREATE_NEW,
                    name = "Test",
                    phone = "test 2"
                ),
                EventDetailHeaderItem(
                    mode = EventDetailMode.CREATE_NEW,
                    headerStringResource = R.string.child
                ),
                EventDetailChildItem(
                    EventDetailMode.CREATE_NEW,
                    name = "Test",
                    age = 14
                ),
                EventDetailHeaderItem(
                    mode = EventDetailMode.CREATE_NEW,
                    headerStringResource = R.string.time
                ),
                EventDetailDateItem(
                    mode = EventDetailMode.CREATE_NEW,
                    startDate = Date(),
                    endDate = Date()
                ),
                EventDetailSubmitItem(
                    EventDetailMode.CREATE_NEW
                )
            )
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getViewModeEventDetailItems(): Single<List<EventDetailItem>> =
        Single.fromCallable {
            listOf<EventDetailItem>()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getClient(cliendId: String): Single<ClientModel> =
        Single.fromCallable {
            clientManager.getClient(cliendId)
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun createNewEvent(childId: String, startAt: Date, endAt: Date): Completable =
        Completable.fromCallable {
            eventEndpoint.createNewEvent(
                CreateNewOrEditEventParamsDto(
                    event = EventParamsDto(
                        childId = childId,
                        locationId = infoRepository.getLocationId(),
                        startAt = startAt.formatToServerTime(),
                        endAt = endAt.formatToServerTime(),
                        isInstantReport = true,
                        isArchimedesIncluded = true,
                        isHobbyIncluded = true
                    )
                )
            )
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}