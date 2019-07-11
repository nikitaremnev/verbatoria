package com.verbatoria.di.recovery_password

import com.verbatoria.business.recovery_password.RecoveryPasswordInteractor
import com.verbatoria.business.recovery_password.RecoveryPasswordInteractorImpl
import com.verbatoria.domain.authorization.AuthorizationManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.recovery_password.RecoveryPasswordPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class RecoveryPasswordModule {

    @Provides
    fun provideRecoveryPasswordInteractor(
        authorizationManager: AuthorizationManager,
        schedulersFactory: RxSchedulersFactory
    ): RecoveryPasswordInteractor =
        RecoveryPasswordInteractorImpl(authorizationManager, schedulersFactory)

    @Provides
    @Reusable
    fun provideRecoveryPasswordPresenter(
        recoveryPasswordInteractor: RecoveryPasswordInteractor
    ): RecoveryPasswordPresenter =
        RecoveryPasswordPresenter(recoveryPasswordInteractor)

}