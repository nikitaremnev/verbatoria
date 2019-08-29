package com.verbatoria.di.questionnaire

import com.verbatoria.di.BaseInjector
import com.verbatoria.ui.questionnaire.QuestionnaireActivity
import dagger.BindsInstance
import dagger.Subcomponent

/**
 * @author n.remnev
 */

@Subcomponent(modules = [QuestionnaireModule::class])
interface QuestionnaireComponent : BaseInjector<QuestionnaireActivity> {

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        fun eventId(eventId: String): Builder

        fun build(): QuestionnaireComponent

    }

}