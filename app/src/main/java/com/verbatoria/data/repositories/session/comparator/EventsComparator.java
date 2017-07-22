package com.verbatoria.data.repositories.session.comparator;

import com.verbatoria.data.repositories.session.model.EventMeasurement;
import java.util.Comparator;

/**
 * Компаратор для сортировки событий по времени
 *
 * @author nikitaremnev
 */
public class EventsComparator implements Comparator<EventMeasurement>  {

    @Override
    public int compare(EventMeasurement eventMeasurement, EventMeasurement anotherEventMeasurement) {
        if (eventMeasurement.getTimestamp() > anotherEventMeasurement.getTimestamp()) {
            return 1;
        } else if (eventMeasurement.getTimestamp() < anotherEventMeasurement.getTimestamp()) {
            return -1;
        }
        return 0;
    }

}
