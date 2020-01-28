package com.verbatoria.ui.login.sms

import com.verbatoria.business.login.sms.SMSLoginInteractor
import com.verbatoria.infrastructure.extensions.formatToTimerTime
import com.verbatoria.ui.base.BasePresenter
import org.slf4j.LoggerFactory
import java.util.*

/**
 * @author n.remnev
 */

private const val TRY_AGAIN_SEND_CODE_INTERVAL = 180000L
private const val TRY_AGAIN_SEND_CODE_UPDATE_TEXT_INTERVAL = 1000L
private const val SEND_SMS_CODE_TEXT = "VERBATORIA code: "

class SMSLoginPresenter(
    val phoneFromLogin: String,
    private val smsLoginInteractor: SMSLoginInteractor
) : BasePresenter<SMSLoginView>(), SMSLoginView.Callback {

    private val logger = LoggerFactory.getLogger("SMSLoginPresenter")

    private var phone: String = phoneFromLogin
    private var code: String = ""

    private var checkingCode: String = ""

    private var tryAgainSendCodeTimer: Timer? = null

    private var tryAgainSendCodeTimerTask: TimerTask = createTryAgainSendCodeTimerTask()

    private var tryAgainSendCodeTimerStartTime: Long = System.currentTimeMillis()

    init {
        if (phone.isBlank()) {
            getCurrentPhone()
        } else {
            sendSMSCode()
        }
    }

    override fun onAttachView(view: SMSLoginView) {
        super.onAttachView(view)
        if (tryAgainSendCodeTimer != null) {
            view.apply {
                hideSendCodeButton()
                showCodeField()
                updateTimerText()
                showTimerText()
            }
        } else {
            view.showProgressForSendCode()
        }
    }

    //region SMSLoginView.Callback

    override fun onPhoneTextChanged(phone: String) {
        if (phone == this.phone) return
        this.phone = phone
        when {
            this.phone.isBlank() -> view?.apply {
                setSendCodeButtonDisabled()
            }
            this.phone.isNotBlank() -> view?.apply {
                setSendCodeButtonEnabled()
            }
        }
    }

    override fun onCodeTextChanged(code: String) {
        if (code == this.code) return
        this.code = code
        when {
            this.code == checkingCode -> view?.openDashboard()
            this.code.isNotBlank() -> view?.apply {
                showClearCodeButton()
                setSendCodeButtonEnabled()
            }
        }
    }

    override fun onCodeClearClicked() {
        code = ""
        view?.apply {
            setCode(code)
            setSendCodeButtonDisabled()
            hideClearCodeButton()
        }
    }

    override fun onSendCodeButtonClicked() {
        sendSMSCode()
    }

    override fun onRepeatButtonClicked() {
        view?.apply {
            hideCodeField()
            hideTimerText()
            hideRepeatButton()
            showSendCodeButton()
        }
        sendSMSCode()
    }

    //endregion

    private fun sendSMSCode() {
        view?.showProgressForSendCode()
        val phone = phone.replace(" ", "")
        smsLoginInteractor.sendSMSCode(phone, SEND_SMS_CODE_TEXT)
            .subscribe({ checkingCode ->
                tryAgainSendCodeTimerStartTime = System.currentTimeMillis()
                this.checkingCode = checkingCode
                view?.apply {
                    hideProgressForSendCode()
                    hideSendCodeButton()
                    showCodeField()
                    showTimerText()
                }
                restartTryAgainSendCodeTimer()
            }, { error ->
                logger.error("Send sms code error occurred", error)
                view?.showErrorSnackbar(error.localizedMessage ?: "Internet connection error occurred")
                view?.hideProgressForSendCode()
            })
            .let(::addDisposable)
    }

    private fun getCurrentPhone() {
        smsLoginInteractor.getCurrentPhone()
            .subscribe({ phone ->
                this.phone = phone
                if (phone.isNotBlank()) {
                    view?.setSendCodeButtonEnabled()
                    sendSMSCode()
                }
            }, { error ->
                logger.error("Get current phone error occurred", error)
                view?.showErrorSnackbar(error.localizedMessage ?: "Get current phone error occurred")
            })
            .let(::addDisposable)
    }


    private fun createTryAgainSendCodeTimerTask(): TimerTask =
        object : TimerTask() {
            override fun run() {
                updateTimerText()
            }
        }

    private fun restartTryAgainSendCodeTimer() {
        tryAgainSendCodeTimer?.cancel()
        tryAgainSendCodeTimer = null
        tryAgainSendCodeTimer = Timer()

        tryAgainSendCodeTimerTask.cancel()
        tryAgainSendCodeTimerTask = createTryAgainSendCodeTimerTask()
        tryAgainSendCodeTimer?.schedule(
            tryAgainSendCodeTimerTask,
            0L,
            TRY_AGAIN_SEND_CODE_UPDATE_TEXT_INTERVAL
        )
    }

    private fun updateTimerText() {
        val timeFromStartTimer = System.currentTimeMillis() - tryAgainSendCodeTimerStartTime
        if (timeFromStartTimer < TRY_AGAIN_SEND_CODE_INTERVAL) {
            view?.setTimerText(Date(TRY_AGAIN_SEND_CODE_INTERVAL - timeFromStartTimer).formatToTimerTime())
        } else {
            tryAgainSendCodeTimer?.cancel()
            tryAgainSendCodeTimer = null
            view?.apply {
                hideTimerText()
                showRepeatButton()
            }
        }
    }

}