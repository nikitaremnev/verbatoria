package com.verbatoria.presentation.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.login.LoginModule;
import com.verbatoria.presentation.dashboard.view.DashboardActivity;
import com.verbatoria.presentation.login.presenter.ILoginPresenter;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.presentation.session.view.submit.SubmitActivity;
import com.verbatoria.presentation.session.view.writing.WritingActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Экран логина
 *
 * @author nikitaremnev
 */
public class LoginActivity extends AppCompatActivity implements ILoginView {

    @Inject
    ILoginPresenter mLoginPresenter;

    /*
        Views
     */
    @BindView(R.id.login_text_view)
    public EditText mLoginEditText;

    @BindView(R.id.password_text_view)
    public EditText mPasswordEditText;

    @BindView(R.id.login_button)
    public Button mLoginButton;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize views
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        setUpViews();
        //bind views
        VerbatoriaApplication.getApplicationComponent().addModule(new LoginModule()).inject(this);
        mLoginPresenter.bindView(this);

        //test
        setPhone("+79266519001");
        setPassword("4eqx8pmRZpfy");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLoginPresenter.unbindView();
    }

    //отображение прогресса
    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
        mLoginButton.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
        mLoginButton.setVisibility(View.VISIBLE);
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
        Intent intent = new Intent(this, ConnectionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String message) {
        //TODO: replace hint
        Snackbar snackbar = Snackbar.make(mLoadingView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    private void setUpViews() {
        mLoginEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mLoginButton.setOnClickListener(v -> mLoginPresenter.login());
    }

}
