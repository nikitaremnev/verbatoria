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

    private var login: String = ""
    private var password: String = ""

    private var country: String = ""

    init {
        getLastLogin()
        getCurrentCountry()
    }

    override fun onAttachView(view: LoginView) {
        super.onAttachView(view)
        if (login.isNotBlank()) {
            view.setLogin(login)
        }
        if (country.isNotBlank()) {
            view.setCurrentCountry(country)
        }
    }

    //region LoginView.Callback

    override fun onLoginTextChanged(login: String) {
        this.login = login
        when {
            login.isBlank() -> view?.setLoginButtonDisabled()
            password.isNotBlank() -> view?.apply {
                setLoginButtonEnabled()
                showClearLoginButton()
            }
            else -> view?.apply {
                setLoginButtonDisabled()
                showClearLoginButton()
            }
        }
    }

    override fun onPasswordTextChanged(password: String) {
        this.password = password
        when {
            password.isBlank() -> view?.setLoginButtonDisabled()
            login.isNotBlank() -> view?.apply {
                setLoginButtonEnabled()
                showClearPasswordButton()
            }
            else -> view?.apply {
                setLoginButtonDisabled()
                showClearPasswordButton()
            }
        }
    }

    override fun onLoginClearClicked() {
        login = ""
        view?.apply {
            setLogin(login)
            setLoginButtonDisabled()
            hideClearLoginButton()
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
        view?.openRecoveryPassword()
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
        loginInteractor.login(login, password)
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
                login = lastLogin
                view?. setLogin(login)
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