package com.verbatoria.presentation.late_send.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.late_send.models.LateReportModel;
import com.verbatoria.di.late_send.LateSendModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.presentation.late_send.presenter.ILateSendPresenter;
import com.verbatoria.presentation.late_send.view.adapter.LateReportAdapter;
import com.verbatoria.utils.Helper;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран поздней отправки отчетов
 *
 * @author nikitaremnev
 */
public class LateSendActivity extends BaseActivity implements ILateSendView, ILateSendView.Callback {

    private static final String TAG = LateSendActivity.class.getSimpleName();

    @Inject
    ILateSendPresenter mLateSendPresenter;

    @BindView(R.id.late_send_reports_recycler_view)
    public RecyclerView mLateSendReportsRecyclerView;

    @BindView(R.id.no_reports_text_view)
    public TextView mNoReportsTextView;

    private LateReportAdapter mLateReportAdapter;

    public static Intent newInstance(Context mContext) {
        Intent intent = new Intent(mContext, LateSendActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        VerbatoriaApplication.getApplicationComponent().addModule(new LateSendModule()).inject(this);

        setContentView(R.layout.activity_late_send);

        mLateSendPresenter.bindView(this);
        setPresenter((BasePresenter) mLateSendPresenter);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLateSendPresenter.unbindView();
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
    public void notifyItemSent(int index) {
        mLateReportAdapter.notifyItemRemoved(index);
    }

    public void showError(String error) {
        Helper.showErrorSnackBar(mLateSendReportsRecyclerView, error);
    }

    @Override
    public void showNoReportsToSend() {
        mLateSendReportsRecyclerView.setVisibility(View.GONE);
        mNoReportsTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLateReports(List<LateReportModel> lateReportModels) {
        mLateReportAdapter = new LateReportAdapter(lateReportModels, this);
        mLateSendReportsRecyclerView.setAdapter(mLateReportAdapter);
    }

    @Override
    protected void setUpViews() {
        setUpNavigation();
        setUpRecyclerView();
    }

    private void setUpNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.late_send_title));
    }

    private void setUpRecyclerView() {
        mLateSendReportsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mLateSendReportsRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    @Override
    public void onItemClicked(int position) {
        mLateSendPresenter.sendReport(position);
    }
}
