package com.verbatoria.data.network.api;

import com.verbatoria.data.network.common.ChildModel;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.request.AddEventRequestModel;
import com.verbatoria.data.network.request.ChildRequestModel;
import com.verbatoria.data.network.request.ClientRequestModel;
import com.verbatoria.data.network.request.EditClientRequestModel;
import com.verbatoria.data.network.request.EditEventRequestModel;
import com.verbatoria.data.network.request.LocationLanguageRequestModel;
import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.request.RecoveryPasswordRequestModel;
import com.verbatoria.data.network.request.ResetPasswordRequestModel;
import com.verbatoria.data.network.request.ScheduleDeleteRequestModel;
import com.verbatoria.data.network.request.ScheduleRequestModel;
import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.ChildResponseModel;
import com.verbatoria.data.network.response.ChildrenResponseModel;
import com.verbatoria.data.network.response.ClientsResponseModel;
import com.verbatoria.data.network.response.EventResponseModel;
import com.verbatoria.data.network.response.EventsResponseModel;
import com.verbatoria.data.network.response.FinishSessionResponseModel;
import com.verbatoria.data.network.response.LocationResponseModel;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.network.response.MessageResponseModel;
import com.verbatoria.data.network.response.ScheduleItemResponseModel;
import com.verbatoria.data.network.response.ScheduleResponseModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;

import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static com.verbatoria.data.network.api.APIConstants.CHILD_ID_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.CLIENT_ID_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.EVENT_ID_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.FROM_TIME_QUERY_KEY;
import static com.verbatoria.data.network.api.APIConstants.LOCATION_ID_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.PER_PAGE_KEY;
import static com.verbatoria.data.network.api.APIConstants.QUERY_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.REPORT_ID_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.SESSION_ID_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.TOKEN_HEADER_KEY;
import static com.verbatoria.data.network.api.APIConstants.TO_TIME_QUERY_KEY;

/**
 *
 * Описание всех запросов
 *
 * @author nikitaremnev
 */
public interface APIService {

    /*
        Login
     */

    @POST(APIConstants.LOGIN_URL)
    Observable<LoginResponseModel> loginRequest(@Body LoginRequestModel loginData);

    @POST(APIConstants.RECOVERY_PASSWORD_URL)
    Observable<MessageResponseModel> recoveryPassword(@Body RecoveryPasswordRequestModel recoveryPasswordRequestModel);

    @POST(APIConstants.RESET_PASSWORD_URL)
    Observable<MessageResponseModel> resetPassword(@Body ResetPasswordRequestModel resetPasswordRequestModel);

    @GET(APIConstants.COUNTRIES_URL)
    Observable<ResponseBody> getCountries(@Header(TOKEN_HEADER_KEY) String accessToken);

    /*
       Verbatolog info
    */

    @GET(APIConstants.VERBATOLOG_INFO_URL)
    Observable<VerbatologInfoResponseModel> getVerbatologInfoRequest(@Header(TOKEN_HEADER_KEY) String accessToken);

    @GET(APIConstants.GET_LOCATION_URL)
    Observable<LocationResponseModel> getLocation(@Path(value = LOCATION_ID_PATH_KEY) String locationId,
                                                  @Header(TOKEN_HEADER_KEY) String accessToken);

    @PUT(APIConstants.SET_LOCATION_LANGUAGE_URL)
    Observable<LocationResponseModel> setLocationLanguage(@Path(value = LOCATION_ID_PATH_KEY) String locationId,
                                                  @Body LocationLanguageRequestModel locationLanguageRequestModel);
     /*
        Calendar
     */

    @GET(APIConstants.GET_EVENTS_URL)
    Observable<EventsResponseModel> getEventsRequest(@Header(TOKEN_HEADER_KEY) String accessToken,
                                                     @Query(FROM_TIME_QUERY_KEY) String fromTime,
                                                     @Query(TO_TIME_QUERY_KEY) String toTime);

    @POST(APIConstants.ADD_EVENT_URL)
    Observable<EventResponseModel> addEventRequest(@Header(TOKEN_HEADER_KEY) String accessToken,
                                                   @Body AddEventRequestModel addEventRequestModel);

    @PUT(APIConstants.EDIT_EVENT_URL)
    Observable<EventResponseModel> editEventRequest(@Path(value = EVENT_ID_PATH_KEY) String eventId,
                                              @Header(TOKEN_HEADER_KEY) String accessToken,
                                             @Body EditEventRequestModel editEventRequestModel);

