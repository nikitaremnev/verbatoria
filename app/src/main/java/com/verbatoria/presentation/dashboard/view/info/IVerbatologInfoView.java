package com.verbatoria.presentation.dashboard.view.info;

import com.verbatoria.business.dashboard.models.LocationModel;

/**
 * Интерфейс вьюхи для отображения информации вербатолога
 *
 * @author nikitaremnev
 */
public interface IVerbatologInfoView {

    void showVerbatologName(String verbatologName);

    void showVerbatologEmail(String verbatologEmail);

    void showVerbatologPhone(String verbatologPhone);

    void showLocationPartner(String partner);

    void showLocationCityCountry(String cityCountry);

    void showLocationAddress(String address);

    void showLocationName(String name);

    void showLocationId(String locationId);

    void showVerbatologInfo(String verbatologFullName, String verbatologPhone, String verbatologEmail);

    void showLocationInfo(String address, String city, String country, String partner, String name, String id);

    void showLocationInfo(LocationModel locationModel);

    void showActiveStatus();
    void showWarningStatus();
    void showBlockedStatus();
}
