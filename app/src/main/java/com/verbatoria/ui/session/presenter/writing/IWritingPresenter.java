package com.verbatoria.ui.session.presenter.writing;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.ui.session.view.writing.IWritingView;

/**
 * Презентер для экрана записи
 *
 * @author nikitaremnev
 */
public interface IWritingPresenter {

    void bindView(@NonNull IWritingView writingView);
    void unbindView();

    void obtainEvent(Intent intent);
    EventModel getEvent();

    void playClick();
    void pauseClick();
    void nextClick();
    void backClick();

    void checkFinishAllowed();

    void submitCode(String code);

}
