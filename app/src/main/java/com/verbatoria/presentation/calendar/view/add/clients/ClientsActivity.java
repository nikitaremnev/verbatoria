package com.verbatoria.presentation.calendar.view.add.clients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.data.network.common.ClientModel;
import com.verbatoria.di.calendar.CalendarModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.presenter.add.clients.IClientsPresenter;
import com.verbatoria.utils.Helper;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран добавления клиентов
 *
 * @author nikitaremnev
 */
public class ClientsActivity extends BaseActivity implements IClientsView {

    private static final String TAG = ClientsActivity.class.getSimpleName();
    public static final String EXTRA_CLIENT_MODEL = "com.verbatoria.presentation.calendar.view.add.clients.EXTRA_CLIENT_MODEL";

    @Inject
    IClientsPresenter mClientsPresenter;

    @BindView(R.id.client_name_field)
    public View mClientNameField;

    @BindView(R.id.client_email_field)
    public View mClientEmailField;

    @BindView(R.id.client_phone_field)
    public View mClientPhoneField;

    @BindView(R.id.client_name_editable_field)
    public View mClientNameEditableField;

    @BindView(R.id.client_email_editable_field)
    public View mClientEmailEditableField;

    @BindView(R.id.client_phone_editable_field)
    public View mClientPhoneEditableField;

    @BindView(R.id.submit_button)
    public Button mSubmitButton;

    private MenuItem mEditMenuItem;
    private MenuItem mCancelMenuItem;

    public static Intent newInstance(Context mContext, ClientModel clientModel) {
        Intent intent = new Intent(mContext, ClientsActivity.class);
        intent.putExtra(EXTRA_CLIENT_MODEL, clientModel);
        return intent;
    }

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, ClientsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getApplicationComponent().addModule(new CalendarModule()).inject(this);
        mClientsPresenter.obtainClient(getIntent());

        setContentView(R.layout.activity_client);

