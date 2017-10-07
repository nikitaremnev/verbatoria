package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель ответа от сервера на запрос данных о локации - страна
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryResponseModel {

    private String mId;

    private String mName;

    public CountryResponseModel() {

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CountryResponseModel that = (CountryResponseModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mName, that.mName);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mName", mName)
                .toString();
    }
}

