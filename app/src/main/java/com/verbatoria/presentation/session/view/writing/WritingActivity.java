package com.verbatoria.presentation.session.view.writing;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.presentation.dashboard.presenter.calendar.detail.CalendarEventDetailPresenter;
import com.verbatoria.presentation.session.presenter.writing.IWritingPresenter;
import com.verbatoria.presentation.session.view.connection.ConnectionActivity;
import com.verbatoria.presentation.session.view.reconnect.ReconnectionActivity;
import com.verbatoria.presentation.session.view.submit.SubmitActivity;
import com.verbatoria.utils.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.verbatoria.business.session.activities.ActivitiesCodes.*;

/**
 * Экран записи
 *
 * @author nikitaremnev
 */
public class WritingActivity extends AppCompatActivity implements IWritingView {

    private static final String TAG = WritingActivity.class.getSimpleName();

    @Inject
    IWritingPresenter mWritingPresenter;

    @BindView(R.id.code_99_button)
    public Button mCode99Button;
    @BindView(R.id.code_11_button)
    public Button mCode11Button;
    @BindView(R.id.code_21_button)
    public Button mCode21Button;
    @BindView(R.id.code_31_button)
    public Button mCode31Button;
    @BindView(R.id.code_41_button)
    public Button mCode41Button;
    @BindView(R.id.code_51_button)
    public Button mCode51Button;
    @BindView(R.id.code_61_button)
    public Button mCode61Button;
    @BindView(R.id.code_71_button)
    public Button mCode71Button;

    @BindView(R.id.line_chart)
    public LineChart mLineChart;

    @BindView(R.id.player_buttons)
    public View mPlayerContainer;

    @BindView(R.id.music_file_name_text_view)
    public TextView mMusicFileNameTextView;
    @BindView(R.id.timer_text_view)
    public TextView mTimerTextView;

    @BindView(R.id.play_floating_button)
    public FloatingActionButton mPlayButton;
    @BindView(R.id.pause_floating_button)
    public FloatingActionButton mPauseButton;
    @BindView(R.id.stop_floating_button)
    public FloatingActionButton mStopButton;
    @BindView(R.id.next_floating_button)
    public FloatingActionButton mNextButton;
    @BindView(R.id.back_floating_button)
    public FloatingActionButton mBackButton;

    @BindView(R.id.finish_button)
    public FloatingActionButton mFinishButton;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    /*
        Handler and runnables for updating UI
    */
    private Handler mUiHandler;

