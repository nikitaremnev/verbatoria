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

    //60-minutes, 1 hour
    private static final long USER_INTERACTION_INTERVAL = 1000 * 60 * 60;

    @Inject
    public Context mContext;

    private long lastInteractionTime = System.currentTimeMillis();
    private boolean isSmsConfirmationStarted = false;

    public UserInteractionTimerTask() {
        VerbatoriaApplication.getInjector().inject(this);
    }

    public void updateLastInteractionTime() {
        if (System.currentTimeMillis() - lastInteractionTime > USER_INTERACTION_INTERVAL) {
            startSMSConfirmation();
        } else {
            lastInteractionTime = System.currentTimeMillis();
        }
    }

    public void dropTimerTaskState() {
        isSmsConfirmationStarted = false;
        lastInteractionTime = System.currentTimeMillis();
    }

    private void startSMSConfirmation() {
        if (!isSmsConfirmationStarted) {
            mContext.startActivity(SMSConfirmationActivity.Companion.newInstance(mContext));
        }
        isSmsConfirmationStarted = true;
    }

}