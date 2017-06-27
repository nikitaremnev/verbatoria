package com.verbatoria.presentation.login.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.presentation.login.view.ILoginView;
import com.verbatoria.utils.Logger;
import com.verbatoria.utils.rx.IRxSchedulers;
import com.verbatoria.utils.rx.RxSchedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Реализация презентера для логина
 *
 * @author nikitaremnev
 */
public class LoginPresenter implements ILoginPresenter {

    private static final String TAG = "LoginPresenter";

    private ILoginInteractor mLoginInteractor;
    private ILoginView mLoginView;
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    IRxSchedulers mRxSchedulers;

    public LoginPresenter(ILoginInteractor loginInteractor) {
        this.mLoginInteractor = loginInteractor;
        mRxSchedulers = new RxSchedulers();
    }

    @Override
    public void bindView(@NonNull ILoginView loginView) {
        this.mLoginView = loginView;
    }

    @Override
    public void unbindView() {
        mCompositeSubscription.clear();
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
        mLoginView.hideProgress();
    }

    private void handleErrorLogin(Throwable throwable) {
        Logger.exc(TAG, throwable);
        mLoginView.hideProgress();
        mLoginView.showError(throwable.getMessage());
    }

}
