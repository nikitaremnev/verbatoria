package com.verbatoria.presentation.login.view.sms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.remnev.verbatoria.R
import com.verbatoria.VerbatoriaApplication
import com.verbatoria.di.login.LoginModule
import com.verbatoria.infrastructure.BaseActivity
import com.verbatoria.infrastructure.BasePresenter
import com.verbatoria.presentation.dashboard.view.DashboardActivity
import com.verbatoria.presentation.login.presenter.sms.SMSConfirmationPresenter
import com.verbatoria.utils.Helper
import javax.inject.Inject

/**
 * @author n.remnev
 */

interface SMSConfirmationView {

    fun showProgress()

    fun hideProgress()

    fun getPhone(): String?

    fun getSMSText(): String

    fun showPhoneInput()

    fun showCodeInput()

    fun showCodeSent()

    fun showCodeConfirmationError()

    fun showCodeSentError()

    fun showPhoneNotFullError()

    fun startDashboard()

    interface Callback {

        fun onConfirmationCodeTextChanged(confirmationCode: String)

        fun onPhoneTextChanged(isFull: Boolean, phone: String)

        fun onSendSMSCodeClicked()

        fun onCheckSMSCodeClicked(confirmationCode: String)

    }

}

class SMSConfirmationActivity : BaseActivity(), SMSConfirmationView, MaskedTextChangedListener.ValueListener {

    companion object {

        private const val PHONE_EXTRA = "phone_extra"

        fun newInstance(context: Context, phone: String): Intent =
            Intent(context, SMSConfirmationActivity::class.java)
                .putExtra(PHONE_EXTRA, phone)

        fun newInstance(context: Context): Intent =
            Intent(context, SMSConfirmationActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    }

    @Inject
    lateinit var presenter: SMSConfirmationPresenter

    private lateinit var phoneEditText: EditText
    private lateinit var codeEditText: EditText
    private lateinit var submitButton: Button
    private lateinit var progressView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        VerbatoriaApplication
            .getApplicationComponent()
            .addModule(LoginModule())
            .inject(this)

        //initialize views
        setContentView(R.layout.activity_sms_confirmation)

        //bind views
        setPresenter(presenter as BasePresenter)

        super.onCreate(savedInstanceState)

        presenter.bindView(this)
    }

    override fun setUpViews() {
        phoneEditText = findViewById(R.id.phone_edit_text)
        codeEditText = findViewById(R.id.code_edit_text)
        submitButton = findViewById(R.id.submit_button)
        progressView = findViewById(R.id.progress_layout)

        setUpPhoneFormatter()
        codeEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                (presenter as SMSConfirmationView.Callback).onConfirmationCodeTextChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //empty
            }

        })
    }

    override fun getPhone(): String? = intent.getStringExtra(PHONE_EXTRA)

    override fun getSMSText(): String = getString(R.string.sms_confirmation_text)

    override fun showProgress() {
        submitButton.visibility = View.GONE
        progressView.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        submitButton.visibility = View.VISIBLE
        progressView.visibility = View.GONE
    }

    override fun showPhoneInput() {
        codeEditText.visibility = View.GONE
        phoneEditText.visibility = View.VISIBLE
        submitButton.text = getString(R.string.recovery_send_code)
        submitButton.setOnClickListener {
            (presenter as SMSConfirmationView.Callback).onSendSMSCodeClicked()
        }
    }

    override fun showCodeInput() {
        phoneEditText.visibility = View.GONE
        codeEditText.visibility = View.VISIBLE
        submitButton.text = getString(R.string.sms_confirmation_confirm)
        submitButton.setOnClickListener {
            (presenter as SMSConfirmationView.Callback).onCheckSMSCodeClicked(codeEditText.text.toString())
        }
    }

    override fun showCodeSent() {
        Helper.showHintSnackBar(submitButton, getString(R.string.sms_confirmation_code_sent))
    }

    override fun showCodeConfirmationError() {
        Helper.showErrorSnackBar(submitButton, getString(R.string.sms_confirmation_code_incorrect))
    }

    override fun showCodeSentError() {
        Helper.showErrorSnackBar(submitButton, getString(R.string.sms_confirmation_code_sent_error))
    }

    override fun showPhoneNotFullError() {
        Helper.showErrorSnackBar(submitButton, getString(R.string.sms_confirmation_code_phone_not_full))
    }

    override fun startDashboard() {
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    private fun setUpPhoneFormatter() {
        val country = presenter.getCountry()
        val formatterResource: Int

        formatterResource = when (country) {
            getString(R.string.country_russia) -> R.string.login_russia_phone_mask
            getString(R.string.country_ukraine) -> R.string.login_ukraine_phone_mask
            getString(R.string.country_azerbaijan) -> R.string.login_azerbaijan_phone_mask
            getString(R.string.country_thailand) -> R.string.login_thailand_phone_mask
            getString(R.string.country_belarus) -> R.string.login_belarus_phone_mask
            getString(R.string.country_israel) -> R.string.login_israel_phone_mask
            getString(R.string.country_uae) -> R.string.login_uae_phone_mask
            else -> R.string.login_uzbekistan_phone_mask
        }

        val listener = MaskedTextChangedListener(
            getString(formatterResource),
            true,
            phoneEditText,
            null,
            this
        )

        phoneEditText.addTextChangedListener(listener)
        phoneEditText.onFocusChangeListener = listener
    }

    //region MaskedTextChangedListener.ValueListener

    override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
        (presenter as SMSConfirmationView.Callback).onPhoneTextChanged(maskFilled, extractedValue)
    }

    //endregion

}