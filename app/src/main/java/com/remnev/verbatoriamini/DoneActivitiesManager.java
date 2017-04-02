package com.remnev.verbatoriamini;

import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nikitaremnev on 31.03.17.
 */

public class DoneActivitiesManager {

    private static Set<String> sMDoneActivitiesArray;

    static {
        sMDoneActivitiesArray = new HashSet<>();
    }

    public static Set<String>  getDoneActivitiesArray() {
        return sMDoneActivitiesArray;
    }

    public static void clearDoneActivities() {
        sMDoneActivitiesArray.clear();
    }

    public static String getAllUndoneActivities() {
        String result = "";
        if (!sMDoneActivitiesArray.contains("11")) {
            result += "11, ";
        }
        if (!sMDoneActivitiesArray.contains("21")) {
            result += "21, ";
        }
        if (!sMDoneActivitiesArray.contains("31")) {
            result += "31, ";
        }
        if (!sMDoneActivitiesArray.contains("41")) {
            result += "41, ";
        }
        if (!sMDoneActivitiesArray.contains("51")) {
            result += "51, ";
        }
        if (!sMDoneActivitiesArray.contains("61")) {
            result += "61, ";
        }
        if (!sMDoneActivitiesArray.contains("71")) {
            result += "71, ";
        }
        if (!sMDoneActivitiesArray.contains("99")) {
            result += "99, ";
        }
        if (!TextUtils.isEmpty(result)) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static boolean addActivityToDoneArray(String string) {
        return sMDoneActivitiesArray.add(string);
    }

    public static boolean containsDoneActivity(String string) {
        return sMDoneActivitiesArray.contains(string);
    }

    public static boolean removeActivityFromDoneArray(String string) {
        return sMDoneActivitiesArray.remove(string);
    }
}
