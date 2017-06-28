package com.verbatoria.presentation.dashboard.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.dashboard.presenter.IDashboardPresenter;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Экран dashboard
 *
 * @author nikitaremnev
 */
public class DashboardActivity extends AppCompatActivity implements IDashboardView {

    @Inject
    IDashboardPresenter mDashboardPresenter;

    @BindView(R.id.token)
    public TextView mTokenTextView;

    @BindView(R.id.expires)
    public TextView mExpiresTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);

        //bind views
        VerbatoriaApplication.getApplicationComponent().addModule(new DashboardModule()).inject(this);
        mDashboardPresenter.bindView(this);

        mDashboardPresenter.readToken();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showToken(TokenModel tokenModel) {
        mTokenTextView.setText(tokenModel.getAccessToken());
        mExpiresTextView.setText(tokenModel.getExpiresToken());
    }
}
