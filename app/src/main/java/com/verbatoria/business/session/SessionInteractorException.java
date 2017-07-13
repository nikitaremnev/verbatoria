package com.verbatoria.business.session;

/**
 * Исключение для интерактора для сессии
 *
 * @author nikitaremnev
 */
public class SessionInteractorException extends RuntimeException {

    public SessionInteractorException() {

    }

    public SessionInteractorException(String detailMessage) {
        super(detailMessage);
    }

}
