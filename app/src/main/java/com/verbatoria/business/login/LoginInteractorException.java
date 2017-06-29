package com.verbatoria.business.login;

/**
 * Исключение для интерактора для токена
 *
 * @author nikitaremnev
 */
public class LoginInteractorException extends RuntimeException {

    public LoginInteractorException() {

    }

    public LoginInteractorException(String detailMessage) {
        super(detailMessage);
    }

}
