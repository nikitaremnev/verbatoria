package com.verbatoria.presentation.session.view.writing;

/**
 *
 * View для экрана записи
 *
 * @author nikitaremnev
 */
public interface IWritingView {

    void addGraphEntry(int value);
    void updateTimer(String timerString);

    void setUpPlayMode();
    void setUpPauseMode();
    void showPlayer();
    void hidePlayer();

    void showPlayingFileName(String fileName);
    void showError(String error);

    void setButtonState(ActivityButtonState state, String code);
}
