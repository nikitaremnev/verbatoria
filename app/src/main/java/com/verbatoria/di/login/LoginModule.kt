package com.verbatoria.di.login

import com.verbatoria.business.login.LoginInteractor
import com.verbatoria.business.login.LoginInteractorImpl
import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.ui.login.LoginPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class LoginModule {

    @Provides
    fun provideLoginInteractor(
        authorizationManager: AuthorizationManager
    ): LoginInteractor =
        LoginInteractorImpl(authorizationManager)

    @Provides
    @Reusable
    fun provideLoginPresenter(
        loginInteractor: LoginInteractor
    ): LoginPresenter =
        LoginPresenter(loginInteractor)

}