package com.verbatoria.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.redmadrobot.inputmask.MaskedTextChangedListener
import com.remnev.verbatoria.BuildConfig
import com.remnev.verbatoria.R
import com.verbatoria.di.Injector
import com.verbatoria.di.login.LoginComponent
import com.verbatoria.infrastructure.extensions.hide
import com.verbatoria.infrastructure.extensions.show
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView
import com.verbatoria.ui.dashboard.DashboardActivity
import com.verbatoria.ui.recovery_password.RecoveryPasswordActivity
import com.verbatoria.ui.login.sms.SMSLoginActivity
import com.verbatoria.utils.CountryHelper

/**
 * @author n.remnev
 */

private const val LANGUAGE_SELECTION_DIALOG_TAG = "LANGUAGE_SELECTION_DIALOG_TAG"

interface LoginView : BaseView {

    fun setPhone(phone: String)

    fun setPassword(password: String)

    fun setCurrentCountry(country: String)

    fun showCountrySelectionDialog()

    fun setLoginButtonEnabled()

    fun setLoginButtonDisabled()

    fun showClearPhoneButton()

    fun hideClearPhoneButton()

    fun showClearPasswordButton()

    fun hideClearPasswordButton()

    fun showProgressForLogin()

    fun hideProgressForLoginWithSuccess()

    fun hideProgressForLoginWithError()

    fun isCountryRequireSkipSMSConfirmation(country: String): Boolean

    fun openDashboard()

    fun openRecoveryPassword(phone: String)

    fun openSMSConfirmation(phone: String)

    interface Callback {

        fun onPhoneClearClicked()

        fun onPasswordClearClicked()

        fun onPhoneTextChanged(phone: String)

        fun onPasswordTextChanged(password: String)

        fun onLoginButtonClicked()

        fun onForgotPasswordClicked()

        fun onSelectCountryClicked()

        fun onCountrySelected(country: String)

    }

}

