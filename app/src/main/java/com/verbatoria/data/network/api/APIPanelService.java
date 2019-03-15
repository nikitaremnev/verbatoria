package com.verbatoria.data.network.api;

import com.verbatoria.data.network.response.SMSConfirmationResponseModel;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 *
 * Описание всех запросов
 *
 * @author nikitaremnev
 */
public interface APIPanelService {

    /*
        SMS confirmation
     */

    @GET(APIConstants.SMS_CONFIRMATION_CODE_URL)
    Observable<SMSConfirmationResponseModel> sendSMSConfirmation(@Query("phone") String phone,
                                                                 @Query("text") String smsText,
                                                                 @Query("token") String token);

}
