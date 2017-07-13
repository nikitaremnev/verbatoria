package com.verbatoria.presentation.dashboard.view.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.dashboard.presenter.main.IDashboardMainPresenter;
import com.verbatoria.presentation.dashboard.presenter.settings.ISettingsPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Фрагмент для отображения настроек приложения
 *
 * @author nikitaremnev
 */
public class SettingsFragment extends Fragment implements ISettingsView {

    @Inject
    ISettingsPresenter mSettingsPresentert;

    @BindView(R.id.item_settings_quit)
    public View mQuitView;

    public SettingsFragment() {
        // Required empty public constructor
    }

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VerbatoriaApplication.getApplicationComponent().addModule(new DashboardModule()).inject(this);
        setUpQuitView();
    }

    private void setUpQuitView() {

    }

}
