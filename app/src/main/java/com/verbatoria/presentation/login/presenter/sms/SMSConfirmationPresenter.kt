package com.verbatoria.presentation.login.presenter.sms

import android.os.Bundle
import com.verbatoria.business.login.ILoginInteractor
import com.verbatoria.data.network.response.SMSConfirmationResponseModel
import com.verbatoria.infrastructure.BasePresenter
import com.verbatoria.presentation.login.view.sms.SMSConfirmationView

/**
 * @author n.remnev
 */

interface SMSConfirmationPresenter {

    fun bindView(smsConfirmationView: SMSConfirmationView)
    fun unbindView()

    fun getCountry(): String

}

class SMSConfirmationPresenterImpl(
    private val loginInteractor: ILoginInteractor
) : BasePresenter(), SMSConfirmationPresenter, SMSConfirmationView.Callback {

    private var mSmsConfirmationView: SMSConfirmationView? = null

    private var phone: String? = null

    private var isPhoneFilled: Boolean = false

    private var confirmCode: Long? = null

    private var isFromLogin: Boolean = false

    override fun bindView(smsConfirmationView: SMSConfirmationView) {
        mSmsConfirmationView = smsConfirmationView
        phone = smsConfirmationView.getPhone()
    }

    override fun onStart() {
        super.onStart()
        if (phone != null) {
            isPhoneFilled = true
            isFromLogin = true
            onSendSMSCodeClicked()
        } else {
            isFromLogin = false
            mSmsConfirmationView?.showPhoneInput()
        }
    }

    override fun unbindView() {
        mSmsConfirmationView = null
    }

    override fun getCountry(): String = loginInteractor.country

    override fun saveState(outState: Bundle?) {
        //empty
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        //empty
    }

    //region SMSConfirmationView.Callback

    override fun onConfirmationCodeTextChanged(confirmationCode: String) {
        if (confirmCode != null && confirmCode == confirmationCode.toLongOrNull()) {
            if (isFromLogin) {
                mSmsConfirmationView?.startDashboard()
            } else {
                mSmsConfirmationView?.close()
            }
        }
    }

    override fun onPhoneTextChanged(isFull: Boolean, phone: String) {
        if (isFull) {
            isPhoneFilled = isFull
            this.phone = phone
        }
    }

    override fun onSendSMSCodeClicked() {
        if (phone == null || !isPhoneFilled) {
            mSmsConfirmationView?.showPhoneNotFullError()
        } else {
            addSubscription(
                loginInteractor.sendSMSConfirmation(phone, mSmsConfirmationView?.getSMSText())
                    .doOnSubscribe {
                        mSmsConfirmationView?.showProgress()
                    }
                    .doOnUnsubscribe {
                        mSmsConfirmationView?.hideProgress()
                    }
                    .subscribe(
                        this::handleSMSCodeSent, this::handleSMSCodeSendingError
                    )
            )
        }
    }

    override fun onCheckSMSCodeClicked(confirmationCode: String) {
        if (confirmCode != null && confirmCode == confirmationCode.toLongOrNull()) {
            if (isFromLogin) {
                mSmsConfirmationView?.startDashboard()
            } else {
                mSmsConfirmationView?.close()
            }
        } else {
            mSmsConfirmationView?.showCodeConfirmationError()
        }
    }

    //endregion

    private fun handleSMSCodeSent(response: SMSConfirmationResponseModel) {
        confirmCode = response.code
        mSmsConfirmationView?.showCodeInput()
        mSmsConfirmationView?.showCodeSent()
    }

    private fun handleSMSCodeSendingError(throwable: Throwable) {
        mSmsConfirmationView?.showCodeSentError()
    }

}