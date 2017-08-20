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
    static final String SESSION_ID_PATH_KEY = "session_id";

    static final String LOGIN_URL = "session.json";
    static final String VERBATOLOG_INFO_URL = "verbatolog/current.json";
    static final String VERBATOLOG_EVENTS_URL = "verbatolog/current/events.json";
    static final String START_SESSION_URL = "reports.json";
    static final String ADD_RESULTS_TO_SESSION_URL = "reports/{" + SESSION_ID_PATH_KEY + "}/measurements/upload.json";

}
