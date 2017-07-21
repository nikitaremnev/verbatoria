package com.verbatoria.business.session.processor;

import android.support.v4.util.Pair;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.inject.Singleton;

/**
 * Процессор для обработки активностей во время сессии
 *
 * @author nikitaremnev
 */
@Singleton
public class DoneActivitiesProcessor {

    private static Set<String> sDoneActivitiesArray;
    private static ArrayList<Pair<String, Long>> sDoneActivitiesTimeArray;

    static {
        sDoneActivitiesArray = new HashSet<>();
        sDoneActivitiesTimeArray = new ArrayList<>();
    }

    private DoneActivitiesProcessor() {
        sDoneActivitiesArray = new HashSet<>();
    }

    public static Set<String> getDoneActivitiesArray() {
        return sDoneActivitiesArray;
    }

    public static void clearDoneActivities() {
        sDoneActivitiesArray.clear();
    }

    public static void clearTimeDoneActivities() {
        sDoneActivitiesTimeArray.clear();
    }

    public static String getAllUndoneActivities() {
        String result = "";
        if (!sDoneActivitiesArray.contains("11")) {
            result += "11, ";
        }
        if (!sDoneActivitiesArray.contains("21")) {
            result += "21, ";
        }
        if (!sDoneActivitiesArray.contains("31")) {
            result += "31, ";
        }
        if (!sDoneActivitiesArray.contains("41")) {
            result += "41, ";
        }
        if (!sDoneActivitiesArray.contains("51")) {
            result += "51, ";
        }
        if (!sDoneActivitiesArray.contains("61")) {
            result += "61, ";
        }
        if (!sDoneActivitiesArray.contains("71")) {
            result += "71, ";
        }
        if (!sDoneActivitiesArray.contains("99")) {
            result += "99, ";
        }
        if (!TextUtils.isEmpty(result)) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static boolean addActivityToDoneArray(String string) {
        return sDoneActivitiesArray.add(string);
    }

    public static boolean addActivityToDoneArray(String string, long time) {
        return sDoneActivitiesTimeArray.add(new Pair<String, Long>(string, time));
    }

    public static boolean containsDoneActivity(String string) {
        return sDoneActivitiesArray.contains(string);
    }

    public static boolean removeActivityFromDoneArray(String string) {
        int i = 0;
        while (i < sDoneActivitiesTimeArray.size()) {
            if (sDoneActivitiesTimeArray.get(i).first.equals(string)) {
                sDoneActivitiesTimeArray.remove(i);
            } else {
                i ++;
            }
        }
        return sDoneActivitiesArray.remove(string);
    }

    public static long getSumOfTime() {
        long time = 0;
        for (int i = 0; i < sDoneActivitiesTimeArray.size(); i ++) {
            time += sDoneActivitiesTimeArray.get(i).second;
        }
        return time;
    }

    public static long getSumOfTimeByCode(String code) {
        long time = 0;
        for (int i = 0; i < sDoneActivitiesTimeArray.size(); i ++) {
            if (sDoneActivitiesTimeArray.get(i).first.equals(code)) {
                time += sDoneActivitiesTimeArray.get(i).second;
            }
        }
        return time;
    }
}
