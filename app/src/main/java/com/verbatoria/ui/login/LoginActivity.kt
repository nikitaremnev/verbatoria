package com.verbatoria.ui.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import com.remnev.verbatoria.R
import com.verbatoria.di.common.Injector
import com.verbatoria.di.login.LoginComponent
import com.verbatoria.ui.base.BasePresenterActivity
import com.verbatoria.ui.base.BaseView

/**
 * @author n.remnev
 */

interface LoginView : BaseView {

    fun setLastLogin(lastLogin: String)

    fun setLastCountry(country: String)

    fun showCountrySelectionDialog()

    fun showLoginError(error: String)

    fun showProgress()

    fun hideProgress()

    fun openRecoveryPassword()

    fun openSMSConfirmation()

    interface Callback {

        fun onLoginTextChanged(login: String)

        fun onPasswordTextChanged(password: String)

        fun onLoginButtonClicked()

        fun onSelectCountryClicked()

        fun onCountrySelected(country: String)

    }

}

class LoginActivity: BasePresenterActivity<LoginView, LoginPresenter, LoginActivity, LoginComponent>(),
    LoginView {

    companion object {

        fun createIntent(context: Context): Intent =
            Intent(context, LoginActivity::class.java)

    }

    private lateinit var loginEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    //region BasePresenterActivity

    override fun getLayoutResourceId(): Int = R.layout.activity_login

    override fun buildComponent(injector: Injector, savedState: Bundle?): LoginComponent =
        injector.plusLoginComponent()
            .build()

    override fun initViews(savedState: Bundle?) {
        loginEditText = findViewById(R.id.login_edit_text)
        passwordEditText = findViewById(R.id.password_edit_text)
        loginButton = findViewById(R.id.login_button)

        loginEditText.addTextChangedListener(

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
                    presenter.onLoginTextChanged(s.toString())
                }

            }
        )
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
    }

    //endregion

    //region LoginView

    override fun setLastLogin(lastLogin: String) {

    }

    override fun setLastCountry(country: String) {

    }

    override fun showCountrySelectionDialog() {

    }

    override fun showLoginError(error: String) {

    }

    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun openRecoveryPassword() {

    }

    override fun openSMSConfirmation() {

    }

    //endregion

}


