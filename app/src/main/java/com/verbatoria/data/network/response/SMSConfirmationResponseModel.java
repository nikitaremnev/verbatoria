package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Objects;

/**
 *
 * Модель ответа от сервера - подтверждение смс
 *
 * @author nikitaremnev
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class SMSConfirmationResponseModel {

    private String mResult;

    private String mGUID;

    private Long mCode;

    public SMSConfirmationResponseModel() {

    }

    @JsonGetter("result")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getResult() {
        return mResult;
    }

    public SMSConfirmationResponseModel setResult(String result) {
        mResult = result;
        return this;
    }

    @JsonGetter("guid")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGUID() {
        return mGUID;
    }

    public SMSConfirmationResponseModel setGUID(String guid) {
        mGUID = guid;
        return this;
    }

    @JsonGetter("code")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long getCode() {
        return mCode;
    }

    public SMSConfirmationResponseModel setCode(Long code) {
        mCode = code;
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
        SMSConfirmationResponseModel that = (SMSConfirmationResponseModel) o;
        return Objects.equals(mResult, that.mResult) &&
                Objects.equals(mGUID, that.mGUID) &&
                Objects.equals(mCode, that.mCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mResult, mGUID, mCode);
    }

}
