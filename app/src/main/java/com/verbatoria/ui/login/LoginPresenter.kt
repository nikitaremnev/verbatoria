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

    private var currentCountry: String = ""

    init {
        getLastLogin()
        getCurrentCountry()
    }

    override fun onAttachView(view: LoginView) {
        super.onAttachView(view)
        if (login.isNotEmpty()) {
            view.setLogin(login)
        }
        if (currentCountry.isNotEmpty()) {
            view.setCurrentCountry(currentCountry)
        }
        view.setLoginFormatter("+ [0] [000] [000] [00] [00]")
    }

    //region LoginView.Callback

    override fun onLoginTextChanged(login: String) {
        this.login = login
        if (login.isBlank()) {
            view?.setLoginButtonDisabled()
        } else if (!password.isBlank()) {
            view?.apply {
                setLoginButtonEnabled()
                showClearLoginButton()
            }
        } else {
            view?.apply {
                setLoginButtonDisabled()
                showClearLoginButton()
            }
        }
    }

    override fun onPasswordTextChanged(password: String) {
        this.password = password
        if (password.isBlank()) {
            view?.setLoginButtonDisabled()
        } else if (!login.isBlank()) {
            view?.apply {
                setLoginButtonEnabled()
                showClearPasswordButton()
            }
        } else {
            view?.apply {
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
        view?.setPassword(password)
        view?.setLoginButtonDisabled()
        view?.hideClearPasswordButton()
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
            .subscribe({ tokenModel ->
                view?.hideProgressForLoginWithSuccess()
                if (BuildConfig.DEBUG) {
                    view?.openDashboard()
                } else {
                    view?.openSMSConfirmation()
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
                currentCountry = country
                view?.setCurrentCountry(currentCountry)
            }, { error ->
                logger.error("get last login error occurred", error)
                view?.showError(error.message ?: "Get last login error occurred")
            })
            .let(::addDisposable)
    }

    private fun saveCurrentCountry(country: String) {
        loginInteractor.saveCurrentCountry(country)
            .subscribe({
                currentCountry = country
                view?.setCurrentCountry(currentCountry)
            }, { error ->
                logger.error("get last login error occurred", error)
                view?.showError(error.message ?: "Get last login error occurred")
            })
            .let(::addDisposable)
    }

}