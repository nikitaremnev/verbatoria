package com.verbatoria.domain.dashboard.info.repository

import android.content.SharedPreferences
import com.verbatoria.business.dashboard.info.models.InfoModel
import com.verbatoria.business.dashboard.info.models.LocationInfoModel
import com.verbatoria.business.dashboard.info.models.PartnerInfoModel
import com.verbatoria.business.user.UserStatus

/**
 * @author n.remnev
 */

private const val STATUS_KEY = "status"
private const val NAME_KEY = "name"
private const val PHONE_KEY = "phone"
private const val EMAIL_KEY = "email"
private const val IS_SCHOOL_KEY = "is_school"

private const val IS_ARCHIMEDES_ALLOWED_KEY = "is_archimedes_allowed"
private const val LAST_INFO_UPDATE_TIME = "last_info_update_time"

private const val LOCATION_ID_KEY = "location_id"
private const val LOCATION_NAME_KEY = "location_name"
private const val LOCATION_ADDRESS_KEY = "location_address"
private const val LOCATION_POINT_KEY = "location_point"
private const val LOCATION_CURRENT_LOCALE_KEY = "location_current_locale"

private const val PARTNER_NAME_KEY = "partner_name"

private const val LAST_LOCATION_INFO_UPDATE_TIME = "last_location_info_update_time"

interface InfoRepository {

    fun putStatus(status: String)

    fun putName(name: String)

    fun putPhone(phone: String)

    fun putEmail(email: String)

    fun putIsArchimedesAllowed(isArchimedesAllowed: Boolean)

    fun putIsSchool(isSchool: Boolean)

    fun putLocationId(locationId: String)

    fun putLocationName(locationName: String)

    fun putLocationAddress(locationAddress: String)

    fun putLocationPoint(locationPoint: String)

    fun putLocationCurrentLocale(currentLocale: String)

    fun putPartnerName(partnerName: String)

    fun putLastInfoUpdateTime(time: Long)

    fun putLastLocationInfoUpdateTime(time: Long)

    fun getStatus(): UserStatus

    fun getName(): String

    fun getPhone(): String

    fun getEmail(): String

    fun getIsArchimedesAllowed(): Boolean

    fun getIsSchool(): Boolean

    fun getLocationId(): String

    fun getLocationName(): String

    fun getLocationAddress(): String

    fun getLocationPoint(): String

    fun getLocationCurrentLocale(): String

    fun getPartnerName(): String

    fun getLastInfoUpdateTime(): Long

    fun getLastLocationInfoUpdateTime(): Long

    fun saveInfo(info: InfoModel)

    fun saveLocationInfo(locationInfo: LocationInfoModel)

    fun savePartnerInfo(partnerInfo: PartnerInfoModel)

    fun getInfo(): InfoModel

    fun getLocationInfo(): LocationInfoModel

    fun getPartnerInfo(): PartnerInfoModel

    fun deleteAll()

}

class InfoRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : InfoRepository {

    override fun putStatus(status: String) {
        sharedPreferences.edit().apply {
            putString(STATUS_KEY, status)
            apply()
        }
    }

    override fun putName(name: String) {
        sharedPreferences.edit().apply {
            putString(NAME_KEY, name)
            apply()
        }
    }

    override fun putPhone(phone: String) {
        sharedPreferences.edit().apply {
            putString(PHONE_KEY, phone)
            apply()
        }
    }

    override fun putEmail(email: String) {
        sharedPreferences.edit().apply {
            putString(EMAIL_KEY, email)
            apply()
        }
    }

