package com.verbatoria.presentation.dashboard.view;

import com.verbatoria.business.dashboard.models.EventModel;

import java.util.List;

/**
 * Интерфейс для вью-отображения ближайших событий вербатолога
 *
 * @author nikitaremnev
 */

public interface IVerbatologEventsView {

    void showVerbatologEvents(List<EventModel> verbatologEvents);

}
