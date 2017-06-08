package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель ответа от сервера на запрос авторизации
 *
 * @author nikitaremnev
 */

public class LoginResponseModel {

    private String mAccessToken;

    private String mExpiresToken;

    public LoginResponseModel() {

    }

    @JsonGetter("access_token")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    @JsonGetter("expires_at")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getExpiresToken() {
        return mExpiresToken;
    }

    public void setExpiresToken(String expiresToken) {
        mExpiresToken = expiresToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginResponseModel that = (LoginResponseModel) o;
        return Objects.equal(mAccessToken, that.mAccessToken) &&
                Objects.equal(mExpiresToken, that.mExpiresToken);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mAccessToken, mExpiresToken);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mAccessToken", mAccessToken)
                .add("mExpiresToken", mExpiresToken)
                .toString();
    }
}
