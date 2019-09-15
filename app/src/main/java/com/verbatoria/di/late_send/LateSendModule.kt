package com.verbatoria.di.late_send;

import com.remnev.verbatoria.R
import com.verbatoria.business.late_send.LateSendInteractorImpl
import com.verbatoria.domain.late_send.manager.LateSendManager
import com.verbatoria.domain.late_send.model.LateSend
import com.verbatoria.domain.submit.SubmitManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.infrastructure.utils.ViewInflater
import com.verbatoria.ui.common.Adapter
import com.verbatoria.ui.common.ItemAdapter
import com.verbatoria.ui.late_send.LateSendPresenter
import com.verbatoria.ui.late_send.item.LateReportBinder
import com.verbatoria.ui.late_send.item.LateReportViewHolderImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class LateSendModule {

    @Provides
    @Reusable
    fun provideLateSendPresenter(
        submitManager: SubmitManager,
        lateSendManager: LateSendManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): LateSendPresenter =
        LateSendPresenter(LateSendInteractorImpl(submitManager, lateSendManager, rxSchedulersFactory))

    @Provides
    @Reusable
    fun provideAdapter(
        lateSendPresenter: LateSendPresenter
    ): Adapter =
        Adapter(
            listOf(
                ItemAdapter(
                    {
                        it is LateSend
                    },
                    {
                        LateReportViewHolderImpl(
                            ViewInflater.inflate(
                                R.layout.item_late_send,
                                it
                            ), lateSendPresenter
                        )
                    },
                    LateReportBinder()
                )
            )
        )

}
