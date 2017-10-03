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

    static final String VERBATOLOG_ID_PATH_KEY = "verbatolog";
    static final String TOKEN_HEADER_KEY = "token";
    static final String CLIENT_ID_PATH_KEY = "client_id";
    static final String CHILD_ID_PATH_KEY = "child_id";
    static final String JSON_PATH = ".json";

    static final String LOGIN_URL = "session" + JSON_PATH;
    static final String RECOVERY_PASSWORD_URL = VERBATOLOG_ID_PATH_KEY + "/restore_password" + JSON_PATH;
    static final String RESET_PASSWORD_URL = VERBATOLOG_ID_PATH_KEY + "/reset_password" + JSON_PATH;

    static final String VERBATOLOG_INFO_URL = VERBATOLOG_ID_PATH_KEY + "/current" + JSON_PATH;
    static final String VERBATOLOG_EVENTS_URL = VERBATOLOG_ID_PATH_KEY + "/current/events" + JSON_PATH;

    static final String ADD_CHILD_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}/children" + JSON_PATH;
    static final String EDIT_CHILD_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}/children/{" + CHILD_ID_PATH_KEY + "}" + JSON_PATH;
    static final String GET_CHILD_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}/children/{" + CHILD_ID_PATH_KEY + "}" + JSON_PATH;

    static final String ADD_CLIENT_URL = "clients" + JSON_PATH;
    static final String EDIT_CLIENT_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}" + JSON_PATH;
    static final String GET_CLIENT_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}" + JSON_PATH;

    static final String SESSION_ID_PATH_KEY = "session_id";

    static final String START_SESSION_URL = "reports" + JSON_PATH;
    static final String ADD_RESULTS_TO_SESSION_URL = "reports/{" + SESSION_ID_PATH_KEY + "}/measurements/upload" + JSON_PATH;
    static final String FINISH_SESSION_URL = "reports/{" + SESSION_ID_PATH_KEY + "}/finalize" + JSON_PATH;

}
