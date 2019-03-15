package com.verbatoria.business.session.activities;

import android.content.Context;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.presentation.login.view.sms.SMSConfirmationActivity;
import javax.inject.Inject;

/**
 * Таймер для неактивности - через 30 минут стартуем смс подтверждение
 *
 * @author nikitaremnev
 */
public class UserInteractionTimerTask {

    private static final long USER_INTERACTION_INTERVAL = 1000 * 60 * 1;

    @Inject
    public Context mContext;

    private long lastInteractionTime = System.currentTimeMillis();

    public UserInteractionTimerTask() {
        VerbatoriaApplication.getApplicationComponent().inject(this);
    }

    public void updateLastInteractionTime() {
        if (System.currentTimeMillis() - lastInteractionTime > USER_INTERACTION_INTERVAL) {
            startSMSConfirmation();
        } else {
            lastInteractionTime = System.currentTimeMillis();
        }
    }

    private void startSMSConfirmation() {
        mContext.startActivity(SMSConfirmationActivity.Companion.newInstance(mContext));
    }

}