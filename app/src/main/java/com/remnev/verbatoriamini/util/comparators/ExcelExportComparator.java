package com.remnev.verbatoriamini.util.comparators;

import android.support.v4.util.Pair;

import java.util.Comparator;

/**
 * Created by nikitaremnev on 12.12.15.
 */
public class ExcelExportComparator implements Comparator<Pair<Long, String>> {

    @Override
    public int compare(Pair<Long, String> pairOne, Pair<Long, String> pairTwo) {
        if (pairOne.first > pairTwo.first) {
            return 1;
        } else if (pairOne.first < pairTwo.first) {
            return -1;
        }
        return 0;
    }
}