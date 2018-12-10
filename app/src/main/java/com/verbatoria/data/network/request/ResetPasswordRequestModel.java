package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 *
 * Модель запроса восстановления пароля
 *
 * @author nikitaremnev
 */
public class ResetPasswordRequestModel {

    private String mPhone;
    private String mRecoveryHash;
    private String mPassword;

    public ResetPasswordRequestModel() {

    }

    public ResetPasswordRequestModel(String phone) {
        mPhone = phone;
    }

    @JsonGetter("phone")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPhone() {
        return mPhone;
    }

    public ResetPasswordRequestModel setPhone(String phone) {
        mPhone = phone;
        return this;
    }

    @JsonGetter("recovery_hash")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getRecoveryHash() {
        return mRecoveryHash;
    }

    public ResetPasswordRequestModel setRecoveryHash(String recoveryHash) {
        mRecoveryHash = recoveryHash;
        return this;
    }

    @JsonGetter("password")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPassword() {
        return mPassword;
    }

    public ResetPasswordRequestModel setPassword(String password) {
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
        ResetPasswordRequestModel that = (ResetPasswordRequestModel) o;
        return Objects.equals(mPhone, that.mPhone) &&
                Objects.equals(mRecoveryHash, that.mRecoveryHash) &&
                Objects.equals(mPassword, that.mPassword);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mPhone, mRecoveryHash, mPassword);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}
