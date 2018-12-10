package com.verbatoria.utils;

import android.support.design.widget.Snackbar;
import android.view.View;

import com.remnev.verbatoria.R;

/**
 *
 * @author nikitaremnev
 */
public class Helper {

    public static void showSnackBar(View view, String text) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
    }

    public static void showHintSnackBar(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundResource(R.color.verbatolog_status_green);
        snackbar.show();
    }

    public static void showShortHintSnackBar(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.verbatolog_status_green);
        snackbar.show();
    }

    public static void showWarningSnackBar(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundResource(R.color.verbatolog_status_yellow);
        snackbar.show();
    }

    public static void showErrorSnackBar(View view, String text) {
        Snackbar snackbar = Snackbar.make(view, text, Snackbar.LENGTH_LONG);
        snackbar.getView().setBackgroundResource(R.color.verbatolog_status_red);
        snackbar.show();
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
