package com.verbatoria.utils

import android.annotation.TargetApi
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList

import java.util.Locale

class UpdateLocaleContextWrapper(base: Context) : ContextWrapper(base) {

    companion object {

        @TargetApi(Build.VERSION_CODES.N)
        fun wrap(context: Context, newLocale: Locale): ContextWrapper {
            var context = context
            val res = context.resources
            val configuration = res.configuration

            context = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                configuration.setLocale(newLocale)

                val localeList = LocaleList(newLocale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)

                context.createConfigurationContext(configuration)

            } else {
                configuration.setLocale(newLocale)
                context.createConfigurationContext(configuration)

            }

            return ContextWrapper(context)
        }
    }

}
