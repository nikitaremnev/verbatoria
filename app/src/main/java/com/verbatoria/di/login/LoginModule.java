package com.verbatoria.di.login;

import com.verbatoria.business.login.ILoginInteractor;
import com.verbatoria.business.login.LoginInteractor;
import com.verbatoria.data.repositories.login.ILoginRepository;
import com.verbatoria.data.repositories.login.LoginRepository;
import com.verbatoria.presentation.login.presenter.ILoginPresenter;
import com.verbatoria.presentation.login.presenter.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by nikitaremnev on 30.05.17.
 */
@Module
public class LoginModule {

    @Provides
    @LoginScope
    ILoginRepository provideLoginRepository() {
        return new LoginRepository();
    }

    @Provides
    @LoginScope
    ILoginInteractor provideLoginInteractor(ILoginRepository loginRepository) {
        return new LoginInteractor(loginRepository);
    }

//    @Provides
//    @LoginScope
//    ProfilePresenterCache provideLoginPresenterCache() {
//        return new ProfilePresenterCache();
//    }

    @Provides
    @LoginScope
    ILoginPresenter provideLoginPresenter(ILoginInteractor loginInteractor) {
        return new LoginPresenter(loginInteractor);
    }

}