class LoginActivity: BasePresenterActivity<LoginView, LoginPresenter, LoginActivity, LoginComponent>(),
    LoginView, CountrySelectionBottomSheetDialog.CountrySelectionListener {

    companion object {

        fun createIntent(context: Context): Intent =
            Intent(context, LoginActivity::class.java)

        fun createIntentWithClearStack(context: Context): Intent =
            Intent(context, LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }

    }

    private lateinit var phoneEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var phoneClearButton: ImageView
    private lateinit var passwordClearButton: ImageView
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var countryContainerView: View
    private lateinit var countryTextView: TextView
    private lateinit var countryFlagImageView: ImageView
    private lateinit var progressBar: ProgressBar
    private lateinit var loginButton: Button

    private var maskedTextChangedListener: MaskedTextChangedListener? = null

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_login

    override fun buildComponent(injector: Injector, savedState: Bundle?): LoginComponent =
        injector.plusLoginComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        phoneEditText = findViewById(R.id.phone_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        phoneClearButton = findViewById(R.id.phone_clear_button)
        passwordClearButton = findViewById(R.id.password_clear_button)
        forgotPasswordTextView = findViewById(R.id.forgot_password_text_view)
        countryContainerView = findViewById(R.id.country_container_layout)
        countryFlagImageView = findViewById(R.id.country_flag_image_view)
        countryTextView = findViewById(R.id.country_text_view)
        progressBar = findViewById(R.id.progress_bar)
        loginButton = findViewById(R.id.login_button)

        passwordEditText.addTextChangedListener(

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
                    presenter.onPasswordTextChanged(s.toString())
                }

            }
        )
        phoneClearButton.setOnClickListener {
            presenter.onPhoneClearClicked()
        }
        passwordClearButton.setOnClickListener {
            presenter.onPasswordClearClicked()
        }
        forgotPasswordTextView.setOnClickListener {
            presenter.onForgotPasswordClicked()
        }
        loginButton.setOnClickListener {
            presenter.onLoginButtonClicked()
        }
        countryContainerView.setOnClickListener {
            presenter.onSelectCountryClicked()
        }

        if (BuildConfig.DEBUG) {
            phoneEditText.setOnLongClickListener {
                //maria
//                phoneEditText.setText("79268932040")
//                passwordEditText.setText("89268932040")

                //my account
                phoneEditText.setText("79153974689")
                passwordEditText.setText("12345")

                //school testing
//                phoneEditText.setText("81234567895")
//                passwordEditText.setText("1234748a")

                return@setOnLongClickListener true
            }
        }
    }

    override fun onUserInteraction() {
        //empty
    }

    //endregion

    //region LoginView

    override fun setPhone(phone: String) {
        phoneEditText.setText(phone)
    }

    override fun setPassword(password: String) {
        passwordEditText.setText(password)
    }

    override fun setCurrentCountry(country: String) {
        if (maskedTextChangedListener != null) {
            phoneEditText.removeTextChangedListener(maskedTextChangedListener)
        }
        maskedTextChangedListener = MaskedTextChangedListener(
            CountryHelper.getPhoneFormatterByCountryKey(this, country),
            true,
            phoneEditText,
            null,
            object : MaskedTextChangedListener.ValueListener {
                override fun onTextChanged(
                    maskFilled: Boolean,
                    extractedValue: String,
                    formattedValue: String
                ) {
                    presenter.onPhoneTextChanged(formattedValue.replace("[^0-9]",""))
                }

            }
        )
        phoneEditText.addTextChangedListener(maskedTextChangedListener)
        phoneEditText.setText("")

        countryTextView.text = CountryHelper.getCountryNameByCountryKey(this, country)
        countryFlagImageView.setImageResource(CountryHelper.getFlagResourceByCountryKey(country))
    }

    override fun showCountrySelectionDialog() {
        CountrySelectionBottomSheetDialog.build {
            titleText = getString(R.string.login_country_selection_title)
        }.show(supportFragmentManager, LANGUAGE_SELECTION_DIALOG_TAG)
    }

    override fun setLoginButtonEnabled() {
        loginButton.isEnabled = true
    }

    override fun setLoginButtonDisabled() {
        loginButton.isEnabled = false
    }

    override fun showClearPhoneButton() {
        phoneClearButton.show()
    }

    override fun hideClearPhoneButton() {
        phoneClearButton.hide()
    }

    override fun showClearPasswordButton() {
        passwordClearButton.show()
    }

    override fun hideClearPasswordButton() {
        passwordClearButton.hide()
    }

    override fun showProgressForLogin() {
        phoneEditText.isEnabled = false
        passwordEditText.isEnabled = false
        forgotPasswordTextView.isEnabled = false
        countryContainerView.isEnabled = false
        loginButton.isEnabled = false
        phoneClearButton.hide()
        passwordClearButton.hide()
        progressBar.show()
    }

    override fun hideProgressForLoginWithSuccess() {
        progressBar.hide()
    }

    override fun hideProgressForLoginWithError() {
        phoneEditText.isEnabled = true
        passwordEditText.isEnabled = true
        forgotPasswordTextView.isEnabled = true
        countryContainerView.isEnabled = true
        loginButton.isEnabled = true
        phoneClearButton.show()
        passwordClearButton.show()
        progressBar.hide()
    }

    override fun isCountryRequireSkipSMSConfirmation(country: String): Boolean =
        CountryHelper.isCountryRequireSkipSMSConfirmation(country)

    override fun openDashboard() {
        startActivity(DashboardActivity.createIntent(this))
        finish()
    }

    override fun openRecoveryPassword(phone: String) {
        startActivity(RecoveryPasswordActivity.createIntent(this, phone))
        finish()
    }

    override fun openSMSConfirmation(phone: String) {
        startActivity(SMSLoginActivity.createIntent(this, phone))
        finish()
    }

    //endregion

    //region CountrySelectionBottomSheetDialog.CountrySelectionListener

    override fun onCountrySelected(tag: String?, countryKey: String) {
        if (tag == LANGUAGE_SELECTION_DIALOG_TAG) {
            presenter.onCountrySelected(countryKey)
        }
    }

    //endregion

}


