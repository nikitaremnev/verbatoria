package com.verbatoria.ui.client

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
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.remnev.verbatoria.R
import com.verbatoria.domain.client.model.Client
import com.verbatoria.di.Injector
import com.verbatoria.di.client.ClientComponent
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.event.EventDetailMode
import com.verbatoria.utils.CountryHelper

/**
 * @author nikitaremnev
 */

private const val EVENT_DETAIL_MODE_EXTRA = "event_detail_mode_extra"

interface ClientView : BaseView {

    fun showSaveProgress()

    fun hideSaveProgress()

    fun setClientName(clientName: String)

    fun setClientPhone(clientPhone: String)

    fun setClientEmail(clientEmail: String)

    fun setCurrentCountry(country: String)

    fun setEditableMode()

    fun setViewOnlyMode()

    fun setSaveButtonEnabled()

    fun setSaveButtonDisabled()

    fun showSaveButton()

    fun hideSaveButton()

    fun close(client: Client?)

    fun close()

    interface Callback {

        fun onClientNameChanged(newClientName: String)

        fun onClientPhoneChanged(newClientPhone: String)

        fun onClientEmailChanged(newClientEmail: String)

        fun onSaveButtonClicked()

        fun onBackPressed()

        fun onNavigationClicked()

    }

}

class ClientActivity : BasePresenterActivity<ClientView, ClientPresenter, ClientActivity, ClientComponent>(), ClientView {

    companion object {

        const val CLIENT_EXTRA = "client_extra"

        fun createIntent(
            context: Context,
            eventDetailMode: EventDetailMode,
            client: Client?
        ): Intent =
            Intent(context, ClientActivity::class.java)
                .putExtra(EVENT_DETAIL_MODE_EXTRA, eventDetailMode.ordinal)
                .putExtra(CLIENT_EXTRA, client)

    }

    private lateinit var toolbar: Toolbar
    private lateinit var clientNameEditText: EditText
    private lateinit var clientPhoneEditText: EditText
    private lateinit var clientEmailEditText: EditText
    private lateinit var saveButton: Button
    private lateinit var progressBar: ProgressBar

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_client_new

    override fun buildComponent(injector: Injector, savedState: Bundle?): ClientComponent =
        injector.plusClientComponent()
            .eventDetailMode(intent.getIntExtra(EVENT_DETAIL_MODE_EXTRA, EventDetailMode.CREATE_NEW.ordinal))
            .client(intent.getParcelableExtra(CLIENT_EXTRA))
            .build()

    override fun initViews(savedState: Bundle?) {
        toolbar = findViewById(R.id.toolbar)

        clientNameEditText = findViewById(R.id.client_name_edit_text)
        clientPhoneEditText = findViewById(R.id.client_phone_edit_text)
        clientEmailEditText = findViewById(R.id.client_email_edit_text)

        saveButton = findViewById(R.id.save_button)
        progressBar = findViewById(R.id.progress_bar)

        toolbar.title = getString(R.string.client)
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        toolbar.setNavigationOnClickListener {
            presenter.onNavigationClicked()
        }

        clientNameEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                presenter.onClientNameChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //empty
            }

        })
        clientEmailEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                presenter.onClientEmailChanged(s.toString())
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

    //region ClientView

    override fun showSaveProgress() {
        saveButton.isEnabled = false
        progressBar.show()
    }

    override fun hideSaveProgress() {
        saveButton.isEnabled = true
        progressBar.hide()
    }

    override fun setClientName(clientName: String) {
        clientNameEditText.setText(clientName)
    }

    override fun setClientPhone(clientPhone: String) {
        clientPhoneEditText.setText(clientPhone)
    }

    override fun setClientEmail(clientEmail: String) {
        clientEmailEditText.setText(clientEmail)
    }

    override fun setCurrentCountry(country: String) {
        clientPhoneEditText.addTextChangedListener(
            MaskedTextChangedListener(
                CountryHelper.getPhoneFormatterByCountry(this, country),
                true,
                clientPhoneEditText,
                null,
                object : MaskedTextChangedListener.ValueListener {

                    override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
                        presenter.onClientPhoneChanged(extractedValue)
                    }

                }
            )
        )

    }

    override fun setEditableMode() {
        clientNameEditText.isEnabled = true
        clientPhoneEditText.isEnabled = true
        clientEmailEditText.isEnabled = true

        saveButton.show()
    }

    override fun setViewOnlyMode() {
        clientNameEditText.isEnabled = false
        clientPhoneEditText.isEnabled = false
        clientEmailEditText.isEnabled = false

        saveButton.hide()
    }

    override fun setSaveButtonEnabled() {
        saveButton.isEnabled = true
    }

    override fun setSaveButtonDisabled() {
        saveButton.isEnabled = false
    }

    override fun showSaveButton() {
        saveButton.show()
    }

    override fun hideSaveButton() {
        saveButton.hide()
    }

    override fun close(client: Client?) {
        setResult(
            Activity.RESULT_OK,
            Intent().putExtra(CLIENT_EXTRA, client)
        )
        finish()
    }

    override fun close() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }

    //endregion

}
