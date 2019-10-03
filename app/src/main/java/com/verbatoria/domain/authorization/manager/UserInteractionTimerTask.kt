package com.verbatoria.domain.authorization.manager

import android.content.Context
import com.verbatoria.VerbatoriaKtApplication
import com.verbatoria.domain.authorization.repository.AuthorizationRepository
import com.verbatoria.infrastructure.extensions.MILLISECONDS_IN_SECOND
import com.verbatoria.ui.login.sms.SMSLoginActivity
import com.verbatoria.utils.CountryHelper
import javax.inject.Inject

/**
 * Таймер для неактивности - через 30 минут стартуем смс подтверждение
 *
 * @author nikitaremnev
 */

private const val USER_INTERACTION_INTERVAL = (MILLISECONDS_IN_SECOND * 60 * 60).toLong()

class UserInteractionTimerTask {

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var authorizationRepository: AuthorizationRepository

    private var lastInteractionTime = System.currentTimeMillis()
    private var isSmsConfirmationStarted = false
    private val currentCountry: String

    init {
        VerbatoriaKtApplication.injector.inject(this)
        currentCountry = authorizationRepository.getCurrentCountry()
    }

    fun updateLastInteractionTime() {
        if (!CountryHelper.isCountryRequireSkipSMSConfirmation(currentCountry) && System.currentTimeMillis() - lastInteractionTime > USER_INTERACTION_INTERVAL) {
            startSMSConfirmation()
        } else {
            lastInteractionTime = System.currentTimeMillis()
        }
    }

    fun dropTimerTaskState() {
        isSmsConfirmationStarted = false
        lastInteractionTime = System.currentTimeMillis()
    }

    private fun startSMSConfirmation() {
        if (!isSmsConfirmationStarted) {
            context.startActivity(SMSLoginActivity.newInstance(context))
        }
        isSmsConfirmationStarted = true
    }

}