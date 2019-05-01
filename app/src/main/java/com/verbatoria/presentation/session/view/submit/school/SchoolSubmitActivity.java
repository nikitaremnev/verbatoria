package com.verbatoria.presentation.session.view.submit.school;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.presentation.dashboard.view.DashboardActivity;
import com.verbatoria.presentation.session.presenter.submit.school.ISchoolSubmitPresenter;
import com.verbatoria.utils.Helper;
import com.verbatoria.utils.Logger;
import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.verbatoria.presentation.session.view.connection.ConnectionActivity.EXTRA_EVENT_MODEL;

/**
 * Экран отправки результатов для школьного режима
 *
 * @author nikitaremnev
 */

public class SchoolSubmitActivity extends AppCompatActivity implements ISchoolSubmitView {

    private static final String TAG = SchoolSubmitActivity.class.getSimpleName();

    @Inject
    ISchoolSubmitPresenter presenter;

    @BindView(R.id.submit_button)
    public Button mSubmitButton;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, SchoolSubmitActivity.class);
    }

    public static Intent newInstance(Context mContext, EventModel eventModel) {
        Intent intent = new Intent(mContext, SchoolSubmitActivity.class);
        intent.putExtra(EXTRA_EVENT_MODEL, eventModel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize views
        setContentView(R.layout.activity_school_submit);
        ButterKnife.bind(this);
        mSubmitButton.setOnClickListener(v -> presenter.sendResults());
        //bind views
        VerbatoriaApplication.getApplicationComponent().addModule(new SessionModule()).inject(this);
        presenter.bindView(this);
        presenter.obtainEvent(getIntent());
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Logger.e(TAG, "onUserInteraction");
        VerbatoriaApplication.onUserInteraction();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
    }

    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
        mSubmitButton.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
        mSubmitButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Helper.showErrorSnackBar(mLoadingView, message);
    }

    @Override
    public void finishSession() {
        startActivity(DashboardActivity.newInstance(this, true));
        finish();
    }

    @Override
    public void finishSessionWithError() {
        startActivity(DashboardActivity.newInstance(this, false));
        finish();
    }

}
