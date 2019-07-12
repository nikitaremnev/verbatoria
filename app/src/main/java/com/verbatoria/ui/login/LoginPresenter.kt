package com.verbatoria.ui.login

import com.remnev.verbatoria.BuildConfig
import com.verbatoria.business.login.LoginInteractor
import com.verbatoria.ui.base.BasePresenter
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

class LoginPresenter(
    private val loginInteractor: LoginInteractor
) : BasePresenter<LoginView>(), LoginView.Callback {

    private val logger = LoggerFactory.getLogger("LoginPresenter")

    private var phone: String = ""
    private var password: String = ""

    private var country: String = ""

    init {
        getLastLogin()
        getCurrentCountry()
    }

    override fun onAttachView(view: LoginView) {
        super.onAttachView(view)
        if (phone.isNotBlank()) {
            view.setPhone(phone)
        }
        if (country.isNotBlank()) {
            view.setCurrentCountry(country)
        }
    }

    //region LoginView.Callback

    override fun onPhoneTextChanged(phone: String) {
        this.phone = phone
        when {
            this.phone.isBlank() -> view?.setLoginButtonDisabled()
            password.isNotBlank() -> view?.apply {
                setLoginButtonEnabled()
                showClearPhoneButton()
            }
            else -> view?.apply {
                setLoginButtonDisabled()
                showClearPhoneButton()
            }
        }
    }

    override fun onPasswordTextChanged(password: String) {
        this.password = password
        when {
            password.isBlank() -> view?.setLoginButtonDisabled()
            phone.isNotBlank() -> view?.apply {
                setLoginButtonEnabled()
                showClearPasswordButton()
            }
            else -> view?.apply {
                setLoginButtonDisabled()
                showClearPasswordButton()
            }
        }
    }

    override fun onPhoneClearClicked() {
        phone = ""
        view?.apply {
            setPhone(phone)
            setLoginButtonDisabled()
            hideClearPhoneButton()
        }
    }

    override fun onPasswordClearClicked() {
        password = ""
        view?.apply {
            setPassword(password)
            setLoginButtonDisabled()
            hideClearPasswordButton()
        }
    }

    override fun onLoginButtonClicked() {
        login()
    }

    override fun onForgotPasswordClicked() {
        view?.openRecoveryPassword(phone)
    }

    override fun onSelectCountryClicked() {
        view?.showCountrySelectionDialog()
    }

    override fun onCountrySelected(country: String) {
        saveCurrentCountry(country)
    }

    //endregion

    private fun login() {
        view?.showProgressForLogin()
        loginInteractor.login(phone, password)
            .subscribe({ isSuccessful ->
                if (isSuccessful) {
                    view?.hideProgressForLoginWithSuccess()
                    if (BuildConfig.DEBUG) {
                        view?.openDashboard()
                    } else {
                        view?.openSMSConfirmation()
                    }
                } else {
                    view?.apply {
                        hideProgressForLoginWithError()
                        showError("Login error occurred")
                    }
                }
            }, { error ->
                logger.error("login error occurred", error)
                view?.apply {
                    hideProgressForLoginWithError()
                    showError(error.message ?: "Login error occurred")
                }
            })
            .let(::addDisposable)
    }

    private fun getLastLogin() {
        loginInteractor.getLastLogin()
            .subscribe({ lastLogin ->
                phone = lastLogin
                view?.setPhone(phone)
            }, { error ->
                logger.error("get last login error occurred", error)
                view?.apply {
                    hideProgressForLoginWithError()
                    showError(error.message ?: "Get last login error occurred")
                }
            })
            .let(::addDisposable)
    }

    private fun getCurrentCountry() {
        loginInteractor.getCurrentCountry()
            .subscribe({ country ->
                this.country = country
                view?.setCurrentCountry(this.country)
            }, { error ->
                logger.error("get current country error occurred", error)
                view?.showError(error.message ?: "Get last login error occurred")
            })
            .let(::addDisposable)
    }

    private fun saveCurrentCountry(country: String) {
        loginInteractor.saveCurrentCountry(country)
            .subscribe({
                this.country = country
                view?.setCurrentCountry(this.country)
            }, { error ->
                logger.error("save current country error occurred", error)
                view?.showError(error.message ?: "Get last login error occurred")
            })
            .let(::addDisposable)
    }

}