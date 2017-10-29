package com.verbatoria.business.dashboard.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

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
public class EventModel implements Parcelable {

    private String mId;

    private Date mStartAt;

    private Date mEndAt;

    private ChildModel mChild;

    private ReportModel mReport;

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
        return DateUtils.toString(mStartAt);
    }

    public void setStartAt(Date startAt) {
        mStartAt = startAt;
    }

    public Date getEndAt() {
        return mEndAt;
    }

    public String getEndAtDateString() throws ParseException {
        return DateUtils.toString(mEndAt);
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
        int fullAge = DateUtils.getYearsFromDate(getChild().getBirthday());
        if (fullAge < 1) {
            String unknown = context.getString(R.string.dashboard_unknown);
            return String.format(context.getString(R.string.dashboard_age), unknown);
        } else {
            return String.format(context.getString(R.string.dashboard_age), Integer.toString(fullAge));
        }
    }

    public String getReportId(Context context) {
        String reportId = mReport != null ? mReport.getReportId() : "";
        if (TextUtils.isEmpty(reportId)) {
            return context.getString(R.string.dashboard_unknown);
        } else {
            return reportId;
        }
    }


    public String getEventTime() {
        return DateUtils.toDDMMString(getStartAt()) + " " + DateUtils.periodToString(getStartAt(), getEndAt());
    }

    public boolean hasTime() {
        return mStartAt != null && mEndAt != null;
    }

    public ReportModel getReport() {
        return mReport;
    }

    public EventModel setReport(ReportModel report) {
        mReport = report;
        return this;
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
                Objects.equal(mChild, that.mChild) &&
                Objects.equal(mReport, that.mReport);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mStartAt, mEndAt, mChild, mReport);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mStartAt", mStartAt)
                .add("mEndAt", mEndAt)
                .add("mChild", mChild)
                .add("mReport", mReport)
                .toString();
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeLong(this.mStartAt != null ? this.mStartAt.getTime() : -1);
        dest.writeLong(this.mEndAt != null ? this.mEndAt.getTime() : -1);
        dest.writeParcelable(this.mChild, flags);
        dest.writeParcelable(this.mReport, flags);
    }

    protected EventModel(Parcel in) {
        this.mId = in.readString();
        long tmpMStartAt = in.readLong();
        this.mStartAt = tmpMStartAt == -1 ? null : new Date(tmpMStartAt);
        long tmpMEndAt = in.readLong();
        this.mEndAt = tmpMEndAt == -1 ? null : new Date(tmpMEndAt);
        this.mChild = in.readParcelable(ChildModel.class.getClassLoader());
        this.mReport = in.readParcelable(ReportModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<EventModel> CREATOR = new Parcelable.Creator<EventModel>() {
        @Override
        public EventModel createFromParcel(Parcel source) {
            return new EventModel(source);
        }

        @Override
        public EventModel[] newArray(int size) {
            return new EventModel[size];
        }
    };
}
