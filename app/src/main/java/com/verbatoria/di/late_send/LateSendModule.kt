package com.verbatoria.di.late_send;

import com.remnev.verbatoria.R
import com.verbatoria.business.late_send.LateSendInteractor
import com.verbatoria.business.late_send.LateReportModel
import com.verbatoria.business.late_send.LateSendItemModel
import com.verbatoria.data.repositories.late_send.LateSendRepository
import com.verbatoria.infrastructure.utils.ViewInflater
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
        lateSendInteractor: LateSendInteractor
    ): LateSendPresenter =
        LateSendPresenter(lateSendInteractor)

    @Provides
    @Reusable
    fun provideAdapter(
        lateSendPresenter: LateSendPresenter
    ): Adapter =
        Adapter(
            listOf(
                ItemAdapter(
                    {
                        it is LateSendItemModel
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
