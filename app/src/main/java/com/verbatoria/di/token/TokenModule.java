package com.verbatoria.di.token;

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
    @TokenScope
    ITokenRepository provideTokenRepository() {
        return new TokenRepository();
    }

}
