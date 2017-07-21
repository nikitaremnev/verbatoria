package com.verbatoria.business.session.timer;

import android.content.Context;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.utils.DateUtils;

import java.util.Date;
import java.util.TimerTask;

import javax.inject.Inject;

/**
 * Таймер для активностей во время сеанса
 *
 * @author nikitaremnev
 */
public class ActivitiesTimerTask extends TimerTask {

    public static final long ACTIVITIES_TIMER_DELAY = 1000;

    @Inject
    public Context mContext;

    private long mTotalSeconds;
    private long mAllActivitiesSeconds;
    private long mFullActivitySeconds;
    private long mCurrentActivitySeconds;
    private long mStartActivityTime;
    private boolean mIsActivityActive;

    private ISessionInteractor.IActivitiesCallback mActivitiesTimerCallback;

    public ActivitiesTimerTask(ISessionInteractor.IActivitiesCallback activitiesTimerCallback) {
        mActivitiesTimerCallback = activitiesTimerCallback;
        mAllActivitiesSeconds = mActivitiesTimerCallback.getDoneActivitiesTime();
        VerbatoriaApplication.getApplicationComponent().inject(this);
    }

    @Override
    public void run() {
        updateTimerVariables();
        mActivitiesTimerCallback.updateTimer(getTimerString());
    }

    private void updateTimerVariables() {
        mTotalSeconds++;
        if (mIsActivityActive) {
            mCurrentActivitySeconds++;
            mAllActivitiesSeconds++;
            mFullActivitySeconds++;
        }
    }

    private String getTimerString() {
        Date totalDate = getTotalDate();
        Date allActivitiesDate = getAllActivitiesDate();
        Date fullActivityDate = getFullActivityDate();
        Date currentActivityDate = getCurrentActivityDate();

        String timerString;
        if (mIsActivityActive) {
            timerString = String.format(mContext.getString(R.string.session_timer_string_full),
                    DateUtils.timeToString(totalDate),
                    DateUtils.timeToString(allActivitiesDate),
                    DateUtils.timeToString(fullActivityDate),
                    DateUtils.timeToString(currentActivityDate));
        } else {
            if (mAllActivitiesSeconds != 0) {
                timerString = String.format(mContext.getString(R.string.session_timer_string_only_full_and_loadings),
                        DateUtils.timeToString(totalDate),
                        DateUtils.timeToString(allActivitiesDate));
            } else {
                timerString = String.format(mContext.getString(R.string.session_timer_string_only_full),
                        DateUtils.timeToString(totalDate));
            }
        }
        return timerString;
    }

    private Date getTotalDate() {
        return new Date(mTotalSeconds * DateUtils.MILLIS_PER_SECOND);
    }

    private Date getAllActivitiesDate() {
        return new Date(mAllActivitiesSeconds * DateUtils.MILLIS_PER_SECOND);
    }

    private Date getFullActivityDate() {
        return new Date(mFullActivitySeconds * DateUtils.MILLIS_PER_SECOND);
    }

    private Date getCurrentActivityDate() {
        return new Date(mCurrentActivitySeconds * DateUtils.MILLIS_PER_SECOND);
    }
}