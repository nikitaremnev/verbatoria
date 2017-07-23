package com.verbatoria.business.token.models;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.verbatoria.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;

/**
 * Модель токена - возращается после регистрации
 *
 * @author nikitaremnev
 */
public class TokenModel {

    private String mAccessToken;

    private Date mExpireDate;

    public TokenModel() {

    }

    public String getAccessToken() {
        return mAccessToken;
    }

    public void setAccessToken(String accessToken) {
        mAccessToken = accessToken;
    }

    public Date getExpireDate() {
        return mExpireDate;
    }

    public String getExpireDateString() throws ParseException {
        return DateUtils.toString(mExpireDate);
    }

    public void setExpireDate(Date expireDate) {
        mExpireDate = expireDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TokenModel that = (TokenModel) o;
        return Objects.equal(mAccessToken, that.mAccessToken) &&
                Objects.equal(mExpireDate, that.mExpireDate);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mAccessToken, mExpireDate);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mAccessToken", mAccessToken)
                .add("mExpireDate", mExpireDate)
                .toString();
    }

}
