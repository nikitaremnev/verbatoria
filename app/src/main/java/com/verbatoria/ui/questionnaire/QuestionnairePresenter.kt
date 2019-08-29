package com.verbatoria.ui.questionnaire

import com.remnev.verbatoria.R
import com.verbatoria.business.questionnaire.QuestionnaireInteractor
import com.verbatoria.domain.questionnaire.model.QuestionAnswer
import com.verbatoria.domain.questionnaire.model.QuestionType
import com.verbatoria.domain.questionnaire.model.QuestionYesOrNoAnswer
import com.verbatoria.domain.questionnaire.model.Questionnaire
import com.verbatoria.ui.base.BasePresenter

/**
 * @author n.remnev
 */

private const val TOTAL_QUESTIONS_COUNT = 10
private const val LAST_NUMBER_QUESTION_INDEX = 6
private const val MEMORY_ATTENTION_QUESTION_INDEX = 7
private const val HOBBY_QUESTION_INDEX = 8
private const val LAST_QUESTION_INDEX = 9

private const val FIRST_QUESTION_POSITION = 0

class QuestionnairePresenter(
    private val eventId: String,
    private val questionnaireInteractor: QuestionnaireInteractor
) : BasePresenter<QuestionnaireView>(), QuestionnaireView.Callback {

    private var questionnaire: Questionnaire = Questionnaire(eventId)

    private var currentQuestionPosition: Int = 0

    init {
        getQuestionnaire()
    }

    override fun onAttachView(view: QuestionnaireView) {
        super.onAttachView(view)
        when {
            currentQuestionPosition <= LAST_NUMBER_QUESTION_INDEX -> {
                setUpNumberQuestion()
                view.showNextButton()
                if (currentQuestionPosition == FIRST_QUESTION_POSITION) {
                    view.hideBackButton()
                }
            }
            currentQuestionPosition == MEMORY_ATTENTION_QUESTION_INDEX -> {
                setUpYesOrNoQuestion(R.string.questionnaire_include_memory_attention)
            }
            currentQuestionPosition == HOBBY_QUESTION_INDEX -> {
                setUpYesOrNoQuestion(R.string.questionnaire_include_hobby)
            }
            else -> {
                view.showFinishButton()
                view.setQuestionText(R.string.questionnaire_report_type_question)
                view.showReportTypeAnswers()
            }
        }
    }

    //region QuestionnaireView.Callback

    override fun onNumberAnswerClicked(answer: QuestionAnswer) {
        view?.setNumberAnswer(answer.value)
        saveNumberAnswerForPosition(answer)
    }

    override fun onYesButtonClicked() {
        view?.setYesOrNoAnswer(true)
        saveYesOrNoAnswerForPosition(QuestionYesOrNoAnswer.ANSWER_YES)
    }

    override fun onNoButtonClicked() {
        view?.setYesOrNoAnswer(false)
        saveYesOrNoAnswerForPosition(QuestionYesOrNoAnswer.ANSWER_NO)
    }

    override fun onReportTypeSelected(checkedId: Int) {

    }

    override fun onHasAnswerCheckedChanged(isChecked: Boolean) {
        if (isChecked) {
            view?.setHasNoAnswer()
            saveNumberAnswerForPosition(QuestionAnswer.NO_ANSWER)
        } else {
            saveNumberAnswerForPosition(QuestionAnswer.NO_ANSWER)
        }
    }

    override fun onBackClicked() {
        when {
            currentQuestionPosition <= LAST_NUMBER_QUESTION_INDEX -> {
                currentQuestionPosition --
                setUpNumberQuestion()
                if (currentQuestionPosition == FIRST_QUESTION_POSITION) {
                    view?.hideBackButton()
                }
            }
            currentQuestionPosition == MEMORY_ATTENTION_QUESTION_INDEX -> {
                currentQuestionPosition --
                setUpNumberQuestion()
            }
            currentQuestionPosition == HOBBY_QUESTION_INDEX -> {
                currentQuestionPosition --
                setUpYesOrNoQuestion(R.string.questionnaire_include_memory_attention)
            }
            currentQuestionPosition == LAST_QUESTION_INDEX -> {
                currentQuestionPosition --
                setUpYesOrNoQuestion(R.string.questionnaire_include_hobby)
                view?.hideFinishButton()
            }
        }
    }

    override fun onNextClicked() {
        when {
            currentQuestionPosition < LAST_NUMBER_QUESTION_INDEX -> {
                currentQuestionPosition ++
                setUpNumberQuestion()
            }
            currentQuestionPosition == LAST_NUMBER_QUESTION_INDEX -> {
                currentQuestionPosition ++
                setUpYesOrNoQuestion(R.string.questionnaire_include_memory_attention)
            }
            currentQuestionPosition == MEMORY_ATTENTION_QUESTION_INDEX -> {
                currentQuestionPosition ++
                setUpYesOrNoQuestion(R.string.questionnaire_include_hobby)
            }
            currentQuestionPosition == HOBBY_QUESTION_INDEX -> {
                view?.showFinishButton()
                view?.setQuestionText(R.string.questionnaire_report_type_question)
                view?.showReportTypeAnswers()
            }
        }
    }

    override fun onBackPressed() {
        //empty
    }

    override fun onFinishClicked() {
        saveQuestionnaire()
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

    private fun saveQuestionnaire() {
        questionnaireInteractor.saveQuestionnaire(questionnaire)
            .subscribe({
                view?.close()
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

    private fun setUpNumberQuestion() {
        view?.apply {
            setNumberQuestionText(currentQuestionPosition)
            showNumberAnswers()

            val currentAnswer = getCurrentNumberQuestionAnswer()
            if (currentAnswer == QuestionAnswer.NO_ANSWER) {
                setHasNoAnswer()
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


}