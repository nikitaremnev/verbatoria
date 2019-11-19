package com.verbatoria.utils

import android.content.Context
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

object CountryHelper {

    const val RUSSIAN_COUNTRY_KEY = "rus"
    const val SWISS_COUNTRY_KEY = "swi"
    const val HONG_KONG_COUNTRY = "hon"
    const val UZBEKISTAN_COUNTRY_KEY = "uzb"
    const val ISRAEL_COUNTRY_KEY = "isr"
    const val BELARUS_COUNTRY_KEY = "bel"
    const val THAILAND_COUNTRY_KEY = "tha"
    const val AZERBAIJAN_COUNTRY_KEY = "aze"
    const val UAE_COUNTRY_KEY = "uae"
    const val UKRAINE_COUNTRY_KEY = "ukr"
    const val INDONESIA_COUNTRY_KEY = "indonesia"
    const val MALAYSIA_COUNTRY_KEY = "mal"
    const val INDIA_COUNTRY_KEY = "ind"
    const val BULGARIA_COUNTRY_KEY = "bul"

    fun getFlagResourceByCountryKey(countryKey: String): Int =
        when (countryKey) {
            RUSSIAN_COUNTRY_KEY -> R.drawable.ic_flag_ru
            SWISS_COUNTRY_KEY -> R.drawable.ic_flag_ch
            HONG_KONG_COUNTRY -> R.drawable.ic_flag_hk
            UZBEKISTAN_COUNTRY_KEY -> R.drawable.ic_flag_uz
            ISRAEL_COUNTRY_KEY -> R.drawable.ic_flag_isr
            BELARUS_COUNTRY_KEY -> R.drawable.ic_flag_by
            THAILAND_COUNTRY_KEY -> R.drawable.ic_flag_th
            AZERBAIJAN_COUNTRY_KEY -> R.drawable.ic_flag_az
            UAE_COUNTRY_KEY -> R.drawable.ic_flag_ae
            UKRAINE_COUNTRY_KEY -> R.drawable.ic_flag_ukr
            INDONESIA_COUNTRY_KEY -> R.drawable.ic_flag_id
            MALAYSIA_COUNTRY_KEY -> R.drawable.ic_flag_my
            INDIA_COUNTRY_KEY -> R.drawable.ic_flag_ind
            BULGARIA_COUNTRY_KEY -> R.drawable.ic_flag_bg
            else -> R.drawable.ic_flag_ru
        }

    fun isCountryRequireSkipSMSConfirmation(countryKey: String): Boolean =
        countryKey == HONG_KONG_COUNTRY ||
                countryKey == MALAYSIA_COUNTRY_KEY ||
                countryKey == INDONESIA_COUNTRY_KEY ||
                countryKey == BULGARIA_COUNTRY_KEY

    fun getPhoneFormatterByCountryKey(context: Context, countryKey: String): String =
        when (countryKey) {
            RUSSIAN_COUNTRY_KEY -> context.getString(R.string.login_russia_phone_mask)
            SWISS_COUNTRY_KEY -> context.getString(R.string.login_swiss_phone_mask)
            HONG_KONG_COUNTRY -> context.getString(R.string.login_hong_kong_phone_mask)
            UZBEKISTAN_COUNTRY_KEY -> context.getString(R.string.login_uzbekistan_phone_mask)
            ISRAEL_COUNTRY_KEY -> context.getString(R.string.login_israel_phone_mask)
            BELARUS_COUNTRY_KEY -> context.getString(R.string.login_belarus_phone_mask)
            THAILAND_COUNTRY_KEY -> context.getString(R.string.login_thailand_phone_mask)
            AZERBAIJAN_COUNTRY_KEY -> context.getString(R.string.login_azerbaijan_phone_mask)
            UAE_COUNTRY_KEY -> context.getString(R.string.login_uae_phone_mask)
            UKRAINE_COUNTRY_KEY -> context.getString(R.string.login_ukraine_phone_mask)
            INDONESIA_COUNTRY_KEY -> context.getString(R.string.login_indonesia_phone_mask)
            MALAYSIA_COUNTRY_KEY -> context.getString(R.string.login_malaysia_phone_mask)
            INDIA_COUNTRY_KEY -> context.getString(R.string.login_india_phone_mask)
            BULGARIA_COUNTRY_KEY -> context.getString(R.string.login_bulgaria_phone_mask)
            else -> context.getString(R.string.login_russia_phone_mask)
        }

    fun getCountryNameByCountryKey(context: Context, countryKey: String): String =
        when (countryKey) {
            RUSSIAN_COUNTRY_KEY -> context.getString(R.string.country_russia)
            SWISS_COUNTRY_KEY -> context.getString(R.string.country_swiss)
            HONG_KONG_COUNTRY -> context.getString(R.string.country_hong_kong)
            UZBEKISTAN_COUNTRY_KEY -> context.getString(R.string.country_uzbekistan)
            ISRAEL_COUNTRY_KEY -> context.getString(R.string.country_israel)
            BELARUS_COUNTRY_KEY -> context.getString(R.string.country_belarus)
            THAILAND_COUNTRY_KEY -> context.getString(R.string.country_thailand)
            AZERBAIJAN_COUNTRY_KEY -> context.getString(R.string.country_azerbaijan)
            UAE_COUNTRY_KEY -> context.getString(R.string.country_uae)
            UKRAINE_COUNTRY_KEY -> context.getString(R.string.country_ukraine)
            INDONESIA_COUNTRY_KEY -> context.getString(R.string.country_indonesia)
            MALAYSIA_COUNTRY_KEY -> context.getString(R.string.country_malaysia)
            INDIA_COUNTRY_KEY -> context.getString(R.string.country_india)
            BULGARIA_COUNTRY_KEY -> context.getString(R.string.country_bulgaria)
            else -> context.getString(R.string.country_russia)
        }

}