package com.verbatoria.utils;

import android.os.Build;

import com.remnev.verbatoriamini.BuildConfig;

/**
 * Утилиты для разработчика
 *
 * @author nikitaremnev
 */
public class DeveloperUtils {

    public static String getApplicationVersion() {
        return BuildConfig.VERSION_NAME;
    }

    public static String getAndroidVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

}
