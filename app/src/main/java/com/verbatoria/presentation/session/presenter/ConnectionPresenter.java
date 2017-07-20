package com.verbatoria.presentation.session.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.presentation.login.presenter.ILoginPresenter;
import com.verbatoria.presentation.login.view.ILoginView;
import com.verbatoria.presentation.session.view.IConnectionView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.RxSchedulers;

/**
 * Реализация презентера для экрана соединения
 *
 * @author nikitaremnev
 */
public class ConnectionPresenter implements IConnectionPresenter {

    private static final String TAG = ConnectionPresenter.class.getSimpleName();

    private ISessionInteractor mSessionInteractor;
    private IConnectionView mConnectionView;

    public ConnectionPresenter(ISessionInteractor sessionInteractor) {
        this.mSessionInteractor = sessionInteractor;
    }

    @Override
    public void bindView(@NonNull IConnectionView connectionView) {

    }

    @Override
    public void unbindView() {

    }



//    @Override
//    public void bindView(@NonNull ILoginView loginView) {
//        this.mLoginView = loginView;
//    }
//
//    @Override
//    public void unbindView() {
//        mLoginView = null;
//    }
//
//    @Override
//    public void login() {
//        mLoginView.showProgress();
//        mLoginInteractor.login(mLoginView.getPhone(), mLoginView.getPassword())
//                .subscribeOn(RxSchedulers.getNewThreadScheduler())
//                .observeOn(RxSchedulers.getMainThreadScheduler())
//                .subscribe(this::handleSuccessLogin, this::handleErrorLogin);
//    }
//
//    private void handleSuccessLogin(TokenModel tokenModel) {
//        Logger.e(TAG, tokenModel.toString());
//        mLoginView.hideProgress();
//        mLoginView.loginSuccess();
//    }
//
//    private void handleErrorLogin(Throwable throwable) {
//        Logger.exc(TAG, throwable);
//        mLoginView.hideProgress();
//        mLoginView.showError(throwable.getMessage());
//    }

}
