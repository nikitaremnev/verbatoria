package com.verbatoria.presentation.dashboard.view.settings;

import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
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

//    @BindView(R.id.item_settings_connection)
//    public View mConnectionView;

    @BindView(R.id.item_settings_developer)
    public View mDeveloperView;

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
//        setUpConnectionView();
        setUpDeveloperView();
        mSettingsPresenter.bindView(this);
    }

    @Override
    public void showDeveloperInfo(String version, String androidVersion) {
        View dialogRootView = getLayoutInflater().inflate(R.layout.dialog_developer_info, null);
        setUpFieldView(dialogRootView.findViewById(R.id.application_version_field), R.drawable.ic_application_version,
                version, getString(R.string.developer_info_application_version), null);
        setUpFieldView(dialogRootView.findViewById(R.id.android_version_field), R.drawable.ic_android_version,
                androidVersion, getString(R.string.developer_info_android_version), null);
        new AlertDialog.Builder(getActivity())
                .setView(dialogRootView)
                .setTitle(R.string.settings_item_developer)
                .setNegativeButton(getString(R.string.ok), null)
                .create()
                .show();
    }

//    @Override
//    public void showConnection() {
//        startActivity(ConnectionActivity.newInstance(getActivity(), EventModel()));
//    }

    @Override
    public void showLogin() {
        startActivity(LoginActivity.newInstance(getActivity()));
    }

//    private void setUpConnectionView() {
//        setUpSettingsItemText(mConnectionView, R.string.settings_item_connection);
//        setUpSettingsImageView(mConnectionView, R.drawable.ic_connection);
//        mConnectionView.setOnClickListener(v -> mSettingsPresenter.onConnectionClicked());
//    }

    private void setUpDeveloperView() {
        setUpSettingsItemText(mDeveloperView, R.string.settings_item_developer);
        setUpSettingsImageView(mDeveloperView, R.drawable.ic_developer_info);
        mDeveloperView.setOnClickListener(v -> mSettingsPresenter.onDeveloperInfoClicked());
    }

    private void setUpQuitView() {
        setUpSettingsItemText(mQuitView, R.string.settings_item_quit);
        setUpSettingsImageView(mQuitView, R.drawable.ic_exit);
        mQuitView.setOnClickListener(v -> mSettingsPresenter.onQuitClicked());
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

    private void setUpFieldView(View fieldView, int imageResource, String title, String subtitle, View.OnClickListener onClickListener) {
        ((ImageView) fieldView.findViewById(R.id.field_image_view)).setImageResource(imageResource);
        ((TextView) fieldView.findViewById(R.id.field_title)).setText(title);
        ((TextView) fieldView.findViewById(R.id.field_subtitle)).setText(subtitle);
        fieldView.setOnClickListener(onClickListener);
    }

}
