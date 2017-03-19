package com.remnev.verbatoriamini.util;

import com.remnev.verbatoriamini.model.ExcelEvent;

import java.util.Comparator;

/**
 * Created by nikitaremnev on 12.12.15.
 */
public class ExcelEventsComparator implements Comparator<ExcelEvent> {

    @Override
    public int compare(ExcelEvent one, ExcelEvent two) {
        if (one.timestamp > two.timestamp) {
            return 1;
        } else if (one.timestamp < two.timestamp) {
            return -1;
        }
        return 0;
    }
}