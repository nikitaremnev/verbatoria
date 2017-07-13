package com.verbatoria.presentation.dashboard.view.settings;

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
import com.verbatoria.presentation.dashboard.presenter.main.IDashboardMainPresenter;
import com.verbatoria.presentation.dashboard.presenter.settings.ISettingsPresenter;

import javax.annotation.Resource;
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
        mSettingsPresenter.bindView(this);
    }

    private void setUpQuitView() {
        setUpSettingsItemText(mQuitView);
        setUpSettingsImageView(mQuitView, R.drawable.ic_exit);
        mQuitView.setOnClickListener(v -> {
            mSettingsPresenter.quit();
        });
    }

    /*
        Задание текста для итема настроек
     */
    private void setUpSettingsItemText(View settingsView) {
        ((TextView) settingsView.findViewById(R.id.settings_item_text_view)).setText(getString(R.string.settings_item_quit));
    }

    /*
        Задание картинки для итема настроек
     */
    private void setUpSettingsImageView(View settingsView, @DrawableRes int imageView) {
        ((ImageView) settingsView.findViewById(R.id.settings_item_image_view)).setImageResource(imageView);
    }

}
