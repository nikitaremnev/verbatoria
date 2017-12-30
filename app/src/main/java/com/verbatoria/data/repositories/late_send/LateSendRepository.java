package com.verbatoria.data.repositories.late_send;

import android.content.Context;

import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.late_send.models.LateReportModel;
import com.verbatoria.data.repositories.session.database.LateReportsDatabase;

import java.util.List;

import javax.inject.Inject;

/**
 *
 * Реализация репозитория для поздней отправки отчетов
 *
 * @author nikitaremnev
 */
public class LateSendRepository implements ILateSendRepository {

    @Inject
    public Context mContext;

    public LateSendRepository() {
        VerbatoriaApplication.getApplicationComponent().inject(this);
    }

    @Override
    public List<LateReportModel> getBackUpReports() {
        return LateReportsDatabase.getLateReportModels(mContext);
    }

    @Override
    public void removeReport(LateReportModel lateReportModel) {
        LateReportsDatabase.removeReport(mContext, lateReportModel);
    }
}
