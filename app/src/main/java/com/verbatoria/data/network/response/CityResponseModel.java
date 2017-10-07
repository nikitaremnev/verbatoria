package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель ответа от сервера на запрос данных о локации - город
 *
 * @author nikitaremnev
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class CityResponseModel {

    private String mId;

    private String mName;

    private CountryResponseModel mCountry;

    public CityResponseModel() {

    }

    @JsonGetter("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @JsonGetter("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @JsonGetter("country")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public CountryResponseModel getCountry() {
        return mCountry;
    }

    public CityResponseModel setCountry(CountryResponseModel country) {
        mCountry = country;
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
        CityResponseModel that = (CityResponseModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mName, that.mName) &&
                Objects.equal(mCountry, that.mCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mCountry);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mName", mName)
                .add("mCountry", mCountry)
                .toString();
    }
}

