package com.verbatoria.domain.client.model

import android.os.Parcelable
import android.util.Patterns
import kotlinx.android.parcel.Parcelize

/**
 * @author n.remnev
 */

private const val MINIMUM_PHONE_SIZE = 6

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
        phone.isNotEmpty() && phone.length > MINIMUM_PHONE_SIZE

    fun hasEmail(): Boolean =
        email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()

}