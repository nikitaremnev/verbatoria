package com.verbatoria.domain.child.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author n.remnev
 */

@Parcelize
data class Child(
    var id: String? = null,
    var name: String = NAME_NOT_SELECTED,
    var gender: Int = GENDER_NOT_SELECTED,
    var age: Int = AGE_NOT_SELECTED
): Parcelable {

    companion object {

        const val AGE_NOT_SELECTED = 0

        const val GENDER_NOT_SELECTED = -1

        const val NAME_NOT_SELECTED = ""

    }

    fun hasId(): Boolean =
        id != null

    fun hasName(): Boolean =
        name != NAME_NOT_SELECTED

    fun hasAge(): Boolean =
        age != AGE_NOT_SELECTED

    fun hasGender(): Boolean =
        gender != GENDER_NOT_SELECTED

}