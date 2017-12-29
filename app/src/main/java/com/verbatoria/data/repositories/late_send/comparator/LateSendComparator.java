package com.verbatoria.data.repositories.late_send.comparator;

import com.verbatoria.business.late_send.models.LateReportModel;

import java.util.Comparator;

/**
 * Компаратор для сортировки событий по времени
 *
 * @author nikitaremnev
 */
public class LateSendComparator implements Comparator<LateReportModel>  {

    @Override
    public int compare(LateReportModel firstLateReportModel, LateReportModel secondLateReportModel) {
        if (firstLateReportModel.getStartAt().getTime() > secondLateReportModel.getStartAt().getTime()) {
            return 1;
        } else if (firstLateReportModel.getStartAt().getTime() < secondLateReportModel.getStartAt().getTime()) {
            return -1;
        }
        return 0;
    }

}
