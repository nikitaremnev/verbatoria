package com.verbatoria.data.network.api;

/**
 *
 * Константы для API
 *
 * @author nikitaremnev
 */
class APIConstants {

    private static final int API_VERSION = 1;
    static final String API_BASE_URL = "https://verbatoria.ru/api/v" + API_VERSION + "/";

    static final String VERBATOLOG_ID_PATH_KEY = "verbatolog";
    static final String TOKEN_HEADER_KEY = "token";
    static final String LOCATION_ID_PATH_KEY = "location_id";
    static final String CLIENT_ID_PATH_KEY = "client_id";
    static final String CHILD_ID_PATH_KEY = "child_id";
    static final String EVENT_ID_PATH_KEY = "event_id";
    static final String FROM_TIME_QUERY_KEY = "from_time";
    static final String TO_TIME_QUERY_KEY = "to_time";
    static final String PER_PAGE_KEY = "per_page";
    static final String QUERY_PATH_KEY = "query";
    static final String JSON_PATH = ".json";

    static final String LOGIN_URL = "session" + JSON_PATH;
    static final String RECOVERY_PASSWORD_URL = VERBATOLOG_ID_PATH_KEY + "/restore_password" + JSON_PATH;
    static final String RESET_PASSWORD_URL = VERBATOLOG_ID_PATH_KEY + "/reset_password" + JSON_PATH;

    static final String VERBATOLOG_INFO_URL = VERBATOLOG_ID_PATH_KEY + "/current" + JSON_PATH;

    static final String GET_EVENTS_URL = VERBATOLOG_ID_PATH_KEY + "/current/events" + JSON_PATH;
    static final String ADD_EVENT_URL = VERBATOLOG_ID_PATH_KEY + "/current/events" + JSON_PATH;
    static final String EDIT_EVENT_URL = VERBATOLOG_ID_PATH_KEY + "/current/events/" + "{" + EVENT_ID_PATH_KEY + "}" + JSON_PATH;
    static final String DELETE_EVENT_URL = VERBATOLOG_ID_PATH_KEY + "/current/events/" + "{" + EVENT_ID_PATH_KEY + "}" + JSON_PATH;

    static final String SEARCH_CHILD_URL = "children" + JSON_PATH;
    static final String ADD_CHILD_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}/children" + JSON_PATH;
    static final String EDIT_CHILD_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}/children/{" + CHILD_ID_PATH_KEY + "}" + JSON_PATH;
    static final String GET_CHILD_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}/children/{" + CHILD_ID_PATH_KEY + "}" + JSON_PATH;

    static final String GET_SCHEDULE_URL = VERBATOLOG_ID_PATH_KEY + "/current/schedule_entries" + JSON_PATH;
    static final String ADD_SCHEDULE_URL = VERBATOLOG_ID_PATH_KEY + "/current/schedule_entries" + JSON_PATH;
    static final String DELETE_SCHEDULE_URL = VERBATOLOG_ID_PATH_KEY + "/current/schedule_entries/delete";

    static final String SEARCH_CLIENT_URL = "clients" + JSON_PATH;
    static final String ADD_CLIENT_URL = "clients" + JSON_PATH;
    static final String EDIT_CLIENT_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}" + JSON_PATH;
    static final String GET_CLIENT_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}" + JSON_PATH;

    static final String GET_LOCATION_URL = "locations/{" + LOCATION_ID_PATH_KEY + "}" + JSON_PATH;

    static final String SESSION_ID_PATH_KEY = "session_id";

    static final String START_SESSION_URL = "reports" + JSON_PATH;
    static final String ADD_RESULTS_TO_SESSION_URL = "reports/{" + SESSION_ID_PATH_KEY + "}/measurements/upload" + JSON_PATH;
    static final String FINISH_SESSION_URL = "reports/{" + SESSION_ID_PATH_KEY + "}/finalize" + JSON_PATH;

}
