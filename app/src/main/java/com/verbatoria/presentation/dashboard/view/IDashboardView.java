package com.verbatoria.presentation.dashboard.view;

import android.support.annotation.NonNull;

import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.presentation.login.view.ILoginView;

/**
 *
 * View для экрана dashboard
 *
 * @author nikitaremnev
 */
public interface IDashboardView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    void showToken(TokenModel tokenModel);

}
