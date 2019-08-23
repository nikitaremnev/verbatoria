package com.verbatoria.business.event.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author n.remnev
 */

@Parcelize
data class Client(
    var id: String? = null,
    var name: String = "",
    var phone: String = "",
    var email: String = ""
): Parcelable {

    fun hasId(): Boolean =
        id != null

    fun hasName(): Boolean =
        name.isNotEmpty()

    fun hasPhone(): Boolean =
        phone.isNotEmpty()

    fun hasEmail(): Boolean =
        email.isNotEmpty()

}