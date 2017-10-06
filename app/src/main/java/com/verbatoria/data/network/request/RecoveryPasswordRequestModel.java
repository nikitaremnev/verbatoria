package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель запроса восстановления пароля
 *
 * @author nikitaremnev
 */
public class RecoveryPasswordRequestModel {

    private String mPhone;

    public RecoveryPasswordRequestModel() {

    }

    public RecoveryPasswordRequestModel(String phone) {
        mPhone = phone;
    }

    @JsonGetter("phone")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getPhone() {
        return mPhone;
    }

    public RecoveryPasswordRequestModel setPhone(String phone) {
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
        RecoveryPasswordRequestModel that = (RecoveryPasswordRequestModel) o;
        return Objects.equal(mPhone, that.mPhone);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mPhone);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mPhone", mPhone)
                .toString();
    }
}