package com.verbatoria.business.late_send;

import com.verbatoria.business.late_send.models.LateReportModel;
import com.verbatoria.data.repositories.late_send.ILateSendRepository;
import com.verbatoria.utils.FileUtils;
import com.verbatoria.utils.RxSchedulers;

import java.io.File;
import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * @author nikitaremnev
 */
public class LateSendInteractor implements ILateSendInteractor {

    private static final String TAG = LateSendInteractor.class.getSimpleName();

    private ILateSendRepository mLateSendRepository;

    public LateSendInteractor(ILateSendRepository lateSendRepository) {
        mLateSendRepository = lateSendRepository;
    }

    @Override
    public Observable<List<LateReportModel>> getLateReports() {
        return Observable.fromCallable(() -> mLateSendRepository.getBackUpReports())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Completable cleanUp(LateReportModel lateReportModel) {
        return Completable.fromAction(() -> {
            mLateSendRepository.removeReport(lateReportModel);
            File file = new File(FileUtils.getApplicationDirectory(), lateReportModel.getReportFileName());
            if (file.exists()) {
                file.delete();
            }
        })
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

}