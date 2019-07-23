package com.verbatoria.business.user

/**
 * @author nikitaremnev
 */

enum class UserStatus(
    val userStatus: String
) {

    ACTIVE("active"), WARNING("warning"), BLOCKED("blocked");

    fun isBlocked(): Boolean =
        this == BLOCKED

    companion object {

        fun valueOfWithDefault(userStatus: String): UserStatus =
            when (userStatus) {
                ACTIVE.userStatus -> ACTIVE
                WARNING.userStatus -> WARNING
                BLOCKED.userStatus -> BLOCKED
                else -> BLOCKED
            }

    }

}