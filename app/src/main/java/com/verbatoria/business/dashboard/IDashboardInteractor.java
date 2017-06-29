package com.verbatoria.business.dashboard;

import com.verbatoria.data.network.response.VerbatologEventResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import java.util.List;
import rx.Observable;

/**
 * Интерфейс интерактора для dashboard
 *
 * @author nikitaremnev
 */
public interface IDashboardInteractor {

    Observable<VerbatologInfoResponseModel> getVerbatologInfo();

    Observable<List<VerbatologEventResponseModel>> getVerbatologEvents();
}
