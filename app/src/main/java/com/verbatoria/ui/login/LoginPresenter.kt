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

    init {


    }

    override fun onAttachView(view: LoginView) {
        super.onAttachView(view)
        val lastLogin = loginInteractor.getLastLogin()
        view.setLastLogin(lastLogin)
        val lastCountry = loginInteractor.getCountry()
        view.setLastCountry(lastCountry)
    }

    //region LoginView.Callback

    override fun onLoginTextChanged(login: String) {
        this.login = login
        if (login.isBlank()) {
            view?.setLoginButtonDisabled()
        } else if (!password.isBlank()) {
            view?.setLoginButtonEnabled()
            view?.showClearLoginButton()
        } else {
            view?.showClearLoginButton()
        }
    }

    override fun onPasswordTextChanged(password: String) {
        this.password = password
        if (password.isBlank()) {
            view?.setLoginButtonDisabled()
        } else if (!login.isBlank()) {
            view?.setLoginButtonEnabled()
            view?.showClearPasswordButton()
        } else {
            view?.showClearPasswordButton()
        }
    }

    override fun onLoginClearClicked() {
        login = ""
        view?.setLogin(login)
        view?.setLoginButtonDisabled()
        view?.hideClearLoginButton()
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

    }

    override fun onCountrySelected(country: String) {

    }

    //endregion

    private fun login() {
        view?.showProgress()
        loginInteractor.login(login, password)
            .doOnComplete {
                view?.hideProgress()
            }
            .subscribe({ tokenModel ->
                if (BuildConfig.DEBUG) {
                    view?.openDashboard()
                } else {
                    view?.openSMSConfirmation()
                }
            }, { error ->
                logger.error("login error occurred", error)
                view?.hideProgress()
                view?.showLoginError(error.message ?: "Login error occurred")
            })
            .let(::addDisposable)
    }

}