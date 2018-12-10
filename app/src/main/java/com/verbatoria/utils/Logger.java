package com.verbatoria.utils;

import android.util.Log;

import com.remnev.verbatoria.BuildConfig;

/**
 * Класс для логирования
 *
 * @author nikitaremnev
 */
public final class Logger {

    private static boolean LOG_PRINT_DEBUG = true;
    private static boolean LOG_PRINT_ERROR = true;

    private Logger() {
        throw new AssertionError();
    }

    public static void v(String tag, String message) {
        if (LOG_PRINT_DEBUG) {
            try {
                Log.v(tag, message);
            } catch (Exception ignored) {
            }
        }
    }

    public static void d(String tag, String message, boolean condition) {
        if (condition && LOG_PRINT_DEBUG) {
            try {
                Log.d(tag, message);
            } catch (Exception ignored) {
            }
        }
    }

    public static void d(String tag, String message) {
        if (LOG_PRINT_DEBUG) {
            try {
                Log.d(tag, message);
            } catch (Exception ignored) {
            }
        }
    }

    public static void d(String tag, String message, Throwable throwable) {
        if (LOG_PRINT_DEBUG) {
            try {
                Log.d(tag, message, throwable);
            } catch (Exception ignored) {
            }
        }
    }

    public static void i(String tag, String message) {
        if (LOG_PRINT_DEBUG) {
            try {
                Log.i(tag, message);
            } catch (Exception ignored) {
            }
        }
    }

    public static void w(String tag, String message) {
        if (LOG_PRINT_DEBUG) {
            try {
                Log.w(tag, message);
            } catch (Exception ignored) {
            }
        }
    }

    public static void w(String tag, String message, Throwable throwable) {
        if (LOG_PRINT_DEBUG) {
            try {
                Log.w(tag, message, throwable);
            } catch (Exception ignored) {
            }
        }
    }

    public static void e(String tag, String message) {
        if (LOG_PRINT_ERROR) {
            try {
                Log.e(tag, message);
            } catch (Exception ignored) {
            }
        }
    }

    public static void e(String tag, String message, Throwable throwable) {
        if (LOG_PRINT_ERROR) {
            try {
                Log.e(tag, message, throwable);
            } catch (Exception ignored) {
            }
        }
    }

    public static void exc(String tag, String message) {
        exc(tag, message, null);
    }

    public static void exc(String tag, Throwable throwable) {
        exc(tag, null, throwable);
    }

    public static void exc(String tag, String message, Throwable throwable) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, message, throwable);
        }
    }

}
