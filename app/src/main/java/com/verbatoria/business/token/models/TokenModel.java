package com.verbatoria.business.token.models;

import com.verbatoria.utils.DateUtils;

import java.text.ParseException;
import java.util.Date;
import java.util.Objects;

/**
 * Модель токена - возращается после регистрации
 *
 * @author nikitaremnev
 */
public class TokenModel {

    private String mAccessToken;

    private Date mExpireDate;

    private String mStatus;

    private String mLocationId;

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

    public String getStatus() {
        return mStatus;
    }

    public TokenModel setStatus(String status) {
        mStatus = status;
        return this;
    }

    public String getLocationId() {
        return mLocationId;
    }

    public TokenModel setLocationId(String locationId) {
        mLocationId = locationId;
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
        TokenModel that = (TokenModel) o;
        return Objects.equals(mAccessToken, that.mAccessToken) &&
                Objects.equals(mExpireDate, that.mExpireDate) &&
                Objects.equals(mStatus, that.mStatus) &&
                Objects.equals(mLocationId, that.mLocationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mAccessToken, mExpireDate, mStatus, mLocationId);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }

}
