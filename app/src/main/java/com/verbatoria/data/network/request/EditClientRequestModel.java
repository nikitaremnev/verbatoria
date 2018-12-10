package com.verbatoria.data.network.request;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 * @author nikitaremnev
 */

public class EditClientRequestModel implements Parcelable {

    private ClientRequestModel mClient;

    public EditClientRequestModel() {

    }

    @JsonGetter("client")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ClientRequestModel getClient() {
        return mClient;
    }

    public EditClientRequestModel setClient(ClientRequestModel client) {
        mClient = client;
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
        EditClientRequestModel that = (EditClientRequestModel) o;
        return Objects.equals(mClient, that.mClient);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mClient);
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mClient, flags);
    }

    protected EditClientRequestModel(Parcel in) {
        this.mClient = in.readParcelable(ClientRequestModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<EditClientRequestModel> CREATOR = new Parcelable.Creator<EditClientRequestModel>() {
        @Override
        public EditClientRequestModel createFromParcel(Parcel source) {
            return new EditClientRequestModel(source);
        }

        @Override
        public EditClientRequestModel[] newArray(int size) {
            return new EditClientRequestModel[size];
        }
    };
}
