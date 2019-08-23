package com.verbatoria.business.child

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * @author n.remnev
 */

@Parcelize
data class Child(
    var id: String? = null,
    var name: String = "",
    var age: Int = 0,
    var birthday: Date? = null
): Parcelable {

    fun hasId(): Boolean =
        id != null

    fun hasName(): Boolean =
        name.isNotEmpty()

    fun hasAge(): Boolean =
        age != 0

    fun hasBirthday(): Boolean =
        birthday != null

}