package com.verbatoria.infrastructure.retrofit

/**
 * @author n.remnev
 */

object APIConstants {

    private const val API_VERSION = 1

    const val BASE_URL = "https://verbatoria.ru/api/v$API_VERSION/"

    const val API_PANEL_BASE_URL = "https://panel.verbatoria.ru/api/"

    const val VERBATOLOG_ID_PATH_KEY = "verbatolog"
    const val TOKEN_HEADER_KEY = "token"
    const val LOCATION_ID_PATH_KEY = "location_id"
    const val CLIENT_ID_PATH_KEY = "client_id"
    const val CHILD_ID_PATH_KEY = "child_id"
    const val EVENT_ID_PATH_KEY = "event_id"
    const val FROM_TIME_QUERY_KEY = "from_time"
    const val TO_TIME_QUERY_KEY = "to_time"
    const val PER_PAGE_KEY = "per_page"
    const val QUERY_PATH_KEY = "query"
    const val JSON_PATH = ".json"

    const val LOGIN_URL = "session$JSON_PATH"
    const val RECOVERY_PASSWORD_URL = "$VERBATOLOG_ID_PATH_KEY/restore_password$JSON_PATH"
    const val RESET_PASSWORD_URL = "$VERBATOLOG_ID_PATH_KEY/reset_password$JSON_PATH"
    const val COUNTRIES_URL = "countries$JSON_PATH"

    const val VERBATOLOG_INFO_URL = "$VERBATOLOG_ID_PATH_KEY/current$JSON_PATH"

    const val GET_EVENTS_URL = "$VERBATOLOG_ID_PATH_KEY/current/events$JSON_PATH"
    const val CREATE_NEW_EVENT_URL = "$VERBATOLOG_ID_PATH_KEY/current/events$JSON_PATH"
    const val EDIT_EVENT_URL = "$VERBATOLOG_ID_PATH_KEY/current/events/{$EVENT_ID_PATH_KEY}$JSON_PATH"
    const val DELETE_EVENT_URL = "$VERBATOLOG_ID_PATH_KEY/current/events/{$EVENT_ID_PATH_KEY}$JSON_PATH"

    const val SEARCH_CHILD_URL = "children$JSON_PATH"
    const val ADD_CHILD_URL = "clients/{$CLIENT_ID_PATH_KEY}/children$JSON_PATH"
    const val EDIT_CHILD_URL = "clients/{$CLIENT_ID_PATH_KEY}/children/{$CHILD_ID_PATH_KEY}$JSON_PATH"
    const val GET_CHILD_URL = "clients/{$CLIENT_ID_PATH_KEY}/children/{$CHILD_ID_PATH_KEY}$JSON_PATH"

    const val GET_SCHEDULE_URL = "$VERBATOLOG_ID_PATH_KEY/current/schedule_entries$JSON_PATH"
    const val ADD_SCHEDULE_URL = "$VERBATOLOG_ID_PATH_KEY/current/schedule_entries$JSON_PATH"
    const val DELETE_SCHEDULE_URL = "$VERBATOLOG_ID_PATH_KEY/current/schedule_entries/delete"

    const val SEARCH_CLIENT_URL = "clients$JSON_PATH"
    const val CREATE_NEW_CLIENT_URL = "clients$JSON_PATH"
    const val EDIT_CLIENT_URL = "clients/{$CLIENT_ID_PATH_KEY}$JSON_PATH"
    const val GET_CLIENT_URL = "clients/{$CLIENT_ID_PATH_KEY}$JSON_PATH"

    const val GET_LOCATION_URL = "locations/{$LOCATION_ID_PATH_KEY}$JSON_PATH"
    const val SET_LOCATION_LANGUAGE_URL = "locations/{$LOCATION_ID_PATH_KEY}$JSON_PATH"

    const val SESSION_ID_PATH_KEY = "session_id"
    const val REPORT_ID_PATH_KEY = "report_id"

    const val START_SESSION_URL = "reports$JSON_PATH"
    const val INCLUDE_ATTENTION_MEMORY_URL = "reports/{$REPORT_ID_PATH_KEY}/add_vnp$JSON_PATH"
    const val ADD_RESULTS_TO_SESSION_URL = "reports/{$SESSION_ID_PATH_KEY}/measurements/upload$JSON_PATH"
    const val FINISH_SESSION_URL = "reports/{$SESSION_ID_PATH_KEY}/finalize$JSON_PATH"
    const val SEND_REPORT_TO_LOCATION_URL = "reports/{$REPORT_ID_PATH_KEY}/send_to_location$JSON_PATH"

    const val AGE_GROUPS_URL = "age_groups$JSON_PATH"

    const val SMS_CONFIRMATION_CODE_URL = "sendsmscode"

}