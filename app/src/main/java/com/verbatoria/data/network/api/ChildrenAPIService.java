package com.verbatoria.data.network.api;

import com.verbatoria.data.network.common.ChildModel;
import com.verbatoria.data.network.request.ChildRequestModel;

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
import static com.verbatoria.data.network.api.APIConstants.JSON_PATH;
import static com.verbatoria.data.network.api.APIConstants.TOKEN_HEADER_KEY;

/**
 *
 * Описание всех запросов
 *
 * @author nikitaremnev
 */
public interface ChildrenAPIService {

    String ADD_CHILD_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}/children" + JSON_PATH;
    String EDIT_CHILD_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}/children/{" + CHILD_ID_PATH_KEY + "}" + JSON_PATH;
    String GET_CHILD_URL = "clients/{" + CLIENT_ID_PATH_KEY + "}/children/{" + CHILD_ID_PATH_KEY + "}" + JSON_PATH;

    @POST(ADD_CHILD_URL)
    Observable<ResponseBody> addChildRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                             @Header(TOKEN_HEADER_KEY) String accessToken,
                                             @Body ChildRequestModel child);

    @PUT(EDIT_CHILD_URL)
    Observable<ResponseBody> editChildRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                              @Path(value = CHILD_ID_PATH_KEY) String childId,
                                              @Header(TOKEN_HEADER_KEY) String accessToken,
                                              @Body ChildRequestModel child);

    @GET(GET_CHILD_URL)
    Observable<ChildModel> getChildRequest(@Path(value = CLIENT_ID_PATH_KEY) String clientId,
                                           @Path(value = CHILD_ID_PATH_KEY) String childId,
                                           @Header(TOKEN_HEADER_KEY) String accessToken);

}
