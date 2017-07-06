package com.verbatoria.presentation.dashboard.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.dashboard.presenter.IDashboardPresenter;
import com.verbatoria.utils.Logger;

import java.util.List;

import javax.inject.Inject;
import butterknife.ButterKnife;

/**
 * Экран dashboard
 *
 * @author nikitaremnev
 */
public class DashboardActivity extends AppCompatActivity implements IDashboardView {

    private static final String TAG = DashboardActivity.class.getSimpleName();

    @Inject
    IDashboardPresenter mDashboardPresenter;

    private IVerbatologInfoView mVerbatologInfoView;
    private IVerbatologEventsView mVerbatologEventsView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setUpFragments();
        ButterKnife.bind(this);

        //bind views
        VerbatoriaApplication.getApplicationComponent().addModule(new DashboardModule()).inject(this);
        mDashboardPresenter.bindView(this);
        mDashboardPresenter.updateVerbatologInfo();
        mDashboardPresenter.updateVerbatologEvents();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDashboardPresenter.unbindView();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showVerbatologInfo(String verbatologFullName, String verbatologPhone, String verbatologEmail) {
        Logger.e(TAG, verbatologFullName);
        mVerbatologInfoView.showVerbatologName(verbatologFullName);
        mVerbatologInfoView.showVerbatologPhone(verbatologPhone);
        mVerbatologInfoView.showVerbatologEmail(verbatologEmail);
    }

    @Override
    public void showVerbatologEvents(List<EventModel> verbatologEvents) {
        Logger.e(TAG, verbatologEvents.toString());
        mVerbatologEventsView.showVerbatologEvents(verbatologEvents);
    }

    private void setUpFragments() {
        setUpVerbatologInfoFragment();
        setUpVerbatologEventsFragment();
    }

    private void setUpVerbatologInfoFragment() {
        VerbatologInfoFragment verbatologInfoFragment = VerbatologInfoFragment.newInstance();
        mVerbatologInfoView = verbatologInfoFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.verbatolog_info_frame_layout, verbatologInfoFragment);
        transaction.commit();
    }

    private void setUpVerbatologEventsFragment() {
        VerbatologEventsFragment verbatologEventsFragment = VerbatologEventsFragment.newInstance();
        mVerbatologEventsView = verbatologEventsFragment;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.verbatolog_events_frame_layout, verbatologEventsFragment);
        transaction.commit();
    }
}
