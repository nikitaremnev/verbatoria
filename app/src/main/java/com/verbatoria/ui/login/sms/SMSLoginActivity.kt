package com.verbatoria.ui.login.sms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.remnev.verbatoria.R
import com.verbatoria.VerbatoriaApplication
import com.verbatoria.di.Injector
import com.verbatoria.di.login.sms.SMSLoginComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.dashboard.view.DashboardActivity
import com.verbatoria.utils.CountryHelper
import com.verbatoria.utils.Helper

/**
 * @author n.remnev
 */

interface SMSLoginView : BaseView {

    fun showProgress()

    fun hideProgress()

    fun getPhone(): String?

    fun showPhoneInput()

    fun showCodeInput()

    fun showCodeSent()

    fun showCodeConfirmationError()

    fun showCodeSentError()

    fun showPhoneNotFullError()

    fun startDashboard()

    fun updateTimer(time: String)

    fun stopTimer()

    fun hideTimer()

    fun showRepeatButton()

    fun hideRepeatButton()

    fun close()

    interface Callback {

        fun onConfirmationCodeTextChanged(confirmationCode: String)

        fun onPhoneTextChanged(isFull: Boolean, phone: String)

        fun onSendSMSCodeClicked()

        fun onCheckSMSCodeClicked(confirmationCode: String)

        fun onRepeatSMSClicked()

    }

}

class SMSLoginActivity : BasePresenterActivity<SMSLoginView, SMSLoginPresenter, SMSLoginActivity, SMSLoginComponent>(),
    SMSLoginView {

    companion object {

        private const val PHONE_EXTRA = "phone_extra"

        fun newInstance(context: Context, phone: String): Intent =
            Intent(context, SMSLoginActivity::class.java)
                .putExtra(PHONE_EXTRA, phone)

        fun newInstance(context: Context): Intent =
            Intent(context, SMSLoginActivity::class.java)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    }

    private lateinit var phoneEditText: EditText
    private lateinit var codeEditText: EditText
    private lateinit var timerTextView: TextView
    private lateinit var repeatTextView: TextView
    private lateinit var submitButton: Button
    private lateinit var progressView: View

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_sms_login

    override fun buildComponent(injector: Injector, savedState: Bundle?): SMSLoginComponent =
        injector.plusSMSLoginComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        phoneEditText = findViewById(R.id.phone_edit_text)
        codeEditText = findViewById(R.id.code_edit_text)
        submitButton = findViewById(R.id.submit_button)
        progressView = findViewById(R.id.progress_layout)
        timerTextView = findViewById(R.id.timer_text_view)
        repeatTextView = findViewById(R.id.repeat_button)

        setUpPhoneFormatter()
        codeEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                presenter.onConfirmationCodeTextChanged(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                //empty
            }

        })
        repeatTextView.setOnClickListener {
            presenter.onRepeatSMSClicked()
        }
    }

    override fun onBackPressed() {
        //empty
    }

    override fun onUserInteraction() {
        //empty
    }

    //endregion

    override fun getPhone(): String? = intent.getStringExtra(PHONE_EXTRA)

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
        submitButton.text = getString(R.string.recovery_password_check_code)
        submitButton.setOnClickListener {
            presenter.onSendSMSCodeClicked()
        }
    }

    override fun showCodeInput() {
        phoneEditText.visibility = View.GONE
        codeEditText.visibility = View.VISIBLE
        submitButton.text = getString(R.string.sms_confirmation_confirm)
        submitButton.setOnClickListener {
            presenter.onCheckSMSCodeClicked(codeEditText.text.toString())
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
        VerbatoriaApplication.onSmsConfirmationPassed()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun updateTimer(time: String) {
        runOnUiThread {
            timerTextView.text = getString(R.string.sms_confirmation_time_before_repeat, time)
            timerTextView.visibility = View.VISIBLE
        }
    }

    override fun stopTimer() {
        runOnUiThread {
            timerTextView.visibility = View.GONE
            repeatTextView.visibility = View.VISIBLE
        }
    }

    override fun hideTimer() {
        timerTextView.visibility = View.GONE
    }

    override fun showRepeatButton() {
        repeatTextView.visibility = View.VISIBLE
    }

    override fun hideRepeatButton() {
        repeatTextView.visibility = View.GONE
    }

    override fun close() {
        VerbatoriaApplication.onSmsConfirmationPassed()
        finish()
    }

    private fun setUpPhoneFormatter() {
        val country = ""
        val listener = MaskedTextChangedListener(
            CountryHelper.getPhoneFormatterByCountry(this, country),
            true,
            phoneEditText,
            null,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
                    presenter.onPhoneTextChanged(maskFilled, extractedValue)
                }
            }
        )

        phoneEditText.addTextChangedListener(listener)
        phoneEditText.onFocusChangeListener = listener
    }

}