package com.verbatoria.data.network.api;

import com.verbatoria.data.network.common.ChildModel;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.request.AddEventRequestModel;
import com.verbatoria.data.network.request.ChildRequestModel;
import com.verbatoria.data.network.request.ClientRequestModel;
import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.FinishSessionResponseModel;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import com.verbatoria.data.network.response.VerbatologEventResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import java.util.List;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;

import static com.verbatoria.data.network.api.APIConstants.CHILD_ID_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.CLIENT_ID_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.SESSION_ID_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.TOKEN_HEADER_KEY;

/**
 *
 * Описание всех запросов
 *
 * @author nikitaremnev
 */
public interface APIService {

    @POST(APIConstants.LOGIN_URL)
    Observable<LoginResponseModel> loginRequest(@Body LoginRequestModel loginData);

    @GET(APIConstants.VERBATOLOG_INFO_URL)
    Observable<VerbatologInfoResponseModel> verbatologInfoRequest(@Header(TOKEN_HEADER_KEY) String accessToken);

    @GET(APIConstants.VERBATOLOG_EVENTS_URL)
    Observable<List<VerbatologEventResponseModel>> verbatologEventsRequest(@Header(TOKEN_HEADER_KEY) String accessToken);


    @POST(APIConstants.VERBATOLOG_EVENTS_URL)
    Observable<ResponseBody> addEventRequest(@Header(TOKEN_HEADER_KEY) String accessToken,
                                             @Body AddEventRequestModel event);

    @POST(APIConstants.ADD_CHILD_URL)
    Observable<ResponseBody> addChildRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
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

    @POST(APIConstants.ADD_CLIENT_URL)
    Observable<ResponseBody> addClientRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                              @Header(TOKEN_HEADER_KEY) String accessToken,
                                              @Body ClientRequestModel child);

    @PUT(APIConstants.EDIT_CLIENT_URL)
    Observable<ResponseBody> editClientRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                               @Header(TOKEN_HEADER_KEY) String accessToken,
                                               @Body ClientRequestModel child);

    @GET(APIConstants.GET_CLIENT_URL)
    Observable<ClientModel> getClientRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                             @Header(TOKEN_HEADER_KEY) String accessToken);

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

}
