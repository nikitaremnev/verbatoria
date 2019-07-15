package com.verbatoria.ui.dashboard.view.settings;

import android.app.ProgressDialog;
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

import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.ui.dashboard.presenter.settings.ISettingsPresenter;
import com.verbatoria.ui.late_send.LateSendActivity;
import com.verbatoria.ui.login.LoginActivity;
import com.verbatoria.ui.schedule.view.ScheduleActivity;
import com.verbatoria.utils.Helper;
import com.verbatoria.utils.LocaleHelper;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author n.remnev
 */

public class SettingsFragment extends Fragment implements ISettingsView {

//    @Inject
    ISettingsPresenter mSettingsPresenter;

//    @BindView(R.id.item_settings_schedule)
//    public View mScheduleView;
//
//    @BindView(R.id.item_settings_late_send)
//    public View mLateSendView;
//
//    @BindView(R.id.item_settings_developer)
//    public View mDeveloperView;
//
//    @BindView(R.id.item_settings_clear)
//    public View mClearView;
//
//    @BindView(R.id.item_settings_locale)
//    public View mLocaleView;
//
//    @BindView(R.id.item_settings_quit)
//    public View mQuitView;

    private AlertDialog mLanguageDialog;

    private ProgressDialog mProgressDialog;

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
//        setUpQuitView();
//        setUpScheduleView();
//        setUpLateSendView();
//        setUpDeveloperView();
//        setUpClearView();
//        setUpLocaleView();
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

    @Override
    public void showLogin() {
        startActivity(LoginActivity.Companion.createIntent(getActivity()));
    }

    @Override
    public void showSchedule() {
        startActivity(ScheduleActivity.newInstance(getActivity()));
    }

    @Override
    public void showLateSend() {
        startActivity(LateSendActivity.Companion.createIntent(getActivity()));
    }

    @Override
    public void showClearDatabaseConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity())
                .setTitle(getString(R.string.settings_clear_database_confirm_title))
                .setMessage(getString(R.string.settings_clear_database_confirm_message));
        builder.setPositiveButton(getString(R.string.settings_item_clear), (dialog, which) -> {
            mSettingsPresenter.clearDatabase();
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.create().show();
    }

    @Override
    public void showDatabaseCleared() {
//        Helper.showHintSnackBar(mClearView, getString(R.string.settings_database_cleared));
    }

    @Override
    public void showLanguagesDialog(boolean isRussianAvailable, boolean isEnglishAvailable, boolean isHongKongAvailable) {
        View dialogRootView = getLayoutInflater().inflate(R.layout.dialog_languages, null);
        View russianLanguageView = dialogRootView.findViewById(R.id.russian_language_field);
        View englishLanguageView = dialogRootView.findViewById(R.id.english_language_field);
        View hongKongLanguageView = dialogRootView.findViewById(R.id.hong_kong_language_field);

        if (isRussianAvailable) {
            setUpLanguageFieldView(russianLanguageView, R.drawable.ic_flag_ru,
                    getString(R.string.language_russian), v -> mSettingsPresenter.onRussianLanguageSelected());
        } else {
            russianLanguageView.setVisibility(View.GONE);
        }
        if (isEnglishAvailable) {
            setUpLanguageFieldView(englishLanguageView, R.drawable.ic_flag_uk,
                    getString(R.string.language_english), v -> mSettingsPresenter.onEnglishLanguageSelected());
        } else {
            englishLanguageView.setVisibility(View.GONE);
        }
        if (isHongKongAvailable) {
            setUpLanguageFieldView(hongKongLanguageView, R.drawable.ic_flag_hk,
                    getString(R.string.language_hong_kong), v -> mSettingsPresenter.onHongKongLanguageSelected());
        } else {
            hongKongLanguageView.setVisibility(View.GONE);
        }
        mLanguageDialog = new AlertDialog.Builder(getActivity())
                .setView(dialogRootView)
                .setTitle(R.string.settings_item_locale)
                .setNegativeButton(getString(R.string.ok), null)
                .create();
        mLanguageDialog.show();
    }

    @Override
    public void setLanguage(String language) {
        mLanguageDialog.dismiss();
        LocaleHelper.setLocale(getActivity(), language);
        getActivity().recreate();
    }

    public void startProgress() {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage(getString(R.string.loading_please_wait));
        mProgressDialog.show();
        if (mLanguageDialog != null && mLanguageDialog.isShowing()) {
            mLanguageDialog.dismiss();
        }
    }

    public void stopProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @Override
    public void showUpdateLanguageError() {
        Helper.showErrorSnackBar(getView(), getString(R.string.dashboard_update_language_error));
    }

    /*
        Задание текста для итема настроек
     */
    private void setUpSettingsItemText(View settingsView, @StringRes int textResource) {
//        ((TextView) settingsView.findViewById(R.id.settings_item_text_view)).setText(getString(textResource));
    }

    /*
        Задание картинки для итема настроек
     */
    private void setUpSettingsImageView(View settingsView, @DrawableRes int imageResource) {
//        ((ImageView) settingsView.findViewById(R.id.settings_item_image_view)).setImageResource(imageResource);
    }

    private void setUpFieldView(View fieldView, int imageResource, String title, String subtitle, View.OnClickListener onClickListener) {
        ((ImageView) fieldView.findViewById(R.id.field_image_view)).setImageResource(imageResource);
        ((TextView) fieldView.findViewById(R.id.field_title)).setText(title);
        ((TextView) fieldView.findViewById(R.id.field_subtitle)).setText(subtitle);
        fieldView.setOnClickListener(onClickListener);
    }

    private void setUpLanguageFieldView(View fieldView, int imageResource, String title, View.OnClickListener onClickListener) {
        ((ImageView) fieldView.findViewById(R.id.field_image_view)).setImageResource(imageResource);
        ((TextView) fieldView.findViewById(R.id.field_title)).setText(title);
        fieldView.setOnClickListener(onClickListener);
    }

}
