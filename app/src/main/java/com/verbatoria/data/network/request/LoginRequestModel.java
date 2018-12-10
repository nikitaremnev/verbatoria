package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 *
 * Модель запроса авторизации
 *
 * @author nikitaremnev
 */
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
        return Objects.equals(mPhone, that.mPhone) &&
                Objects.equals(mPassword, that.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mPhone, mPassword);
    }


}
