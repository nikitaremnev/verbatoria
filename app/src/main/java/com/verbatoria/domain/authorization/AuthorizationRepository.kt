package com.verbatoria.domain.authorization

import android.content.SharedPreferences

/**
* @author p.o.drozdov
*/

private const val FIELD_HASPIN = "has_pin"

interface AuthorizationRepository {

    fun putHasPin(hasPin: Boolean)

    fun hasPin(): Boolean

}

class AuthorizationRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : AuthorizationRepository {

    override fun putHasPin(hasPin: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(FIELD_HASPIN, hasPin)
        editor.apply()
    }

    override fun hasPin(): Boolean =
        sharedPreferences.getBoolean(FIELD_HASPIN, false)

}