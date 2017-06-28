package com.verbatoria.di.application;

import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.business.login.LoginInteractor;
import com.verbatoria.business.token.interactor.ITokenInteractor;
import com.verbatoria.business.token.interactor.TokenInteractor;
import com.verbatoria.data.repositories.dashboard.DashboardRepository;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.data.repositories.token.TokenRepository;
import com.verbatoria.di.login.LoginScope;

import dagger.Module;
import dagger.Provides;

/**
 * Модуль даггера для токена
 *
 * @author nikitaremnev
 */
@Module
public class TokenModule {

    @Provides
    ITokenRepository provideTokenRepository() {
        return new TokenRepository();
    }

    @Provides
    ITokenInteractor provideTokenInteractor(ITokenRepository tokenRepository) {
        return new TokenInteractor(tokenRepository);
    }

}
