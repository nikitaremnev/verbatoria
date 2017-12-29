package com.verbatoria.business.late_send;

import com.verbatoria.business.late_send.models.LateReportModel;

import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * @author nikitaremnev
 */

public interface ILateSendInteractor {

    Observable<List<LateReportModel>> getLateReports();

    Completable cleanUp(LateReportModel lateReportModel);

}
