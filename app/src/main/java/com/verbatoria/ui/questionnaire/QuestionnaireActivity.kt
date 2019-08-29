package com.verbatoria.ui.questionnaire

import android.os.Bundle
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.questionnaire.QuestionnaireComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView

/**
 * @author nikitaremnev
 */

interface QuestionnaireView : BaseView {


    interface Callback {



    }

}

class QuestionnaireActivity : BasePresenterActivity<QuestionnaireView, QuestionnairePresenter, QuestionnaireActivity, QuestionnaireComponent>(),
    QuestionnaireView {

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_submit

    override fun buildComponent(injector: Injector, savedState: Bundle?): QuestionnaireComponent =
        injector.plusQuestionnaireComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        //empty
    }

    //endregion

    //region WritingView



    //endregion

}
