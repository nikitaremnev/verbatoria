package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;
import java.util.Objects;

/**
 *
 * Модель ответа от сервера на запрос событий нейрометриста - сущность локации
 *
 * @author nikitaremnev
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationResponseModel {

    public static final int IS_SCHOOL_VALUE = 1;

    private String mId;

    private String mName;

    private String mAddress;

    private String mLocale;

    private Integer isSchool;

    private List<String> mAvailableLocales;

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

    @JsonGetter("school")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public int isSchool() {
        return isSchool;
    }

    public void setIsSchool(int isSchool) {
        this.isSchool = isSchool;
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

    @JsonGetter("locale")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocale() {
        return mLocale;
    }

    public void setLocale(String locale) {
        this.mLocale = locale;
    }

    @JsonGetter("available_locales_array")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public List<String> getAvailableLocales() {
        return mAvailableLocales;
    }

    public void setAvailableLocales(List<String> availableLocales) {
        this.mAvailableLocales = availableLocales;
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
        return Objects.equals(mId, that.mId) &&
                Objects.equals(mName, that.mName) &&
                Objects.equals(isSchool, that.isSchool) &&
                Objects.equals(mAddress, that.mAddress) &&
                Objects.equals(mLocale, that.mLocale) &&
                Objects.equals(mAvailableLocales, that.mAvailableLocales) &&
                Objects.equals(mPartner, that.mPartner) &&
                Objects.equals(mCity, that.mCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mId, mName, isSchool, mAddress, mLocale, mAvailableLocales, mPartner, mCity);
    }


}

