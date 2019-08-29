package com.verbatoria.business.dashboard.info

import com.verbatoria.business.dashboard.info.models.InfoModel
import com.verbatoria.business.dashboard.info.models.LocationInfoModel
import com.verbatoria.business.dashboard.info.models.PartnerInfoModel
import com.verbatoria.domain.dashboard.info.manager.InfoManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import io.reactivex.Completable
import io.reactivex.Single

/**
 * @author n.remnev
 */

interface InfoInteractor {

    fun getInfo(): Single<InfoModel>

    fun getLocationAndPartnerInfo(locationId: String): Single<Pair<LocationInfoModel, PartnerInfoModel>>

    fun loadAndSaveAgeGroupsForArchimedes(): Completable

}

class InfoInteractorImpl(
    private val infoManager: InfoManager,
    private val schedulersFactory: RxSchedulersFactory
) : InfoInteractor {

    override fun getInfo(): Single<InfoModel> =
        Single.fromCallable {
            infoManager.getInfo()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun getLocationAndPartnerInfo(locationId: String): Single<Pair<LocationInfoModel, PartnerInfoModel>> =
        Single.fromCallable {
            infoManager.getLocationAndPartnerInfo()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

    override fun loadAndSaveAgeGroupsForArchimedes(): Completable =
        Completable.fromCallable {
            infoManager.loadAndSaveAgeGroupsForArchimedes()
        }
            .subscribeOn(schedulersFactory.io)
            .observeOn(schedulersFactory.main)

}