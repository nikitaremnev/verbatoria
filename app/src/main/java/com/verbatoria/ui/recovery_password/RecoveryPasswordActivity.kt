package com.verbatoria.ui.recovery_password

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.verbatoria.di.Injector
import com.verbatoria.di.recovery_password.RecoveryPasswordComponent
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.login.LoginActivity
import com.verbatoria.utils.CountryHelper
import android.os.Handler
import com.remnev.verbatoria.R

/**
 * @author n.remnev
 */

private const val PHONE_FROM_LOGIN_EXTRA = "PHONE_FROM_LOGIN_EXTRA"
private const val START_LOGIN_DELAY = 1500L

interface RecoveryPasswordView : BaseView {

    fun setPhone(phone: String)

    fun setConfirmationCode(confirmationCode: String)

    fun setNewPassword(newPassword: String)

    fun setRepeatNewPassword(repeatNewPassword: String)

    fun setPhoneFormatterBasedOnCountry(country: String)

    fun showPhoneField()

    fun hidePhoneField()

    fun showConfirmationCodeField()

    fun hideConfirmationCodeField()

    fun showNewPasswordFields()

    fun hideNewPasswordFields()

    fun showClearPhoneButton()

    fun hideClearPhoneButton()

    fun showClearConfirmationCodeButton()

    fun hideClearConfirmationCodeButton()

    fun showClearNewPasswordButton()

    fun hideClearNewPasswordButton()

    fun showClearRepeatNewPasswordButton()

    fun hideClearRepeatNewPasswordButton()

    fun setSubmitButtonEnabled()

    fun setSubmitButtonDisabled()

    fun showProgressForRecoveryPassword()

    fun hideProgressForRecoveryPassword()

    fun showProgressForResetPassword()

    fun hideProgressForResetPassword()

    fun setSendCodeTitleToSubmitButton()

    fun setCheckCodeTitleToSubmitButton()

    fun setSetNewPasswordTitleToSubmitButton()

    fun showResetPasswordSuccess()

    fun showError(error: String)

    fun openLogin()

    fun openLoginWithTimeout()

    interface Callback {

        fun onPhoneTextChanged(phone: String)

        fun onConfirmationCodeTextChanged(confirmationCode: String)

        fun onNewPasswordTextChanged(newPassword: String)

        fun onRepeatNewPasswordTextChanged(repeatNewPassword: String)

        fun onPhoneClearClicked()

        fun onConfirmationCodeClearClicked()

        fun onNewPasswordClearClicked()

        fun onRepeatNewPasswordClearClicked()

        fun onRememberPasswordClicked()

        fun onSubmitButtonClicked()

    }

}

