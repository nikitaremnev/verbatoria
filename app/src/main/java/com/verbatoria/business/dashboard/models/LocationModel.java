package com.verbatoria.business.dashboard.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

import java.util.List;

/**
 * Реализация модели для записи нейрометрии
 *
 * @author nikitaremnev
 */
public class LocationModel implements Parcelable {

    private String mId;

    private String mName;

    private String mAddress;

    private String mLocale;

    private List<String> mAvailableLocales;

    private String mPartner;

    private String mCity;

    private String mCountry;

    private boolean mIsUpdateLocaleRequired;

    public LocationModel() {

    }

    public String getId() {
        return mId;
    }

    public LocationModel setId(String id) {
        mId = id;
        return this;
    }

    public String getName() {
        return mName;
    }

    public LocationModel setName(String name) {
        mName = name;
        return this;
    }

    public String getAddress() {
        return mAddress;
    }

    public LocationModel setAddress(String address) {
        mAddress = address;
        return this;
    }

    public String getLocale() {
        return mLocale;
    }

    public LocationModel setLocale(String locale) {
        this.mLocale = locale;
        return this;
    }

    public List<String> getAvailableLocales() {
        return mAvailableLocales;
    }

    public LocationModel setAvailableLocales(List<String> availableLocales) {
        this.mAvailableLocales = availableLocales;
        return this;
    }

    public String getPartner() {
        return mPartner;
    }

    public LocationModel setPartner(String partner) {
        mPartner = partner;
        return this;
    }

    public String getCity() {
        return mCity;
    }

    public LocationModel setCity(String city) {
        mCity = city;
        return this;
    }

    public String getCountry() {
        return mCountry;
    }

    public LocationModel setCountry(String country) {
        mCountry = country;
        return this;
    }

    public String getCityCountry() {
        return mCity + ", " + mCountry;
    }

    public boolean isUpdateLocaleRequired() {
        return mIsUpdateLocaleRequired;
    }

    public void setUpdateLocaleRequired(boolean updateLocaleRequired) {
        this.mIsUpdateLocaleRequired = updateLocaleRequired;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LocationModel that = (LocationModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mName, that.mName) &&
                Objects.equal(mAddress, that.mAddress) &&
                Objects.equal(mLocale, that.mLocale) &&
                Objects.equal(mAvailableLocales, that.mAvailableLocales) &&
                Objects.equal(mPartner, that.mPartner) &&
                Objects.equal(mCity, that.mCity) &&
                Objects.equal(mCountry, that.mCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mAddress, mLocale, mAvailableLocales, mPartner, mCity, mCountry);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mName", mName)
                .add("mAddress", mAddress)
                .add("mLocale", mLocale)
                .add("mAvailableLocales", mAvailableLocales)
                .add("mPartner", mPartner)
                .add("mCity", mCity)
                .add("mCountry", mCountry)
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
        dest.writeString(this.mAddress);
        dest.writeString(this.mLocale);
        dest.writeList(this.mAvailableLocales);
        dest.writeString(this.mPartner);
        dest.writeString(this.mCity);
        dest.writeString(this.mCountry);
    }

    protected LocationModel(Parcel in) {
        this.mId = in.readString();
        this.mName = in.readString();
        this.mAddress = in.readString();
        this.mLocale = in.readString();
        in.readStringList(this.mAvailableLocales);
        this.mPartner = in.readString();
        this.mCity = in.readString();
        this.mCountry = in.readString();
    }

    public static final Parcelable.Creator<LocationModel> CREATOR = new Parcelable.Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel source) {
            return new LocationModel(source);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };
}
