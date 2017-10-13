package com.verbatoria.data.repositories.calendar.comparator;

import com.verbatoria.business.dashboard.models.EventModel;

import java.util.Comparator;

/**
 * @author nikitaremnev
 */
public class EventsComparator implements Comparator<EventModel> {

    @Override
    public int compare(EventModel o1, EventModel o2) {
        if (o1.getStartAt().getTime()  > o2.getStartAt().getTime() ) {
            return 1;
        } else if (o1.getStartAt().getTime()  < o2.getStartAt().getTime() ) {
            return -1;
        }
        return 0;
    }
}
