package com.verbatoria.ui.session.view.submit;

import com.verbatoria.ui.session.view.submit.questions.IAnswerClickCallback;

/**
 * Презентер для экрана отправки результатов
 *
 * @author nikitaremnev
 */
public interface ISubmitView extends IAnswerClickCallback {

    void showProgress();
    void hideProgress();

    void showError(String message);

    void finishSession();
    void finishSessionWithError();
}
