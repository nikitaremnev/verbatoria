package com.verbatoria.ui.calendar.presenter.add.children;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.verbatoria.ui.calendar.view.add.children.IChildView;
import com.verbatoria.ui.calendar.view.add.children.search.ISearchChildrenView;

import java.util.Date;

/**
 * Презентер для добавления события в календарь
 *
 * @author nikitaremnev
 */
public interface IChildPresenter {

    void bindView(@NonNull ISearchChildrenView searchChildrenView);
    void bindView(@NonNull IChildView childrenView);
    void unbindView();

    void obtainChild(Intent intent);

    void onSuccessMessageDismissed();
    void onActivityResultChildFound();
    void onAgeFieldClicked();
    void onBackPressed();

    boolean isEditMode();

    void createChild();
    void editChild();
    void searchChildren();

    void searchClientChildren();

    String getChildName();
    String getChildAge();
    String getChildGender();

    void setChildBirthday(Date birthday);
    void setChildGender(String gender);

    boolean isGenderSet();

}
