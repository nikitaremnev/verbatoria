package com.verbatoria.presentation.login.view.login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.redmadrobot.inputmask.MaskedTextChangedListener;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.login.LoginModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.dashboard.view.DashboardActivity;
import com.verbatoria.presentation.login.presenter.login.ILoginPresenter;
import com.verbatoria.presentation.login.view.recovery.RecoveryActivity;
import com.verbatoria.utils.Helper;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран логина
 *
 * @author nikitaremnev
 */
public class LoginActivity extends BaseActivity implements ILoginView {

    @Inject
    ILoginPresenter mLoginPresenter;

    /*
        Views
     */

    @BindView(R.id.country_code_spinner)
    public Spinner mCounryCodeSpinner;

    @BindView(R.id.login_edit_text)
    public EditText mLoginEditText;

    @BindView(R.id.password_edit_text)
    public EditText mPasswordEditText;

    @BindView(R.id.login_button)
    public Button mLoginButton;

    @BindView(R.id.recovery_password_text_view)
    public TextView mRecoveryPasswordTextView;

    public static Intent newInstance(Context context) {
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        VerbatoriaApplication.getApplicationComponent().addModule(new LoginModule()).inject(this);

        //initialize views
        setContentView(R.layout.activity_login);

        //bind views
        setPresenter((BasePresenter) mLoginPresenter);
        mLoginPresenter.bindView(this);

        super.onCreate(savedInstanceState);
        //test
        setPhone("79153974689");
        setPassword("test");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.unbindView();
    }

    //отображение прогресса
    @Override
    public void showProgress() {
        startProgress();
    }

    @Override
    public void hideProgress() {
        stopProgress();
    }

    @Override
    public void setPhone(String phone) {
        mLoginEditText.setText(phone);
    }

    @Override
    public void setPassword(String password) {
        mPasswordEditText.setText(password);
    }

    @Override
    public String getPhone() {
        return mLoginEditText.getText().toString();
    }

    @Override
    public String getPassword() {
        return mPasswordEditText.getText().toString();
    }

    //отображение результатов запроса
    @Override
    public void loginSuccess() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String message) {
        Helper.showSnackBar(mLoginButton, message);
    }

    @Override
    public void startRecoveryPassword() {
        Intent intent = RecoveryActivity.newInstance(this, getPhone());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void setUpViews() {
        setUpPhoneFormatter();
        mLoginButton.setOnClickListener(v -> mLoginPresenter.login());
        mRecoveryPasswordTextView.setPaintFlags(mRecoveryPasswordTextView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        mRecoveryPasswordTextView.setOnClickListener(v -> mLoginPresenter.startRecoveryPassword());
    }

    private void setUpPhoneFormatter() {
        final MaskedTextChangedListener listener = new MaskedTextChangedListener(
                getString(R.string.login_phone_mask),
                true,
                mLoginEditText,
                null,
                (b, s) -> {}
        );
        mLoginEditText.addTextChangedListener(listener);
        mLoginEditText.setOnFocusChangeListener(listener);
    }

}
