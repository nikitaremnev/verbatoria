package com.verbatoria.di.application;

import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.presentation.dashboard.view.main.events.VerbatologEventsAdapter;

/**
 * Интерфейс для inject-методов для context
 *
 * @author nikitaremnev
 */
public interface ApplicationComponentInjects {

    void inject(TokenProcessor tokenProcessor);

    void inject(VerbatologEventsAdapter verbatologEventsAdapter);
}
