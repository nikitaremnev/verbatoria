package com.verbatoria.di.questionnaire

import com.verbatoria.ui.questionnaire.QuestionnairePresenter
import dagger.Module
import dagger.Provides
import dagger.Reusable

/**
 * @author n.remnev
 */

@Module
class QuestionnaireModule {

    @Provides
    @Reusable
    fun provideQuestionnairePresenter(): QuestionnairePresenter =
        QuestionnairePresenter()

}