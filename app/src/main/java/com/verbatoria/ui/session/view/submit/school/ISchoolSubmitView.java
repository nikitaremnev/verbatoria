package com.verbatoria.ui.session.view.submit.school;

/**
 * Презентер для экрана отправки результатов для школы
 *
 * @author nikitaremnev
 */

public interface ISchoolSubmitView {

    void showProgress();
    void hideProgress();

    void showError(String message);

    void finishSession();
    void finishSessionWithError();

}
