package com.verbatoria.business.dashboard;

import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;

import rx.Observable;

/**
 * Интерфейс интерактора для dashboard
 *
 * @author nikitaremnev
 */
public interface IDashboardInteractor {

    Observable<VerbatologInfoResponseModel> getVerbatologInfo();

}
