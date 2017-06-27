package com.verbatoria.business.token.interactor;

/**
 * Исключение для интерактора для токена
 *
 * @author nikitaremnev
 */
public class TokenInteractorException extends RuntimeException {

    public TokenInteractorException() {
    }

    public TokenInteractorException(String detailMessage) {
        super(detailMessage);
    }

}
