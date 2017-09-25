package com.verbatoria.data.network.api;

import com.verbatoria.data.network.request.StartSessionRequestModel;
import com.verbatoria.data.network.response.FinishSessionResponseModel;
import com.verbatoria.data.network.response.StartSessionResponseModel;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

import retrofit2.http.Path;
import rx.Observable;

import static com.verbatoria.data.network.api.APIConstants.JSON_PATH;
import static com.verbatoria.data.network.api.APIConstants.TOKEN_HEADER_KEY;

/**
 *
 * Описание всех запросов
 *
 * @author nikitaremnev
 */
public interface SessionAPIService {

    String SESSION_ID_PATH_KEY = "session_id";
    String START_SESSION_URL = "reports" + JSON_PATH;
    String ADD_RESULTS_TO_SESSION_URL = "reports/{" + SESSION_ID_PATH_KEY + "}/measurements/upload" + JSON_PATH;
    String FINISH_SESSION_URL = "reports/{" + SESSION_ID_PATH_KEY + "}/finalize" + JSON_PATH;

    @POST(START_SESSION_URL)
    Observable<StartSessionResponseModel> startSessionRequest(@Header(TOKEN_HEADER_KEY) String accessToken,
                                                              @Body StartSessionRequestModel eventId);

    @POST(ADD_RESULTS_TO_SESSION_URL)
    Observable<ResponseBody> addResultsToSessionRequest(@Path(value = SESSION_ID_PATH_KEY) String sessionId,
                                                        @Header(TOKEN_HEADER_KEY) String accessToken,
                                                        @Body RequestBody body);

    @POST(FINISH_SESSION_URL)
    Observable<FinishSessionResponseModel> finishSessionRequest(@Path(value = SESSION_ID_PATH_KEY) String sessionId,
                                                                @Header(TOKEN_HEADER_KEY) String accessToken);

}
