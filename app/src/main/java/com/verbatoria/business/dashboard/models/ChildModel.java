package com.verbatoria.business.dashboard.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.verbatoria.utils.DateUtils;

import java.util.Date;

import static com.verbatoria.presentation.calendar.view.add.children.age.ChildAgeDialogFragment.START_AGE;

/**
 * Реализация модели для ребенка, записанного на сеанс нейрометрии
 *
 * @author nikitaremnev
 */


public class ChildModel implements Parcelable {

    public static final String FEMALE_GENDER = "female";

    public static final String MALE_GENDER = "male";

    private String mId;

    private String mClientId;

    private String mName;

    private Date mBirthday;

    private String mGender;

    public ChildModel() {
    }

    public static boolean isFemale(String gender) {
        return gender != null && gender.equals(FEMALE_GENDER);
    }

    public static boolean isMale(String gender) {
        return gender != null && gender.equals(MALE_GENDER);
    }

    public String getId() {
        return mId;
    }

    public ChildModel setId(String id) {
        mId = id;
        return this;
    }

    public String getName() {
        return mName;
    }

    public ChildModel setName(String name) {
        mName = name;
        return this;
    }

    public Date getBirthday() {
        if (mBirthday == null) {
            mBirthday = new Date();
        }
        return mBirthday;
    }

    public String getBirthdayDateString() {
        try {
            int yearsFromDate = getAge();
            if (yearsFromDate < START_AGE) {
                return "";
            }
            return String.valueOf(yearsFromDate);
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    public int getAge() {
        return DateUtils.getYearsFromDate(getBirthday());
    }

    public ChildModel setBirthday(Date birthday) {
        mBirthday = birthday;
        return this;
    }

    public String getClientId() {
        return mClientId;
    }

    public ChildModel setClientId(String clientId) {
        mClientId = clientId;
        return this;
    }

    public String getGender() {
        return mGender;
    }

    public ChildModel setGender(String gender) {
        mGender = gender;
        return this;
    }

    public boolean isFull() {
        return !(TextUtils.isEmpty(mName) || mBirthday == null || mGender == null && DateUtils.getYearsFromDate(mBirthday) < START_AGE);
    }

    public boolean isMale() {
        return mGender != null && mGender.equals(MALE_GENDER);
    }

    public boolean isFemale() {
        return mGender != null && mGender.equals(FEMALE_GENDER);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChildModel that = (ChildModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mName, that.mName) &&
                Objects.equal(mBirthday, that.mBirthday) &&
                Objects.equal(mClientId, that.mClientId) &&
                Objects.equal(mGender, that.mGender);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mBirthday, mClientId, mGender);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mName", mName)
                .add("mBirthday", mBirthday)
                .add("mClientId", mClientId)
                .add("mGender", mGender)
                .toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
        dest.writeString(this.mName);
        dest.writeLong(this.mBirthday != null ? this.mBirthday.getTime() : -1);
        dest.writeString(this.mClientId);
        dest.writeString(this.mGender);
    }

    protected ChildModel(Parcel in) {
        this.mId = in.readString();
        this.mName = in.readString();
        long tmpMBirthday = in.readLong();
        this.mBirthday = tmpMBirthday == -1 ? null : new Date(tmpMBirthday);
        this.mClientId = in.readString();
        this.mGender = in.readString();
    }

    public static final Parcelable.Creator<ChildModel> CREATOR = new Parcelable.Creator<ChildModel>() {
        @Override
        public ChildModel createFromParcel(Parcel source) {
            return new ChildModel(source);
        }

        @Override
        public ChildModel[] newArray(int size) {
            return new ChildModel[size];
        }
    };
}