    @DELETE(APIConstants.DELETE_EVENT_URL)
    Observable<ResponseBody> deleteEventRequest(@Path(value = EVENT_ID_PATH_KEY) String eventId,
                                              @Header(TOKEN_HEADER_KEY) String accessToken);
    /*
        Childs
     */

    @GET(APIConstants.SEARCH_CHILD_URL)
    Observable<ChildrenResponseModel> findChildRequest(@Query(QUERY_PATH_KEY) String query,
                                                       @Header(TOKEN_HEADER_KEY) String accessToken);


    @POST(APIConstants.ADD_CHILD_URL)
    Observable<ChildResponseModel> addChildRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                                   @Header(TOKEN_HEADER_KEY) String accessToken,
                                                   @Body ChildRequestModel child);

    @PUT(APIConstants.EDIT_CHILD_URL)
    Observable<ResponseBody> editChildRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                              @Path(value = CHILD_ID_PATH_KEY) String childId,
                                              @Header(TOKEN_HEADER_KEY) String accessToken,
                                              @Body ChildRequestModel child);

    @GET(APIConstants.GET_CHILD_URL)
    Observable<ChildModel> getChildRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                           @Path(value = CHILD_ID_PATH_KEY) String childId,
                                           @Header(TOKEN_HEADER_KEY) String accessToken);

    /*
        Clients
     */

    @GET(APIConstants.SEARCH_CLIENT_URL)
    Observable<ClientsResponseModel> findClientRequest(@Query(QUERY_PATH_KEY) String query,
                                                       @Header(TOKEN_HEADER_KEY) String accessToken);

    @POST(APIConstants.ADD_CLIENT_URL)
    Observable<ClientModel> addClientRequest(@Header(TOKEN_HEADER_KEY) String accessToken,
                                              @Body ClientRequestModel client);

    @PUT(APIConstants.EDIT_CLIENT_URL)
    Observable<ClientModel> editClientRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                               @Header(TOKEN_HEADER_KEY) String accessToken,
                                               @Body EditClientRequestModel client);

    @GET(APIConstants.GET_CLIENT_URL)
    Observable<ClientModel> getClientRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                             @Header(TOKEN_HEADER_KEY) String accessToken);

    /*
        Session
     */

    @POST(APIConstants.START_SESSION_URL)
    Observable<StartSessionResponseModel> startSessionRequest(@Header(TOKEN_HEADER_KEY) String accessToken,
                                                              @Body StartSessionRequestModel eventId);

    @POST(APIConstants.ADD_RESULTS_TO_SESSION_URL)
    Observable<ResponseBody> addResultsToSessionRequest(@Path(value = SESSION_ID_PATH_KEY) String sessionId,
                                                        @Header(TOKEN_HEADER_KEY) String accessToken,
                                                        @Body RequestBody body);

    @POST(APIConstants.FINISH_SESSION_URL)
    Observable<FinishSessionResponseModel> finishSessionRequest(@Path(value = SESSION_ID_PATH_KEY) String sessionId,
                                                                @Header(TOKEN_HEADER_KEY) String accessToken);

    @POST(APIConstants.INCLUDE_ATTENTION_MEMORY_URL)
    Observable<ResponseBody> includeAttentionMemoryRequest(@Path(value = REPORT_ID_PATH_KEY) String reportId,
                                                                @Header(TOKEN_HEADER_KEY) String accessToken);

    /*
        Schedule
     */

    @GET(APIConstants.GET_SCHEDULE_URL)
    Observable<ScheduleResponseModel> getSchedule(@Header(TOKEN_HEADER_KEY) String accessToken,
                                                  @Query(FROM_TIME_QUERY_KEY) String fromTime,
                                                  @Query(TO_TIME_QUERY_KEY) String toTime,
                                                  @Query(PER_PAGE_KEY) int perPage);

    @POST(APIConstants.ADD_SCHEDULE_URL)
    Observable<List<ScheduleItemResponseModel>> saveSchedule(@Header(TOKEN_HEADER_KEY) String accessToken,
                                                            @Body ScheduleRequestModel scheduleRequestModel);


    @HTTP(method = "DELETE", path = APIConstants.DELETE_SCHEDULE_URL, hasBody = true)
    Observable<List<ScheduleItemResponseModel>> deleteSchedule(@Header(TOKEN_HEADER_KEY) String accessToken,
                                                              @Body ScheduleDeleteRequestModel scheduleDeleteRequestModel);

    /*
        Send report to location
     */

    @POST(APIConstants.SEND_REPORT_TO_LOCATION_URL)
    Observable<ResponseBody> sendReportToLocation(@Header(TOKEN_HEADER_KEY) String accessToken,
                                                  @Path(value = REPORT_ID_PATH_KEY) String reportId);

}
