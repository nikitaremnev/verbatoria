package com.verbatoria.ui.questionnaire

import com.remnev.verbatoria.R
import com.verbatoria.business.questionnaire.QuestionnaireInteractor
import com.verbatoria.domain.questionnaire.model.*
import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

private const val LAST_NUMBER_QUESTION_INDEX = 6
private const val MEMORY_ATTENTION_QUESTION_INDEX = 7
private const val HOBBY_QUESTION_INDEX = 8
private const val LAST_QUESTION_INDEX = 9

private const val FIRST_QUESTION_POSITION = 0

class QuestionnairePresenter(
    private val sessionId: String,
    private val questionnaireInteractor: QuestionnaireInteractor
) : BasePresenter<QuestionnaireView>(), QuestionnaireView.Callback {

    private var questionnaire: Questionnaire = Questionnaire(sessionId)

    private var isQuestionnaireLoaded: Boolean = false

    private var currentQuestionPosition: Int = 0

    init {
        getQuestionnaire()
    }

    override fun onAttachView(view: QuestionnaireView) {
        super.onAttachView(view)
        if (isQuestionnaireLoaded) {
            setUpCurrentQuestionnaireState()
        }
    }

    //region QuestionnaireView.Callback

    override fun onNumberAnswerClicked(answer: QuestionAnswer) {
        view?.setNumberAnswer(answer.value)
        saveNumberAnswerForPosition(answer)
        onNextClicked()
    }

    override fun onYesButtonClicked() {
        view?.setYesOrNoAnswer(true)
        saveYesOrNoAnswerForPosition(QuestionYesOrNoAnswer.ANSWER_YES)
        onNextClicked()
    }

    override fun onNoButtonClicked() {
        view?.setYesOrNoAnswer(false)
        saveYesOrNoAnswerForPosition(QuestionYesOrNoAnswer.ANSWER_NO)
        onNextClicked()
    }

    override fun onReportTypeSelected(reportType: ReportType) {
        questionnaire.reportType = reportType
    }

    override fun onHasAnswerCheckedChanged(isChecked: Boolean) {
        if (isChecked) {
            view?.setHasNoAnswerForNumber()
        }
        saveNumberAnswerForPosition(QuestionAnswer.NO_ANSWER)
    }

    override fun onBackClicked() {
        currentQuestionPosition --
        setUpCurrentQuestionnaireState()
    }

    override fun onNextClicked() {
        currentQuestionPosition ++
        setUpCurrentQuestionnaireState()
    }

    override fun onBackPressed() {
        //empty
    }

    override fun onFinishClicked() {
        saveQuestionnaire()
    }

    //endregion

    private fun getQuestionnaire() {
        questionnaireInteractor.getQuestionnaire(sessionId)
            .subscribe({ questionnaire ->
                this.questionnaire = questionnaire
                isQuestionnaireLoaded = true
                setUpCurrentQuestionnaireState()
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage)
            })
            .let(::addDisposable)
    }

    private fun saveQuestionnaire() {
        questionnaireInteractor.saveQuestionnaire(questionnaire)
            .subscribe({
                view?.openSubmit(sessionId)
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage)
            })
            .let(::addDisposable)
    }

    private fun saveNumberAnswerForPosition(answer: QuestionAnswer) {
        when (currentQuestionPosition) {
            QuestionType.LINGUISTIC.ordinal -> questionnaire.linguisticQuestionAnswer = answer
            QuestionType.LOGIC_MATHEMATICAL.ordinal -> questionnaire.logicMathematicalAnswer = answer
            QuestionType.MUSIC.ordinal -> questionnaire.musicAnswer = answer
            QuestionType.SPATIAL.ordinal -> questionnaire.spatialAnswer = answer
            QuestionType.BODY_KINESTHETIC.ordinal -> questionnaire.bodyKinestheticAnswer = answer
            QuestionType.UNDERSTANDING_PEOPLE.ordinal -> questionnaire.understandingPeopleAnswer = answer
            QuestionType.UNDERSTANDING_YOURSELF.ordinal -> questionnaire.understandingYourselfAnswer = answer
        }
    }

    private fun saveYesOrNoAnswerForPosition(answer: QuestionYesOrNoAnswer) {
        when (currentQuestionPosition) {
            MEMORY_ATTENTION_QUESTION_INDEX -> questionnaire.includeAttentionMemory = answer
            HOBBY_QUESTION_INDEX -> questionnaire.includeHobby = answer
        }
    }

    private fun getCurrentNumberQuestionAnswer(): QuestionAnswer =
        when (currentQuestionPosition) {
            QuestionType.LINGUISTIC.ordinal -> questionnaire.linguisticQuestionAnswer
            QuestionType.LOGIC_MATHEMATICAL.ordinal -> questionnaire.logicMathematicalAnswer
            QuestionType.MUSIC.ordinal -> questionnaire.musicAnswer
            QuestionType.SPATIAL.ordinal -> questionnaire.spatialAnswer
            QuestionType.BODY_KINESTHETIC.ordinal -> questionnaire.bodyKinestheticAnswer
            QuestionType.UNDERSTANDING_PEOPLE.ordinal -> questionnaire.understandingPeopleAnswer
            QuestionType.UNDERSTANDING_YOURSELF.ordinal -> questionnaire.understandingYourselfAnswer
            else -> QuestionAnswer.NO_ANSWER
        }

    private fun getCurrentYesOrNoQuestionAnswer(): QuestionYesOrNoAnswer =
        when (currentQuestionPosition) {
            MEMORY_ATTENTION_QUESTION_INDEX -> questionnaire.includeAttentionMemory
            HOBBY_QUESTION_INDEX -> questionnaire.includeHobby
            else -> QuestionYesOrNoAnswer.NO_ANSWER
        }

    private fun setUpCurrentQuestionnaireState() {
        //question state
        when {

            currentQuestionPosition <= LAST_NUMBER_QUESTION_INDEX -> {
                setUpNumberQuestion()
            }

            currentQuestionPosition == MEMORY_ATTENTION_QUESTION_INDEX -> {
                setUpYesOrNoQuestion(R.string.questionnaire_include_memory_attention)
            }

            currentQuestionPosition == HOBBY_QUESTION_INDEX -> {
                setUpYesOrNoQuestion(R.string.questionnaire_include_hobby)
            }

            else -> {
                setUpReportTypeQuestion()
            }
        }

        //buttons state
        when (currentQuestionPosition) {

            FIRST_QUESTION_POSITION ->
                view?.apply {
                    hideBackButton()
                    showNextButton()
                }

            in (FIRST_QUESTION_POSITION + 1 until LAST_QUESTION_INDEX) ->
                view?.apply {
                    showBackButton()
                    hideFinishButton()
                    showNextButton()
                }

            LAST_QUESTION_INDEX ->
                view?.apply {
                    showBackButton()
                    hideNextButton()
                    showFinishButton()
                }
        }

    }


    private fun setUpNumberQuestion() {
        view?.apply {
            setNumberQuestionText(currentQuestionPosition)
            showNumberAnswers()

            val currentAnswer = getCurrentNumberQuestionAnswer()
            if (currentAnswer == QuestionAnswer.NO_ANSWER) {
                setHasNoAnswerForNumber()
            } else {
                setNumberAnswer(currentAnswer.value)
            }
        }
    }

    private fun setUpYesOrNoQuestion(questionTextResourceId: Int) {
        view?.apply {
            setQuestionText(questionTextResourceId)
            showYesOrNoAnswers()

            val currentAnswer = getCurrentYesOrNoQuestionAnswer()
            if (currentAnswer == QuestionYesOrNoAnswer.NO_ANSWER) {
                setHasNoAnswerForYesOrNo()
            } else {
                setYesOrNoAnswer(currentAnswer == QuestionYesOrNoAnswer.ANSWER_YES)
            }
        }
    }

    private fun setUpReportTypeQuestion() {
        view?.apply {
            setQuestionText(R.string.questionnaire_report_type_question)
            showReportTypeAnswers()
            setReportTypeAnswer(questionnaire.reportType)
        }
    }

}