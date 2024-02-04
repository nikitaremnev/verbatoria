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
    const val CHINA_COUNTRY_KEY = "cn"
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
    const val TANZANIA_COUNTRY_KEY = "tz"
    const val USA_COUNTRY_KEY = "us"
    const val CYPRUS_COUNTRY_KEY = "cy"
    const val BOSNIA_COUNTRY_KEY = "ba"
    const val NEPAL_COUNTRY_KEY = "np"
    const val FRANCE_COUNTRY_KEY = "fra"
    const val UGANDA_COUNTRY_KEY = "ug"
    const val NEW_ZEALAND_COUNTRY_KEY = "nz"
    const val MACEDONIA_COUNTRY_KEY = "mk"
    const val ARGENTINA_COUNTRY_KEY = "ar"
    const val SLOVAKIA_COUNTRY_KEY = "svk"
    const val GREECE_COUNTRY_KEY = "gre"
    const val MOZAMBIQUE_COUNTRY_KEY = "mz"
    const val MEXICO_COUNTRY_KEY = "mex"
    const val AUSTRALIA_COUNTRY_KEY = "aus"
    const val LATVIA_COUNTRY_KEY = "lat"
    const val CROATIA_COUNTRY_KEY = "cro"
    const val KENYA_COUNTRY_KEY = "ke"
    const val JAPAN_COUNTRY_KEY = "jp"
    const val MONTENEGRO_COUNTRY_KEY = "me"
    const val SLOVENIA_COUNTRY_KEY = "sl"

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
            CHINA_COUNTRY_KEY -> R.drawable.ic_flag_cn
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
            TANZANIA_COUNTRY_KEY -> R.drawable.ic_flag_tz
            USA_COUNTRY_KEY -> R.drawable.ic_flag_us
            CYPRUS_COUNTRY_KEY -> R.drawable.ic_flag_cy
            BOSNIA_COUNTRY_KEY -> R.drawable.ic_flag_ba
            NEPAL_COUNTRY_KEY -> R.drawable.ic_flag_np
            FRANCE_COUNTRY_KEY -> R.drawable.ic_flag_fr
            UGANDA_COUNTRY_KEY -> R.drawable.ic_flag_ug
            NEW_ZEALAND_COUNTRY_KEY -> R.drawable.ic_flag_nz
            MACEDONIA_COUNTRY_KEY -> R.drawable.ic_flag_mk
            GREECE_COUNTRY_KEY -> R.drawable.ic_flag_gr
            ARGENTINA_COUNTRY_KEY -> R.drawable.ic_flag_ar
            SLOVAKIA_COUNTRY_KEY -> R.drawable.ic_flag_sk
            MOZAMBIQUE_COUNTRY_KEY -> R.drawable.ic_flag_mz
            MEXICO_COUNTRY_KEY -> R.drawable.ic_flag_mex
            AUSTRALIA_COUNTRY_KEY -> R.drawable.ic_flag_aus
            LATVIA_COUNTRY_KEY -> R.drawable.ic_flag_lat
            CROATIA_COUNTRY_KEY -> R.drawable.ic_flag_cro
            KENYA_COUNTRY_KEY -> R.drawable.ic_flag_ke
            JAPAN_COUNTRY_KEY -> R.drawable.ic_flag_jp
            MONTENEGRO_COUNTRY_KEY -> R.drawable.ic_flag_me
            SLOVENIA_COUNTRY_KEY -> R.drawable.ic_flag_sl
            else -> R.drawable.ic_flag_ru
        }

    fun isCountryRequireSkipSMSConfirmation(countryKey: String): Boolean =
        countryKey != RUSSIAN_COUNTRY_KEY &&
                countryKey != UZBEKISTAN_COUNTRY_KEY &&
                countryKey != AZERBAIJAN_COUNTRY_KEY  &&
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
            CHINA_COUNTRY_KEY -> context.getString(R.string.login_china_phone_mask)
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
            TANZANIA_COUNTRY_KEY -> context.getString(R.string.login_tanzania_phone_mask)
            USA_COUNTRY_KEY -> context.getString(R.string.login_usa_phone_mask)
            CYPRUS_COUNTRY_KEY -> context.getString(R.string.login_cyprus_phone_mask)
            BOSNIA_COUNTRY_KEY -> context.getString(R.string.login_bosnia_phone_mask)
            NEPAL_COUNTRY_KEY -> context.getString(R.string.login_nepal_phone_mask)
            FRANCE_COUNTRY_KEY -> context.getString(R.string.login_france_phone_mask)
            UGANDA_COUNTRY_KEY -> context.getString(R.string.login_uganda_phone_mask)
            NEW_ZEALAND_COUNTRY_KEY -> context.getString(R.string.login_new_zealand_phone_mask)
            MACEDONIA_COUNTRY_KEY -> context.getString(R.string.login_macedonia_phone_mask)
            GREECE_COUNTRY_KEY -> context.getString(R.string.login_greece_phone_mask)
            ARGENTINA_COUNTRY_KEY -> context.getString(R.string.login_argentina_phone_mask)
            SLOVAKIA_COUNTRY_KEY -> context.getString(R.string.login_slovakia_phone_mask)
            MOZAMBIQUE_COUNTRY_KEY -> context.getString(R.string.login_mozambique_phone_mask)
            MEXICO_COUNTRY_KEY -> context.getString(R.string.login_mexico_phone_mask)
            AUSTRALIA_COUNTRY_KEY -> context.getString(R.string.login_australia_phone_mask)
            LATVIA_COUNTRY_KEY -> context.getString(R.string.login_latvia_phone_mask)
            CROATIA_COUNTRY_KEY -> context.getString(R.string.login_croatia_phone_mask)
            KENYA_COUNTRY_KEY -> context.getString(R.string.login_kenya_phone_mask)
            JAPAN_COUNTRY_KEY -> context.getString(R.string.login_japan_phone_mask)
            MONTENEGRO_COUNTRY_KEY -> context.getString(R.string.login_montenegro_phone_mask)
            SLOVENIA_COUNTRY_KEY -> context.getString(R.string.login_slovenia_phone_mask)
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
            CHINA_COUNTRY_KEY -> context.getString(R.string.country_china)
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
            TANZANIA_COUNTRY_KEY -> context.getString(R.string.country_tanzania)
            USA_COUNTRY_KEY -> context.getString(R.string.country_usa)
            CYPRUS_COUNTRY_KEY -> context.getString(R.string.country_cyprus)
            BOSNIA_COUNTRY_KEY -> context.getString(R.string.country_bosnia)
            NEPAL_COUNTRY_KEY -> context.getString(R.string.country_nepal)
            FRANCE_COUNTRY_KEY -> context.getString(R.string.country_france)
            UGANDA_COUNTRY_KEY -> context.getString(R.string.country_uganda)
            NEW_ZEALAND_COUNTRY_KEY -> context.getString(R.string.country_new_zealand)
            MACEDONIA_COUNTRY_KEY -> context.getString(R.string.country_macedonia)
            GREECE_COUNTRY_KEY -> context.getString(R.string.country_greece)
            ARGENTINA_COUNTRY_KEY -> context.getString(R.string.country_argentina)
            SLOVAKIA_COUNTRY_KEY -> context.getString(R.string.country_slovakia)
            MOZAMBIQUE_COUNTRY_KEY -> context.getString(R.string.country_mozambique)
            MEXICO_COUNTRY_KEY -> context.getString(R.string.country_mexico)
            AUSTRALIA_COUNTRY_KEY -> context.getString(R.string.country_australia)
            LATVIA_COUNTRY_KEY -> context.getString(R.string.country_latvia)
            CROATIA_COUNTRY_KEY -> context.getString(R.string.country_croatia)
            KENYA_COUNTRY_KEY -> context.getString(R.string.country_kenya)
            JAPAN_COUNTRY_KEY -> context.getString(R.string.country_japan)
            MONTENEGRO_COUNTRY_KEY -> context.getString(R.string.country_montenegro)
            SLOVENIA_COUNTRY_KEY -> context.getString(R.string.country_slovenia)
            else -> context.getString(R.string.country_russia)
        }

}
