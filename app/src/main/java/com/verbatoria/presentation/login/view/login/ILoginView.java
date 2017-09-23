package com.verbatoria.presentation.login.view.login;

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

    void setPhone(String phone);
    void setPassword(String password);

    //получение данных введенных полей
    String getPhone();
    String getPassword();

    //отображение результатов запроса
    void loginSuccess();
    void showError(String message);

    void startRecoveryPassword();

}
