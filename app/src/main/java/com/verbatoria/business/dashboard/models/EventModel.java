package com.verbatoria.business.dashboard.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.remnev.verbatoria.R;
import com.verbatoria.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

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

    private boolean mIsInstantReport;

    private boolean mArchimed;

    private boolean mIsArchimedAllowed;

    public EventModel() {

    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public boolean isInstantReport() {
        return mIsInstantReport;
    }

    public void setIsInstantReport(boolean isInstantReport) {
        mIsInstantReport = isInstantReport;
    }

    public boolean getArchimed() {
        return mArchimed;
    }

    public void setArchimed(boolean archimed) {
        mArchimed = archimed;
    }

    public void setIsArchimedAllowed(boolean isArchimedAllowed) {
        mIsArchimedAllowed = isArchimedAllowed;
    }

    public boolean isArchimedAllowed() {
        return mIsArchimedAllowed;
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
        return DateUtils.toDDMMString(getStartAt()) + " " + DateUtils.timeHHmmToString(getStartAt());
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

    public boolean isInstantReportAvailable() {
        return getReport() != null && getReport().isSent();
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
        return Objects.equals(mId, that.mId) &&
                Objects.equals(mStartAt, that.mStartAt) &&
                Objects.equals(mEndAt, that.mEndAt) &&
                Objects.equals(mChild, that.mChild) &&
                Objects.equals(mReport, that.mReport) &&
                Objects.equals(mIsInstantReport, that.mIsInstantReport) &&
                Objects.equals(mArchimed, that.mArchimed) &&
                Objects.equals(mIsArchimedAllowed, that.mIsArchimedAllowed);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mStartAt, mEndAt, mChild, mReport, mIsInstantReport, mArchimed, mIsArchimedAllowed);
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
        dest.writeInt(this.mIsInstantReport ? 1 : 0);
        dest.writeInt(this.mArchimed ? 1 : 0);
        dest.writeInt(this.mIsArchimedAllowed ? 1 : 0);
    }

    protected EventModel(Parcel in) {
        this.mId = in.readString();
        long tmpMStartAt = in.readLong();
        this.mStartAt = tmpMStartAt == -1 ? null : new Date(tmpMStartAt);
        long tmpMEndAt = in.readLong();
        this.mEndAt = tmpMEndAt == -1 ? null : new Date(tmpMEndAt);
        this.mChild = in.readParcelable(ChildModel.class.getClassLoader());
        this.mReport = in.readParcelable(ReportModel.class.getClassLoader());
        this.mIsInstantReport = in.readInt() == 1;
        this.mArchimed = in.readInt() == 1;
        this.mIsArchimedAllowed = in.readInt() == 1;
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
