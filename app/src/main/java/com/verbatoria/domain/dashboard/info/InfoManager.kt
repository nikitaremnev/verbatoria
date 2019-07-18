package com.verbatoria.domain.dashboard.info

import com.verbatoria.business.dashboard.info.models.InfoModel
import com.verbatoria.business.dashboard.info.models.LocationInfoModel
import com.verbatoria.business.dashboard.info.models.PartnerInfoModel
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.InfoEndpoint
import java.lang.Exception

/**
 * @author n.remnev
 */

private const val NOT_LOADED_TIME = 0L
private const val ONE_DAY_IN_MILLIS = 1000 * 60 * 60 * 24

interface InfoManager {

    fun getInfo(): InfoModel

    fun getLocationAndPartnerInfo(): Pair<LocationInfoModel, PartnerInfoModel>

}

class InfoManagerImpl(
    private val infoRepository: InfoRepository,
    private val settingsRepository: SettingsRepository,
    private val infoEndpoint: InfoEndpoint
) : InfoManager {

    override fun getInfo(): InfoModel {
        val lastUpdateInfoTime = infoRepository.getLastInfoUpdateTime()
        return when {
            lastUpdateInfoTime == NOT_LOADED_TIME -> {
                val response = infoEndpoint.getInfo()
                val responseConverted = InfoModel(
                    name = response.middleName + " " + response.firstName + " " + response.lastName,
                    phone = response.phone,
                    email = response.email,
                    isArchimedesAllowed = response.isArchimedesAllowed,
                    locationId = response.locationId
                )
                infoRepository.saveInfo(responseConverted)
                infoRepository.putLastInfoUpdateTime(System.currentTimeMillis())
                responseConverted
            }
            System.currentTimeMillis() - lastUpdateInfoTime < ONE_DAY_IN_MILLIS -> infoRepository.getInfo()
            else -> {
                try {
                    val response = infoEndpoint.getInfo()
                    val responseConverted = InfoModel(
                        name = response.middleName + " " + response.firstName + " " + response.lastName,
                        phone = response.phone,
                        email = response.email,
                        isArchimedesAllowed = response.isArchimedesAllowed,
                        locationId = response.locationId
                    )
                    infoRepository.saveInfo(responseConverted)
                    infoRepository.putLastInfoUpdateTime(System.currentTimeMillis())
                    responseConverted
                } catch (error: Exception) {
                    error.printStackTrace()
                    infoRepository.getInfo()
                }
            }
        }
    }

    override fun getLocationAndPartnerInfo(): Pair<LocationInfoModel, PartnerInfoModel> {
        val locationId = infoRepository.getLocationId()
        val lastUpdateLocationInfoTime = infoRepository.getLastLocationInfoUpdateTime()
        return when {
            lastUpdateLocationInfoTime == NOT_LOADED_TIME -> {
                val response = infoEndpoint.getLocationInfo(locationId)
                val responseLocationInfoConverted = LocationInfoModel(
                    id = response.id,
                    name = response.name,
                    address = response.address,
                    point = response.city.country.name + " " + response.city.name
                )
                val responsePartnerInfoConverted = PartnerInfoModel(
                    name = response.partner.name
                )

                infoRepository.saveLocationInfo(responseLocationInfoConverted)
                infoRepository.savePartnerInfo(responsePartnerInfoConverted)
                infoRepository.putLastLocationInfoUpdateTime(System.currentTimeMillis())

                settingsRepository.putLocales(response.availableLocales)

                Pair(responseLocationInfoConverted, responsePartnerInfoConverted)
            }
            System.currentTimeMillis() - lastUpdateLocationInfoTime < ONE_DAY_IN_MILLIS ->
                Pair(
                    infoRepository.getLocationInfo(),
                    infoRepository.getPartnerInfo()
                )
            else -> {
                try {
                    val response = infoEndpoint.getLocationInfo(locationId)
                    val responseLocationInfoConverted = LocationInfoModel(
                        id = response.id,
                        name = response.name,
                        address = response.address,
                        point = response.city.country.name + " " + response.city.name
                    )
                    val responsePartnerInfoConverted = PartnerInfoModel(
                        name = response.partner.name
                    )

                    infoRepository.saveLocationInfo(responseLocationInfoConverted)
                    infoRepository.savePartnerInfo(responsePartnerInfoConverted)
                    infoRepository.putLastLocationInfoUpdateTime(System.currentTimeMillis())

                    settingsRepository.putLocales(response.availableLocales)

                    Pair(responseLocationInfoConverted, responsePartnerInfoConverted)
                } catch (error: Exception) {
                    error.printStackTrace()
                    Pair(
                        infoRepository.getLocationInfo(),
                        infoRepository.getPartnerInfo()
                    )
                }
            }
        }
    }

}