    public static Intent newInstance(Context mContext) {
        return new Intent(mContext, WritingActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize views
        setContentView(R.layout.activity_writing);
        ButterKnife.bind(this);
        setUpViews();
        setUpChart();
        setUpHandler();
        //bind views
        VerbatoriaApplication.getApplicationComponent().addModule(new SessionModule()).inject(this);
        mWritingPresenter.bindView(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //NOT ALLOWED TO BACK
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWritingPresenter.unbindView();
    }

    @Override
    public void addGraphEntry(int value) {
        LineData data = mLineChart.getData();
        if (data == null) {
            data = new LineData();
            data.setDrawValues(false);
            mLineChart.setData(data);
        }
        ILineDataSet set = data.getDataSetByIndex(0);
        if (set == null) {
            set = createSet();
            data.addDataSet(set);
        }
        data.addXValue(Integer.toString(set.getEntryCount()));
        data.addEntry(new Entry(value, set.getEntryCount()), 0);

        mLineChart.notifyDataSetChanged();
        mLineChart.setScaleXEnabled(false);
        mLineChart.setVisibleXRangeMaximum(20);
        mLineChart.moveViewToX(data.getXValCount() - 21);
    }

    @Override
    public void updateTimer(String timerString) {
        mUiHandler.post(() -> mTimerTextView.setText(timerString));
    }

     /*
        Player methods
     */
    @Override
    public void setUpPlayMode() {
        mPlayButton.setVisibility(View.VISIBLE);
        mPauseButton.setVisibility(View.GONE);
    }

    @Override
    public void setUpPauseMode() {
        mPlayButton.setVisibility(View.GONE);
        mPauseButton.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlayingFileName(String fileName) {
        mMusicFileNameTextView.setText(fileName);
    }

    @Override
    public void showPlayer() {
        mPlayerContainer.setVisibility(View.VISIBLE);
        setUpPlayMode();
    }

    @Override
    public void hidePlayer() {
        mPlayerContainer.setVisibility(View.GONE);
        mWritingPresenter.pauseClick();
    }

    @Override
    public void showError(String error) {
        Snackbar snackbar = Snackbar.make(mPlayerContainer, error, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    public void setButtonState(ActivityButtonState state, String code) {
        View buttonByCode = foundButtonByCode(code);
        switch (state) {
            case STATE_NEW:
                buttonByCode.setBackground(getResources().getDrawable(R.drawable.background_button_unselected));
                break;
            case STATE_SELECTED:
                buttonByCode.setBackground(getResources().getDrawable(R.drawable.background_button_selected));
                break;
            case STATE_DONE:
                buttonByCode.setBackground(getResources().getDrawable(R.drawable.background_button_done));
                break;
        }
    }

    @Override
    public void showFinishButton() {
        mFinishButton.show();
    }

    @Override
    public void hideFinishButton() {
        mFinishButton.hide();
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
    public void showSomeActivitiesNotFinished(String activities) {
        Logger.e(TAG, "activities: " + activities);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        if (activities.length() == CODE_11.length()) {
            dialog.setMessage(String.format(getString(R.string.session_activity_not_finished), activities));
        } else {
            dialog.setMessage(String.format(getString(R.string.session_some_activities_not_finished), activities));
        }
        dialog.setNegativeButton(getString(R.string.session_continue), (dialogInterface, i) -> dialogInterface.dismiss());
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    public void finishSession() {
        Intent intent = SubmitActivity.newInstance(this);
        startActivity(intent);
        finish();
    }

    @Override
    public void showConnectionError() {
        runOnUiThread(() -> {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(getString(R.string.session_connection_error));
            dialog.setNegativeButton(getString(R.string.session_connection_error_try), (dialog1, which) -> {
                dialog1.dismiss();
                startReconnection();
            });
            dialog.setCancelable(false);
            dialog.show();
        });
    }

    private void setUpViews() {
        mPlayButton.setOnClickListener(v -> mWritingPresenter.playClick());
        mPauseButton.setOnClickListener(v -> {
            setUpPlayMode();
            mWritingPresenter.pauseClick();
        });
        mNextButton.setOnClickListener(v -> {
            setUpPlayMode();
            mWritingPresenter.nextClick();
        });
        mBackButton.setOnClickListener(v -> {
            setUpPlayMode();
            mWritingPresenter.backClick();
        });
        mFinishButton.setOnClickListener(v -> mWritingPresenter.checkFinishAllowed());
        mCode99Button.setOnClickListener(new SubmitCodeOnClickListener());
        mCode11Button.setOnClickListener(new SubmitCodeOnClickListener());
        mCode21Button.setOnClickListener(new SubmitCodeOnClickListener());
        mCode31Button.setOnClickListener(new SubmitCodeOnClickListener());
        mCode41Button.setOnClickListener(new SubmitCodeOnClickListener());
        mCode51Button.setOnClickListener(new SubmitCodeOnClickListener());
        mCode61Button.setOnClickListener(new SubmitCodeOnClickListener());
        mCode71Button.setOnClickListener(new SubmitCodeOnClickListener());
    }

    private void setUpHandler() {
        mUiHandler = new Handler();
    }

    private void setUpChart() {
        mLineChart.setDescription("");
        mLineChart.setNoDataText(getString(R.string.noData));
        mLineChart.setDrawGridBackground(false);
        mLineChart.setDrawMarkerViews(false);
        mLineChart.getLegend().setEnabled(false);
        mLineChart.setBackgroundColor(getResources().getColor(R.color.font));

        setUpXAxis();
        setUpYAxis();

        addZeroLine();
    }

    private void setUpXAxis() {
        XAxis xl = mLineChart.getXAxis();
        xl.setTextColor(getResources().getColor(R.color.main));
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(false);
        xl.setSpaceBetweenLabels(5);
        xl.setEnabled(true);
        xl.setDrawAxisLine(false);
        xl.setDrawLabels(false);
    }

    private void setUpYAxis() {
        YAxis leftAxis = mLineChart.getAxisLeft();
        leftAxis.setTextColor(getResources().getColor(R.color.black));
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(true);
        YAxis rightAxis = mLineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void addZeroLine() {
        for (int i = 0; i < 20; i++) {
            addGraphEntry(1);
        }
    }

    private LineDataSet createSet() {
        LineDataSet set = new LineDataSet(null, "");
        set.setAxisDependency(YAxis.AxisDependency.LEFT);
        set.setColor(getResources().getColor(R.color.main));
        set.setCircleColor(getResources().getColor(R.color.main));
        set.setLineWidth(2f);
        set.setDrawCubic(true);
        set.setDrawCircles(false);
        set.setCircleColorHole(getResources().getColor(R.color.main));
        set.setFillColor(getResources().getColor(R.color.main));
        set.setDrawValues(false);
        return set;
    }

    private View foundButtonByCode(String code) {
        switch (code) {
            case CODE_11:
                return mCode11Button;
            case CODE_21:
                return mCode21Button;
            case CODE_31:
                return mCode31Button;
            case CODE_41:
                return mCode41Button;
            case CODE_51:
                return mCode51Button;
            case CODE_61:
                return mCode61Button;
            case CODE_71:
                return mCode71Button;
            default:
                return mCode99Button;
        }
    }

    private void startReconnection() {
        Intent intent = ReconnectionActivity.newInstance(this);
        startActivity(intent);
        finish();
    }

    private class SubmitCodeOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            String viewText = ((Button) v).getText().toString();
            mWritingPresenter.submitCode(viewText);
        }
    }

}
