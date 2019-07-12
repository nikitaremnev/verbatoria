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

    enum class RecoveryPasswordState {

        PHONE, CONFIRMATION_CODE, NEW_PASSWORD

    }

    private val logger = LoggerFactory.getLogger("RecoveryPasswordPresenter")

    private var phone: String = phoneFromLogin
    private var confirmationCode: String = ""
    private var newPassword: String = ""
    private var repeatNewPassword: String = ""

    private var country: String = ""

    private var recoveryPasswordState = RecoveryPasswordState.PHONE

    init {
        getCurrentCountry()
    }

    override fun onAttachView(view: RecoveryPasswordView) {
        super.onAttachView(view)
        if (country.isNotBlank()) {
            view.setPhoneFormatterBasedOnCountry(country)
        }
        view.setPhone(phone)

        when (recoveryPasswordState) {
            RecoveryPasswordState.PHONE -> setPhoneRecoveryPasswordState()
            RecoveryPasswordState.CONFIRMATION_CODE -> setConfirmationCodeRecoveryPasswordState()
            RecoveryPasswordState.NEW_PASSWORD -> setNewPasswordRecoveryPasswordState()
        }
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
        when (recoveryPasswordState) {
            RecoveryPasswordState.PHONE -> recoveryPassword()
            RecoveryPasswordState.CONFIRMATION_CODE -> {
                recoveryPasswordState = RecoveryPasswordState.NEW_PASSWORD
                setNewPasswordRecoveryPasswordState()
            }
            RecoveryPasswordState.NEW_PASSWORD -> resetPassword()
        }
    }

    //endregion

    private fun getCurrentCountry() {
        recoveryPasswordInteractor.getCurrentCountry()
            .subscribe({ country ->
                this.country = country
                view?.setPhoneFormatterBasedOnCountry(this.country)
            }, { error ->
                logger.error("Get current country error occurred", error)
                view?.showError(error.message ?: "Get current country error occurred")
            })
            .let(::addDisposable)
    }

    private fun recoveryPassword() {
        view?.showProgressForRecoveryPassword()
        recoveryPasswordInteractor.recoveryPassword(phone)
            .subscribe({
                recoveryPasswordState = RecoveryPasswordState.CONFIRMATION_CODE
                view?.hideProgressForRecoveryPassword()
                setConfirmationCodeRecoveryPasswordState()
            }, { error ->
                logger.error("get current country error occurred", error)
                view?.apply {
                    showError(error.message ?: "Send confirmation code error occurred")
                    hideProgressForRecoveryPassword()
                }
            })
            .let(::addDisposable)
    }

    private fun resetPassword() {
        view?.showProgressForResetPassword()
        recoveryPasswordInteractor.resetPassword(phone, confirmationCode, newPassword)
            .subscribe({
                view?.apply {
                    showResetPasswordSuccess()
                    openLoginWithTimeout()
                }
            }, { error ->
                logger.error("get current country error occurred", error)
                confirmationCode = ""
                view?.apply {
                    showError(error.message ?: "Send confirmation code error occurred")
                    hideProgressForResetPassword()
                    hideClearConfirmationCodeButton()
                    setConfirmationCode(confirmationCode)
                }
                setConfirmationCodeRecoveryPasswordState()
            })
            .let(::addDisposable)
    }

    private fun setPhoneRecoveryPasswordState() {
        view?.apply {
            hideConfirmationCodeField()
            hideNewPasswordFields()
            showPhoneField()
            setSendCodeTitleToSubmitButton()
        }
    }

    private fun setConfirmationCodeRecoveryPasswordState() {
        view?.apply {
            hidePhoneField()
            hideNewPasswordFields()
            showConfirmationCodeField()
            setCheckCodeTitleToSubmitButton()
            setSubmitButtonDisabled()
        }
    }

    private fun setNewPasswordRecoveryPasswordState() {
        view?.apply {
            hidePhoneField()
            hideConfirmationCodeField()
            showNewPasswordFields()
            setSetNewPasswordTitleToSubmitButton()
            setSubmitButtonDisabled()
        }
    }

}