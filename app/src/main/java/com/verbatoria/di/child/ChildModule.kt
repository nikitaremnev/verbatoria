package com.verbatoria.di.child

import com.verbatoria.business.child.Child
import com.verbatoria.business.child.ChildInteractorImpl
import com.verbatoria.domain.child.ChildManager
import com.verbatoria.infrastructure.rx.RxSchedulersFactory
import com.verbatoria.ui.child.ChildPresenter
import com.verbatoria.ui.event.EventDetailMode
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class ChildModule {

    @Provides
    @Reusable
    fun provideChildPresenter(
        eventDetailModeOrdinal: Int,
        child: Child?,
        clientId: String,
        childManager: ChildManager,
        rxSchedulersFactory: RxSchedulersFactory
    ): ChildPresenter =
        ChildPresenter(
            EventDetailMode.valueOf(eventDetailModeOrdinal),
            child ?: Child(),
            clientId,
            ChildInteractorImpl(
                childManager,
                rxSchedulersFactory
            )
        )

}