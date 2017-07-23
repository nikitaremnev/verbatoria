package com.verbatoria.presentation.session.presenter.writing;

import android.content.Intent;
import android.support.annotation.NonNull;
import com.verbatoria.presentation.session.view.writing.IWritingView;

/**
 * Презентер для экрана записи
 *
 * @author nikitaremnev
 */
public interface IWritingPresenter {

    void bindView(@NonNull IWritingView writingView);
    void unbindView();

    void playClick();
    void pauseClick();
    void nextClick();
    void backClick();

    void showPlayer();
    void hidePlayer();

    void checkFinishAllowed();

    void submitCode(String code);
    void obtainEvent(Intent intent);
}
