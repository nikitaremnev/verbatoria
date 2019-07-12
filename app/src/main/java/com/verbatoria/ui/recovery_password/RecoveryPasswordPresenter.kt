package com.verbatoria.ui.recovery_password

import com.verbatoria.business.recovery_password.RecoveryPasswordInteractor
import com.verbatoria.ui.base.BasePresenter
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

class RecoveryPasswordPresenter(
    phoneFromLogin: String,
    private val recoveryPasswordInteractor: RecoveryPasswordInteractor
) : BasePresenter<RecoveryPasswordView>(), RecoveryPasswordView.Callback {

    private val logger = LoggerFactory.getLogger("RecoveryPasswordPresenter")

    private var phone: String = phoneFromLogin
    private var confirmationCode: String = ""
    private var newPassword: String = ""
    private var repeatNewPassword: String = ""

    private var country: String = ""

    init {
        getCurrentCountry()
    }

    override fun onAttachView(view: RecoveryPasswordView) {
        super.onAttachView(view)
        if (country.isNotBlank()) {
            view.setPhoneFormatterBasedOnCountry(country)
        }
        view.setPhone(phone)
    }

    //region RecoveryPasswordView.Callback

    override fun onPhoneTextChanged(phone: String) {
        this.phone = phone
        when {
            this.phone.isBlank() -> view?.apply {
                hideClearPhoneButton()
                setSubmitButtonDisabled()
            }
            this.phone.isNotBlank() -> view?.apply {
                showClearPhoneButton()
                setSubmitButtonEnabled()
            }
        }
    }

    override fun onConfirmationCodeTextChanged(confirmationCode: String) {
        this.confirmationCode = confirmationCode
        when {
            this.confirmationCode.isBlank() -> view?.apply {
                hideClearConfirmationCodeButton()
                setSubmitButtonDisabled()
            }
            this.confirmationCode.isNotBlank() -> view?.apply {
                showClearConfirmationCodeButton()
                setSubmitButtonEnabled()
            }
        }
    }

    override fun onNewPasswordTextChanged(newPassword: String) {
        this.newPassword = newPassword
        when {
            this.newPassword.isBlank() -> view?.apply {
                hideClearNewPasswordButton()
                setSubmitButtonDisabled()
            }
            this.newPassword.isNotBlank() -> view?.apply {
                showClearNewPasswordButton()
                if (repeatNewPassword == newPassword) {
                    setSubmitButtonEnabled()
                } else {
                    setSubmitButtonDisabled()
                }
            }
        }
    }

    override fun onRepeatNewPasswordTextChanged(repeatNewPassword: String) {
        this.repeatNewPassword = repeatNewPassword
        when {
            this.repeatNewPassword.isBlank() -> view?.apply {
                hideClearRepeatNewPasswordButton()
                setSubmitButtonDisabled()
            }
            this.repeatNewPassword.isNotBlank() -> view?.apply {
                showClearRepeatNewPasswordButton()
                if (repeatNewPassword == newPassword) {
                    setSubmitButtonEnabled()
                } else {
                    setSubmitButtonDisabled()
                }
            }
        }
    }

    override fun onPhoneClearClicked() {
        phone = ""
        view?.apply {
            setPhone(phone)
            setSubmitButtonDisabled()
            hideClearPhoneButton()
        }
    }

    override fun onConfirmationCodeClearClicked() {
        confirmationCode = ""
        view?.apply {
            setConfirmationCode(confirmationCode)
            setSubmitButtonDisabled()
            hideClearConfirmationCodeButton()
        }
    }

    override fun onNewPasswordClearClicked() {
        newPassword = ""
        repeatNewPassword = ""
        view?.apply {
            setNewPassword(newPassword)
            setRepeatNewPassword(repeatNewPassword)
            setSubmitButtonDisabled()
            hideClearNewPasswordButton()
            hideClearRepeatNewPasswordButton()
        }
    }

    override fun onRepeatNewPasswordClearClicked() {
        repeatNewPassword = ""
        view?.apply {
            setRepeatNewPassword(repeatNewPassword)
            setSubmitButtonDisabled()
            hideClearRepeatNewPasswordButton()
        }
    }

    override fun onRememberPasswordClicked() {
        view?.openLogin()
    }

    override fun onSubmitButtonClicked() {
        sendConfirmationCode()
    }

    //endregion

    private fun getCurrentCountry() {
        recoveryPasswordInteractor.getCurrentCountry()
            .subscribe({ country ->
                this.country = country
                view?.setPhoneFormatterBasedOnCountry(this.country)
            }, { error ->
                logger.error("get current country error occurred", error)
                view?.showError(error.message ?: "Get last login error occurred")
            })
            .let(::addDisposable)
    }

    private fun sendConfirmationCode() {
        recoveryPasswordInteractor.recoveryPassword(phone)
            .subscribe({ isConfirmationCodeSent ->


            }, { error ->
                logger.error("get current country error occurred", error)
                view?.showError(error.message ?: "Get last login error occurred")
            })
            .let(::addDisposable)
    }

}