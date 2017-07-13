package com.verbatoria.di.token;

import com.verbatoria.data.repositories.dashboard.DashboardRepository;
import com.verbatoria.data.repositories.session.SessionRepository;
import com.verbatoria.utils.PreferencesStorage;

/**
 * Интерфейс для inject-методов для токена
 *
 * @author nikitaremnev
 */
public interface TokenComponentInjects {

    void inject(PreferencesStorage preferencesStorage);

    void inject(DashboardRepository dashboardRepository);

    void inject(SessionRepository sessionRepository);

}