class RecoveryPasswordActivity :
    BasePresenterActivity<RecoveryPasswordView, RecoveryPasswordPresenter, RecoveryPasswordActivity, RecoveryPasswordComponent>(),
    RecoveryPasswordView {

    companion object {

        fun createIntent(context: Context, phone: String): Intent =
            Intent(context, RecoveryPasswordActivity::class.java).apply {
                putExtra(PHONE_FROM_LOGIN_EXTRA, phone)
            }

    }

    private lateinit var phoneEditText: EditText
    private lateinit var confirmationCodeEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var repeatNewPasswordEditText: EditText

    private lateinit var clearPhoneImageView: ImageView
    private lateinit var clearConfirmationCodeImageView: ImageView
    private lateinit var clearNewPasswordImageView: ImageView
    private lateinit var clearRepeatNewPasswordImageView: ImageView

    private lateinit var rememberPasswordTextView: TextView

    private lateinit var progressBar: ProgressBar
    private lateinit var submitButton: Button

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_recovery_password

    override fun buildComponent(
        injector: Injector,
        savedState: Bundle?
    ): RecoveryPasswordComponent =
        injector.plusRecoveryPasswordComponent()
            .phoneFromLogin(intent.getStringExtra(PHONE_FROM_LOGIN_EXTRA) ?: "")
            .build()

    override fun initViews(savedState: Bundle?) {
        phoneEditText = findViewById(R.id.phone_edit_text)
        confirmationCodeEditText = findViewById(R.id.confirmation_code_edit_text)
        newPasswordEditText = findViewById(R.id.new_password_edit_text)
        repeatNewPasswordEditText = findViewById(R.id.repeat_new_password_edit_text)
        clearPhoneImageView = findViewById(R.id.phone_clear_button)
        clearConfirmationCodeImageView = findViewById(R.id.confirmation_code_clear_button)
        clearNewPasswordImageView = findViewById(R.id.new_password_clear_button)
        clearRepeatNewPasswordImageView = findViewById(R.id.repeat_new_password_clear_button)
        rememberPasswordTextView = findViewById(R.id.remember_password_text_view)
        progressBar = findViewById(R.id.progress_bar)
        submitButton = findViewById(R.id.submit_button)

        confirmationCodeEditText.addTextChangedListener(

            object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                    //empty
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //empty
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.onConfirmationCodeTextChanged(s.toString())
                }

            }
        )
        newPasswordEditText.addTextChangedListener(

            object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                    //empty
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //empty
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.onNewPasswordTextChanged(s.toString())
                }

            }
        )
        repeatNewPasswordEditText.addTextChangedListener(

            object : TextWatcher {

                override fun afterTextChanged(s: Editable?) {
                    //empty
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    //empty
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    presenter.onRepeatNewPasswordTextChanged(s.toString())
                }

            }
        )

        clearPhoneImageView.setOnClickListener {
            presenter.onPhoneClearClicked()
        }
        clearConfirmationCodeImageView.setOnClickListener {
            presenter.onConfirmationCodeClearClicked()
        }
        clearNewPasswordImageView.setOnClickListener {
            presenter.onNewPasswordClearClicked()
        }
        clearRepeatNewPasswordImageView.setOnClickListener {
            presenter.onRepeatNewPasswordClearClicked()
        }

        rememberPasswordTextView.setOnClickListener {
            presenter.onRememberPasswordClicked()
        }

        submitButton.setOnClickListener {
            presenter.onSubmitButtonClicked()
        }
    }

    //region RecoveryPasswordView

    override fun setPhone(phone: String) {
        phoneEditText.setText(phone)
    }

    override fun setConfirmationCode(confirmationCode: String) {
        confirmationCodeEditText.setText(confirmationCode)
    }

    override fun setNewPassword(newPassword: String) {
        newPasswordEditText.setText(newPassword)
    }

    override fun setRepeatNewPassword(repeatNewPassword: String) {
        repeatNewPasswordEditText.setText(repeatNewPassword)
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

    override fun showConfirmationCodeField() {
        confirmationCodeEditText.show()
    }

    override fun hideConfirmationCodeField() {
        confirmationCodeEditText.hide()
    }

    override fun showNewPasswordFields() {
        newPasswordEditText.show()
        repeatNewPasswordEditText.show()
    }

    override fun hideNewPasswordFields() {
        newPasswordEditText.hide()
        repeatNewPasswordEditText.hide()
    }

    override fun showClearPhoneButton() {
        clearPhoneImageView.show()
    }

    override fun hideClearPhoneButton() {
        clearPhoneImageView.hide()
    }

    override fun showClearConfirmationCodeButton() {
        clearConfirmationCodeImageView.show()
    }

    override fun hideClearConfirmationCodeButton() {
        clearConfirmationCodeImageView.hide()
    }

    override fun showClearNewPasswordButton() {
        clearNewPasswordImageView.show()
    }

    override fun hideClearNewPasswordButton() {
        clearNewPasswordImageView.hide()
    }

    override fun showClearRepeatNewPasswordButton() {
        clearRepeatNewPasswordImageView.show()
    }

    override fun hideClearRepeatNewPasswordButton() {
        clearRepeatNewPasswordImageView.hide()
    }

    override fun setSubmitButtonEnabled() {
        Log.e("test", "RecoveryPasswordActivity setSubmitButtonEnabled")
        submitButton.isEnabled = true
    }

    override fun setSubmitButtonDisabled() {
        Log.e("test", "RecoveryPasswordActivity setSubmitButtonDisabled")
        submitButton.isEnabled = false
    }

    override fun showProgressForRecoveryPassword() {
        phoneEditText.isEnabled = false
        clearPhoneImageView.isEnabled = false
        submitButton.isEnabled = false
        rememberPasswordTextView.isEnabled = false
        progressBar.show()
    }

    override fun hideProgressForRecoveryPassword() {
        phoneEditText.isEnabled = true
        clearPhoneImageView.isEnabled = true
        submitButton.isEnabled = true
        rememberPasswordTextView.isEnabled = true
        progressBar.hide()
    }

    override fun showProgressForResetPassword() {
        newPasswordEditText.isEnabled = false
        repeatNewPasswordEditText.isEnabled = false
        clearNewPasswordImageView.isEnabled = false
        clearRepeatNewPasswordImageView.isEnabled = false
        submitButton.isEnabled = false
        rememberPasswordTextView.isEnabled = false
        progressBar.show()
    }

    override fun hideProgressForResetPassword() {
        newPasswordEditText.isEnabled = true
        repeatNewPasswordEditText.isEnabled = true
        clearNewPasswordImageView.isEnabled = true
        clearRepeatNewPasswordImageView.isEnabled = true
        submitButton.isEnabled = true
        rememberPasswordTextView.isEnabled = true
        progressBar.hide()
    }

    override fun setSendCodeTitleToSubmitButton() {
        submitButton.text = getString(R.string.recovery_password_check_code)
    }

    override fun setCheckCodeTitleToSubmitButton() {
        submitButton.text = getString(R.string.recovery_password_next)
    }

    override fun setSetNewPasswordTitleToSubmitButton() {
        submitButton.text = getString(R.string.recovery_password_send_new_password)
    }

    override fun showResetPasswordSuccess() {
        showShortHintSnackbar(getString(R.string.recovery_password_reset_success))
    }

    override fun showError(error: String) {
        showErrorSnackbar(error)
    }

    override fun openLogin() {
        startActivity(LoginActivity.createIntent(this))
        finish()
    }

    override fun openLoginWithTimeout() {
        Handler().postDelayed({
            openLogin()
        }, START_LOGIN_DELAY)
    }

    //endregion

}


