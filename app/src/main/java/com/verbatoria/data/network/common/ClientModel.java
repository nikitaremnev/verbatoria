package com.verbatoria.data.network.common;

import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 * @author nikitaremnev
 */

public class ClientModel {

    private String mName;

    private String mEmail;

    private String mPhone;

    public ClientModel() {

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

    @JsonGetter("email")
    @Nullable
    public String getPhone() {
        return mPhone;
    }

    public ClientModel setPhone(String phone) {
        mPhone = phone;
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
        ClientModel that = (ClientModel) o;
        return Objects.equal(mName, that.mName) &&
                Objects.equal(mEmail, that.mEmail) &&
                Objects.equal(mPhone, that.mPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mName, mEmail, mPhone);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mName", mName)
                .add("mEmail", mEmail)
                .add("mPhone", mPhone)
                .toString();
    }
}
