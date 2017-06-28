package com.verbatoria.presentation.login.presenter;

import android.support.annotation.NonNull;
import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.business.token.interactor.ITokenInteractor;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.presentation.login.view.ILoginView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.rx.IRxSchedulers;
import com.verbatoria.utils.rx.RxSchedulers;

/**
 * Реализация презентера для логина
 *
 * @author nikitaremnev
 */
public class LoginPresenter implements ILoginPresenter {

    private static final String TAG = LoginPresenter.class.getSimpleName();

    private ILoginInteractor mLoginInteractor;
    private ITokenInteractor mTokenInteractor;
    private ILoginView mLoginView;

    IRxSchedulers mRxSchedulers;

    public LoginPresenter(ILoginInteractor loginInteractor, ITokenInteractor tokenInteractor) {
        this.mLoginInteractor = loginInteractor;
        this.mTokenInteractor = tokenInteractor;
        mRxSchedulers = new RxSchedulers();
    }

    @Override
    public void bindView(@NonNull ILoginView loginView) {
        this.mLoginView = loginView;
    }

    @Override
    public void unbindView() {
        mLoginView = null;
    }

    @Override
    public void login() {
        mLoginView.showProgress();
        mLoginInteractor.login(mLoginView.getPhone(), mLoginView.getPassword())
                .subscribeOn(mRxSchedulers.getNewThreadScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler())
                .subscribe(this::handleSuccessLogin, this::handleErrorLogin);
    }

    private void handleSuccessLogin(@NonNull LoginResponseModel loginResponseModel) {
        Logger.e(TAG, loginResponseModel.toString());
        saveToken(loginResponseModel);
        mLoginView.hideProgress();
        mLoginView.loginSuccess();
    }

    private void handleErrorLogin(Throwable throwable) {
        Logger.exc(TAG, throwable);
        mLoginView.hideProgress();
        mLoginView.showError(throwable.getMessage());
    }

    private void saveToken(@NonNull LoginResponseModel loginResponseModel) {
        mTokenInteractor.updateToken(loginResponseModel)
                .subscribeOn(mRxSchedulers.getMainThreadScheduler())
                .observeOn(mRxSchedulers.getMainThreadScheduler())
                .subscribe();
    }

}
