package com.verbatoria.ui.questionnaire

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.questionnaire.QuestionnaireComponent
import com.verbatoria.domain.questionnaire.model.QuestionAnswer
import com.verbatoria.infrastructure.extensions.getDrawableFromRes
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView

/**
 * @author nikitaremnev
 */

private const val EVENT_ID_EXTRA = "event_id_extra"

interface QuestionnaireView : BaseView {

    fun setQuestionText(questionTextResourceId: Int)

    fun setNumberAnswer(answer: Int)

    fun setNoAnswer()

    fun setYesOrNoAnswer(isYesAnswer: Boolean)

    fun showNumberAnswers()

    fun showYesOrNoAnswers()

    fun showReportTypeAnswers()

    fun showBackButton()

    fun hideBackButton()

    fun showNextButton()

    fun showFinishButton()

    fun hideFinishButton()

    fun close()

    interface Callback {

        fun onNumberAnswerClicked(value: Int)

        fun onYesButtonClicked()

        fun onNoButtonClicked()

        fun onReportTypeSelected(checkedId: Int)

        fun onHasAnswerCheckedChanged(isChecked: Boolean)

        fun onBackClicked()

        fun onNextClicked()

        fun onBackPressed()

        fun onFinishClicked()

    }

}

class QuestionnaireActivity : BasePresenterActivity<QuestionnaireView, QuestionnairePresenter, QuestionnaireActivity, QuestionnaireComponent>(),
    QuestionnaireView {

    companion object {

        fun createIntent(
            context: Context,
            eventId: String
        ): Intent =
            Intent(context, QuestionnaireActivity::class.java)
                .putExtra(EVENT_ID_EXTRA, eventId)

    }

    private lateinit var questionTextView: TextView

    private lateinit var answer10Button: Button
    private lateinit var answer20Button: Button
    private lateinit var answer40Button: Button
    private lateinit var answer60Button: Button
    private lateinit var answer90Button: Button
    private lateinit var hasAnswerCheckbox: CheckBox

    private lateinit var yesButton: Button
    private lateinit var noButton: Button

    private lateinit var radioGroup: RadioGroup
    private lateinit var reportTypeContainer: View

    private lateinit var backButton: Button
    private lateinit var nextButton: Button
    private lateinit var finishButton: Button

    private lateinit var selectedAnswerDrawable: Drawable
    private lateinit var notSelectedAnswerDrawable: Drawable

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_questionnaire

    override fun buildComponent(injector: Injector, savedState: Bundle?): QuestionnaireComponent =
        injector.plusQuestionnaireComponent()
            .eventId(intent.getStringExtra(EVENT_ID_EXTRA))
            .build()

    override fun initViews(savedState: Bundle?) {
        questionTextView = findViewById(R.id.question_text_view)
        answer10Button = findViewById(R.id.answer_10_button)
        answer20Button = findViewById(R.id.answer_20_button)
        answer40Button = findViewById(R.id.answer_40_button)
        answer60Button = findViewById(R.id.answer_60_button)
        answer90Button = findViewById(R.id.answer_90_button)
        hasAnswerCheckbox = findViewById(R.id.has_answer_checkbox)
        yesButton = findViewById(R.id.yes_button)
        noButton = findViewById(R.id.no_button)
        radioGroup = findViewById(R.id.radio_group)
        reportTypeContainer = findViewById(R.id.report_type_question_container)
        backButton = findViewById(R.id.back_button)
        nextButton = findViewById(R.id.next_button)
        finishButton = findViewById(R.id.finish_button)

        selectedAnswerDrawable = getDrawableFromRes(R.drawable.background_button_selected)
        notSelectedAnswerDrawable = getDrawableFromRes(R.drawable.background_button_unselected)

        backButton.setOnClickListener {
            presenter.onBackClicked()
        }
        nextButton.setOnClickListener {
            presenter.onNextClicked()
        }
        finishButton.setOnClickListener {
            presenter.onFinishClicked()
        }

        hasAnswerCheckbox.setOnCheckedChangeListener { _, isChecked ->
            presenter.onHasAnswerCheckedChanged(isChecked)
        }

        answer10Button.setOnClickListener {
            presenter.onNumberAnswerClicked(QuestionAnswer.ANSWER_10.value)
        }
        answer20Button.setOnClickListener {
            presenter.onNumberAnswerClicked(QuestionAnswer.ANSWER_20.value)
        }
        answer40Button.setOnClickListener {
            presenter.onNumberAnswerClicked(QuestionAnswer.ANSWER_40.value)
        }
        answer60Button.setOnClickListener {
            presenter.onNumberAnswerClicked(QuestionAnswer.ANSWER_60.value)
        }
        answer90Button.setOnClickListener {
            presenter.onNumberAnswerClicked(QuestionAnswer.ANSWER_90.value)
        }

        yesButton.setOnClickListener {
            presenter.onYesButtonClicked()
        }
        noButton.setOnClickListener {
            presenter.onNoButtonClicked()
        }

        radioGroup.setOnCheckedChangeListener { group, checkedId ->
            presenter.onReportTypeSelected(checkedId)
        }

    }

    //endregion

    //region QuestionnaireActivity

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    //endregion

    //region QuestionnaireView

    override fun setQuestionText(questionTextResourceId: Int) {
        questionTextView.setText(questionTextResourceId)
    }

    override fun setNumberAnswer(answer: Int) {
        dropNumberAnswersSelection()

        when (answer) {
            QuestionAnswer.ANSWER_10.value -> answer10Button.background = selectedAnswerDrawable
            QuestionAnswer.ANSWER_20.value -> answer20Button.background = selectedAnswerDrawable
            QuestionAnswer.ANSWER_40.value -> answer40Button.background = selectedAnswerDrawable
            QuestionAnswer.ANSWER_60.value -> answer60Button.background = selectedAnswerDrawable
            QuestionAnswer.ANSWER_90.value -> answer90Button.background = selectedAnswerDrawable
        }

        hasAnswerCheckbox.isChecked = false
    }

    override fun setNoAnswer() {
        dropNumberAnswersSelection()

        hasAnswerCheckbox.isChecked = true
    }

    override fun setYesOrNoAnswer(isYesAnswer: Boolean) {
        if (isYesAnswer) {
            yesButton.background = selectedAnswerDrawable
            noButton.background = notSelectedAnswerDrawable
        } else {
            yesButton.background = notSelectedAnswerDrawable
            noButton.background = selectedAnswerDrawable
        }
    }

    override fun showNumberAnswers() {
        hideYesOrNoAnswers()
        hideReportTypeAnswersAnswers()

        answer10Button.show()
        answer20Button.show()
        answer40Button.show()
        answer60Button.show()
        answer90Button.show()
        hasAnswerCheckbox.show()
    }

    override fun showYesOrNoAnswers() {
        hideReportTypeAnswersAnswers()
        hideNumberAnswers()

        yesButton.show()
        noButton.show()
    }

    override fun showReportTypeAnswers() {
        hideYesOrNoAnswers()
        hideNumberAnswers()

        reportTypeContainer.show()
    }

    override fun showBackButton() {
        backButton.show()
    }

    override fun hideBackButton() {
        backButton.hide()
    }

    override fun showNextButton() {
        nextButton.show()
    }

    override fun showFinishButton() {
        nextButton.hide()
        finishButton.show()
    }

    override fun hideFinishButton() {
        nextButton.hide()
    }

    override fun close() {

    }

    //endregion

    private fun dropNumberAnswersSelection() {
        answer10Button.background = notSelectedAnswerDrawable
        answer20Button.background = notSelectedAnswerDrawable
        answer40Button.background = notSelectedAnswerDrawable
        answer60Button.background = notSelectedAnswerDrawable
        answer90Button.background = notSelectedAnswerDrawable
    }

    private fun hideNumberAnswers() {
        answer10Button.hide()
        answer20Button.hide()
        answer40Button.hide()
        answer60Button.hide()
        answer90Button.hide()
        hasAnswerCheckbox.hide()
    }

    private fun hideYesOrNoAnswers() {
        yesButton.hide()
        noButton.hide()
    }

    private fun hideReportTypeAnswersAnswers() {
        reportTypeContainer.hide()
    }


}
