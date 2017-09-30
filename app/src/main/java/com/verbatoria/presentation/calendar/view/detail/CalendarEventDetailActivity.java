package com.verbatoria.presentation.calendar.view.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.calendar.presenter.detail.CalendarEventDetailPresenter;
import com.verbatoria.presentation.calendar.presenter.detail.ICalendarEventDetailPresenter;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.presentation.session.view.writing.WritingActivity;

import javax.inject.Inject;
import butterknife.BindView;

/**
 * Экран события
 *
 * @author nikitaremnev
 */
public class CalendarEventDetailActivity extends BaseActivity implements ICalendarEventDetailView {

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
        VerbatoriaApplication.getApplicationComponent().addModule(new SessionModule()).inject(this);
        setContentView(R.layout.activity_calendar_event_detail);

        mCalendarEventDetailPresenter.bindView(this);
        mCalendarEventDetailPresenter.obtainEvent(getIntent());

        setPresenter((BasePresenter) mCalendarEventDetailPresenter);
        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setUpViews() {
        setUpToolbar();
        setUpStartSessionButton();
    }

    private void setUpToolbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.dashboard_event));
    }

    private void setUpStartSessionButton() {
        mStartSessionButton.setOnClickListener(v -> mCalendarEventDetailPresenter.startSession());
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
    public void startConnection() {
        Intent intent = ConnectionActivity.newInstance(this, mCalendarEventDetailPresenter.getEvent());
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String message) {
        Snackbar snackbar = Snackbar.make(mLoadingView, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

}
