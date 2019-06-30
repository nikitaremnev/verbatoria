package com.verbatoria.ui.login.presenter.sms

import android.os.Bundle
import com.verbatoria.business.login.AuthorizationInteractor
import com.verbatoria.data.network.response.SMSConfirmationResponseModel
import com.verbatoria.infrastructure.BasePresenter
import com.verbatoria.ui.login.view.sms.SMSConfirmationView
import com.verbatoria.utils.DateUtils
import java.util.*

/**
 * @author n.remnev
 */

private const val WAS_REPEAT_BUTTON_SHOWED_EXTRA = "repeatButton"

interface SMSConfirmationPresenter {

    fun bindView(smsConfirmationView: SMSConfirmationView)
    fun unbindView()

    fun getCountry(): String

}

class SMSConfirmationPresenterImpl(
    private val loginInteractor: AuthorizationInteractor
) : BasePresenter(), SMSConfirmationPresenter, SMSConfirmationView.Callback {

    //3 minutes
    private val SMS_AGAIN_INTERVAL = (1000 * 60 * 3).toLong()

    private var mSmsConfirmationView: SMSConfirmationView? = null

    private var phone: String? = null

    private var isPhoneFilled: Boolean = false

    private var confirmCode: Long? = null

    private var isFromLogin: Boolean = false

    private var wasRepeatButtonShowed: Boolean = false

    private var updateTimeTimer: Timer? = null

    private var lastSMSSendTime: Long = 0L

    private var repeatSMSTimerTask: TimerTask = createTimerTask()

    override fun bindView(smsConfirmationView: SMSConfirmationView) {
        mSmsConfirmationView = smsConfirmationView
        phone = smsConfirmationView.getPhone()
    }

    override fun onStart() {
        super.onStart()
        lastSMSSendTime = loginInteractor.lastSmsConfirmationTimeInMillis
        if (phone != null) {
            isPhoneFilled = true
            isFromLogin = true
            checkSmsConfirmationLastTimeIsOk()
        } else {
            val lastLogin = loginInteractor.lastLogin
            if (lastLogin.isNullOrEmpty()) {
                isFromLogin = false
                mSmsConfirmationView?.showPhoneInput()
            } else {
                phone = lastLogin
                isPhoneFilled = true
                isFromLogin = true
                checkSmsConfirmationLastTimeIsOk()
            }
        }
    }

    override fun unbindView() {
        mSmsConfirmationView = null
    }

    override fun getCountry(): String = loginInteractor.country

    override fun saveState(outState: Bundle?) {
        outState?.putBoolean(WAS_REPEAT_BUTTON_SHOWED_EXTRA, wasRepeatButtonShowed)
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        wasRepeatButtonShowed = savedInstanceState?.getBoolean(WAS_REPEAT_BUTTON_SHOWED_EXTRA, false) ?: false
    }

    //region SMSConfirmationView.Callback

    override fun onConfirmationCodeTextChanged(confirmationCode: String) {
        if (confirmCode != null && confirmCode == confirmationCode.toLongOrNull()) {
            repeatSMSTimerTask.cancel()
            updateTimeTimer?.cancel()
            updateTimeTimer = null
            mSmsConfirmationView?.stopTimer()

            loginInteractor.updateLastSmsConfirmationTime(0L)
            loginInteractor.saveSMSConfirmationCode(0L)


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
//            addSubscription(
//                loginInteractor.sendSMSConfirmation(phone, "")
//                    .doOnSubscribe {
//                        mSmsConfirmationView?.showProgress()
//                    }
//                    .doOnTerminate {
//                        mSmsConfirmationView?.hideProgress()
//                    }
//                    .subscribe(
//                        this::handleSMSCodeSent, this::handleSMSCodeSendingError
//                    )
//            )
        }
    }

    override fun onRepeatSMSClicked() {
        onSendSMSCodeClicked()
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

    private fun checkSmsConfirmationLastTimeIsOk() {
        if (System.currentTimeMillis() - lastSMSSendTime < SMS_AGAIN_INTERVAL) {
            restartTimer()

            confirmCode = loginInteractor.smsConfirmationCode
            mSmsConfirmationView?.showCodeInput()
            mSmsConfirmationView?.hideRepeatButton()
        } else {
            if (!wasRepeatButtonShowed) {
                onSendSMSCodeClicked()
            } else {
                mSmsConfirmationView?.showCodeInput()
                mSmsConfirmationView?.showRepeatButton()
            }
        }
    }

    private fun handleSMSCodeSent(response: SMSConfirmationResponseModel) {
        lastSMSSendTime = System.currentTimeMillis()

        loginInteractor.updateLastSmsConfirmationTime(lastSMSSendTime)
        loginInteractor.saveSMSConfirmationCode(response.code)

        confirmCode = response.code
        mSmsConfirmationView?.showCodeInput()
        mSmsConfirmationView?.showCodeSent()
        mSmsConfirmationView?.hideRepeatButton()
        wasRepeatButtonShowed = false

        restartTimer()
    }

    private fun handleSMSCodeSendingError(throwable: Throwable) {
        throwable.printStackTrace()
        mSmsConfirmationView?.showCodeSentError()
    }

    private fun restartTimer() {
        updateTimeTimer?.cancel()
        updateTimeTimer = null
        updateTimeTimer = Timer()

        repeatSMSTimerTask.cancel()
        repeatSMSTimerTask = createTimerTask()
        updateTimeTimer?.schedule(repeatSMSTimerTask, 0L, 1000L)
    }

    private fun createTimerTask() =
            object : TimerTask() {
                override fun run() {
                    val timeDifference = System.currentTimeMillis() - lastSMSSendTime
                    val date = Date(SMS_AGAIN_INTERVAL - timeDifference)
                    if (timeDifference < SMS_AGAIN_INTERVAL) {
                        mSmsConfirmationView?.updateTimer(DateUtils.timeToString(date))
                    } else {
                        cancel()
                        updateTimeTimer?.cancel()
                        updateTimeTimer = null
                        mSmsConfirmationView?.stopTimer()
                        wasRepeatButtonShowed = true
                    }
                }
            }

}