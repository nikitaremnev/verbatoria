package com.verbatoria.data.network.response;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 *
 * Модель ответа от сервера на запрос событий нейрометриста
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class IdResponseModel implements Parcelable {

    private String mId;

    public IdResponseModel() {

    }

    @JsonGetter("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdResponseModel that = (IdResponseModel) o;
        return Objects.equals(mId, that.mId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mId);
    }

    protected IdResponseModel(Parcel in) {
        this.mId = in.readString();
    }

    public static final Parcelable.Creator<IdResponseModel> CREATOR = new Parcelable.Creator<IdResponseModel>() {
        @Override
        public IdResponseModel createFromParcel(Parcel source) {
            return new IdResponseModel(source);
        }

        @Override
        public IdResponseModel[] newArray(int size) {
            return new IdResponseModel[size];
        }
    };
}
