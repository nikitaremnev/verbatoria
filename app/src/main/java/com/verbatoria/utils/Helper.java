package com.verbatoria.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

/**
 *
 * @author nikitaremnev
 */
public class Helper {

    public static void showSnackBar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }

    public static long processTimestamp(long timestamp) {
        long left = timestamp % 1000;
        if (left > 499) {
            return (timestamp / 1000) + 1;
        } else {
            return timestamp / 1000;
        }
    }
}
