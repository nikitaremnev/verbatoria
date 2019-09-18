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
        getCurrentCountry()
        getLastLogin()
    }

    override fun onAttachView(view: LoginView) {
        super.onAttachView(view)
        view.setCurrentCountry(country)

        if (phone.isNotBlank()) {
            view.setPhone(phone)
        }

        if (phone.isNotBlank() && password.isNotBlank()) {
            view.apply {
                showClearPhoneButton()
                showClearPasswordButton()
                setLoginButtonEnabled()
            }
        } else {
            view.setLoginButtonDisabled()
            if (phone.isNotBlank()) {
                view.showClearPhoneButton()
            } else {
                view.hideClearPhoneButton()
            }
            if (password.isNotBlank()) {
                view.showClearPasswordButton()
            } else {
                view.hideClearPasswordButton()
            }
        }
    }

    //region LoginView.Callback

    override fun onPhoneTextChanged(phone: String) {
        if (phone == this.phone) return
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
        if (password == this.password) return
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
            .subscribe({
                view?.hideProgressForLoginWithSuccess()
//                if (BuildConfig.DEBUG) {
//                    view?.openDashboard()
//                } else {
                    if (view?.isCountryRequireSkipSMSConfirmation(country) == true) {
                        view?.openDashboard()
                    } else {
                        view?.openSMSConfirmation(phone)
                    }
//                }
            }, { error ->
                logger.error("Login error occurred", error)
                view?.apply {
                    hideProgressForLoginWithError()
                    showErrorSnackbar(error.message ?: "Login error occurred")
                }
            })
            .let(::addDisposable)
    }

    private fun getLastLogin() {
        loginInteractor.getLastLogin()
            .subscribe({ lastLogin ->
                phone = lastLogin
                if (lastLogin.isNotBlank()) {
                    view?.apply {
                        setPhone(phone)
                        showClearPhoneButton()
                    }
                }
            }, { error ->
                logger.error("Get last login error occurred", error)
                view?.apply {
                    hideProgressForLoginWithError()
                    showErrorSnackbar(error.message ?: "Get last login error occurred")
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
                logger.error("Get current country error occurred", error)
                view?.showErrorSnackbar(error.message ?: "Get current country error occurred")
            })
            .let(::addDisposable)
    }

    private fun saveCurrentCountry(country: String) {
        loginInteractor.saveCurrentCountry(country)
            .subscribe({
                this.country = country
                view?.setCurrentCountry(this.country)
                view?.setPhone(this.phone)
            }, { error ->
                logger.error("Save current country error occurred", error)
                view?.showErrorSnackbar(error.message ?: "Save current country error occurred")
            })
            .let(::addDisposable)
    }

    //region BCIPresenterCallback

}