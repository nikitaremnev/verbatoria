package com.verbatoria.ui.login.sms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.remnev.verbatoria.R
import com.verbatoria.VerbatoriaKtApplication
import com.verbatoria.di.Injector
import com.verbatoria.di.login.sms.SMSLoginComponent
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.dashboard.DashboardActivity
import com.verbatoria.utils.CountryHelper

/**
 * @author n.remnev
 */

private const val PHONE_EXTRA = "phone_extra"

interface SMSLoginView : BaseView {

    fun setPhone(phone: String)

    fun setCode(code: String)

    fun setPhoneFormatterBasedOnCountry(country: String)

    fun showPhoneField()

    fun hidePhoneField()

    fun showCodeField()

    fun hideCodeField()

    fun showClearCodeButton()

    fun hideClearCodeButton()

    fun setSendCodeButtonEnabled()

    fun setSendCodeButtonDisabled()

    fun showRepeatButton()

    fun hideRepeatButton()

    fun showTimerText()

    fun hideTimerText()

    fun showProgressForSendCode()

    fun hideProgressForSendCode()

    fun showSendCodeButton()

    fun hideSendCodeButton()

    fun setTimerText(timer: String)

    fun openDashboard()

    fun close()

    interface Callback {

        fun onPhoneTextChanged(phone: String)

        fun onCodeTextChanged(code: String)

        fun onCodeClearClicked()

        fun onSendCodeButtonClicked()

        fun onRepeatButtonClicked()

    }

}

class SMSLoginActivity :
    BasePresenterActivity<SMSLoginView, SMSLoginPresenter, SMSLoginActivity, SMSLoginComponent>(),
    SMSLoginView {

    companion object {

        fun createIntent(context: Context, phone: String): Intent =
            Intent(context, SMSLoginActivity::class.java).apply {
                putExtra(PHONE_EXTRA, phone)
            }

        fun newInstance(context: Context): Intent =
            Intent(context, SMSLoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

    }

    private lateinit var phoneEditText: EditText
    private lateinit var codeEditText: EditText

    private lateinit var clearCodeImageView: ImageView

    private lateinit var timerTextView: TextView
    private lateinit var repeatTextView: TextView

    private lateinit var sendCodeButton: Button
    private lateinit var progressBar: ProgressBar

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_sms_login

    override fun buildComponent(injector: Injector, savedState: Bundle?): SMSLoginComponent =
        injector.plusSMSLoginComponent()
            .phoneFromLogin(intent.getStringExtra(PHONE_EXTRA) ?: "")
            .build()

    override fun initViews(savedState: Bundle?) {
        phoneEditText = findViewById(R.id.phone_edit_text)
        codeEditText = findViewById(R.id.code_edit_text)

        clearCodeImageView = findViewById(R.id.code_clear_button)

        sendCodeButton = findViewById(R.id.send_code_button)
        progressBar = findViewById(R.id.progress_bar)
        timerTextView = findViewById(R.id.timer_text_view)
        repeatTextView = findViewById(R.id.repeat_text_view)

        codeEditText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                //empty
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //empty
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                presenter.onCodeTextChanged(s.toString())
            }

        })
        sendCodeButton.setOnClickListener {
            presenter.onSendCodeButtonClicked()
        }
        clearCodeImageView.setOnClickListener {
            presenter.onCodeClearClicked()
        }
        repeatTextView.setOnClickListener {
            presenter.onRepeatButtonClicked()
        }
    }

    override fun onBackPressed() {
        //empty
    }

    override fun onUserInteraction() {
        //empty
    }

    //endregion

    //region SMSLoginView

    override fun setPhone(phone: String) {
        phoneEditText.setText(phone)
    }

    override fun setCode(code: String) {
        codeEditText.setText(code)
    }

    override fun setPhoneFormatterBasedOnCountry(country: String) {
        phoneEditText.addTextChangedListener(
            MaskedTextChangedListener(
                CountryHelper.getPhoneFormatterByCountry(this, country),
                true,
                phoneEditText,
                null,
                object : MaskedTextChangedListener.ValueListener {

                    override fun onTextChanged(maskFilled: Boolean, extractedValue: String) {
                        presenter.onPhoneTextChanged(extractedValue)
                    }

                }
            )
        )
    }

    override fun showPhoneField() {
        phoneEditText.show()
    }

    override fun hidePhoneField() {
        phoneEditText.hide()
    }

    override fun showCodeField() {
        codeEditText.show()
    }

    override fun hideCodeField() {
        codeEditText.hide()
    }

    override fun showClearCodeButton() {
        clearCodeImageView.show()
    }

    override fun hideClearCodeButton() {
        clearCodeImageView.hide()
    }

    override fun setSendCodeButtonEnabled() {
        sendCodeButton.isEnabled = true
    }

    override fun setSendCodeButtonDisabled() {
        sendCodeButton.isEnabled = false
    }

    override fun showRepeatButton() {
        runOnUiThread {
            repeatTextView.show()
        }
    }

    override fun hideRepeatButton() {
        repeatTextView.hide()
    }

    override fun showTimerText() {
        timerTextView.show()
    }

    override fun hideTimerText() {
        runOnUiThread {
            timerTextView.hide()
        }
    }

    override fun showProgressForSendCode() {
        phoneEditText.isEnabled = false
        sendCodeButton.isEnabled = false
        progressBar.show()
    }

    override fun hideProgressForSendCode() {
        phoneEditText.isEnabled = true
        sendCodeButton.isEnabled = true
        progressBar.hide()
    }

    override fun showSendCodeButton() {
        sendCodeButton.show()
    }

    override fun hideSendCodeButton() {
        sendCodeButton.hide()
    }

    override fun setTimerText(timer: String) {
        runOnUiThread {
            timerTextView.text = getString(R.string.sms_login_time_before_repeat, timer)
        }
    }

    override fun openDashboard() {
        (application as? VerbatoriaKtApplication)?.onSmsConfirmationPassed()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun close() {
        (application as? VerbatoriaKtApplication)?.onSmsConfirmationPassed()
        finish()
    }

    //endregion

}