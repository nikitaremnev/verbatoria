package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;

/**
 *
 * Модель запроса авторизации
 *
 * @author nikitaremnev
 */
public class LocationLanguageRequestModel {

    private String mLocale;

    public LocationLanguageRequestModel() {

    }

    @JsonGetter("locale")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getLocale() {
        return mLocale;
    }

    public LocationLanguageRequestModel setLocale(String locale) {
        mLocale = locale;
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
        LocationLanguageRequestModel that = (LocationLanguageRequestModel) o;
        return Objects.equal(mLocale, that.mLocale);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mLocale);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mLocale", mLocale)
                .toString();
    }
}