        setPresenter((BasePresenter) mClientsPresenter);
        mClientsPresenter.bindView(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_edit, menu);
        mCancelMenuItem = menu.findItem(R.id.action_cancel);
        mEditMenuItem = menu.findItem(R.id.action_edit);
        mEditMenuItem.setVisible(mClientsPresenter.isEditMode());
        mCancelMenuItem.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void setUpViews() {
        setUpNavigation();
        setUpFields();
        setUpEditableFields();
        if (mClientsPresenter.isEditMode()) {
            setUpReadonlyMode();
        } else {
            setUpNewClientMode();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        if (item.getItemId() == R.id.action_edit) {
            setUpEditableMode();
        }
        if (item.getItemId() == R.id.action_cancel) {
            setUpReadonlyMode();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.calendar_activity_add_client_title));
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
    public void setUpEditableMode() {
        mClientEmailEditableField.setVisibility(View.VISIBLE);
        mClientNameEditableField.setVisibility(View.VISIBLE);
        mClientPhoneEditableField.setVisibility(View.VISIBLE);
        mClientEmailField.setVisibility(View.GONE);
        mClientNameField.setVisibility(View.GONE);
        mClientPhoneField.setVisibility(View.GONE);
        if (mEditMenuItem != null && mCancelMenuItem != null) {
            mEditMenuItem.setVisible(false);
            mCancelMenuItem.setVisible(true);
        }
        mSubmitButton.setText(getString(R.string.calendar_activity_save));
        mSubmitButton.setVisibility(View.VISIBLE);
        mSubmitButton.setOnClickListener(v -> mClientsPresenter.editClient());
    }

    @Override
    public void setUpNewClientMode() {
        mClientEmailEditableField.setVisibility(View.VISIBLE);
        mClientNameEditableField.setVisibility(View.VISIBLE);
        mClientPhoneEditableField.setVisibility(View.VISIBLE);
        mClientEmailField.setVisibility(View.GONE);
        mClientNameField.setVisibility(View.GONE);
        mClientPhoneField.setVisibility(View.GONE);
        if (mEditMenuItem != null && mCancelMenuItem != null) {
            mEditMenuItem.setVisible(false);
            mCancelMenuItem.setVisible(true);
        }
        mSubmitButton.setText(getString(R.string.calendar_activity_create));
        mSubmitButton.setVisibility(View.VISIBLE);
        mSubmitButton.setOnClickListener(v -> mClientsPresenter.createClient());
    }

    @Override
    public void finishWithResult() {
        setResult(RESULT_OK, createClientIntent());
        finish();
    }

    private Intent createClientIntent() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_CLIENT_MODEL, mClientsPresenter.getClientModel());
        return intent;
    }

    @Override
    public void setUpReadonlyMode() {
        mClientEmailEditableField.setVisibility(View.GONE);
        mClientNameEditableField.setVisibility(View.GONE);
        mClientPhoneEditableField.setVisibility(View.GONE);
        mClientEmailField.setVisibility(View.VISIBLE);
        mClientNameField.setVisibility(View.VISIBLE);
        mClientPhoneField.setVisibility(View.VISIBLE);
        if (mEditMenuItem != null && mCancelMenuItem != null) {
            if (mClientsPresenter.isEditMode()) {
                mEditMenuItem.setVisible(true);
                mCancelMenuItem.setVisible(false);
            } else {
                mEditMenuItem.setVisible(false);
                mCancelMenuItem.setVisible(false);
            }
        }
        mSubmitButton.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Helper.showSnackBar(mSubmitButton, message);
    }

    @Override
    public String getClientName() {
        return getFieldValue(mClientNameEditableField);
    }

    @Override
    public String getClientEmail() {
        return getFieldValue(mClientEmailEditableField);
    }

    @Override
    public String getClientPhone() {
        return getFieldValue(mClientPhoneEditableField);
    }

    private void setUpFields() {
        String clientNameString = TextUtils.isEmpty(mClientsPresenter.getClientName()) ? getString(R.string.event_detail_activity_field_empty): mClientsPresenter.getClientName();
        String clientPhoneString = TextUtils.isEmpty(mClientsPresenter.getClientPhone()) ? getString(R.string.event_detail_activity_field_empty): mClientsPresenter.getClientPhone();
        String clientEmailString = TextUtils.isEmpty(mClientsPresenter.getClientEmail()) ? getString(R.string.event_detail_activity_field_empty): mClientsPresenter.getClientEmail();
        setUpFieldView(mClientNameField, R.drawable.ic_client_name, clientNameString, getString(R.string.event_detail_activity_client_name));
        setUpFieldView(mClientEmailField, R.drawable.ic_client_email, clientEmailString, getString(R.string.event_detail_activity_client_email));
        setUpFieldView(mClientPhoneField, R.drawable.ic_client_phone, clientPhoneString, getString(R.string.event_detail_activity_client_phone));

    }

    private void setUpEditableFields() {
        setUpEditableFieldView(mClientNameEditableField, R.drawable.ic_client_name, mClientsPresenter.getClientName(),
                getString(R.string.event_detail_activity_client_name_editable_hint), InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        setUpEditableFieldView(mClientEmailEditableField, R.drawable.ic_client_email, mClientsPresenter.getClientEmail(),
                getString(R.string.event_detail_activity_client_email_editable_hint), InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        setUpEditableFieldView(mClientPhoneEditableField, R.drawable.ic_client_phone, mClientsPresenter.getClientPhone(),
                getString(R.string.event_detail_activity_client_phone_editable_hint), InputType.TYPE_TEXT_VARIATION_PHONETIC);
    }

    private void setUpFieldsStatus() {
        if (!mClientsPresenter.isEditMode()) {
            ((ImageView) mClientNameField.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_not_ok);
            ((ImageView) mClientEmailField.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_not_ok);
            ((ImageView) mClientPhoneField.findViewById(R.id.status_image_view)).setImageResource(R.drawable.ic_not_ok);
        }
    }

    private void setUpFieldView(View fieldView, int imageResource, String title, String subtitle) {
        ((ImageView) fieldView.findViewById(R.id.field_image_view)).setImageResource(imageResource);
        ((TextView) fieldView.findViewById(R.id.field_title)).setText(title);
        ((TextView) fieldView.findViewById(R.id.field_subtitle)).setText(subtitle);
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

}
