package com.verbatoria.data.repositories.session.comparator;

import com.verbatoria.data.repositories.session.model.BaseMeasurement;
import com.verbatoria.data.repositories.session.model.EventMeasurement;
import java.util.Comparator;

/**
 * Компаратор для сортировки событий по времени
 *
 * @author nikitaremnev
 */
public class EventsComparator implements Comparator<BaseMeasurement>  {

    @Override
    public int compare(BaseMeasurement eventMeasurement, BaseMeasurement anotherEventMeasurement) {
        if (eventMeasurement.getTimestamp() > anotherEventMeasurement.getTimestamp()) {
            return 1;
        } else if (eventMeasurement.getTimestamp() < anotherEventMeasurement.getTimestamp()) {
            return -1;
        }
        return 0;
    }

}
