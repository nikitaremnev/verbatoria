package com.verbatoria.business.late_send;

import com.verbatoria.business.late_send.models.LateReportModel;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.repositories.late_send.ILateSendRepository;
import com.verbatoria.data.repositories.session.ISessionRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.RxSchedulers;

import java.util.List;

import rx.Completable;
import rx.Observable;

/**
 * @author nikitaremnev
 */
public class LateSendInteractor implements ILateSendInteractor {

    private static final String TAG = LateSendInteractor.class.getSimpleName();

    private ILateSendRepository mLateSendRepository;
    private ISessionRepository mSessionRepository;
    private ITokenRepository mTokenRepository;

    public LateSendInteractor(ILateSendRepository lateSendRepository,
                              ISessionRepository sessionRepository,
                              ITokenRepository tokenRepository) {
        mLateSendRepository = lateSendRepository;
        mSessionRepository = sessionRepository;
        mTokenRepository = tokenRepository;
    }

    @Override
    public Observable<List<LateReportModel>> getLateReports() {
        return Observable.fromCallable(() -> mLateSendRepository.getBackUpReports())
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Completable cleanUp(LateReportModel lateReportModel) {
        return Completable.fromAction(() -> mLateSendRepository.removeReport(lateReportModel))
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }
}