package com.verbatoria.business.dashboard;

import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.business.dashboard.models.VerbatologModel;
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

    Observable<VerbatologModel> getVerbatologInfo(VerbatologModel verbatolog);

    Observable<VerbatologModel> getVerbatologEvents(VerbatologModel verbatolog);

    Observable<List<EventModel>> getVerbatologEvents();

}
