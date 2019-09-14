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
import com.verbatoria.domain.questionnaire.model.ReportType
import com.verbatoria.infrastructure.extensions.getDrawableFromRes
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.submit.SubmitActivity

/**
 * @author nikitaremnev
 */

private const val SESSION_ID_EXTRA = "session_id_extra"

interface QuestionnaireView : BaseView {

    fun setNumberQuestionText(position: Int)

    fun setQuestionText(questionTextResourceId: Int)

    fun setNumberAnswer(answer: Int)

    fun setHasNoAnswerForNumber()

    fun setYesOrNoAnswer(isYesAnswer: Boolean)

    fun setHasNoAnswerForYesOrNo()

    fun setReportTypeAnswer(reportType: ReportType)

    fun showNumberAnswers()

    fun showYesOrNoAnswers()

    fun showReportTypeAnswers()

    fun showBackButton()

    fun hideBackButton()

    fun showNextButton()

    fun hideNextButton()

    fun showFinishButton()

    fun hideFinishButton()

    fun openSubmit(sessionId: String)

    fun close()

    interface Callback {

        fun onNumberAnswerClicked(answer: QuestionAnswer)

        fun onYesButtonClicked()

        fun onNoButtonClicked()

        fun onReportTypeSelected(reportType: ReportType)

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
            sessionId: String
        ): Intent =
            Intent(context, QuestionnaireActivity::class.java)
                .putExtra(SESSION_ID_EXTRA, sessionId)

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

    private lateinit var selectedYesOrNoAnswerDrawable: Drawable
    private lateinit var notSelectedYesOrNoAnswerDrawable: Drawable

    private lateinit var numberQuestionsArray: Array<String>

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_questionnaire

    override fun buildComponent(injector: Injector, savedState: Bundle?): QuestionnaireComponent =
        injector.plusQuestionnaireComponent()
            .sessionId(intent.getStringExtra(SESSION_ID_EXTRA))
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

        selectedAnswerDrawable = getDrawableFromRes(R.drawable.background_code_button_state_selected)
        notSelectedAnswerDrawable = getDrawableFromRes(R.drawable.background_code_button_state_new)

        selectedYesOrNoAnswerDrawable = getDrawableFromRes(R.drawable.background_code_button_state_selected)
        notSelectedYesOrNoAnswerDrawable = getDrawableFromRes(R.drawable.background_code_button_state_new)

        backButton.setOnClickListener {
            presenter.onBackClicked()
        }
        nextButton.setOnClickListener {
            presenter.onNextClicked()
        }
        finishButton.setOnClickListener {
            presenter.onFinishClicked()
        }

        answer10Button.setOnClickListener {
            presenter.onNumberAnswerClicked(QuestionAnswer.ANSWER_10)
        }
        answer20Button.setOnClickListener {
            presenter.onNumberAnswerClicked(QuestionAnswer.ANSWER_20)
        }
        answer40Button.setOnClickListener {
            presenter.onNumberAnswerClicked(QuestionAnswer.ANSWER_40)
        }
        answer60Button.setOnClickListener {
            presenter.onNumberAnswerClicked(QuestionAnswer.ANSWER_60)
        }
        answer90Button.setOnClickListener {
            presenter.onNumberAnswerClicked(QuestionAnswer.ANSWER_90)
        }

        yesButton.setOnClickListener {
            presenter.onYesButtonClicked()
        }
        noButton.setOnClickListener {
            presenter.onNoButtonClicked()
        }

        radioGroup.setOnCheckedChangeListener { _, checkedId ->
            presenter.onReportTypeSelected(
                when (checkedId) {
                    R.id.type_0 -> ReportType.TYPE_0
                    R.id.type_1 -> ReportType.TYPE_1
                    R.id.type_2 -> ReportType.TYPE_2
                    R.id.type_3 -> ReportType.TYPE_3
                    R.id.type_4 -> ReportType.TYPE_4
                    R.id.type_5 -> ReportType.TYPE_5
                    R.id.type_6 -> ReportType.TYPE_6
                    R.id.type_7 -> ReportType.TYPE_7
                    R.id.type_8 -> ReportType.TYPE_8
                    R.id.type_9 -> ReportType.TYPE_9
                    R.id.type_10 -> ReportType.TYPE_10
                    else -> ReportType.NOT_SELECTED
                }
            )
        }

        numberQuestionsArray = resources.getStringArray(R.array.questionnaire_number_questions)

    }

    //endregion

    //region QuestionnaireActivity

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    //endregion

    //region QuestionnaireView

    override fun setNumberQuestionText(position: Int) {
        questionTextView.text = numberQuestionsArray[position]
    }

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

        dropHasAnswerCheckedChangedListener()
        hasAnswerCheckbox.isChecked = false
        setHasAnswerCheckedChangedListener()
    }

    override fun setHasNoAnswerForNumber() {
        dropNumberAnswersSelection()

        dropHasAnswerCheckedChangedListener()
        hasAnswerCheckbox.isChecked = true
        setHasAnswerCheckedChangedListener()
    }

    override fun setYesOrNoAnswer(isYesAnswer: Boolean) {
        if (isYesAnswer) {
            yesButton.background = selectedYesOrNoAnswerDrawable
            noButton.background = notSelectedYesOrNoAnswerDrawable
        } else {
            yesButton.background = selectedYesOrNoAnswerDrawable
            noButton.background = notSelectedYesOrNoAnswerDrawable
        }
    }

    override fun setHasNoAnswerForYesOrNo() {
        yesButton.background = notSelectedYesOrNoAnswerDrawable
        noButton.background = notSelectedYesOrNoAnswerDrawable
    }

    override fun setReportTypeAnswer(reportType: ReportType) {
        when (reportType) {
            ReportType.NOT_SELECTED -> radioGroup.clearCheck()
            ReportType.TYPE_0 -> radioGroup.check(R.id.type_0)
            ReportType.TYPE_1 -> radioGroup.check(R.id.type_1)
            ReportType.TYPE_2 -> radioGroup.check(R.id.type_2)
            ReportType.TYPE_3 -> radioGroup.check(R.id.type_3)
            ReportType.TYPE_4 -> radioGroup.check(R.id.type_4)
            ReportType.TYPE_5 -> radioGroup.check(R.id.type_5)
            ReportType.TYPE_6 -> radioGroup.check(R.id.type_6)
            ReportType.TYPE_7 -> radioGroup.check(R.id.type_7)
            ReportType.TYPE_8 -> radioGroup.check(R.id.type_8)
            ReportType.TYPE_9 -> radioGroup.check(R.id.type_9)
            ReportType.TYPE_10 -> radioGroup.check(R.id.type_10)
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

    override fun hideNextButton() {
        nextButton.hide()
    }

    override fun showFinishButton() {
        finishButton.show()
    }

    override fun hideFinishButton() {
        nextButton.hide()
    }

    override fun openSubmit(sessionId: String) {
        startActivity(SubmitActivity.createIntent(this, sessionId))
        finish()
    }

    override fun close() {
        finish()
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

    private fun dropHasAnswerCheckedChangedListener() {
        hasAnswerCheckbox.setOnCheckedChangeListener(null)
    }

    private fun setHasAnswerCheckedChangedListener() {
        hasAnswerCheckbox.setOnCheckedChangeListener { _, isChecked ->
            presenter.onHasAnswerCheckedChanged(isChecked)
        }
    }

}
