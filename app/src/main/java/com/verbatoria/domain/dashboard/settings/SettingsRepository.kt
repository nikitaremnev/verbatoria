package com.verbatoria.domain.dashboard.settings

import android.content.SharedPreferences

/**
 * @author n.remnev
 */

private const val LOCALES_KEY = "locales"

interface SettingsRepository {

    fun putLocales(locales: List<String>)

    fun getLocales(): List<String>

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
        sharedPreferences.getStringSet(LOCALES_KEY, setOf<String>()).toList()

    override fun deleteAll() {
        sharedPreferences
            .edit()
            .clear()
            .apply()
    }

}