package com.remnev.verbatoriamini.fragment;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.bluelinelabs.logansquare.LoganSquare;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.neurosky.connection.DataType.MindDataType;
import com.remnev.verbatoriamini.NeuroApplicationClass;
import com.remnev.verbatoriamini.callbacks.IExportPossibleCallback;
import com.remnev.verbatoriamini.util.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.callbacks.IClearButtonsCallback;
import com.remnev.verbatoriamini.callbacks.INeuroInterfaceCallback;
import com.remnev.verbatoriamini.callbacks.INFCCallback;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.model.Code;
import com.remnev.verbatoriamini.util.NeuroExcelWriter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class AttentionFragment extends Fragment implements
        OnChartValueSelectedListener, INeuroInterfaceCallback, INFCCallback, IClearButtonsCallback {


    private View mRootView;
    private LineChart mChartView;
    private TextView mLoadTextView;

    private String selectedButtonText;

    private Button button99;
    private Button button11;
    private Button button21;
    private Button button31;
    private Button button41;
    private Button button51;
    private Button button61;
    private Button button71;

    private PlayerManager mThirdLoadPlayerManager;
    private Timer mConnectionCheckTimer;
    private IExportPossibleCallback mExportPossibleCallback;

    public AttentionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IExportPossibleCallback) {
            mExportPossibleCallback = (IExportPossibleCallback) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(R.layout.fragment_realtime_attention, null, false);


        mThirdLoadPlayerManager = new PlayerManager(mRootView);

        mChartView = (LineChart) mRootView.findViewById(R.id.line_chart);
        selectedButtonText = "";
        mLoadTextView = (TextView) mRootView.findViewById(R.id.load_text);

        NeuroApplicationClass mNeuroApplicationClass = (NeuroApplicationClass) getActivity().getApplicationContext();
        mNeuroApplicationClass.setOnBCIConnectionCallback(this);
        mNeuroApplicationClass.setContext(getActivity());

        setUpChart();
        setUpCodeButtons();
        setUpOnClickListeners();
        setAllButtonsUnselected(null);

        mConnectionCheckTimer = new Timer();
        mConnectionCheckTimer.schedule(new CheckConnectionTimerTask(), 0, 2000);

        return mRootView;
    }

    private void setUpCodeButtons() {
        button99 = (Button) mRootView.findViewById(R.id.button99);
        button11 = (Button) mRootView.findViewById(R.id.button11);
        button21 = (Button) mRootView.findViewById(R.id.button21);
        button31 = (Button) mRootView.findViewById(R.id.button31);
        button41 = (Button) mRootView.findViewById(R.id.button41);
        button51 = (Button) mRootView.findViewById(R.id.button51);
        button61 = (Button) mRootView.findViewById(R.id.button61);
        button71 = (Button) mRootView.findViewById(R.id.button71);
    }

    @Override
    public void clearRemovedButtons() {
        if (!NeuroApplicationClass.containsDoneActivity("99")) {
            button99.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!NeuroApplicationClass.containsDoneActivity("11")) {
            button11.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!NeuroApplicationClass.containsDoneActivity("21")) {
            button21.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!NeuroApplicationClass.containsDoneActivity("31")) {
            button31.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!NeuroApplicationClass.containsDoneActivity("41")) {
            button41.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!NeuroApplicationClass.containsDoneActivity("51")) {
            button51.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!NeuroApplicationClass.containsDoneActivity("61")) {
            button61.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!NeuroApplicationClass.containsDoneActivity("71")) {
            button71.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
    }

    private void setAllButtonsUnselected(View needToSelect) {
        if (NeuroApplicationClass.containsDoneActivity("99")) {
            button99.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button99.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (NeuroApplicationClass.containsDoneActivity("11")) {
            button11.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button11.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (NeuroApplicationClass.containsDoneActivity("21")) {
            button21.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button21.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (NeuroApplicationClass.containsDoneActivity("31")) {
            button31.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button31.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (NeuroApplicationClass.containsDoneActivity("41")) {
            button41.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button41.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (NeuroApplicationClass.containsDoneActivity("51")) {
            button51.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button51.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (NeuroApplicationClass.containsDoneActivity("61")) {
            button61.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button61.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (NeuroApplicationClass.containsDoneActivity("71")) {
            button71.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button71.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (needToSelect != null)
            needToSelect.setBackground(getResources().getDrawable(R.drawable.btn_code_form_pressed));
    }

    private void setUpOnClickListeners() {
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String thisButtonText = ((Button) view).getText().toString();
                if (thisButtonText.equals("31")) {
                    mThirdLoadPlayerManager.showPlayer();
                } else {
                    mThirdLoadPlayerManager.hidePlayer();
                }
                submitCode(thisButtonText);
            }
        };
        button99.setOnClickListener(onClickListener);
        button11.setOnClickListener(onClickListener);
        button21.setOnClickListener(onClickListener);
        button31.setOnClickListener(onClickListener);
        button41.setOnClickListener(onClickListener);
        button51.setOnClickListener(onClickListener);
        button61.setOnClickListener(onClickListener);
        button71.setOnClickListener(onClickListener);
    }

    private void setUpChart() {
        mChartView.setOnChartValueSelectedListener(this);
        mChartView.setDescription("");
        mChartView.setNoDataText(getString(R.string.noData));
        mChartView.setDrawGridBackground(false);
        mChartView.setDrawMarkerViews(false);
        mChartView.getLegend().setEnabled(false);
        mChartView.setBackgroundColor(getResources().getColor(R.color.font));

        XAxis xl = mChartView.getXAxis();
        xl.setTextColor(getResources().getColor(R.color.main));
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(false);
        xl.setSpaceBetweenLabels(5);
        xl.setEnabled(true);
        xl.setDrawAxisLine(false);
        xl.setDrawLabels(false);

        YAxis leftAxis = mChartView.getAxisLeft();
        leftAxis.setTextColor(getResources().getColor(R.color.black));
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(true);

        YAxis rightAxis = mChartView.getAxisRight();
        rightAxis.setEnabled(false);

        for (int i = 0; i < 20; i ++ ) {
            addEntry(1);
        }
    }

    private void addEntry(int value) {
        LineData data = mChartView.getData();
        if (data == null) {
            data = new LineData();
            data.setDrawValues(false);
            mChartView.setData(data);
        }
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            data.addXValue(Integer.toString(set.getEntryCount()));
            data.addEntry(new Entry(value, set.getEntryCount()), 0);

            mChartView.notifyDataSetChanged();
            mChartView.setScaleXEnabled(false);
            mChartView.setVisibleXRangeMaximum(20);
            mChartView.moveViewToX(data.getXValCount() - 21);
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

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    @Override
    public void onNeuroInterfaceStateChanged(int connectionStates) {

    }

    @Override
    public void onNeuroDataReceived(int code, int attention) {
        switch (code) {
            case MindDataType.CODE_ATTENTION:
                addEntry(attention);
                break;
        }
    }

    @Override
    public void onNFCTagReaded(Tag msg) {
        if (msg != null) {
            try {
                String readedText = Helper.readTag(msg, getActivity());
                try {
                    Code code = LoganSquare.parse(readedText, Code.class);
                    if (!mLoadTextView.getText().toString().isEmpty() && readedText != null && !Integer.toString(code.getCode()).equals(mLoadTextView.getText().toString())) {
                        Helper.showSnackBar(mLoadTextView, getString(R.string.tag_not_correct));
                        submitCode(Integer.toString(code.getCode()));
                    }
                    if (code != null) {
                        submitCode(Integer.toString(code.getCode()));
                    }
                } catch (Exception ex) {
                    Helper.showSnackBar(mLoadTextView, getString(R.string.tag_not_code));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Helper.showSnackBar(mLoadTextView, getString(R.string.tag_empty));
            }
        }
    }

    private View foundButtonByCode(String code) {
        if (code.equals("99")) {
            return button99;
        }
        if (code.equals("11")) {
            return button11;
        }
        if (code.equals("21")) {
            return button21;
        }
        if (code.equals("31")) {
            return button31;
        }
        if (code.equals("41")) {
            return button41;
        }
        if (code.equals("51")) {
            return button51;
        }
        if (code.equals("61")) {
            return button61;
        }
        if (code.equals("71")) {
            return button71;
        }
        return null;
    }

    private void submitCode(String code) {
        if (code.equals("0")) {
            code = "00";
        }
        if (selectedButtonText.isEmpty()) {
            NeuroApplicationClass.addActivityToDoneArray(code);
            StatisticsDatabase.addEventToDatabase(getActivity(), code, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
            Helper.showSnackBar(mLoadTextView, getString(R.string.success_write_event));
            selectedButtonText = code;
            setAllButtonsUnselected(foundButtonByCode(code));
            changeExportValue(false);
        } else {
            if (selectedButtonText.equals(code)) {
                StatisticsDatabase.addEventToDatabase(getActivity(), code, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
                Helper.showSnackBar(mLoadTextView, getString(R.string.success_write_event));
                mLoadTextView.setText("");
                selectedButtonText = "";
                setAllButtonsUnselected(null);
                NeuroApplicationClass.addActivityToDoneArray(code);
                changeExportValue(true);
            } else {
                String textToWrite = selectedButtonText;
                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
                textToWrite = code;
                NeuroApplicationClass.addActivityToDoneArray(textToWrite);
                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
                Helper.showSnackBar(mLoadTextView, getString(R.string.success_write_event));
                selectedButtonText = code;
                setAllButtonsUnselected(foundButtonByCode(code));
                changeExportValue(false);
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mConnectionCheckTimer.cancel();
        mConnectionCheckTimer = null;

        mThirdLoadPlayerManager.onDestroy();
        mThirdLoadPlayerManager = null;
    }

    private class CheckConnectionTimerTask extends TimerTask {

        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new ChangeFont());
            }
        }

        private class ChangeFont implements Runnable {

            @Override
            public void run() {
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (!NeuroApplicationClass.isConnected()) {
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        mRootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_red_border));
                    } else {
                        mRootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_red_border));
                    }
                } else {
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        mRootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_usual));
                    } else {
                        mRootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_usual));
                    }
                }
            }
        }
    }

    public void changeExportValue(boolean changedValue) {
        if (mExportPossibleCallback != null) {
            mExportPossibleCallback.exportPossibleValueChanged(changedValue);
        } else if (getActivity() != null && getActivity() instanceof IExportPossibleCallback) {
            mExportPossibleCallback = (IExportPossibleCallback) getActivity();
            changeExportValue(changedValue);
        }
    }

    private class FullTimerTask extends TimerTask {

        @Override
        public void run() {

        }
    }

    private class PlayerManager {

        private FloatingActionButton mPlayButton;
        private FloatingActionButton mNextButton;
        private FloatingActionButton mBackButton;
        private FloatingActionButton mPauseButton;
        private TextView mMusicFileName;
        private View mPlayerButtons;

        private int mCurrentMusicIndex = 1;

        private MediaPlayer mMediaPlayer;

        private int[] sMusicRaw = new int[] {
                R.raw.zvuk1,
                R.raw.zvuk2,
                R.raw.zvuk3,
                R.raw.zvuk4,
                R.raw.zvuk5
        };

        PlayerManager(View rootView) {
            mPlayButton = (FloatingActionButton) rootView.findViewById(R.id.play_floating_button);
            mPauseButton = (FloatingActionButton) rootView.findViewById(R.id.pause_floating_button);
            mNextButton = (FloatingActionButton) rootView.findViewById(R.id.next_floating_button);
            mBackButton = (FloatingActionButton) rootView.findViewById(R.id.back_floating_button);
            mMusicFileName = (TextView) rootView.findViewById(R.id.music_file_name);
            mPlayerButtons = rootView.findViewById(R.id.player_buttons);
            setUpPlayer();
        }

        private AssetFileDescriptor getAssetFileDescriptor(int index) {
            return getActivity().getResources().openRawResourceFd(sMusicRaw[index - 1]);
        }

        private void onDestroy() {
            try {
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            mMediaPlayer = null;
            mNextButton = null;
            mPlayButton = null;
            mPauseButton = null;
            mBackButton = null;
            sMusicRaw = null;
            mPlayerButtons = null;
        }

        private void setUpPlayer() {
            mPlayButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    setPlayingFileName();
                    if (mMediaPlayer != null) {
                        mMediaPlayer.start();
                        setUpPauseMode();
                    } else if (5 >= mCurrentMusicIndex) {
                        setUpMediaPlayer();
                        AssetFileDescriptor afd = getAssetFileDescriptor(mCurrentMusicIndex);
                        if (afd == null) {
                            showErrorSnackbar();
                        } else {
                            preparePlayer(afd);
                        }
                    } else {
                        showErrorSnackbar();
                    }
                }
            });
            mNextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveToNextPlayingFile();
                    pausePlayer();
                    setUpPlayMode();
                    setPlayingFileName();
                }
            });
            mBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    moveToPreviousPlayingFile();
                    pausePlayer();
                    setUpPlayMode();
                    setPlayingFileName();
                }
            });
            mPauseButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mMediaPlayer.pause();
                    setUpPlayMode();
                }
            });
        }

        private void setUpMediaPlayer() {
            mMediaPlayer = new MediaPlayer();
            mMediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    mediaPlayer.start();
                    setUpPauseMode();
                }
            });
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    setUpPlayMode();
                    mMediaPlayer = null;
                }
            });
        }

        //visibility changes
        private void setUpPlayMode() {
            mPlayButton.setVisibility(View.VISIBLE);
            mPauseButton.setVisibility(View.GONE);
        }

        private void setUpPauseMode() {
            mPlayButton.setVisibility(View.GONE);
            mPauseButton.setVisibility(View.VISIBLE);
        }

        //full visibility changes
        private void showPlayer() {
            mPlayerButtons.setVisibility(View.VISIBLE);
            mCurrentMusicIndex = 1;
            setUpPlayMode();
        }

        private void hidePlayer() {
            mPlayerButtons.setVisibility(View.GONE);
            pausePlayer();
        }

        //file name
        private void setPlayingFileName() {
            mMusicFileName.setText(Integer.toString(mCurrentMusicIndex));
        }

        //media player job
        private void preparePlayer(AssetFileDescriptor afd) {
            try {
                mMediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                afd.close();
                mMediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
                showErrorSnackbar();
            }

        }

        private void pausePlayer() {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer = null;
            }
        }

        //move music
        private void moveToNextPlayingFile() {
            mCurrentMusicIndex++;
            if (5 < mCurrentMusicIndex) {
                mCurrentMusicIndex = 1;
            }
        }

        private void moveToPreviousPlayingFile() {
            mCurrentMusicIndex--;
            if (mCurrentMusicIndex < 1) {
                mCurrentMusicIndex = 1;
            }
        }

        private void showErrorSnackbar() {
            Helper.showSnackBar(mPlayerButtons, getString(R.string.cant_play));
        }
    }

}
