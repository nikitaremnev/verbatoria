package com.verbatoria.business.schedule.models;

/**
 * @author nikitaremnev
 */

public class ScheduleItemModel {

    private boolean mSelected = false;

    public boolean isSelected() {
        return mSelected;
    }

    public ScheduleItemModel setSelected(boolean selected) {
        mSelected = selected;
        return this;
    }

}
