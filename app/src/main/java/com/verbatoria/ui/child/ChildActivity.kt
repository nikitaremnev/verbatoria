package com.verbatoria.ui.child

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.remnev.verbatoria.R
import com.verbatoria.business.child.Child
import com.verbatoria.di.Injector
import com.verbatoria.di.child.ChildComponent
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.event.EventDetailMode
import java.util.*

/**
 * @author nikitaremnev
 */

private const val EVENT_DETAIL_MODE_EXTRA = "event_detail_mode_extra"
private const val CLIENT_ID_EXTRA = "client_id_extra"

interface ChildView : BaseView {

    fun showSaveProgress()

    fun hideSaveProgress()

    fun setChildName(childName: String)

    fun setEditableMode()

    fun setViewOnlyMode()

    fun setSaveButtonEnabled()

    fun setSaveButtonDisabled()

    fun close(child: Child?)

    fun close()

    interface Callback {

        fun onChildNameChanged(newChildName: String)

        fun onChildAgeSelected(age: Int)

        fun onChildBirthdaySelected(birthday: Date)

        fun onSaveButtonClicked()

        fun onBackPressed()

        fun onNavigationClicked()

    }

}

class ChildActivity : BasePresenterActivity<ChildView, ChildPresenter, ChildActivity, ChildComponent>(), ChildView {

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

    override fun setChildName(childName: String) {
        childNameEditText.setText(childName)
    }

    override fun setEditableMode() {
        childNameEditText.isEnabled = true

        saveButton.show()
    }

    override fun setViewOnlyMode() {
        childNameEditText.isEnabled = false

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

}
