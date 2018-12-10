package com.verbatoria.business.session.activities;

import android.content.Context;

import com.remnev.verbatoria.R;
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

    public void setTotalSeconds(long totalSeconds) {
        mTotalSeconds = totalSeconds;
    }

    public void setAllActivitiesSeconds(long allActivitiesSeconds) {
        mAllActivitiesSeconds = allActivitiesSeconds;
    }

    public void setFullActivitySeconds(long fullActivitySeconds) {
        mFullActivitySeconds = fullActivitySeconds;
    }

    public void setCurrentActivitySeconds(long currentActivitySeconds) {
        mCurrentActivitySeconds = currentActivitySeconds;
    }

    public void setStartActivityTime(long startActivityTime) {
        mStartActivityTime = startActivityTime;
    }

    public void setActivityActive(boolean activityActive) {
        mIsActivityActive = activityActive;
    }

    public long getTotalSeconds() {
        return mTotalSeconds;
    }

    public long getAllActivitiesSeconds() {
        return mAllActivitiesSeconds;
    }

    public long getFullActivitySeconds() {
        return mFullActivitySeconds;
    }

    public long getCurrentActivitySeconds() {
        return mCurrentActivitySeconds;
    }

    public long getStartActivityTime() {
        return mStartActivityTime;
    }

    public boolean isActivityActive() {
        return mIsActivityActive;
    }

    public ActivitiesTimerTask copy(ISessionInteractor.IActivitiesCallback activitiesCallback) {
        ActivitiesTimerTask activitiesTimerTask = new ActivitiesTimerTask(activitiesCallback);
        activitiesTimerTask.setTotalSeconds(mTotalSeconds);
        activitiesTimerTask.setAllActivitiesSeconds(mAllActivitiesSeconds);
        activitiesTimerTask.setFullActivitySeconds(mFullActivitySeconds);
        activitiesTimerTask.setCurrentActivitySeconds(mCurrentActivitySeconds);
        activitiesTimerTask.setStartActivityTime(mStartActivityTime);
        return activitiesTimerTask;
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