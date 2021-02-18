package com.verbatoria.ui.questionnaire

import com.remnev.verbatoria.R
import com.verbatoria.business.questionnaire.QuestionnaireInteractor
import com.verbatoria.domain.questionnaire.model.*
import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

private const val LAST_NUMBER_QUESTION_INDEX = 4
private const val MEMORY_ATTENTION_QUESTION_INDEX = 5
private const val HOBBY_QUESTION_INDEX = 6
private const val LAST_QUESTION_INDEX = 7

private const val FIRST_QUESTION_POSITION = 0

class QuestionnairePresenter(
    private val sessionId: String,
    private val bluetoothDeviceAddress: String,
    private val childAge: Int,
    private val questionnaireInteractor: QuestionnaireInteractor
) : BasePresenter<QuestionnaireView>(), QuestionnaireView.Callback {

    private var questionnaire: Questionnaire = Questionnaire(sessionId, bluetoothDeviceAddress)

    private var isQuestionnaireLoaded: Boolean = false

    private var isAgeAllowedForHobby: Boolean = false

    private var isShortQuestionnaire: Boolean = false

    private var currentQuestionPosition: Int = 0

    init {
        getQuestionnaire()
        getIsShortQuestionnaire()
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
        if (!isShortQuestionnaire || currentQuestionPosition != LAST_NUMBER_QUESTION_INDEX) {
            onNextClicked()
        }
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
        if (currentQuestionPosition == HOBBY_QUESTION_INDEX && !isAgeAllowedForHobby) {
            currentQuestionPosition --
        }
        setUpCurrentQuestionnaireState()
    }

    override fun onNextClicked() {
        currentQuestionPosition ++
        if (currentQuestionPosition == HOBBY_QUESTION_INDEX && !isAgeAllowedForHobby) {
            currentQuestionPosition ++
        }
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
        questionnaireInteractor.getQuestionnaire(sessionId, bluetoothDeviceAddress, childAge)
            .subscribe({ (questionnaire, isAgeAllowedForHobby) ->
                this.questionnaire = questionnaire
                isQuestionnaireLoaded = true
                this.isAgeAllowedForHobby = isAgeAllowedForHobby
                setUpCurrentQuestionnaireState()
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage ?: "Get questionnaire error occurred")
            })
            .let(::addDisposable)
    }

    private fun saveQuestionnaire() {
        questionnaireInteractor.saveQuestionnaire(sessionId, questionnaire)
            .subscribe({
                view?.openSubmit(sessionId, bluetoothDeviceAddress)
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage ?: "Save questionnaire error occurred")
            })
            .let(::addDisposable)
    }

    private fun getIsShortQuestionnaire() {
        questionnaireInteractor.isShortQuestionnaire()
            .subscribe({
                this.isShortQuestionnaire = it
            }, { error ->
                view?.showErrorSnackbar(error.localizedMessage ?: "Get is short questionnaire error occurred")
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
        }
    }

    private fun saveYesOrNoAnswerForPosition(answer: QuestionYesOrNoAnswer) {
        when (currentQuestionPosition) {
            MEMORY_ATTENTION_QUESTION_INDEX -> {
                questionnaire.includeAttentionMemory = answer
            }
            HOBBY_QUESTION_INDEX -> {
                questionnaire.includeHobby = answer
            }
        }
    }

    private fun getCurrentNumberQuestionAnswer(): QuestionAnswer =
        when (currentQuestionPosition) {
            QuestionType.LINGUISTIC.ordinal -> questionnaire.linguisticQuestionAnswer
            QuestionType.LOGIC_MATHEMATICAL.ordinal -> questionnaire.logicMathematicalAnswer
            QuestionType.MUSIC.ordinal -> questionnaire.musicAnswer
            QuestionType.SPATIAL.ordinal -> questionnaire.spatialAnswer
            QuestionType.BODY_KINESTHETIC.ordinal -> questionnaire.bodyKinestheticAnswer
            else -> QuestionAnswer.NO_ANSWER
        }

    private fun getCurrentYesOrNoQuestionAnswer(): QuestionYesOrNoAnswer =
        when (currentQuestionPosition) {
            MEMORY_ATTENTION_QUESTION_INDEX -> {
                questionnaire.includeAttentionMemory
            }
            HOBBY_QUESTION_INDEX -> {
                questionnaire.includeHobby
            }
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



            in (FIRST_QUESTION_POSITION + 1 until LAST_QUESTION_INDEX) -> {
                if (currentQuestionPosition == LAST_NUMBER_QUESTION_INDEX && isShortQuestionnaire) {
                    setUpLastQuestionButtonsState()
                } else {
                    view?.apply {
                        showBackButton()
                        hideFinishButton()
                        showNextButton()
                    }
                }
            }

            LAST_QUESTION_INDEX ->
                setUpLastQuestionButtonsState()
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

    private fun setUpLastQuestionButtonsState() {
        view?.apply {
            showBackButton()
            hideNextButton()
            showFinishButton()
        }
    }

}