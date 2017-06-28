package com.verbatoria.business.dashboard;

/**
 * Исключение для интерактора для dashboard
 *
 * @author nikitaremnev
 */
public class DashboardInteractorException extends RuntimeException {

    public DashboardInteractorException() {
    }

    public DashboardInteractorException(String detailMessage) {
        super(detailMessage);
    }

}