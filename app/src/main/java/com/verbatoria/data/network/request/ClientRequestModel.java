package com.verbatoria.data.network.request;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.verbatoria.data.network.common.ClientModel;

/**
 * @author nikitaremnev
 */

public class ClientRequestModel {

    private ClientModel mClient;

    public ClientRequestModel() {

    }

    @JsonGetter("child")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public ClientModel getClient() {
        return mClient;
    }

    public ClientRequestModel setClient(ClientModel client) {
        mClient = client;
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
        ClientRequestModel that = (ClientRequestModel) o;
        return Objects.equal(mClient, that.mClient);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mClient);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mClient", mClient)
                .toString();
    }
}
