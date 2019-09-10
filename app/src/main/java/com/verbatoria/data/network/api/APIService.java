package com.verbatoria.data.network.api;

import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.AgeGroupResponseModel;
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

import io.reactivex.Completable;
import io.reactivex.Observable;
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
        Send report to location
     */

    @POST(APIConstants.SEND_REPORT_TO_LOCATION_URL)
    Observable<ResponseBody> sendReportToLocation(@Header(TOKEN_HEADER_KEY) String accessToken,
                                                  @Path(value = REPORT_ID_PATH_KEY) String reportId);

}
