package com.verbatoria.business.late_send;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author nikitaremnev
 */

public interface ILateSendInteractor {

    Observable<List<LateReportModel>> getLateReports();

    Completable cleanUp(LateReportModel lateReportModel);

}
