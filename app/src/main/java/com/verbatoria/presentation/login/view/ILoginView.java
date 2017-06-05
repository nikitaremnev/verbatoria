package com.verbatoria.presentation.login.view;

/**
 *
 * View для экрана логина
 *
 * @author nikitaremnev
 */

public interface ILoginView {

    //отображение прогресса
    void showProgress();
    void hideProgress();

    //отображение результатов запроса
    void showSuccess();
    void showError();

    //менеджмент кнопки "Войти"
    void setLoginButtonAvailability(boolean enabled);

}
