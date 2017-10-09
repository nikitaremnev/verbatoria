package com.verbatoria.presentation.calendar.view.add.clients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.calendar.CalendarModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.presenter.add.clients.IClientsPresenter;
import com.verbatoria.utils.Helper;

import javax.inject.Inject;
import butterknife.BindView;

/**
 * Экран поиска клиентов
 *
 * @author nikitaremnev
 */
public class SearchClientsActivity extends BaseActivity implements ISearchClientsView {

    private static final String TAG = SearchClientsActivity.class.getSimpleName();

    @Inject
    IClientsPresenter mClientsPresenter;

    @BindView(R.id.client_name_editable_field)
    public View mClientNameEditableField;

    @BindView(R.id.client_email_editable_field)
    public View mClientEmailEditableField;

    @BindView(R.id.client_phone_editable_field)
    public View mClientPhoneEditableField;

    @BindView(R.id.search_button)
    public Button mSearchButton;

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, SearchClientsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getApplicationComponent().addModule(new CalendarModule()).inject(this);

        setContentView(R.layout.activity_search_client);

        setPresenter((BasePresenter) mClientsPresenter);
        mClientsPresenter.bindView(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void setUpViews() {
        setUpNavigation();
        setUpEditableFields();
    }

    private void setUpNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.calendar_activity_search_client_title));
    }

    private void setUpEditableFields() {
        setUpEditableFieldView(mClientNameEditableField, R.drawable.ic_client_name, mClientsPresenter.getClientName(),
                getString(R.string.event_detail_activity_client_name_editable_hint), InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        setUpEditableFieldView(mClientEmailEditableField, R.drawable.ic_client_email, mClientsPresenter.getClientEmail(),
                getString(R.string.event_detail_activity_client_email_editable_hint), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        setUpEditableFieldView(mClientPhoneEditableField, R.drawable.ic_client_phone, mClientsPresenter.getClientPhone(),
                getString(R.string.event_detail_activity_client_phone_editable_hint), InputType.TYPE_TEXT_VARIATION_PHONETIC);
    }

    private void setUpEditableFieldView(View fieldView, int imageResource, String title, String hint, int inputType) {
        ((ImageView) fieldView.findViewById(R.id.field_image_view)).setImageResource(imageResource);
        EditText fieldEditText = (EditText) fieldView.findViewById(R.id.field_edit_text);
        fieldEditText.setInputType(inputType);
        if (inputType == InputType.TYPE_TEXT_VARIATION_PHONETIC) {
            setUpPhoneFormatter(fieldEditText);
        }
        fieldEditText.setText(title);
        fieldEditText.setHint(hint);
    }

    private void setUpPhoneFormatter(EditText phoneEditText) {
        final MaskedTextChangedListener listener = new MaskedTextChangedListener(
                getString(R.string.login_phone_mask),
                true,
                phoneEditText,
                null,
                (b, s) -> {}
        );
        phoneEditText.addTextChangedListener(listener);
        phoneEditText.setOnFocusChangeListener(listener);
    }

    private String getFieldValue(View fieldView) {
        EditText fieldEditText = (EditText) fieldView.findViewById(R.id.field_edit_text);
        return fieldEditText.getText().toString();
    }

    @Override
    public void showProgress() {
        startProgress();
    }

    @Override
    public void hideProgress() {
        stopProgress();
    }

    @Override
    public void showError(String message) {
        Helper.showSnackBar(mSearchButton, message);
    }

}
