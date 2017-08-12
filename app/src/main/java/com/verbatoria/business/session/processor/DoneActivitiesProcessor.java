package com.verbatoria.business.session.processor;

import android.text.TextUtils;

import com.verbatoria.utils.Logger;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.inject.Singleton;
import static com.verbatoria.business.session.activities.ActivitiesCodes.*;

/**
 * Процессор для обработки активностей во время сессии
 *
 * @author nikitaremnev
 */
@Singleton
public class DoneActivitiesProcessor {

    private static final String TAG = DoneActivitiesProcessor.class.getSimpleName();

    private static final int MINIMUM_ACTIVITY_TIME = 15;

    private static Set<String> sDoneActivitiesArray;
    private static Map<String, Long> sDoneActivitiesTimeArray;

    static {
        sDoneActivitiesArray = new HashSet<>();
        sDoneActivitiesTimeArray = new HashMap<>();
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
        if (!sDoneActivitiesArray.contains(CODE_11)) {
            result += CODE_11 + ", ";
        }
        if (!sDoneActivitiesArray.contains(CODE_21)) {
            result += CODE_21 + ", ";
        }
        if (!sDoneActivitiesArray.contains(CODE_31)) {
            result += CODE_31 + ", ";
        }
        if (!sDoneActivitiesArray.contains(CODE_41)) {
            result += CODE_41 + ", ";
        }
        if (!sDoneActivitiesArray.contains(CODE_51)) {
            result += CODE_51 + ", ";
        }
        if (!sDoneActivitiesArray.contains(CODE_61)) {
            result += CODE_61 + ", ";
        }
        if (!sDoneActivitiesArray.contains(CODE_71)) {
            result += CODE_71 + ", ";
        }
        if (!sDoneActivitiesArray.contains(CODE_99)) {
            result += CODE_99 + ", ";
        }
        if (!TextUtils.isEmpty(result)) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static String getAllNotEnoughTimeActivities() {
        return "";
//        String result = "";
//        if (!sDoneActivitiesTimeArray.containsKey(CODE_11) ||
//                sDoneActivitiesTimeArray.get(CODE_11) < MINIMUM_ACTIVITY_TIME) {
//            Logger.e(TAG, "CODE_11 : " + sDoneActivitiesTimeArray.get(CODE_11));
//            result += CODE_11 + ", ";
//        }
//        if (!sDoneActivitiesTimeArray.containsKey(CODE_21) ||
//                sDoneActivitiesTimeArray.get(CODE_21) < MINIMUM_ACTIVITY_TIME) {
//            Logger.e(TAG, "CODE_21 : " + sDoneActivitiesTimeArray.get(CODE_21));
//            result += CODE_21 + ", ";
//        }
//        if (!sDoneActivitiesTimeArray.containsKey(CODE_31) ||
//                sDoneActivitiesTimeArray.get(CODE_31) < MINIMUM_ACTIVITY_TIME) {
//            Logger.e(TAG, "CODE_31 : " + sDoneActivitiesTimeArray.get(CODE_31));
//            result += CODE_31 + ", ";
//        }
//        if (!sDoneActivitiesTimeArray.containsKey(CODE_41) ||
//                sDoneActivitiesTimeArray.get(CODE_41) < MINIMUM_ACTIVITY_TIME) {
//            Logger.e(TAG, "CODE_41 : " + sDoneActivitiesTimeArray.get(CODE_41));
//            result += CODE_41 + ", ";
//        }
//        if (!sDoneActivitiesTimeArray.containsKey(CODE_51) ||
//                sDoneActivitiesTimeArray.get(CODE_51) < MINIMUM_ACTIVITY_TIME) {
//            Logger.e(TAG, "CODE_51 : " + sDoneActivitiesTimeArray.get(CODE_51));
//            result += CODE_51 + ", ";
//        }
//        if (!sDoneActivitiesTimeArray.containsKey(CODE_61) ||
//                sDoneActivitiesTimeArray.get(CODE_61) < MINIMUM_ACTIVITY_TIME) {
//            Logger.e(TAG, "CODE_61 : " + sDoneActivitiesTimeArray.get(CODE_61));
//            result += CODE_61 + ", ";
//        }
//        if (!sDoneActivitiesTimeArray.containsKey(CODE_71) ||
//                sDoneActivitiesTimeArray.get(CODE_71) < MINIMUM_ACTIVITY_TIME) {
//            Logger.e(TAG, "CODE_71 : " + sDoneActivitiesTimeArray.get(CODE_71));
//            result += CODE_71 + ", ";
//        }
//        if (!sDoneActivitiesTimeArray.containsKey(CODE_99) ||
//                sDoneActivitiesTimeArray.get(CODE_99) < MINIMUM_ACTIVITY_TIME) {
//            Logger.e(TAG, "CODE_99 : " + sDoneActivitiesTimeArray.get(CODE_99));
//            result += CODE_99 + ", ";
//        }
//        if (!TextUtils.isEmpty(result)) {
//            result = result.substring(0, result.length() - 2);
//        }
//        return result;
    }

    public static boolean addActivityToDoneArray(String string) {
        return sDoneActivitiesArray.add(string);
    }

    public static void addActivityToDoneArray(String string, long time) {
        long timeSum = time;
        if (sDoneActivitiesTimeArray.containsKey(string)) {
            timeSum += sDoneActivitiesTimeArray.get(string);
        }
        sDoneActivitiesTimeArray.put(string, timeSum);
    }

    public static boolean containsDoneActivity(String string) {
        return sDoneActivitiesArray.contains(string);
    }

    public static boolean removeActivityFromDoneArray(String string) {
        int i = 0;
        if (sDoneActivitiesTimeArray.containsKey(string)) {
            sDoneActivitiesTimeArray.remove(string);
        }
        return sDoneActivitiesArray.remove(string);
    }

    public static long getSumOfTime() {
        long time = 0;
        for (String key : sDoneActivitiesTimeArray.keySet()) {
            time += sDoneActivitiesTimeArray.get(key);
        }
        return time;
    }

    public static long getSumOfTimeByCode(String code) {
        if (sDoneActivitiesTimeArray.containsKey(code)) {
            return sDoneActivitiesTimeArray.get(code);
        }
        return 0;
    }
}
