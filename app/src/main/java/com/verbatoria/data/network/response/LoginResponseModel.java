package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 *
 * Модель ответа от сервера на запрос авторизации
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseModel {

    private String mAccessToken;

    private String mExpiresToken;

    private String mStatus;

    private String mLocationId;

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

    @JsonGetter("status")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getStatus() {
        return mStatus;
    }

    @JsonGetter("location_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocationId() {
        return mLocationId;
    }

    public void setLocationId(String locationId) {
        mLocationId = locationId;
    }

    public LoginResponseModel setStatus(String status) {
        mStatus = status;
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
        LoginResponseModel that = (LoginResponseModel) o;
        return Objects.equals(mAccessToken, that.mAccessToken) &&
                Objects.equals(mExpiresToken, that.mExpiresToken) &&
                Objects.equals(mStatus, that.mStatus) &&
                Objects.equals(mLocationId, that.mLocationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mAccessToken, mExpiresToken, mStatus, mLocationId);
    }


}
