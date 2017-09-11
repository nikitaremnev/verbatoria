package com.verbatoria.data.repositories.session.comparator;

import com.verbatoria.data.repositories.session.model.BaseMeasurement;

import java.util.Comparator;

/**
 * Компаратор для сортировки событий по времени
 *
 * @author nikitaremnev
 */
public class BaseMeasurementComparator implements Comparator<BaseMeasurement>  {

    @Override
    public int compare(BaseMeasurement measurement, BaseMeasurement anotherMeasurement) {
        if (measurement.getTimestamp() > anotherMeasurement.getTimestamp()) {
            return -1;
        } else if (measurement.getTimestamp() < anotherMeasurement.getTimestamp()) {
            return 1;
        }
        return 0;
    }

}
