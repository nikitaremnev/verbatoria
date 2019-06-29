package com.verbatoria.ui.dashboard.view.info;

import com.verbatoria.business.dashboard.models.LocationModel;

/**
 * Интерфейс вьюхи для отображения информации вербатолога
 *
 * @author nikitaremnev
 */
public interface IVerbatologInfoView {

    void showLocationPartner(String partner);

    void showLocationCityCountry(String cityCountry);

    void showLocationAddress(String address);

    void showLocationName(String name);

    void showLocationId(String locationId);

    void showVerbatologInfo(String verbatologFullName, String verbatologPhone, String verbatologEmail, boolean isArchimedAllowed);

    void showLocationInfo(LocationModel locationModel);

    void showActiveStatus();

    void showWarningStatus();

    void showBlockedStatus();

    void updateLocale(String language);

}
