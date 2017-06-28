package com.verbatoria.data.network.api;

import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

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
    Observable<VerbatologInfoResponseModel> verbatologInfoRequest(@Header("access_token") String accessToken);

}
