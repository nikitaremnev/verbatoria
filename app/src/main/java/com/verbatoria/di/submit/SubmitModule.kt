package com.verbatoria.di.submit

import com.verbatoria.business.submit.SubmitInteractorImpl
import com.verbatoria.domain.late_send.manager.LateSendManager
import com.verbatoria.domain.submit.SubmitManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.submit.SubmitPresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable
import javax.inject.Named

/**
 * @author n.remnev
 */

@Module
class SubmitModule {

    @Provides
    @Reusable
    fun provideSubmitPresenter(
        @Named("sessionId") sessionId: String,
        @Named("bluetoothDeviceAddress") bluetoothDeviceAddress: String,
        submitManager: SubmitManager,
        lateSendManager: LateSendManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): SubmitPresenter =
        SubmitPresenter(
            sessionId,
            bluetoothDeviceAddress,
            SubmitInteractorImpl(submitManager, lateSendManager, rxSchedulersFactory)
        )

}