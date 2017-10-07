package com.verbatoria.presentation.dashboard.view.main.info;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.remnev.verbatoriamini.R;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Фрагмент для отображения информации о вербатологе
 *
 * @author nikitaremnev
 */
public class VerbatologInfoFragment extends Fragment implements IVerbatologInfoView {

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
        ButterKnife.bind(this, rootView);
        return rootView;
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

}
