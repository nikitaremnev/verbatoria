package com.verbatoria.data.network.response;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Objects;

/**
 *
 * Модель ответа от сервера на запрос авторизации
 *
 * @author nikitaremnev
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResponseModel {

    public static final String CREATED_MESSAGE = "created";

    private String mMessage;

    public MessageResponseModel() {

    }

    public MessageResponseModel(String message) {
        mMessage = message;
    }

    @JsonGetter("message")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String getMessage() {
        return mMessage;
    }

    public MessageResponseModel setMessage(String message) {
        mMessage = message;
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
        MessageResponseModel that = (MessageResponseModel) o;
        return Objects.equals(mMessage, that.mMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(mMessage);
    }

    @Override
    public String toString() {
        return Objects.toString(this);
    }

}
