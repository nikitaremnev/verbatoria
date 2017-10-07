package com.verbatoria.data.network.common;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.verbatoria.data.network.response.IdResponseModel;

import java.util.List;

/**
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ClientModel implements Parcelable {

    private String mId;

    private String mName;

    private String mEmail;

    private String mPhone;

    private List<IdResponseModel> mChildren;

    public ClientModel() {

    }

    public String getId() {
        return mId;
    }

    public ClientModel setId(String id) {
        mId = id;
        return this;
    }

    @JsonGetter("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() {
        return mName;
    }

    public ClientModel setName(String name) {
        mName = name;
        return this;
    }

    @JsonGetter("email")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getEmail() {
        return mEmail;
    }

    public ClientModel setEmail(String email) {
        mEmail = email;
        return this;
    }

    @JsonGetter("phone")
    @Nullable
    public String getPhone() {
        return mPhone;
    }

    public ClientModel setPhone(String phone) {
        mPhone = phone;
        return this;
    }

    @JsonGetter("children")
    @Nullable
    public List<IdResponseModel> getChildren() {
        return mChildren;
    }

    public ClientModel setChildren(List<IdResponseModel> children) {
        mChildren = children;
        return this;
    }

    public boolean isFull() {
        return !(TextUtils.isEmpty(mEmail) || TextUtils.isEmpty(mPhone) || TextUtils.isEmpty(mName));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ClientModel that = (ClientModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mName, that.mName) &&
                Objects.equal(mEmail, that.mEmail) &&
                Objects.equal(mPhone, that.mPhone) &&
                Objects.equal(mChildren, that.mChildren);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mEmail, mPhone, mChildren);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mName", mName)
                .add("mEmail", mEmail)
                .add("mPhone", mPhone)
                .add("mChildren", mChildren)
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
        dest.writeString(this.mEmail);
        dest.writeString(this.mPhone);
    }

    protected ClientModel(Parcel in) {
        this.mId = in.readString();
        this.mName = in.readString();
        this.mEmail = in.readString();
        this.mPhone = in.readString();
        in.readList(this.mChildren, IdResponseModel.class.getClassLoader());
    }

    public static final Parcelable.Creator<ClientModel> CREATOR = new Parcelable.Creator<ClientModel>() {
        @Override
        public ClientModel createFromParcel(Parcel source) {
            return new ClientModel(source);
        }

        @Override
        public ClientModel[] newArray(int size) {
            return new ClientModel[size];
        }
    };
}
