package com.verbatoria.presentation.login.view.recovery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.login.LoginModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.login.presenter.recovery.IRecoveryPresenter;
import com.verbatoria.presentation.login.view.login.LoginActivity;
import com.verbatoria.utils.Helper;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран восстановления пароля, включая полный цикл,
 *  ввод код потверждения, задание нового пароля
 *
 * @author nikitaremnev
 */
public class RecoveryActivity extends BaseActivity implements IRecoveryView {

    private static final String TAG = RecoveryActivity.class.getSimpleName();
    public static final String EXTRA_PHONE = "com.verbatoria.presentation.login.view.recovery.EXTRA_PHONE";

    private static final int REQUEST_PERMISSION_CODE = 2445;

    @Inject
    IRecoveryPresenter mRecoveryPresenter;

    /*
        Views
     */
    @BindView(R.id.phone_edit_text)
    public EditText mPhoneEditText;

    @BindView(R.id.code_edit_text)
    public EditText mCodeEditText;

    @BindView(R.id.new_password_edit_text)
    public EditText mNewPasswordEditText;

    @BindView(R.id.new_password_confirm_edit_text)
    public EditText mNewPasswordConfirmEditText;

    @BindView(R.id.remember_text_view)
    public TextView mRememberTextView;

    @BindView(R.id.submit_button)
    public Button mSubmitButton;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    public static Intent newInstance(Context mContext, String phone) {
        Intent intent = new Intent(mContext, RecoveryActivity.class);
        intent.putExtra(EXTRA_PHONE, phone);
        return intent;
    }

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, RecoveryActivity.class);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getApplicationComponent().addModule(new LoginModule()).inject(this);

        //initialize views
        setContentView(R.layout.activity_recovery);

        //bind views
        setPresenter((BasePresenter) mRecoveryPresenter);
        mRecoveryPresenter.bindView(this);

        super.onCreate(savedInstanceState);

        mRecoveryPresenter.obtainPhone(getIntent());
        showPhoneInput();
    }

    @Override
    protected void onResume() {
        super.onResume();
        askPermissions();
    }

    @Override
    public String getPhone() {
        return mPhoneEditText.getText().toString();
    }

    @Override
    public String getCode() {
        return mCodeEditText.getText().toString();
    }

    @Override
    public void setPhone(String phone) {
        mPhoneEditText.setText(phone);
    }

    @Override
    public String getNewPassword() {
        return mNewPasswordEditText.getText().toString();
    }

    @Override
    public String getNewConfirmPassword() {
        return mNewPasswordConfirmEditText.getText().toString();
    }

    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void showPhoneInput() {
        mPhoneEditText.setEnabled(true);
        mPhoneEditText.setVisibility(View.VISIBLE);
        mCodeEditText.setVisibility(View.GONE);
        mNewPasswordEditText.setVisibility(View.GONE);
        mNewPasswordConfirmEditText.setVisibility(View.GONE);
        mSubmitButton.setText(getString(R.string.recovery_send_code));
        mSubmitButton.setOnClickListener(v -> mRecoveryPresenter.recoveryPassword());
    }

    @Override
    public void showCodeInput() {
        mCodeEditText.setEnabled(true);
        mPhoneEditText.setEnabled(false);
        mPhoneEditText.setVisibility(View.VISIBLE);
        mCodeEditText.setVisibility(View.VISIBLE);
        mNewPasswordEditText.setVisibility(View.GONE);
        mNewPasswordConfirmEditText.setVisibility(View.GONE);
        mSubmitButton.setText(getString(R.string.recovery_send_code));
        mSubmitButton.setOnClickListener(v -> showNewPasswordInput());
    }

    @Override
    public void showNewPasswordInput() {
        mPhoneEditText.setVisibility(View.GONE);
        mCodeEditText.setVisibility(View.GONE);
        mNewPasswordEditText.setVisibility(View.VISIBLE);
//        mNewPasswordConfirmEditText.setVisibility(View.VISIBLE);
        mSubmitButton.setText(getString(R.string.recovery_send_new_password));
        mSubmitButton.setOnClickListener(v -> mRecoveryPresenter.sendNewPassword());
    }

    @Override
    public void showError(String message) {
        Helper.showErrorSnackBar(mLoadingView, message);
    }

    @Override
    public void rememberPassword() {
        startActivity(LoginActivity.newInstance(this));
        finish();
    }

    @Override
    protected void setUpViews() {
        setUpPhoneFormatter();
        setUpNewPasswordFormatter();
        setUpNewPasswordConfirmFormatter();
        mPhoneEditText.setText("");
        mRememberTextView.setPaintFlags(mRememberTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mRememberTextView.setOnClickListener(v -> mRecoveryPresenter.rememberPassword());
    }

    private void askPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{ Manifest.permission.RECEIVE_SMS }, REQUEST_PERMISSION_CODE);
        }
    }

    private void setUpPhoneFormatter() {
        String country = mRecoveryPresenter.getCountry();
        int formatterResource;
        if (country.equals(getString(R.string.country_russia))) {
            formatterResource = R.string.login_russia_phone_mask;
        } else if (country.equals(getString(R.string.country_ukraine))) {
            formatterResource = R.string.login_ukraine_phone_mask;
        } else if (country.equals(getString(R.string.country_azerbaijan))) {
            formatterResource = R.string.login_azerbaijan_phone_mask;
        } else if (country.equals(getString(R.string.country_thailand))) {
            formatterResource = R.string.login_thailand_phone_mask;
        } else if (country.equals(getString(R.string.country_belarus))) {
            formatterResource = R.string.login_belarus_phone_mask;
        } else if (country.equals(getString(R.string.country_israel))) {
            formatterResource = R.string.login_israel_phone_mask;
        } else if (country.equals(getString(R.string.country_uae))) {
            formatterResource = R.string.login_uae_phone_mask;
        } else {
            formatterResource = R.string.login_uzbekistan_phone_mask;
        }
        final MaskedTextChangedListener listener = new MaskedTextChangedListener(
                getString(formatterResource),
                true,
                mPhoneEditText,
                null,
                (b, s) -> {}
        );
        mPhoneEditText.addTextChangedListener(listener);
        mPhoneEditText.setOnFocusChangeListener(listener);
    }

    private void setUpNewPasswordFormatter() {
        final TextWatcher newPasswordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mRecoveryPresenter.checkPasswordRequirements();
            }
        };
        mNewPasswordEditText.addTextChangedListener(newPasswordWatcher);
    }

    private void setUpNewPasswordConfirmFormatter() {
        final TextWatcher newPasswordConfirmWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mRecoveryPresenter.confirmPassword();
            }
        };
        mNewPasswordConfirmEditText.addTextChangedListener(newPasswordConfirmWatcher);
    }

}
