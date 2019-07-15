package com.verbatoria.ui.login.sms

import com.verbatoria.business.login.sms.SMSLoginInteractor
import com.verbatoria.ui.base.BasePresenter
import org.slf4j.LoggerFactory

/**
 * @author n.remnev
 */

private const val WAS_REPEAT_BUTTON_SHOWED_EXTRA = "repeatButton"

class SMSLoginPresenter(
    val phoneFromLogin: String,
    private val smsLoginInteractor: SMSLoginInteractor
) : BasePresenter<SMSLoginView>(), SMSLoginView.Callback {

    private val logger = LoggerFactory.getLogger("SMSLoginPresenter")

    //3 minutes
//    private val SMS_AGAIN_INTERVAL = (1000 * 60 * 3).toLong()
//
//    private var phone: String? = null
//
//    private var isPhoneFilled: Boolean = false
//
//    private var confirmCode: Long? = null
//
//    private var isFromLogin: Boolean = false
//
//    private var wasRepeatButtonShowed: Boolean = false
//
//    private var updateTimeTimer: Timer? = null
//
//    private var lastSMSSendTime: Long = 0L
//
//    private var repeatSMSTimerTask: TimerTask = createTimerTask()

    private var phone: String = phoneFromLogin
    private var code: String = ""

    private var country: String = ""

    init {
        getCurrentCountry()
    }

    override fun onAttachView(view: SMSLoginView) {
        super.onAttachView(view)
        view.setPhoneFormatterBasedOnCountry(country)
        view.setPhone(phone)
    }


//    override fun onAttachView(view: SMSLoginView) {
//        super.onAttachView(view)
//        lastSMSSendTime = smsLoginInteractor.lastSmsConfirmationTimeInMillis
//        if (phone != null) {
//            isPhoneFilled = true
//            isFromLogin = true
//            checkSmsConfirmationLastTimeIsOk()
//        } else {
//            val lastLogin = smsLoginInteractor.lastLogin
//            if (lastLogin.isNullOrEmpty()) {
//                isFromLogin = false
//                view.showPhoneInput()
//            } else {
//                phone = lastLogin
//                isPhoneFilled = true
//                isFromLogin = true
//                checkSmsConfirmationLastTimeIsOk()
//            }
//        }
//    }

//    fun getCountry(): String = smsLoginInteractor.country

    //region SMSConfirmationView.Callback

//    override fun onConfirmationCodeTextChanged(confirmationCode: String) {
//        if (confirmCode != null && confirmCode == confirmationCode.toLongOrNull()) {
//            repeatSMSTimerTask.cancel()
//            updateTimeTimer?.cancel()
//            updateTimeTimer = null
//            view?.stopTimer()
////
////            smsLoginInteractor.updateLastSmsConfirmationTime(0L)
////            smsLoginInteractor.saveSMSConfirmationCode(0L)
//
//            if (isFromLogin) {
//                view?.startDashboard()
//            } else {
//                view?.close()
//            }
//        }
//    }

//    override fun onPhoneTextChanged(isFull: Boolean, phone: String) {
//        if (isFull) {
//            isPhoneFilled = isFull
//            this.phone = phone
//        }
//    }

//    override fun onSendSMSCodeClicked() {
//        if (phone == null || !isPhoneFilled) {
//            view?.showPhoneNotFullError()
//        } else {
////            addSubscription(
////                loginInteractor.sendSMSConfirmation(phone, "")
////                    .doOnSubscribe {
////                        mSmsConfirmationView?.showProgressForLogin()
////                    }
////                    .doOnTerminate {
////                        mSmsConfirmationView?.hideProgress()
////                    }
////                    .subscribe(
////                        this::handleSMSCodeSent, this::handleSMSCodeSendingError
////                    )
////            )
//        }
//    }

//    override fun onRepeatSMSClicked() {
//        onSendSMSCodeClicked()
//    }
//
//    override fun onCheckSMSCodeClicked(confirmationCode: String) {
//        if (confirmCode != null && confirmCode == confirmationCode.toLongOrNull()) {
//            if (isFromLogin) {
//                view?.startDashboard()
//            } else {
//                view?.close()
//            }
//        } else {
//            view?.showCodeConfirmationError()
//        }
//    }

    //endregion

//    private fun checkSmsConfirmationLastTimeIsOk() {
//        if (System.currentTimeMillis() - lastSMSSendTime < SMS_AGAIN_INTERVAL) {
//            restartTimer()
//
////            confirmCode = smsLoginInteractor.smsConfirmationCode
//            view?.showCodeInput()
//            view?.hideRepeatButton()
//        } else {
//            if (!wasRepeatButtonShowed) {
//                onSendSMSCodeClicked()
//            } else {
//                view?.showCodeInput()
//                view?.showRepeatButton()
//            }
//        }
//    }

//    private fun handleSMSCodeSent(response: SMSConfirmationResponseModel) {
//        lastSMSSendTime = System.currentTimeMillis()

//        smsLoginInteractor.updateLastSmsConfirmationTime(lastSMSSendTime)
//        smsLoginInteractor.saveSMSConfirmationCode(response.code)

//        confirmCode = response.code
//        view?.showCodeInput()
//        view?.showCodeSent()
//        view?.hideRepeatButton()
//        wasRepeatButtonShowed = false
//
//        restartTimer()
//    }
//
//    private fun handleSMSCodeSendingError(throwable: Throwable) {
//        throwable.printStackTrace()
//        view?.showCodeSentError()
//    }
//
//    private fun restartTimer() {
//        updateTimeTimer?.cancel()
//        updateTimeTimer = null
//        updateTimeTimer = Timer()
//
//        repeatSMSTimerTask.cancel()
//        repeatSMSTimerTask = createTimerTask()
//        updateTimeTimer?.schedule(repeatSMSTimerTask, 0L, 1000L)
//    }

//    private fun createTimerTask() =
//            object : TimerTask() {
//                override fun run() {
//                    val timeDifference = System.currentTimeMillis() - lastSMSSendTime
//                    val date = Date(SMS_AGAIN_INTERVAL - timeDifference)
//                    if (timeDifference < SMS_AGAIN_INTERVAL) {
//                        view?.updateTimer(DateUtils.timeToString(date))
//                    } else {
//                        cancel()
//                        updateTimeTimer?.cancel()
//                        updateTimeTimer = null
//                        view?.stopTimer()
//                        wasRepeatButtonShowed = true
//                    }
//                }
//            }

    //region SMSLoginView.Callback

    override fun onPhoneTextChanged(phone: String) {

    }

    override fun onCodeTextChanged(code: String) {

    }

    override fun onPhoneClearClicked() {

    }

    override fun onCodeClearClicked() {

    }

    override fun onSendCodeButtonClicked() {

    }

    override fun onRepeatButtonClicked() {

    }

    override fun onRepeatSMSClicked() {

    }

    //endregion

    private fun getCurrentCountry() {
        smsLoginInteractor.getCurrentCountry()
            .subscribe({ country ->
                this.country = country
                view?.setPhoneFormatterBasedOnCountry(this.country)
            }, { error ->
                logger.error("Get current country error occurred", error)
                view?.showError(error.message ?: "Get current country error occurred")
            })
            .let(::addDisposable)
    }

}