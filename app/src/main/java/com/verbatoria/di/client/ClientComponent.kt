package com.verbatoria.di.client

import com.verbatoria.business.client.Client
import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.client.ClientActivity
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [ClientModule::class])
interface ClientComponent : BaseInjector<ClientActivity> {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun eventDetailMode(eventDetailModeOrdinal: Int): Builder

        @BindsInstance
        fun client(client: Client?): Builder

        fun build(): ClientComponent

    }

}