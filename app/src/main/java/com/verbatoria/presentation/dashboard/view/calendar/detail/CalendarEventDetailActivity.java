package com.verbatoria.presentation.dashboard.view.calendar.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.presentation.dashboard.presenter.calendar.detail.CalendarEventDetailPresenter;
import com.verbatoria.presentation.dashboard.presenter.calendar.detail.ICalendarEventDetailPresenter;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Экран события
 *
 * @author nikitaremnev
 */
public class CalendarEventDetailActivity extends AppCompatActivity implements ICalendarEventDetailView {

    private static final String TAG = CalendarEventDetailActivity.class.getSimpleName();

    @Inject
    ICalendarEventDetailPresenter mCalendarEventDetailPresenter;

    @BindView(R.id.start_session_button)
    public Button mStartSessionButton;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    public static Intent newInstance(Context mContext, EventModel eventModel) {
        Intent intent = new Intent(mContext, CalendarEventDetailActivity.class);
        intent.putExtra(CalendarEventDetailPresenter.EXTRA_EVENT_MODEL, eventModel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar_event_detail);
        VerbatoriaApplication.getApplicationComponent().addModule(new SessionModule()).inject(this);
        ButterKnife.bind(this);
        setUpNavigation();
        setUpStartSessionButton();
        mCalendarEventDetailPresenter.bindView(this);
        mCalendarEventDetailPresenter.obtainEvent(getIntent());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void setUpNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.dashboard_event));
    }

    private void setUpStartSessionButton() {
        mStartSessionButton.setOnClickListener(v -> {
            mCalendarEventDetailPresenter.startSession();
        });
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

    @Override
    public void startSession() {
        mCalendarEventDetailPresenter.startSession();
    }

    @Override
    public void showError(String message) {
        //TODO: replace hint
        Snackbar snackbar = Snackbar.make(mLoadingView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
