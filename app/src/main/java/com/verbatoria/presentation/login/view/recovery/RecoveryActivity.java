package com.verbatoria.presentation.login.view.recovery;

import android.graphics.Paint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.remnev.verbatoriamini.R;
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
    public EditText mRememberTextView;

    @BindView(R.id.submit_button)
    public Button mSubmitButton;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getApplicationComponent().addModule(new LoginModule()).inject(this);

        //initialize views
        setContentView(R.layout.activity_recovery);

        //bind views
        setPresenter((BasePresenter) mRecoveryPresenter);
        mRecoveryPresenter.bindView(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public String getPhone() {
        return mPhoneEditText.toString();
    }

    @Override
    public String getNewPassword() {
        return mNewPasswordEditText.toString();
    }

    @Override
    public String confirmPasswords(String password, String confirmPassword) {
        return mRecoveryPresenter.toString();
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
    public void sendCodeToPhone() {

    }

    @Override
    public void sendNewPassword() {

    }

    @Override
    public void showPhoneInput() {
        mPhoneEditText.setEnabled(true);
        mPhoneEditText.setVisibility(View.VISIBLE);
        mCodeEditText.setVisibility(View.GONE);
        mNewPasswordEditText.setVisibility(View.GONE);
        mNewPasswordConfirmEditText.setVisibility(View.GONE);
        mSubmitButton.setText(getString(R.string.recovery_send_code));
    }

    @Override
    public void showCodeInput() {
        mPhoneEditText.setEnabled(false);
        mPhoneEditText.setVisibility(View.VISIBLE);
        mCodeEditText.setVisibility(View.VISIBLE);
        mNewPasswordEditText.setVisibility(View.GONE);
        mNewPasswordConfirmEditText.setVisibility(View.GONE);
        mSubmitButton.setText(getString(R.string.recovery_send_code));
    }

    @Override
    public void showNewPasswordInput() {
        mPhoneEditText.setVisibility(View.GONE);
        mCodeEditText.setVisibility(View.GONE);
        mNewPasswordEditText.setVisibility(View.VISIBLE);
        mNewPasswordConfirmEditText.setVisibility(View.VISIBLE);
        mSubmitButton.setText(getString(R.string.recovery_send_new_password));
    }

    @Override
    public void showError(String message) {
        Helper.showSnackBar(mLoadingView, message);
    }

    @Override
    public void rememberPassword() {
        startActivity(LoginActivity.createIntent(this));
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

    private void setUpPhoneFormatter() {
        final MaskedTextChangedListener listener = new MaskedTextChangedListener(
                getString(R.string.login_phone_mask),
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

            }
        };
        mNewPasswordConfirmEditText.addTextChangedListener(newPasswordConfirmWatcher);
    }
}
