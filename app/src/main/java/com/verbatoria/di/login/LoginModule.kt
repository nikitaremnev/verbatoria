package com.verbatoria.di.login

import com.verbatoria.business.login.LoginInteractor
import com.verbatoria.business.login.LoginInteractorImpl
import com.verbatoria.data.repositories.login.ILoginRepository
import com.verbatoria.data.repositories.login.LoginRepository
import com.verbatoria.data.repositories.token.ITokenRepository
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
    fun provideLoginRepository(): ILoginRepository =
        LoginRepository()

    @Provides
    fun provideLoginInteractor(
        loginRepository: ILoginRepository,
        tokenRepository: ITokenRepository
    ): LoginInteractor =
        LoginInteractorImpl(tokenRepository, loginRepository)

    @Provides
    @Reusable
    fun provideLoginPresenter(
        loginInteractor: LoginInteractor
    ): LoginPresenter =
        LoginPresenter(loginInteractor)

}