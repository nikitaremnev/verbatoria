package com.verbatoria.data.network.api;

/**
 *
 * Константы для API
 *
 * @author nikitaremnev
 */
class APIConstants {

    private static final int API_VERSION = 1;
    static final String API_BASE_URL = "http://verbatoria.ru/api/v" + API_VERSION + "/";

    static final String TOKEN_HEADER_KEY = "token";
    static final String CLIENT_ID_PATH_KEY = "client_id";
    static final String CHILD_ID_PATH_KEY = "child_id";
    static final String JSON_PATH = ".json";

    static final String LOGIN_URL = "session" + JSON_PATH;
    static final String VERBATOLOG_INFO_URL = "verbatolog/current" + JSON_PATH;
    static final String VERBATOLOG_EVENTS_URL = "verbatolog/current/events" + JSON_PATH;

    //session


    //clients


    //children

}
