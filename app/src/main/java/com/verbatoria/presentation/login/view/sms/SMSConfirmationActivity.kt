package com.verbatoria.presentation.login.view.sms

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.remnev.verbatoria.R
import com.verbatoria.VerbatoriaApplication
import com.verbatoria.di.login.LoginModule
import com.verbatoria.infrastructure.BaseActivity
import com.verbatoria.infrastructure.BasePresenter
import com.verbatoria.presentation.login.presenter.sms.SMSConfirmationPresenter
import javax.inject.Inject

/**
 * @author n.remnev
 */

interface SMSConfirmationView {

}

class SMSConfirmationActivity : BaseActivity(), SMSConfirmationView {

    companion object {

        fun newInstance(context: Context): Intent =
            Intent(context, SMSConfirmationActivity::class.java)

    }

    @Inject
    lateinit var mSmsConfirmationPresenter: SMSConfirmationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        VerbatoriaApplication
            .getApplicationComponent()
            .addModule(LoginModule())
            .inject(this)

        //initialize views
        setContentView(R.layout.activity_recovery)

        //bind views
        setPresenter(mSmsConfirmationPresenter as BasePresenter)
        mSmsConfirmationPresenter.bindView(this)

        super.onCreate(savedInstanceState)
    }

    override fun setUpViews() {

    }

}