package com.verbatoria.ui.questionnaire

import com.verbatoria.business.questionnaire.QuestionnaireInteractor
import com.verbatoria.domain.questionnaire.model.QuestionAnswer
import com.verbatoria.domain.questionnaire.model.Questionnaire
import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

private const val TOTAL_QUESTIONS_COUNT = 10
private const val NUMBER_QUESTIONS_COUNT = 7
private const val FIRST_QUESTION_POSITION = 0

class QuestionnairePresenter(
    private val eventId: String,
    private val questionnaireInteractor: QuestionnaireInteractor
) : BasePresenter<QuestionnaireView>(), QuestionnaireView.Callback {

    private var questionnaire: Questionnaire? = null

    private var currentQuestionPosition: Int = 0

    init {
        getQuestionnaire()
    }

    override fun onAttachView(view: QuestionnaireView) {
        super.onAttachView(view)
        if (currentQuestionPosition == FIRST_QUESTION_POSITION) {
            view.apply {
                showNumberAnswers()

                hideBackButton()
                showNextButton()

                if (questionnaire?.linguisticQuestionAnswer == QuestionAnswer.NO_ANSWER) {
                    view.setNoAnswer()
                } else {
                    view.setNumberAnswer(questionnaire?.linguisticQuestionAnswer?.value ?: QuestionAnswer.NO_ANSWER.value)
                }
            }
        }

    }

    //region QuestionnaireView.Callback

    override fun onNumberAnswerClicked(value: Int) {
        when (value) {
            QuestionAnswer.NO_ANSWER.value -> QuestionAnswer.NO_ANSWER
            QuestionAnswer.ANSWER_10.value -> QuestionAnswer.ANSWER_10
            QuestionAnswer.ANSWER_20.value -> QuestionAnswer.ANSWER_20
            QuestionAnswer.ANSWER_40.value -> QuestionAnswer.ANSWER_40
            QuestionAnswer.ANSWER_60.value -> QuestionAnswer.ANSWER_60
            QuestionAnswer.ANSWER_90.value -> QuestionAnswer.ANSWER_90
        }
    }

    override fun onYesButtonClicked() {

    }

    override fun onNoButtonClicked() {

    }

    override fun onReportTypeSelected(checkedId: Int) {

    }

    override fun onHasAnswerCheckedChanged(isChecked: Boolean) {
        if (isChecked) {
            view?.setNoAnswer()
        }
    }

    override fun onBackClicked() {

    }

    override fun onNextClicked() {

    }

    override fun onBackPressed() {

    }

    override fun onFinishClicked() {

    }

    //endregion

    private fun getQuestionnaire() {
        questionnaireInteractor.getQuestionnaire(eventId)
            .subscribe({ questionnaire ->
                this.questionnaire = questionnaire
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage)
            })
            .let(::addDisposable)
    }

}