package com.verbatoria.data.network.api;

import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.data.network.request.ClientRequestModel;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import rx.Observable;
import static com.verbatoria.data.network.api.APIConstants.CLIENT_ID_PATH_KEY;
import static com.verbatoria.data.network.api.APIConstants.JSON_PATH;
import static com.verbatoria.data.network.api.APIConstants.TOKEN_HEADER_KEY;

/**
 *
 * Описание всех запросов
 *
 * @author nikitaremnev
 */
public interface ClientAPIService {

    String ADD_CLIENT_URL = "clients" + JSON_PATH;
    String EDIT_CLIENT_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}" + JSON_PATH;
    String GET_CLIENT_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}" + JSON_PATH;

    @POST(ADD_CLIENT_URL)
    Observable<ResponseBody> addClientRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                             @Header(TOKEN_HEADER_KEY) String accessToken,
                                             @Body ClientRequestModel child);

    @PUT(EDIT_CLIENT_URL)
    Observable<ResponseBody> editClientRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                              @Header(TOKEN_HEADER_KEY) String accessToken,
                                              @Body ClientRequestModel child);

    @GET(GET_CLIENT_URL)
    Observable<ClientModel> getClientRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                             @Header(TOKEN_HEADER_KEY) String accessToken);
}
