package com.verbatoria.presentation.login.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.data.network.request.LoginRequestModel;
import com.verbatoria.di.login.LoginModule;
import com.verbatoria.presentation.login.models.LoginFilledDataModel;
import com.verbatoria.presentation.login.presenter.ILoginPresenter;

import javax.inject.Inject;

import butterknife.BindView;

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
    private EditText mLoginEditText;

    @BindView(R.id.password_text_view)
    private EditText mPasswordEditText;

    @BindView(R.id.login_button)
    private Button mLoginButton;

    @BindView(R.id.progress_layout)
    private View mLoadingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        VerbatoriaApplication.get(this).applicationComponent().addModule(new LoginModule()).inject(this);

        setUpViews();
    }

    //отображение прогресса
    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
    }

    //отображение результатов запроса
    @Override
    public void showSuccess() {

    }

    @Override
    public void showError() {

    }

    //менеджмент кнопки "Войти"
    @Override
    public void setLoginButtonAvailability(boolean enabled) {
        mLoginButton.setEnabled(enabled);
    }

    private void setUpViews() {
        mLoginEditText.addTextChangedListener(new PhoneNumberFormattingTextWatcher());
        mLoginButton.setOnClickListener(v -> mLoginPresenter.login(getLoginRequestModel()));
    }

    private LoginFilledDataModel getLoginRequestModel() {
        return new LoginFilledDataModel();
    }

}
