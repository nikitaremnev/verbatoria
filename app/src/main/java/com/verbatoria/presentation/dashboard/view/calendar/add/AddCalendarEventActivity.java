package com.verbatoria.presentation.dashboard.view.calendar.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.dashboard.DashboardModule;
import com.verbatoria.presentation.dashboard.presenter.calendar.add.IAddCalendarEventPresenter;
import com.verbatoria.presentation.dashboard.presenter.calendar.detail.CalendarEventDetailPresenter;
import com.verbatoria.presentation.dashboard.view.calendar.detail.CalendarEventDetailActivity;

import javax.inject.Inject;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Экран добавления события в календарь
 *
 * @author nikitaremnev
 */
public class AddCalendarEventActivity extends AppCompatActivity implements IAddCalendarEventView {

    private static final String TAG = AddCalendarEventActivity.class.getSimpleName();

    @Inject
    IAddCalendarEventPresenter mAddCalendarEventPresenter;

    @BindView(R.id.child_id_edit_text)
    public EditText mChildIdEditText;

    @BindView(R.id.location_id_edit_text)
    public EditText mLocationIdEditText;

    @BindView(R.id.start_date_edit_text)
    public EditText mStartDateEditText;

    @BindView(R.id.end_date_edit_text)
    public EditText mEndDateEditText;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, AddCalendarEventActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_calendar_event);
        VerbatoriaApplication.getApplicationComponent().addModule(new DashboardModule()).inject(this);
        ButterKnife.bind(this);
        setUpNavigation();
        mAddCalendarEventPresenter.bindView(this);
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
        getSupportActionBar().setTitle(getString(R.string.dashboard_add_event));
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
    public void addEvent() {
        mAddCalendarEventPresenter.addEvent(mChildIdEditText.getText().toString(),
                mLocationIdEditText.getText().toString(),
                mStartDateEditText.getText().toString(),
                mEndDateEditText.getText().toString());
    }


}
