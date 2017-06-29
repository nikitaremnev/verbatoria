package com.verbatoria.di.application;

import com.verbatoria.business.token.processor.TokenProcessor;

/**
 * Интерфейс для inject-методов для context
 *
 * @author nikitaremnev
 */
public interface ApplicationComponentInjects {

    void inject(TokenProcessor tokenProcessor);

}
