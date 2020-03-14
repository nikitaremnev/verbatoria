package com.verbatoria.di.questionnaire

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.questionnaire.QuestionnaireActivity
import dagger.BindsInstance
import dagger.Subcomponent
import javax.inject.Named

/**
 * @author n.remnev
 */

@Subcomponent(modules = [QuestionnaireModule::class])
interface QuestionnaireComponent : BaseInjector<QuestionnaireActivity> {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun sessionId(@Named("sessionId") sessionId: String): Builder

        @BindsInstance
        fun bluetoothDeviceAddress(@Named("bluetoothDeviceAddress") bluetoothDeviceAddress: String): Builder

        @BindsInstance
        fun childAge(childAge: Int): Builder

        fun build(): QuestionnaireComponent

    }

}