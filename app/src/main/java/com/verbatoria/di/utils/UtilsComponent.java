package com.verbatoria.di.utils;

import com.verbatoria.business.token.processor.TokenProcessor;

/**
 * Интерфейс для inject-методов для utils
 *
 * @author nikitaremnev
 */
public interface UtilsComponent {

    void inject(TokenProcessor tokenProcessor);

}
