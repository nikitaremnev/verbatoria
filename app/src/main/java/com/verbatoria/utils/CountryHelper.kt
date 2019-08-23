package com.verbatoria.utils

import android.content.Context
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

object CountryHelper {

    fun getFlagResourceByCountry(context: Context, country: String): Int =
        when (country) {
            context.getString(R.string.country_russia) -> R.drawable.ic_flag_ru
            context.getString(R.string.country_swiss) -> R.drawable.ic_flag_ch
            context.getString(R.string.country_hong_kong) -> R.drawable.ic_flag_hk
            context.getString(R.string.country_uzbekistan) -> R.drawable.ic_flag_uz
            context.getString(R.string.country_israel) -> R.drawable.ic_flag_isr
            context.getString(R.string.country_belarus) -> R.drawable.ic_flag_by
            context.getString(R.string.country_thailand) -> R.drawable.ic_flag_th
            context.getString(R.string.country_azerbaijan) -> R.drawable.ic_flag_az
            context.getString(R.string.country_uae) -> R.drawable.ic_flag_ae
            context.getString(R.string.country_ukraine) -> R.drawable.ic_flag_ukr
            else -> R.drawable.ic_flag_ru
        }

    fun getPhoneFormatterByCountry(context: Context, country: String): String =
        when (country) {
            context.getString(R.string.country_russia) -> context.getString(R.string.login_russia_phone_mask)
            context.getString(R.string.country_swiss) -> context.getString(R.string.login_swiss_phone_mask)
            context.getString(R.string.country_hong_kong) -> context.getString(R.string.login_hong_kong_phone_mask)
            context.getString(R.string.country_uzbekistan) -> context.getString(R.string.login_uzbekistan_phone_mask)
            context.getString(R.string.country_israel) -> context.getString(R.string.login_israel_phone_mask)
            context.getString(R.string.country_belarus) -> context.getString(R.string.login_belarus_phone_mask)
            context.getString(R.string.country_thailand) -> context.getString(R.string.login_thailand_phone_mask)
            context.getString(R.string.country_azerbaijan) -> context.getString(R.string.login_azerbaijan_phone_mask)
            context.getString(R.string.country_uae) -> context.getString(R.string.login_uae_phone_mask)
            context.getString(R.string.country_ukraine) -> context.getString(R.string.login_ukraine_phone_mask)
            else -> context.getString(R.string.login_russia_phone_mask)
        }

    fun getCountryCodeByCountry(context: Context, country: String): String {
        return if (country == context.getString(R.string.country_russia)) {
            "ru"
        } else if (country == context.getString(R.string.country_ukraine)) {
            "ua"
        } else {
            "az"
        }
    }

}