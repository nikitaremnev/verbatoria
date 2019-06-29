package com.verbatoria.ui.schedule.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;

import com.cleveroad.adaptivetablelayout.AdaptiveTableLayout;
import com.cleveroad.adaptivetablelayout.OnItemClickListener;
import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.schedule.datasource.IScheduleDataSource;
import com.verbatoria.di.schedule.ScheduleModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.ui.schedule.presenter.ISchedulePresenter;
import com.verbatoria.ui.schedule.view.adapter.ScheduleAdapter;
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

        VerbatoriaApplication.getInjector().addModule(new ScheduleModule()).inject(this);

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
            case R.id.action_save:
                mSchedulePresenter.onSaveScheduleClicked();
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
        startProgress();
    }

    @Override
    public void hideProgress() {
        stopProgress();
    }

    @Override
    public void notifyItemChanged(int row, int column, boolean state) {
        mScheduleAdapter.notifyItemChanged(row, column, state);
    }

    @Override
    public void notifyScheduleCleared() {
        Helper.showShortHintSnackBar(mScheduleLayout, getString(R.string.schedule_cleared));
        mScheduleLayout.notifyDataSetChanged();
    }

    @Override
    public void showScheduleClearConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.confirmation))
                .setMessage(getString(R.string.schedule_clear_confirm_message));
        builder.setPositiveButton(getString(R.string.clear), (dialog, which) -> {
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
    public void showScheduleSaved() {
        Helper.showShortHintSnackBar(mScheduleLayout, getString(R.string.schedule_saved));
    }

    @Override
    public void showScheduleLoaded(String period) {
        Helper.showShortHintSnackBar(mScheduleLayout, String.format(getString(R.string.schedule_loaded_by_period), period));
    }

    @Override
    public void confirmSaveSchedule() {
        View dialogRootView = getLayoutInflater().inflate(R.layout.dialog_save_schedule, null);
        RadioButton noOption = dialogRootView.findViewById(R.id.no_option);
        RadioButton firstOption = dialogRootView.findViewById(R.id.first_option);
        RadioButton secondOption = dialogRootView.findViewById(R.id.second_option);
        RadioButton thirdOption = dialogRootView.findViewById(R.id.third_option);
        RadioButton fourthOption = dialogRootView.findViewById(R.id.fourth_option);
        AlertDialog builder = new AlertDialog.Builder(this)
                .setView(dialogRootView)
                .setTitle(getString(R.string.schedule_saving))
                .setPositiveButton(getString(R.string.save), (dialog, which) -> {
                    int selectedWeeks = noOption.isChecked() ? 0 :
                            firstOption.isChecked() ? 1 :
                                    secondOption.isChecked() ? 2 :
                                            thirdOption.isChecked() ? 3 :
                                                    fourthOption.isChecked() ? 4 : 5;
                    mSchedulePresenter.saveSchedule(selectedWeeks);
                })
                .setNegativeButton(getString(R.string.cancel), null)
                .create();
        noOption.setChecked(true);
        builder.show();
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
        mScheduleAdapter = new ScheduleAdapter(this, scheduleDataSource, mScheduleLayout.getWidth(), mScheduleLayout.getHeight());
        mScheduleAdapter.setOnItemClickListener(this);
        mScheduleLayout.setAdapter(mScheduleAdapter);
        mScheduleLayout.notifyLayoutChanged();
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
