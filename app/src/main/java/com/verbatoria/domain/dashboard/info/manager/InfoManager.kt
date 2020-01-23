package com.verbatoria.domain.dashboard.info.manager

import com.verbatoria.business.dashboard.info.models.InfoModel
import com.verbatoria.business.dashboard.info.models.LocationInfoModel
import com.verbatoria.business.dashboard.info.models.PartnerInfoModel
import com.verbatoria.business.user.UserStatus
import com.verbatoria.domain.dashboard.info.repository.AgeGroupRepository
import com.verbatoria.domain.dashboard.info.repository.InfoRepository
import com.verbatoria.domain.dashboard.settings.SettingsRepository
import com.verbatoria.domain.dashboard.info.model.AgeGroupEntity
import com.verbatoria.infrastructure.retrofit.endpoints.dashboard.InfoEndpoint
import java.lang.Exception

/**
 * @author n.remnev
 */

private const val NOT_LOADED_TIME = 0L
private const val ONE_DAY_IN_MILLIS = 1000 * 60 * 60 * 24
private const val IS_SCHOOL = 1
private const val MINIMUM_HOBBY_AGE = 18

interface InfoManager {

    fun getInfo(): InfoModel

    fun getLocationAndPartnerInfo(): Pair<LocationInfoModel, PartnerInfoModel>

    fun getStatus(): UserStatus

    fun updateInfo(): UserStatus

    fun getLocationId(): String

    fun isAgeAvailableForArchimedes(age: Int): Boolean

    fun isAgeAvailableForHobby(age: Int): Boolean

    fun isArchimedesAllowedForVerbatolog(): Boolean

    fun isSchool(): Boolean

    fun dropLastLocationInfoUpdateTime()

}

