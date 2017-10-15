package com.verbatoria.presentation.dashboard.view.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.dashboard.presenter.settings.ISettingsPresenter;
import com.verbatoria.presentation.login.view.login.LoginActivity;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;

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
    ISettingsPresenter mSettingsPresenter;

    @BindView(R.id.item_settings_connection)
    public View mConnectionView;

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
        setUpConnectionView();
        mSettingsPresenter.bindView(this);
    }

    private void setUpConnectionView() {
        setUpSettingsItemText(mConnectionView, R.string.settings_item_connection);
        setUpSettingsImageView(mConnectionView, R.drawable.ic_connection);
        mConnectionView.setOnClickListener(v -> startConnection());
    }

    private void setUpQuitView() {
        setUpSettingsItemText(mQuitView, R.string.settings_item_quit);
        setUpSettingsImageView(mQuitView, R.drawable.ic_exit);
        mQuitView.setOnClickListener(v -> startLogin());
    }

    /*
        Задание текста для итема настроек
     */
    private void setUpSettingsItemText(View settingsView, @StringRes int textResource) {
        ((TextView) settingsView.findViewById(R.id.settings_item_text_view)).setText(getString(textResource));
    }

    /*
        Задание картинки для итема настроек
     */
    private void setUpSettingsImageView(View settingsView, @DrawableRes int imageResource) {
        ((ImageView) settingsView.findViewById(R.id.settings_item_image_view)).setImageResource(imageResource);
    }

    private void startConnection() {
        Intent intent = new Intent(getActivity(), ConnectionActivity.class);
        startActivity(intent);
    }

    private void startLogin() {
        startActivity(LoginActivity.newInstance(getActivity()));
    }

}
