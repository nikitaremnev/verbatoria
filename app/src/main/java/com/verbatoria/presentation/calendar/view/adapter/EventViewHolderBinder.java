package com.verbatoria.presentation.calendar.view.adapter;

import android.content.Context;

import com.remnev.verbatoriamini.R;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.ReportModel;

/**
 * Binder для отображения ячейки события
 *
 * @author nikitaremnev
 */
public class EventViewHolderBinder {

    public static void bind(EventModel event, EventViewHolder holder, Context context) {
        String childName = event.getChild().getName();
        if (childName == null) {
            holder.setChildName(context.getString(R.string.dashboard_unknown));
        } else {
            holder.setChildName(childName);
        }
        holder.setAge(event.getFullAge(context));
        holder.setReportId(event.getReportId(context));
        holder.setTimePeriod(event.getEventTime());
        switch (event.getReport().getStatus()) {
            case ReportModel.STATUS.NEW:
                holder.showNewReport(context);
                break;
            case ReportModel.STATUS.READY:
                holder.showReadyReport(context);
                break;
            case ReportModel.STATUS.SENT:
                holder.showSentReport(context);
                break;
            case ReportModel.STATUS.UPLOADED:
                holder.showUploadedReport(context);
                break;
        }
    }

}
