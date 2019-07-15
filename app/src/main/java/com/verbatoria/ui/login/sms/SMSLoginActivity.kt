package com.verbatoria.ui.login.sms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.remnev.verbatoria.R
import com.verbatoria.VerbatoriaApplication
import com.verbatoria.di.Injector
import com.verbatoria.di.login.sms.SMSLoginComponent
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.dashboard.view.DashboardActivity
import com.verbatoria.utils.CountryHelper
import com.verbatoria.utils.Helper

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

    fun showClearPhoneButton()

    fun hideClearPhoneButton()

    fun showClearCodeButton()

    fun hideClearCodeButton()

    fun setSendCodeButtonEnabled()

    fun setSendCodeButtonDisabled()

    fun showRepeatButton()

    fun hideRepeatButton()

    fun showProgressForSendCode()

    fun hideProgressForSendCode()

    fun setTimerText(timer: String)

    fun openDashboard()

    fun showError(error: String)

    fun close()

    interface Callback {

        fun onPhoneTextChanged(phone: String)

        fun onCodeTextChanged(code: String)

        fun onPhoneClearClicked()

        fun onCodeClearClicked()

        fun onSendCodeButtonClicked()

        fun onRepeatButtonClicked()

//
//        fun onCheckSMSCodeClicked(confirmationCode: String)
//
        fun onRepeatSMSClicked()

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

    private lateinit var clearPhoneImageView: ImageView
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

        clearPhoneImageView = findViewById(R.id.phone_clear_button)
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
        clearPhoneImageView.setOnClickListener {
            presenter.onPhoneClearClicked()
        }
        clearCodeImageView.setOnClickListener {
            presenter.onCodeClearClicked()
        }
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

    override fun showClearPhoneButton() {
        clearPhoneImageView.show()
    }

    override fun hideClearPhoneButton() {
        clearPhoneImageView.hide()
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
        repeatTextView.show()
    }

    override fun hideRepeatButton() {
        repeatTextView.hide()
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

    override fun setTimerText(timer: String) {
        timerTextView.text = timer
    }

    override fun openDashboard() {
        VerbatoriaApplication.onSmsConfirmationPassed()
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
    }

    override fun showError(error: String) {
        Helper.showErrorSnackBar(sendCodeButton, error)
    }

    override fun close() {
        VerbatoriaApplication.onSmsConfirmationPassed()
        finish()
    }

    //endregion



//    override fun showProgress() {
//        submitButton.visibility = View.GONE
//        progressView.visibility = View.VISIBLE
//    }
//
//    override fun hideProgress() {
//        submitButton.visibility = View.VISIBLE
//        progressView.visibility = View.GONE
//    }
//
//    override fun showPhoneInput() {
//        codeEditText.visibility = View.GONE
//        phoneEditText.visibility = View.VISIBLE
//        submitButton.text = getString(R.string.recovery_password_check_code)
//        submitButton.setOnClickListener {
//            presenter.onSendSMSCodeClicked()
//        }
//    }
//
//    override fun showCodeInput() {
//        phoneEditText.visibility = View.GONE
//        codeEditText.visibility = View.VISIBLE
//        submitButton.text = getString(R.string.sms_confirmation_confirm)
//        submitButton.setOnClickListener {
//            presenter.onCheckSMSCodeClicked(codeEditText.text.toString())
//        }
//    }
//
//    override fun showCodeSent() {
//        Helper.showHintSnackBar(submitButton, getString(R.string.sms_confirmation_code_sent))
//    }
//
//    override fun showCodeConfirmationError() {
//        Helper.showErrorSnackBar(submitButton, getString(R.string.sms_confirmation_code_incorrect))
//    }
//
//    override fun showCodeSentError() {
//        Helper.showErrorSnackBar(submitButton, getString(R.string.sms_confirmation_code_sent_error))
//    }
//
//    override fun showPhoneNotFullError() {
//        Helper.showErrorSnackBar(submitButton, getString(R.string.sms_confirmation_code_phone_not_full))
//    }
//    override fun updateTimer(time: String) {
//        runOnUiThread {
//            timerTextView.text = getString(R.string.sms_confirmation_time_before_repeat, time)
//            timerTextView.visibility = View.VISIBLE
//        }
//    }
//
//    override fun stopTimer() {
//        runOnUiThread {
//            timerTextView.visibility = View.GONE
//            repeatTextView.visibility = View.VISIBLE
//        }
//    }

}