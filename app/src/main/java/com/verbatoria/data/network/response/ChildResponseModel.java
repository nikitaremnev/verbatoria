package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель ответа от сервера на запрос событий нейрометриста - сущность ребенка
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChildResponseModel {

    private String mId;

    private String mClientId;

    private String mName;

    private String mBirthday;

    private String mGender;

    public ChildResponseModel() {

    }

    @JsonGetter("id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    @JsonGetter("client_id")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getClientId() {
        return mClientId;
    }

    public void setClientId(String clientId) {
        mClientId = clientId;
    }

    @JsonGetter("name")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    @JsonGetter("birth_day")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String birthday) {
        mBirthday = birthday;
    }

    @JsonGetter("gender")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getGender() {
        return mGender;
    }

    public void setGender(String gender) {
        mGender = gender;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChildResponseModel that = (ChildResponseModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mClientId, that.mClientId) &&
                Objects.equal(mName, that.mName) &&
                Objects.equal(mBirthday, that.mBirthday) &&
                Objects.equal(mGender, that.mGender);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mClientId, mName, mBirthday, mGender);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mClientId", mClientId)
                .add("mName", mName)
                .add("mBirthday", mBirthday)
                .add("mGender", mGender)
                .toString();
    }
}

