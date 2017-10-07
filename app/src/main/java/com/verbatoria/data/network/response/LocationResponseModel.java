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
public class LocationResponseModel {

    private String mId;

    private String mName;

    private String mAddress;

    private PartnerResponseModel mPartner;

    private CityResponseModel mCity;

    public LocationResponseModel() {

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

    @JsonGetter("address")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getAddress() {
        return mAddress;
    }

    public LocationResponseModel setAddress(String address) {
        mAddress = address;
        return this;
    }

    @JsonGetter("partner")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public PartnerResponseModel getPartner() {
        return mPartner;
    }

    public LocationResponseModel setPartner(PartnerResponseModel partner) {
        mPartner = partner;
        return this;
    }

    @JsonGetter("city")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public CityResponseModel getCity() {
        return mCity;
    }

    public LocationResponseModel setCity(CityResponseModel city) {
        mCity = city;
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
        LocationResponseModel that = (LocationResponseModel) o;
        return Objects.equal(mId, that.mId) &&
                Objects.equal(mName, that.mName) &&
                Objects.equal(mAddress, that.mAddress) &&
                Objects.equal(mPartner, that.mPartner) &&
                Objects.equal(mCity, that.mCity);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mId, mName, mAddress, mPartner, mCity);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mId", mId)
                .add("mName", mName)
                .add("mAddress", mAddress)
                .add("mPartner", mPartner)
                .add("mCity", mCity)
                .toString();
    }
}

