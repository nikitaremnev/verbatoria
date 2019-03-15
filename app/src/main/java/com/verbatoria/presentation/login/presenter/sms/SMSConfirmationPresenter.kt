package com.verbatoria.presentation.login.presenter.sms

import android.os.Bundle
import com.verbatoria.infrastructure.BasePresenter
import com.verbatoria.presentation.login.view.sms.SMSConfirmationView

interface SMSConfirmationPresenter {

    fun bindView(smsConfirmationView: SMSConfirmationView)
    fun unbindView()

}

class SMSConfirmationPresenterImpl : BasePresenter(), SMSConfirmationPresenter {

    override fun bindView(smsConfirmationView: SMSConfirmationView) {

    }

    override fun unbindView() {

    }

    override fun saveState(outState: Bundle?) {
        //empty
    }

    override fun restoreState(savedInstanceState: Bundle?) {
        //empty
    }

}