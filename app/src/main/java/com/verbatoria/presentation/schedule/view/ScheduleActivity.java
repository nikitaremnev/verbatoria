package com.verbatoria.presentation.schedule.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;

import com.cleveroad.adaptivetablelayout.AdaptiveTableLayout;
import com.cleveroad.adaptivetablelayout.OnItemClickListener;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.schedule.datasource.IScheduleDataSource;
import com.verbatoria.di.schedule.ScheduleModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.schedule.presenter.ISchedulePresenter;
import com.verbatoria.presentation.schedule.view.adapter.ScheduleAdapter;
import com.verbatoria.utils.Helper;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран расписания
 *
 * @author nikitaremnev
 */
public class ScheduleActivity extends BaseActivity implements IScheduleView, OnItemClickListener {

    private static final String TAG = ScheduleActivity.class.getSimpleName();

    @BindView(R.id.schedule_recycler_view)
    public AdaptiveTableLayout mScheduleLayout;

    @Inject
    ISchedulePresenter mSchedulePresenter;

    private ScheduleAdapter mScheduleAdapter;

    public static Intent newInstance(Context mContext) {
        Intent intent = new Intent(mContext, ScheduleActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        VerbatoriaApplication.getApplicationComponent().addModule(new ScheduleModule()).inject(this);

        setContentView(R.layout.activity_schedule);

        mSchedulePresenter.bindView(this);
        setPresenter((BasePresenter) mSchedulePresenter);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_schedule, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.action_prev_week:
                mSchedulePresenter.onPreviousWeekClicked();
                break;
            case R.id.action_next_week:
                mSchedulePresenter.onNextWeekClicked();
                break;
            case R.id.action_clear:
                mSchedulePresenter.onClearScheduleClicked();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSchedulePresenter.unbindView();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void notifyItemChanged(int row, int column, boolean state) {
        mScheduleAdapter.notifyItemChanged(row, column, state);
    }

    @Override
    public void notifyScheduleCleared() {
        mScheduleAdapter.notifyDataSetChanged();
    }

    @Override
    public void showScheduleClearConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.schedule_clear_confirm_title))
                .setMessage(getString(R.string.schedule_clear_confirm_message));
        builder.setPositiveButton(getString(R.string.schedule_clear), (dialog, which) -> {
            mSchedulePresenter.clearSchedule();
        });
        builder.setNegativeButton(getString(R.string.cancel), null);
        builder.create().show();
    }

    @Override
    public void showError(String error) {
        Helper.showErrorSnackBar(mScheduleLayout, error);
    }

    @Override
    public void showScheduleNextWeekSelectedMessage(String week) {
        Helper.showShortHintSnackBar(mScheduleLayout, String.format(getString(R.string.schedule_week_selected_message), week));
    }

    @Override
    public void showSchedulePreviousWeekSelectedMessage(String week) {
        Helper.showShortHintSnackBar(mScheduleLayout, String.format(getString(R.string.schedule_week_selected_message), week));
    }

    @Override
    protected void setUpViews() {
        setUpNavigation();
    }

    private void setUpNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.schedule_title));
    }

    @Override
    public void setUpAdapter(IScheduleDataSource scheduleDataSource) {
        mScheduleAdapter = new ScheduleAdapter(this, scheduleDataSource);
        mScheduleAdapter.setOnItemClickListener(this);
        mScheduleLayout.setAdapter(mScheduleAdapter);
    }

    @Override
    public void onItemClick(int row, int column) {
        mSchedulePresenter.onItemClicked(row, column);
    }

    @Override
    public void onRowHeaderClick(int row) {
        //empty
    }

    @Override
    public void onColumnHeaderClick(int column) {
        //empty
    }

    @Override
    public void onLeftTopHeaderClick() {
        //empty
    }
}
