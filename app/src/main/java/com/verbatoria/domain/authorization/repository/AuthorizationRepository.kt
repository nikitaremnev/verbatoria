package com.verbatoria.domain.authorization.repository

import android.content.SharedPreferences

/**
* @author n.remnev
*/

private const val LAST_LOGIN_KEY = "last_login"
private const val CURRENT_COUNTRY_KEY = "current_country"

interface AuthorizationRepository {

    fun putLastLogin(phone: String)

    fun getLastLogin(): String

    fun putCurrentCountry(country: String)

    fun getCurrentCountry(): String

}

class AuthorizationRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : AuthorizationRepository {

    override fun putLastLogin(phone: String) {
        sharedPreferences.edit().apply {
            putString(LAST_LOGIN_KEY, phone)
            apply()
        }
    }

    override fun getLastLogin(): String =
        sharedPreferences.getString(LAST_LOGIN_KEY, "")

    override fun putCurrentCountry(country: String) {
        sharedPreferences.edit().apply {
            putString(CURRENT_COUNTRY_KEY, country)
            apply()
        }
    }

    override fun getCurrentCountry(): String =
        sharedPreferences.getString(CURRENT_COUNTRY_KEY, "")

}