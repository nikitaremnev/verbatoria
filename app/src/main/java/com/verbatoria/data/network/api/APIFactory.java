package com.verbatoria.data.network.api;

import android.support.annotation.NonNull;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Общая настройка работы с сетью
 *
 * @author nikitaremnev
 */
public class APIFactory {

    private static final int CONNECT_TIMEOUT = 30;
    private static final int READ_TIMEOUT = 60;
    private static final int WRITE_TIMEOUT = 60;

    private static final OkHttpClient OK_HTTP_CLIENT;

    private static final Retrofit VERBATORIA_RETROFIT;

    static {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OK_HTTP_CLIENT = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .build();

        VERBATORIA_RETROFIT = new Retrofit.Builder()
                .baseUrl(APIConstants.API_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(OK_HTTP_CLIENT)
                .build();
    }

    @NonNull
    public static APIService getAPIService() {
        return getRetrofit().create(APIService.class);
    }

    @NonNull
    private static Retrofit getRetrofit() {
        return VERBATORIA_RETROFIT;
    }

    @NonNull
    public static APIPanelService getPanelAPIService() {
        return getPanelRetrofit().create(APIPanelService.class);
    }

    @NonNull
    private static Retrofit getPanelRetrofit() {
        return new Retrofit.Builder()
                .baseUrl(APIConstants.API_PANEL_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create())
                .client(OK_HTTP_CLIENT)
                .build();
    }

}