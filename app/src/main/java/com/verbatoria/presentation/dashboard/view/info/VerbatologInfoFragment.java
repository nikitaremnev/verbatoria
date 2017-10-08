package com.verbatoria.presentation.dashboard.view.info;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.LocationModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.dashboard.presenter.main.IVerbatologInfoPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Фрагмент для отображения информации о вербатологе
 *
 * @author nikitaremnev
 */
public class VerbatologInfoFragment extends Fragment implements IVerbatologInfoView {

    @Inject
    IVerbatologInfoPresenter mVerbatologInfoPresenter;

    @BindView(R.id.name_text_view)
    public TextView mVerbatologNameTextView;

    @BindView(R.id.phone_text_view)
    public TextView mVerbatologPhoneTextView;

    @BindView(R.id.email_text_view)
    public TextView mVerbatologEmailTextView;

    @BindView(R.id.location_name_text_view)
    public TextView mLocationNameTextView;

    @BindView(R.id.location_point_text_view)
    public TextView mLocationCityCountryTextView;

    @BindView(R.id.location_address_text_view)
    public TextView mLocationAddressTextView;

    @BindView(R.id.partner_name_text_view)
    public TextView mPartnerNameTextView;

    public VerbatologInfoFragment() {
        // Required empty public constructor
    }

    public static VerbatologInfoFragment newInstance() {
        return new VerbatologInfoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_verbatolog_info, container, false);
        //bind views
        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //bind views
        VerbatoriaApplication.getApplicationComponent().addModule(new DashboardModule()).inject(this);
        mVerbatologInfoPresenter.bindView(this);
        mVerbatologInfoPresenter.updateVerbatologInfo();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mVerbatologInfoPresenter.unbindView();
    }

    @Override
    public void showVerbatologName(String verbatologName) {
        mVerbatologNameTextView.setText(verbatologName);
    }

    @Override
    public void showVerbatologEmail(String verbatologEmail) {
        mVerbatologEmailTextView.setText(verbatologEmail);
    }

    @Override
    public void showVerbatologPhone(String verbatologPhone) {
        mVerbatologPhoneTextView.setText(verbatologPhone);
    }

    @Override
    public void showLocationPartner(String partner) {
        mPartnerNameTextView.setText(partner);
    }

    @Override
    public void showLocationCityCountry(String cityCountry) {
        mLocationCityCountryTextView.setText(cityCountry);
    }

    @Override
    public void showLocationAddress(String address) {
        mLocationAddressTextView.setText(address);
    }

    @Override
    public void showLocationName(String name) {
        mLocationNameTextView.setText(name);
    }

    @Override
    public void showVerbatologInfo(String verbatologFullName, String verbatologPhone, String verbatologEmail) {
        showVerbatologName(verbatologFullName);
        showVerbatologPhone(verbatologPhone);
        showVerbatologEmail(verbatologEmail);
    }

    @Override
    public void showLocationInfo(LocationModel locationModel) {
        showLocationAddress(locationModel.getAddress());
        showLocationCityCountry(locationModel.getCityCountry());
        showLocationPartner(locationModel.getPartner());
        showLocationName(locationModel.getName());
    }
}