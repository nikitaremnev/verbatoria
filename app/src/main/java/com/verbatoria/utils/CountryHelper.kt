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
    const val MACAU_COUNTRY_KEY = "mo"
    const val SINGAPORE_COUNTRY_KEY = "sg"
    const val EGYPT_COUNTRY_KEY = "eg"
    const val ETHIOPIA_COUNTRY_KEY = "et"
    const val SAUDI_ARABIA_COUNTRY_KEY = "sa"
    const val SERBIA_COUNTRY_KEY = "rs"
    const val MONGOLIA_COUNTRY_KEY = "mn"
    const val KAZAKHSTAN_COUNTRY_KEY = "kz"
    const val ESTONIA_COUNTRY_KEY = "ee"
    const val JORDAN_COUNTRY_KEY = "jo"
    const val TURKEY_COUNTRY_KEY = "tur"

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
            MACAU_COUNTRY_KEY -> R.drawable.ic_flag_mo
            SINGAPORE_COUNTRY_KEY -> R.drawable.ic_flag_sg
            EGYPT_COUNTRY_KEY -> R.drawable.ic_flag_eg
            ETHIOPIA_COUNTRY_KEY -> R.drawable.ic_flag_et
            SAUDI_ARABIA_COUNTRY_KEY -> R.drawable.ic_flag_sa
            SERBIA_COUNTRY_KEY -> R.drawable.ic_flag_rs
            MONGOLIA_COUNTRY_KEY -> R.drawable.ic_flag_mn
            KAZAKHSTAN_COUNTRY_KEY -> R.drawable.ic_flag_kz
            ESTONIA_COUNTRY_KEY -> R.drawable.ic_flag_ee
            JORDAN_COUNTRY_KEY -> R.drawable.ic_flag_jo
            TURKEY_COUNTRY_KEY -> R.drawable.ic_flag_tur
            else -> R.drawable.ic_flag_ru
        }

    fun isCountryRequireSkipSMSConfirmation(countryKey: String): Boolean =
        countryKey != RUSSIAN_COUNTRY_KEY &&
                countryKey != UZBEKISTAN_COUNTRY_KEY &&
                countryKey != UKRAINE_COUNTRY_KEY &&
                countryKey != AZERBAIJAN_COUNTRY_KEY  &&
                countryKey != BELARUS_COUNTRY_KEY &&
                countryKey != ""

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
            MACAU_COUNTRY_KEY -> context.getString(R.string.login_macau_phone_mask)
            SINGAPORE_COUNTRY_KEY -> context.getString(R.string.login_singapore_phone_mask)
            EGYPT_COUNTRY_KEY -> context.getString(R.string.login_egypt_phone_mask)
            ETHIOPIA_COUNTRY_KEY -> context.getString(R.string.login_ethiopia_phone_mask)
            SAUDI_ARABIA_COUNTRY_KEY -> context.getString(R.string.login_saudi_arabia_phone_mask)
            SERBIA_COUNTRY_KEY -> context.getString(R.string.login_serbia_phone_mask)
            MONGOLIA_COUNTRY_KEY -> context.getString(R.string.login_mongolia_phone_mask)
            KAZAKHSTAN_COUNTRY_KEY -> context.getString(R.string.login_kazakhstan_phone_mask)
            JORDAN_COUNTRY_KEY -> context.getString(R.string.login_jordan_phone_mask)
            ESTONIA_COUNTRY_KEY -> context.getString(R.string.login_estonia_phone_mask)
            TURKEY_COUNTRY_KEY -> context.getString(R.string.login_turkey_phone_mask)
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
            MACAU_COUNTRY_KEY -> context.getString(R.string.country_macau)
            SINGAPORE_COUNTRY_KEY -> context.getString(R.string.country_singapore)
            EGYPT_COUNTRY_KEY -> context.getString(R.string.country_egypt)
            ETHIOPIA_COUNTRY_KEY -> context.getString(R.string.country_ethiopia)
            SAUDI_ARABIA_COUNTRY_KEY -> context.getString(R.string.country_saudi_arabia)
            SERBIA_COUNTRY_KEY -> context.getString(R.string.country_serbia)
            MONGOLIA_COUNTRY_KEY -> context.getString(R.string.country_mongolia)
            KAZAKHSTAN_COUNTRY_KEY -> context.getString(R.string.country_kazakhstan)
            JORDAN_COUNTRY_KEY -> context.getString(R.string.country_jordan)
            ESTONIA_COUNTRY_KEY -> context.getString(R.string.country_estonia)
            TURKEY_COUNTRY_KEY -> context.getString(R.string.country_turkey)
            else -> context.getString(R.string.country_russia)
        }

}