package com.verbatoria.ui.dashboard.info

import com.verbatoria.business.dashboard.info.InfoInteractor
import com.verbatoria.business.user.UserStatus
import com.verbatoria.ui.base.BasePresenter
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

class InfoPresenter(
    private val infoInteractor: InfoInteractor
) : BasePresenter<InfoView>() {

    private val logger = LoggerFactory.getLogger("InfoPresenter")

    init {
        getInfo()
        loadAndSaveAgeGroupsForArchimedes()
    }

    private fun getInfo() {
        infoInteractor.getInfo()
            .subscribe({ infoModel ->
                view?.apply {
                    hideInfoLoadingProgress()
                    setName(infoModel.name)
                    setPhone(infoModel.phone)
                    setEmailPhone(infoModel.email)
                    setIsArchimedesAllowed(infoModel.isArchimedesAllowed)
                    when (infoModel.status) {
                        UserStatus.ACTIVE -> setActiveStatus()
                        UserStatus.WARNING -> setWarningStatus()
                        UserStatus.BLOCKED -> setBlockedStatus()
                    }
                }
                getLocationAndPartnerInfo(infoModel.locationId)
            }, { error ->
                logger.error("get info error occurred", error)
            }
            ).let(::addDisposable)
    }

    private fun getLocationAndPartnerInfo(locationId: String) {
        infoInteractor.getLocationAndPartnerInfo(locationId)
            .subscribe({ (locationInfo, partnerInfo) ->
                view?.apply {
                    hideLocationInfoLoadingProgress()
                    setLocationId(locationInfo.id)
                    setLocationName(locationInfo.name)
                    setLocationAddress(locationInfo.address)
                    setLocationPoint(locationInfo.point)

                    hidePartnerInfoLoadingProgress()
                    setPartnerName(partnerInfo.name)
                }
            }, { error ->
                logger.error("get location and partner info error occurred", error)
            }
            ).let(::addDisposable)
    }

    private fun loadAndSaveAgeGroupsForArchimedes() {
        infoInteractor.loadAndSaveAgeGroupsForArchimedes()
            .subscribe({
                //empty
            }, { error ->
                logger.error("load and save age groups for archimedes", error)
            }
            ).let(::addDisposable)
    }

}