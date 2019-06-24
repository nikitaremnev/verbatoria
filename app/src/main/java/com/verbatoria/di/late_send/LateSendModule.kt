package com.verbatoria.di.late_send;

import com.verbatoria.business.late_send.LateSendInteractor
import com.verbatoria.business.late_send.models.LateReportModel
import com.verbatoria.business.session.SessionInteractor
import com.verbatoria.data.repositories.late_send.LateSendRepository
import com.verbatoria.presentation.common.Adapter
import com.verbatoria.presentation.common.ItemAdapter
import com.verbatoria.presentation.late_send.LateSendPresenter
import com.verbatoria.presentation.late_send.item.LateReportBinder
import com.verbatoria.presentation.late_send.item.LateReportViewHolderImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class LateSendModule {

    @Provides
    fun provideLateSendRepository(): LateSendRepository =
        LateSendRepository()

    @Provides
    @Reusable
    fun provideLateSendInteractor(lateSendRepository: LateSendRepository): LateSendInteractor =
        LateSendInteractor(lateSendRepository)

    @Provides
    @Reusable
    fun provideLateSendPresenter(
        lateSendInteractor: LateSendInteractor,
        sessionInteractor: SessionInteractor
    ): LateSendPresenter =
        LateSendPresenter(lateSendInteractor, sessionInteractor)

    @Provides
    @Reusable
    fun provideAdapter(
        lateSendPresenter: LateSendPresenter
    ): Adapter =
        Adapter(
            listOf(
                ItemAdapter(
                    { model ->
                        model is LateReportModel
                    },
                    { viewGroup ->
                        LateReportViewHolderImpl(viewGroup, lateSendPresenter)
                    },
                    LateReportBinder()
                )
            )
        )

}