class InfoManagerImpl(
    private val infoRepository: InfoRepository,
    private val settingsRepository: SettingsRepository,
    private val ageGroupRepository: AgeGroupRepository,
    private val infoEndpoint: InfoEndpoint
) : InfoManager {

    override fun getInfo(): InfoModel {
        val lastUpdateInfoTime = infoRepository.getLastInfoUpdateTime()
        return when {
            lastUpdateInfoTime == NOT_LOADED_TIME -> {
                val response = infoEndpoint.getInfo()
                val responseConverted = InfoModel(
                    name = response.lastName + " " + response.firstName + " " + response.middleName,
                    phone = response.phone,
                    email = response.email,
                    status = UserStatus.valueOfWithDefault(response.status),
                    isArchimedesAllowed = response.isArchimedesAllowed,
                    locationId = response.locationId
                )
                infoRepository.saveInfo(responseConverted)
                infoRepository.putLastInfoUpdateTime(System.currentTimeMillis())

                loadAndSaveAgeGroupsForArchimedes()

                responseConverted
            }
            System.currentTimeMillis() - lastUpdateInfoTime < ONE_DAY_IN_MILLIS -> {
                infoRepository.getInfo()
            }
            else -> {
                try {
                    val response = infoEndpoint.getInfo()
                    val responseConverted = InfoModel(
                        name = response.lastName + " " + response.firstName + " " + response.middleName,
                        phone = response.phone,
                        email = response.email,
                        status = UserStatus.valueOfWithDefault(response.status),
                        isArchimedesAllowed = response.isArchimedesAllowed,
                        locationId = response.locationId
                    )
                    infoRepository.saveInfo(responseConverted)
                    infoRepository.putLastInfoUpdateTime(System.currentTimeMillis())

                    loadAndSaveAgeGroupsForArchimedes()

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
                    point = response.city.country.name + " " + response.city.name,
                    updatedLocale = response.locale
                )
                val responsePartnerInfoConverted = PartnerInfoModel(
                    name = response.partner.name
                )

                infoRepository.putIsSchool(response.isSchool == IS_SCHOOL)
                infoRepository.saveLocationInfo(responseLocationInfoConverted)
                infoRepository.savePartnerInfo(responsePartnerInfoConverted)
                infoRepository.putLastLocationInfoUpdateTime(System.currentTimeMillis())
                infoRepository.putLocationCurrentLocale(response.locale)

                settingsRepository.putLocales(response.availableLocales)

                responseLocationInfoConverted.currentLocale = settingsRepository.getCurrentLocale()
                settingsRepository.putCurrentLocale(response.locale)

                Pair(responseLocationInfoConverted, responsePartnerInfoConverted)
            }
            System.currentTimeMillis() - lastUpdateLocationInfoTime < ONE_DAY_IN_MILLIS -> {
                Pair(
                    infoRepository.getLocationInfo().apply {
                        currentLocale = updatedLocale
                    },
                    infoRepository.getPartnerInfo()
                )
            }
            else -> {
                try {
                    val response = infoEndpoint.getLocationInfo(locationId)
                    val responseLocationInfoConverted = LocationInfoModel(
                        id = response.id,
                        name = response.name,
                        address = response.address,
                        point = response.city.country.name + ", " + response.city.name,
                        updatedLocale = response.locale
                    )
                    val responsePartnerInfoConverted = PartnerInfoModel(
                        name = response.partner.name
                    )

                    infoRepository.putIsSchool(response.isSchool == IS_SCHOOL)
                    infoRepository.saveLocationInfo(responseLocationInfoConverted)
                    infoRepository.savePartnerInfo(responsePartnerInfoConverted)
                    infoRepository.putLastLocationInfoUpdateTime(System.currentTimeMillis())
                    infoRepository.putLocationCurrentLocale(response.locale)

                    settingsRepository.putLocales(response.availableLocales)

                    responseLocationInfoConverted.currentLocale = settingsRepository.getCurrentLocale()
                    settingsRepository.putCurrentLocale(response.locale)

                    Pair(responseLocationInfoConverted, responsePartnerInfoConverted)
                } catch (error: Exception) {
                    Pair(
                        infoRepository.getLocationInfo().apply {
                            currentLocale = settingsRepository.getCurrentLocale()
                        },
                        infoRepository.getPartnerInfo()
                    )
                }
            }
        }
    }

    override fun getStatus(): UserStatus =
        infoRepository.getStatus()

    override fun updateInfo(): UserStatus {
        val response = infoEndpoint.getInfo()
        val responseConverted = InfoModel(
            name = response.lastName + " " + response.firstName + " " + response.middleName,
            phone = response.phone,
            email = response.email,
            status = UserStatus.valueOfWithDefault(response.status),
            isArchimedesAllowed = response.isArchimedesAllowed,
            locationId = response.locationId
        )
        infoRepository.saveInfo(responseConverted)
        infoRepository.putLastInfoUpdateTime(System.currentTimeMillis())
        return responseConverted.status
    }

    override fun getLocationId(): String =
            infoRepository.getLocationId()

    override fun isAgeAvailableForArchimedes(age: Int): Boolean =
        ageGroupRepository.isAgeAvailableForArchimedes(age)

    override fun isAgeAvailableForHobby(age: Int): Boolean =
        age >= MINIMUM_HOBBY_AGE

    override fun isArchimedesAllowedForVerbatolog(): Boolean =
        infoRepository.getIsArchimedesAllowed()

    override fun isSchool(): Boolean =
        infoRepository.getIsSchool()

    override fun dropLastLocationInfoUpdateTime() {
        infoRepository.putLastInfoUpdateTime(0L)
        infoRepository.putLastLocationInfoUpdateTime(0L)
    }

    private fun loadAndSaveAgeGroupsForArchimedes() {
        val responseConverted = infoEndpoint.getAgeGroupsForArchimedes().map { dto ->
            AgeGroupEntity(
                id = System.currentTimeMillis().toString(),
                minAge = dto.minAge,
                maxAge = dto.maxAge,
                isArhimedesAllowed = dto.isArchimedesAllowed
            )
        }
        ageGroupRepository.deleteAll()
        ageGroupRepository.saveAll(responseConverted)
    }

}