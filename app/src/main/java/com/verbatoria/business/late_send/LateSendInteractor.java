package com.verbatoria.business.late_send;

import com.verbatoria.data.repositories.late_send.ILateSendRepository;
import com.verbatoria.utils.FileUtils;
import com.verbatoria.utils.RxSchedulers;

import java.io.File;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author nikitaremnev
 */
public class LateSendInteractor implements ILateSendInteractor {

    private static final String TAG = "LateSendInteractor";

    private ILateSendRepository lateSendRepository;

    public LateSendInteractor(ILateSendRepository lateSendRepository) {
        this.lateSendRepository = lateSendRepository;
    }

    @Override
    public Observable<List<LateReportModel>> getLateReports() {
        return Observable.fromCallable(() -> lateSendRepository.getBackUpReports())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Completable cleanUp(LateReportModel lateReportModel) {
        return Completable.fromAction(() -> {
            lateSendRepository.removeReport(lateReportModel);
            File file = new File(FileUtils.getApplicationDirectory(), lateReportModel.getReportFileName());
            if (file.exists()) {
                file.delete();
            }
        })
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

}