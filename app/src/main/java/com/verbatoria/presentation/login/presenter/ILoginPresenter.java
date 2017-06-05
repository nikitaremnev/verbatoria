package com.verbatoria.presentation.login.presenter;

import android.support.annotation.NonNull;

import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.presentation.login.models.LoginFilledDataModel;
import com.verbatoria.presentation.login.view.ILoginView;

/**
 * Презентер для логина
 *
 * @author nikitaremnev
 */

public interface ILoginPresenter {

    void bindView(@NonNull ILoginView loginView);
    void unbindView();

    void login(LoginFilledDataModel loginFilledDataModel);
}
