package com.verbatoria.presentation.dashboard.view.main;

import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.presentation.login.view.ILoginView;

import java.util.List;

/**
 *
 * View для экрана dashboard
 *
 * @author nikitaremnev
 */
public interface IDashboardMainView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    void showVerbatologInfo(String verbatologFullName, String verbatologPhone, String verbatologEmail);
    void showVerbatologEvents(List<EventModel> verbatologEvents);
}
