package com.verbatoria.domain.dashboard.settings

import android.content.SharedPreferences

/**
 * @author n.remnev
 */

private const val LOCALES_KEY = "locales"
private const val CURRENT_LOCALE_KEY = "current_locale"

interface SettingsRepository {

    fun putLocales(locales: List<String>)

    fun getLocales(): List<String>

    fun putCurrentLocale(locale: String)

    fun getCurrentLocale(): String

    fun deleteAll()

}

class SettingsRepositoryImpl(
    private val sharedPreferences: SharedPreferences
) : SettingsRepository {

    override fun putLocales(locales: List<String>) {
        sharedPreferences.edit().apply {
            putStringSet(LOCALES_KEY, locales.toSet())
            apply()
        }
    }

    override fun getLocales(): List<String> =
        (sharedPreferences.getStringSet(LOCALES_KEY, emptySet()) ?: emptySet()).toList()

    override fun putCurrentLocale(locale: String) {
        sharedPreferences.edit().apply {
            putString(CURRENT_LOCALE_KEY, locale)
            apply()
        }
    }

    override fun getCurrentLocale(): String =
        sharedPreferences.getString(CURRENT_LOCALE_KEY, "") ?: ""

    override fun deleteAll() {
        sharedPreferences
            .edit()
            .clear()
            .apply()
    }

}