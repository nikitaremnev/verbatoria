package com.verbatoria.utils

import android.content.Context
import com.remnev.verbatoria.R

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
            else -> R.drawable.ic_flag_ukr
        }

    fun getPhoneFormatterByCountry(context: Context, country: String): Int =
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
            else -> R.drawable.ic_flag_ukr
        }

}