    override fun putIsArchimedesAllowed(isArchimedesAllowed: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(IS_ARCHIMEDES_ALLOWED_KEY, isArchimedesAllowed)
            apply()
        }
    }

    override fun putIsSchool(isSchool: Boolean) {
        sharedPreferences.edit().apply {
            putBoolean(IS_SCHOOL_KEY, isSchool)
            apply()
        }
    }

    override fun putLocationId(locationId: String) {
        sharedPreferences.edit().apply {
            putString(LOCATION_ID_KEY, locationId)
            apply()
        }
    }

    override fun putLocationName(locationName: String) {
        sharedPreferences.edit().apply {
            putString(LOCATION_NAME_KEY, locationName)
            apply()
        }
    }

    override fun putLocationAddress(locationAddress: String) {
        sharedPreferences.edit().apply {
            putString(LOCATION_ADDRESS_KEY, locationAddress)
            apply()
        }
    }

    override fun putLocationPoint(locationPoint: String) {
        sharedPreferences.edit().apply {
            putString(LOCATION_POINT_KEY, locationPoint)
            apply()
        }
    }

    override fun putLocationCurrentLocale(currentLocale: String) {
        sharedPreferences.edit().apply {
            putString(LOCATION_CURRENT_LOCALE_KEY, currentLocale)
            apply()
        }
    }

    override fun putPartnerName(partnerName: String) {
        sharedPreferences.edit().apply {
            putString(PARTNER_NAME_KEY, partnerName)
            apply()
        }
    }

    override fun putLastInfoUpdateTime(time: Long) {
        sharedPreferences.edit().apply {
            putLong(LAST_INFO_UPDATE_TIME, time)
            apply()
        }
    }

    override fun putLastLocationInfoUpdateTime(time: Long) {
        sharedPreferences.edit().apply {
            putLong(LAST_LOCATION_INFO_UPDATE_TIME, time)
            apply()
        }
    }

    override fun getStatus(): UserStatus =
        UserStatus.valueOfWithDefault(sharedPreferences.getString(STATUS_KEY, ""))

    override fun getName(): String =
        sharedPreferences.getString(NAME_KEY, "")

    override fun getPhone(): String =
        sharedPreferences.getString(PHONE_KEY, "")

    override fun getEmail(): String =
        sharedPreferences.getString(EMAIL_KEY, "")

    override fun getIsArchimedesAllowed(): Boolean =
        sharedPreferences.getBoolean(IS_ARCHIMEDES_ALLOWED_KEY, false)

    override fun getIsSchool(): Boolean =
        sharedPreferences.getBoolean(IS_SCHOOL_KEY, false)

    override fun getLocationId(): String =
        sharedPreferences.getString(LOCATION_ID_KEY, "")

    override fun getLocationName(): String =
        sharedPreferences.getString(LOCATION_NAME_KEY, "")

    override fun getLocationAddress(): String =
        sharedPreferences.getString(LOCATION_ADDRESS_KEY, "")

    override fun getLocationPoint(): String =
        sharedPreferences.getString(LOCATION_POINT_KEY, "")

    override fun getLocationCurrentLocale(): String =
        sharedPreferences.getString(LOCATION_CURRENT_LOCALE_KEY, "")

    override fun getPartnerName(): String =
        sharedPreferences.getString(PARTNER_NAME_KEY, "")

    override fun getLastInfoUpdateTime(): Long =
        sharedPreferences.getLong(LAST_INFO_UPDATE_TIME, 0L)

    override fun getLastLocationInfoUpdateTime(): Long =
        sharedPreferences.getLong(LAST_LOCATION_INFO_UPDATE_TIME, 0L)

    override fun saveInfo(info: InfoModel) {
        sharedPreferences.edit().apply {
            putString(STATUS_KEY, info.status.userStatus)
            putString(NAME_KEY, info.name)
            putString(PHONE_KEY, info.phone)
            putString(EMAIL_KEY, info.email)
            putBoolean(IS_ARCHIMEDES_ALLOWED_KEY, info.isArchimedesAllowed)
            apply()
        }
    }

    override fun saveLocationInfo(locationInfo: LocationInfoModel) {
        sharedPreferences.edit().apply {
            putString(LOCATION_POINT_KEY, locationInfo.point)
            putString(LOCATION_ADDRESS_KEY, locationInfo.address)
            putString(LOCATION_NAME_KEY, locationInfo.name)
            apply()
        }
    }

    override fun savePartnerInfo(partnerInfo: PartnerInfoModel) {
        putPartnerName(partnerInfo.name)
    }

    override fun getInfo(): InfoModel =
        InfoModel(
            name = getName(),
            phone = getPhone(),
            email = getEmail(),
            status = getStatus(),
            isArchimedesAllowed = getIsArchimedesAllowed(),
            locationId = getLocationId()
        )

    override fun getLocationInfo(): LocationInfoModel =
        LocationInfoModel(
            id = getLocationId(),
            name = getLocationName(),
            address = getLocationAddress(),
            isSchool = getIsSchool(),
            point = getLocationPoint(),
            updatedLocale = getLocationCurrentLocale()
        )

    override fun getPartnerInfo(): PartnerInfoModel =
        PartnerInfoModel(
            name = getPartnerName()
        )

    override fun deleteAll() {
        val saveLocationId = getLocationId()
        sharedPreferences
            .edit()
            .clear()
            .putString(LOCATION_ID_KEY, saveLocationId)
            .apply()
    }

}