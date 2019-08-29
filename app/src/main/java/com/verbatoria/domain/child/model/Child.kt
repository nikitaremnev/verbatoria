package com.verbatoria.domain.child.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author n.remnev
 */

@Parcelize
data class Child(
    var id: String? = null,
    var name: String = "",
    var gender: Int = 0,
    var age: Int = 0
): Parcelable {

    fun hasId(): Boolean =
        id != null

    fun hasName(): Boolean =
        name.isNotEmpty()

    fun hasAge(): Boolean =
        age != 0

}