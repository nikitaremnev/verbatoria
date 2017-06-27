package com.verbatoria.data.network.api;

import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.data.network.response.LoginResponseModel;

import retrofit2.http.Body;
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

}
