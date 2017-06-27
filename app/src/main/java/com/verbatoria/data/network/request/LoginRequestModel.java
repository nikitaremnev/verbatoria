package com.verbatoria.data.network.request;

import com.bluelinelabs.logansquare.annotation.JsonObject;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель запроса авторизации
 *
 * @author nikitaremnev
 */
@JsonObject
public class LoginRequestModel {

    private String mPhone;

    private String mPassword;

    public LoginRequestModel() {

    }

    public LoginRequestModel(String phone, String password) {
        mPhone = phone;
        mPassword = password;
    }

    @JsonGetter("phone")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPhone() {
        return mPhone;
    }

    public LoginRequestModel setPhone(String phone) {
        mPhone = phone;
        return this;
    }

    @JsonGetter("password")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPassword() {
        return mPassword;
    }

    public LoginRequestModel setPassword(String password) {
        mPassword = password;
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
        LoginRequestModel that = (LoginRequestModel) o;
        return Objects.equal(mPhone, that.mPhone) &&
                Objects.equal(mPassword, that.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mPhone, mPassword);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mPhone", mPhone)
                .add("mPassword", mPassword)
                .toString();
    }
}
