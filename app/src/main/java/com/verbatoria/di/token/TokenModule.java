package com.verbatoria.di.token;

import com.verbatoria.business.token.interactor.ITokenInteractor;
import com.verbatoria.business.token.interactor.TokenInteractor;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.data.repositories.token.TokenRepository;

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
