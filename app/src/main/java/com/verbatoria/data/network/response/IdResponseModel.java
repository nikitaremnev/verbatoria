package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель ответа от сервера на запрос событий нейрометриста
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)

public class IdResponseModel {

    private String mId;

    public IdResponseModel() {

    }

    @JsonGetter("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        IdResponseModel that = (IdResponseModel) o;
        return Objects.equal(mId, that.mId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .toString();
    }
}
