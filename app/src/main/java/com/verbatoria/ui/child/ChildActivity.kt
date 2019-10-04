package com.verbatoria.ui.child

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import com.remnev.verbatoria.R
import com.verbatoria.domain.child.model.Child
import com.verbatoria.di.Injector
import com.verbatoria.di.child.ChildComponent
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.event.EventDetailMode

/**
 * @author nikitaremnev
 */

private const val AGE_SELECTION_DIALOG_TAG = "AGE_SELECTION_DIALOG_TAG"

private const val EVENT_DETAIL_MODE_EXTRA = "event_detail_mode_extra"
private const val CLIENT_ID_EXTRA = "client_id_extra"

private const val MALE_GENDER_POSITION = 0
private const val FEMALE_GENDER_POSITION = 1

interface ChildView : BaseView {

    fun showSaveProgress()

    fun hideSaveProgress()

    fun showAgeSelectionDialog(isSchoolMode: Boolean)

    fun setChildName(childName: String)

    fun setChildGender(childGender: Int)

    fun setChildAge(childAge: Int)

    fun setEditableMode()

    fun setViewOnlyMode()

    fun setSaveButtonEnabled()

    fun setSaveButtonDisabled()

    fun close(child: Child?)

    fun close()

    interface Callback {

        fun onChildNameChanged(newChildName: String)

        fun onChildGenderSelected(newChildGender: Int)

        fun onChildAgeSelected(newChildAge: Int)

        fun onSaveButtonClicked()

        fun onChildAgeClicked()

        fun onBackPressed()

        fun onNavigationClicked()

    }

}

class ChildActivity : BasePresenterActivity<ChildView, ChildPresenter, ChildActivity, ChildComponent>(), ChildView, ChildAgeSelectionBottomSheetDialog.ChildAgeSelectionListener {

    companion object {

        const val CHILD_EXTRA = "child_extra"

        fun createIntent(
            context: Context,
            eventDetailMode: EventDetailMode,
            child: Child?,
            clientId: String
        ): Intent =
            Intent(context, ChildActivity::class.java)
                .putExtra(EVENT_DETAIL_MODE_EXTRA, eventDetailMode.ordinal)
                .putExtra(CHILD_EXTRA, child)
                .putExtra(CLIENT_ID_EXTRA, clientId)

    }

    private lateinit var toolbar: Toolbar
    private lateinit var childNameEditText: EditText
    private lateinit var childGenderRadioGroup: RadioGroup
    private lateinit var childAgeTextView: TextView
    private lateinit var saveButton: Button
    private lateinit var progressBar: ProgressBar

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_child_new

    override fun buildComponent(injector: Injector, savedState: Bundle?): ChildComponent =
        injector.plusChildComponent()
            .eventDetailMode(intent.getIntExtra(EVENT_DETAIL_MODE_EXTRA, EventDetailMode.CREATE_NEW.ordinal))
            .child(intent.getParcelableExtra(CHILD_EXTRA))
            .clientId(intent.getStringExtra(CLIENT_ID_EXTRA))
            .build()

    override fun initViews(savedState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)

        childNameEditText = findViewById(R.id.child_name_edit_text)
        childGenderRadioGroup = findViewById(R.id.child_gender_radio_group)
        childAgeTextView = findViewById(R.id.child_age_text_view)

        saveButton = findViewById(R.id.save_button)
        progressBar = findViewById(R.id.progress_bar)

        toolbar.title = getString(R.string.child)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            presenter.onNavigationClicked()
        }

        childNameEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                presenter.onChildNameChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //empty
            }

        })

        childGenderRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == R.id.child_gender_male_radio_button) {
                presenter.onChildGenderSelected(MALE_GENDER_POSITION)
            } else if (checkedId == R.id.child_gender_female_radio_button) {
                presenter.onChildGenderSelected(FEMALE_GENDER_POSITION)
            }
        }

        childAgeTextView.setOnClickListener {
            presenter.onChildAgeClicked()
        }

        saveButton.setOnClickListener {
            presenter.onSaveButtonClicked()
        }
    }

    //endregion

    //region BasePresenterActivity

    override fun onBackPressed() {
        presenter.onBackPressed()
    }

    //endregion

    //region com.verbatoria.business.child.ChildView

    override fun showSaveProgress() {
        saveButton.isEnabled = false
        progressBar.show()
    }

    override fun hideSaveProgress() {
        saveButton.isEnabled = true
        progressBar.hide()
    }

    override fun showAgeSelectionDialog(isSchoolMode: Boolean) {
        ChildAgeSelectionBottomSheetDialog.build {
           this.isSchoolMode = isSchoolMode
        }.show(supportFragmentManager, AGE_SELECTION_DIALOG_TAG)
    }

    override fun setChildName(childName: String) {
        childNameEditText.setText(childName)
    }

    override fun setChildGender(childGender: Int) {
        if (childGender == MALE_GENDER_POSITION) {
            childGenderRadioGroup.check(R.id.child_gender_male_radio_button)
        } else if (childGender == FEMALE_GENDER_POSITION) {
            childGenderRadioGroup.check(R.id.child_gender_female_radio_button)
        }
    }

    override fun setChildAge(childAge: Int) {
        childAgeTextView.text = childAge.toString()
    }

    override fun setEditableMode() {
        childNameEditText.isEnabled = true
        saveButton.show()
    }

    override fun setViewOnlyMode() {
        childNameEditText.isEnabled = false
        childGenderRadioGroup.isEnabled = false
        findViewById<RadioButton>(R.id.child_gender_female_radio_button).isEnabled = false
        findViewById<RadioButton>(R.id.child_gender_male_radio_button).isEnabled = false
        saveButton.hide()
    }

    override fun setSaveButtonEnabled() {
        saveButton.isEnabled = true
    }

    override fun setSaveButtonDisabled() {
        saveButton.isEnabled = false
    }

    override fun close(child: Child?) {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(CHILD_EXTRA, child)
        )
        finish()
    }

    override fun close() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    //endregion

    //region ChildAgeSelectionBottomSheetDialog.ChildAgeSelectionListener

    override fun onChildAgeSelected(tag: String?, age: Int) {
        presenter.onChildAgeSelected(age)
    }

    //endregion

}
