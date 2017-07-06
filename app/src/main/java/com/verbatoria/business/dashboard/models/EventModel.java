package com.verbatoria.business.dashboard.models;

import android.content.Context;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.remnev.verbatoriamini.R;
import com.verbatoria.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Реализация модели для записи нейрометрии
 *
 * @author nikitaremnev
 */
public class EventModel {

    private String mId;

    private Date mStartAt;

    private Date mEndAt;

    private ChildModel mChild;

    public EventModel() {

    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Date getStartAt() {
        return mStartAt;
    }

    public String getStartAtDateString() throws ParseException {
        try {
            return DateUtils.toString(mStartAt);
        } catch (ParseException e) {
            throw e;
        }
    }

    public void setStartAt(Date startAt) {
        mStartAt = startAt;
    }

    public Date getEndAt() {
        return mEndAt;
    }

    public String getEndAtDateString() throws ParseException {
        try {
            return DateUtils.toString(mEndAt);
        } catch (ParseException e) {
            throw e;
        }
    }

    public void setEndAt(Date endAt) {
        mEndAt = endAt;
    }

    public ChildModel getChild() {
        return mChild;
    }

    public void setChild(ChildModel child) {
        mChild = child;
    }

    public String getFullAge(Context context) {
        return String.format(context.getString(R.string.dashboard_age),
                Integer.toString(DateUtils.getYearsFromDate(getChild().getBirthday())));
    }

    public String getEventTime() {
        return DateUtils.periodToString(getStartAt(), getEndAt());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EventModel that = (EventModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mStartAt, that.mStartAt) &&
                Objects.equal(mEndAt, that.mEndAt) &&
                Objects.equal(mChild, that.mChild);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mStartAt, mEndAt, mChild);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mStartAt", mStartAt)
                .add("mEndAt", mEndAt)
                .add("mChild", mChild)
                .toString();
    }
}
