package com.verbatoria.data.repositories.late_send;

import com.verbatoria.business.late_send.LateReportModel;

import java.util.List;

/**
 *
 * Интерфейс-репозитория для поздней отправки отчетов
 *
 * @author nikitaremnev
 */
public interface ILateSendRepository {

    List<LateReportModel> getBackUpReports();

    void removeReport(LateReportModel lateReportModel);

}
