package com.verbatoria.presentation.login.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.presentation.login.view.ILoginView;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Реализация презентера для логина
 *
 * @author nikitaremnev
 */

public class LoginPresenter implements ILoginPresenter {

    private ILoginInteractor mLoginInteractor;

    private ILoginView mLoginView;

    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();

    public LoginPresenter(ILoginInteractor loginInteractor) {
        this.mLoginInteractor = loginInteractor;
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
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleSuccessLogin, this::handleErrorLogin);
    }

    private void handleSuccessLogin(@NonNull LoginResponseModel loginResponseModel) {

    }

    private void handleErrorLogin(Throwable throwable) {

    }

}
