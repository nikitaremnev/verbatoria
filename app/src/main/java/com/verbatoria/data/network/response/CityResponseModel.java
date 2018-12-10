package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

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
        return Objects.equals(mId, that.mId) &&
                Objects.equals(mName, that.mName) &&
                Objects.equals(mCountry, that.mCountry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mName, mCountry);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }
}

