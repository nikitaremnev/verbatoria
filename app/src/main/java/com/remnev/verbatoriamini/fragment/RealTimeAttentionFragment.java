package com.remnev.verbatoriamini.fragment;


import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
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
import com.neurosky.thinkgear.TGDevice;
import com.remnev.verbatoriamini.ApplicationClass;
import com.remnev.verbatoriamini.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.activities.MainActivity;
import com.remnev.verbatoriamini.activities.SplashActivity;
import com.remnev.verbatoriamini.callbacks.IClearButtons;
import com.remnev.verbatoriamini.callbacks.OnBCIConnectionCallback;
import com.remnev.verbatoriamini.callbacks.OnNewIntentCallback;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.model.Code;
import com.remnev.verbatoriamini.sharedpreferences.SpecialistSharedPrefs;
import com.remnev.verbatoriamini.util.ExcelEventWriter;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RealTimeAttentionFragment extends Fragment implements
        OnChartValueSelectedListener, OnBCIConnectionCallback, OnNewIntentCallback, IClearButtons {

    private LineChart mChart;
    private ApplicationClass applicationClass;
    private TextView loadTextView;

    private String selectedButtonText;

    private View rootView;
    private Button button99;
    private Button button11;
    private Button button21;
    private Button button31;
    private Button button41;
    private Button button51;
    private Button button61;
    private Button button71;

    private View playerButtons;

    private FloatingActionButton playButton;
    private FloatingActionButton nextButton;
    private FloatingActionButton backButton;
    private FloatingActionButton pauseButton;

    private Timer timer;

    private boolean canExport = true;

    public RealTimeAttentionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_realtime_attention, null, false);

        playerButtons = rootView.findViewById(R.id.player_buttons);
        playButton = (FloatingActionButton) rootView.findViewById(R.id.play_floating_button);
        pauseButton = (FloatingActionButton) rootView.findViewById(R.id.pause_floating_button);
        nextButton = (FloatingActionButton) rootView.findViewById(R.id.next_floating_button);
        backButton = (FloatingActionButton) rootView.findViewById(R.id.back_floating_button);
        playerButtons.setVisibility(View.GONE);

        setUpPlayer();

        mChart = (LineChart) rootView.findViewById(R.id.line_chart);
        selectedButtonText = "";
        loadTextView = (TextView) rootView.findViewById(R.id.load_text);

        applicationClass = (ApplicationClass) getActivity().getApplicationContext();
        applicationClass.setOnBCIConnectionCallback(this);
        applicationClass.setMContext(getActivity());

        setUpChart();
        setUpCodeButtons();
        setUpOnClickListeners();
        setAllButtonsUnselected(null);

        timer = new Timer();
        timer.schedule(new FontChangeTimerTask(), 2000, 2000);

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("test", "onResume RealTimeAttentionFragment");
    }

    private void setUpCodeButtons() {
        button99 = (Button) rootView.findViewById(R.id.button99);
        button11 = (Button) rootView.findViewById(R.id.button11);
        button21 = (Button) rootView.findViewById(R.id.button21);
        button31 = (Button) rootView.findViewById(R.id.button31);
        button41 = (Button) rootView.findViewById(R.id.button41);
        button51 = (Button) rootView.findViewById(R.id.button51);
        button61 = (Button) rootView.findViewById(R.id.button61);
        button71 = (Button) rootView.findViewById(R.id.button71);
    }

    @Override
    public void clearRemovedButtons() {
        Log.e("test", "clearRemovedButtons");
        if (!ApplicationClass.containsDoneActivity("99")) {
            Log.e("test", "!ApplicationClass.containsDoneActivity(\"99\")");
            button99.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!ApplicationClass.containsDoneActivity("11")) {
            button11.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!ApplicationClass.containsDoneActivity("21")) {
            button21.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!ApplicationClass.containsDoneActivity("31")) {
            button31.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!ApplicationClass.containsDoneActivity("41")) {
            button41.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!ApplicationClass.containsDoneActivity("51")) {
            button51.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!ApplicationClass.containsDoneActivity("61")) {
            button61.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (!ApplicationClass.containsDoneActivity("71")) {
            button71.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
    }

    private void setAllButtonsUnselected(View needToSelect) {
        if (ApplicationClass.containsDoneActivity("99")) {
            button99.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button99.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (ApplicationClass.containsDoneActivity("11")) {
            button11.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button11.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (ApplicationClass.containsDoneActivity("21")) {
            button21.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button21.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (ApplicationClass.containsDoneActivity("31")) {
            button31.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button31.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (ApplicationClass.containsDoneActivity("41")) {
            button41.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button41.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (ApplicationClass.containsDoneActivity("51")) {
            button51.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button51.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (ApplicationClass.containsDoneActivity("61")) {
            button61.setBackground(getResources().getDrawable(R.drawable.btn_code_form_done));
        } else {
            button61.setBackground(getResources().getDrawable(R.drawable.btn_code_form));
        }
        if (ApplicationClass.containsDoneActivity("71")) {
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
                    playerButtons.setVisibility(View.VISIBLE);
                    currentMusicIndex = 1;
                    playButton.setVisibility(View.VISIBLE);
                    pauseButton.setVisibility(View.GONE);
                } else {
                    playerButtons.setVisibility(View.GONE);
                    if (mediaPlayer != null) {
                        mediaPlayer.stop();
                        mediaPlayer = null;
                    }
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
        mChart.setOnChartValueSelectedListener(this);
        mChart.setDescription("");
        mChart.setNoDataText(getString(R.string.noData));
        mChart.setDrawGridBackground(false);
        mChart.setDrawMarkerViews(false);
        mChart.getLegend().setEnabled(false);
        mChart.setBackgroundColor(getResources().getColor(R.color.font));

        XAxis xl = mChart.getXAxis();
        xl.setTextColor(getResources().getColor(R.color.main));
        xl.setDrawGridLines(false);
        xl.setAvoidFirstLastClipping(false);
        xl.setSpaceBetweenLabels(5);
        xl.setEnabled(true);
        xl.setDrawAxisLine(false);
        xl.setDrawLabels(false);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setTextColor(getResources().getColor(R.color.black));
        leftAxis.setAxisMaxValue(100f);
        leftAxis.setAxisMinValue(0f);
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawAxisLine(false);
        leftAxis.setDrawLabels(true);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(false);

        for (int i = 0; i < 20; i ++ ) {
            addEntry(1);
        }
    }

    private void addEntry(int value) {
        LineData data = mChart.getData();
        if (data == null) {
            data = new LineData();
            data.setDrawValues(false);
            mChart.setData(data);
        }
        if (data != null) {
            ILineDataSet set = data.getDataSetByIndex(0);
            if (set == null) {
                set = createSet();
                data.addDataSet(set);
            }
            data.addXValue(Integer.toString(set.getEntryCount()));
            data.addEntry(new Entry(value, set.getEntryCount()), 0);

            mChart.notifyDataSetChanged();
            mChart.setScaleXEnabled(false);
            mChart.setVisibleXRangeMaximum(20);
            mChart.moveViewToX(data.getXValCount() - 21);
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
    public void onMessageReceived(Message msg) {
        switch( msg.what ) {
            case TGDevice.MSG_STATE_CHANGE:
                switch (msg.arg1) {
                    case TGDevice.STATE_IDLE:
                        Log.e("bci", "STATE_IDLE");
                        break;
                    case TGDevice.STATE_ERR_BT_OFF:
                        Log.e("bci", "STATE_ERR_BT_OFF");
                        break;
                    case TGDevice.STATE_CONNECTING:
                        break;
                    case TGDevice.STATE_ERR_NO_DEVICE:
                        Log.e("bci", "STATE_ERR_NO_DEVICE");
                        break;
                    case TGDevice.STATE_NOT_FOUND:
                        Log.e("bci", "STATE_NOT_FOUND");
                        break;
                    case TGDevice.STATE_CONNECTED:
                        Log.e("bci", "STATE_CONNECTED");
                        break;
                    case TGDevice.STATE_DISCONNECTED:
                        Log.e("bci", "STATE_DISCONNECTED");
                        break;
                    default:
                        break;
                }
                break;
            case TGDevice.MSG_POOR_SIGNAL:
                break;
            case TGDevice.MSG_ATTENTION:
                int rawValue = msg.arg1;
                addEntry(rawValue);
                Log.e("ATTENTION", "value: " + rawValue);
                break;
            case TGDevice.MSG_RAW_DATA:
                break;
            case TGDevice.MSG_EEG_POWER:
                break;
            default:
                break;
        }
    }

    @Override
    public void animateStatusChanged(int value) {

    }

    @Override
    public void promptForContent(Tag msg) {
        if (msg != null) {
            try {
                String readedText = Helper.readTag(msg, getActivity());
                try {
                    Code code = LoganSquare.parse(readedText, Code.class);
                    if (!loadTextView.getText().toString().isEmpty() && readedText != null && !Integer.toString(code.getCode()).equals(loadTextView.getText().toString())) {
                        Helper.snackBar(loadTextView, getString(R.string.tag_not_correct));
                        submitCode(Integer.toString(code.getCode()));
                    }
                    if (code != null) {
                        submitCode(Integer.toString(code.getCode()));
                    }
                } catch (Exception ex) {
                    Helper.snackBar(loadTextView, getString(R.string.tag_not_code));
                }
            } catch (Exception e) {
                e.printStackTrace();
                Helper.snackBar(loadTextView, getString(R.string.tag_empty));
            }
        }
    }

    private void submitCode(Code code) {
        if (loadTextView.getText().toString().isEmpty()) {
            loadTextView.setText("" + code.getCode());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                loadTextView.setBackground(getResources().getDrawable(R.drawable.background_in_process));
            }
            String textToWrite = loadTextView.getText().toString();
            if (TextUtils.isEmpty(textToWrite)) {
                textToWrite =  getString(R.string.LABEL);
                loadTextView.setText(textToWrite);
                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, ExcelEventWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
                Helper.snackBar(loadTextView, getString(R.string.standart_written));
            } else {
                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, ExcelEventWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
                Helper.snackBar(loadTextView, getString(R.string.success_write_event));
            }
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                loadTextView.setBackground(null);
            }

            String textToWrite = loadTextView.getText().toString();
            StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, ExcelEventWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
            if (textToWrite.equals(getString(R.string.LABEL))) {
                Helper.snackBar(loadTextView, getString(R.string.standart_written));
            } else {
                Helper.snackBar(loadTextView, getString(R.string.success_write_event));
            }
            loadTextView.setText("");
        }
    }

    private View foundButtonByCode(String code) {
        if (code.equals("99")) {
            return button99;
        }
//        if (code.equals("00") || code.equals("0")) {
//            return button00;
//        }
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
//        if (code.equals("81")) {
//            return button81;
//        }
        return null;
    }

    private void submitCode(String code) {
        if (code.equals("0")) {
            code = "00";
        }
        if (selectedButtonText.isEmpty()) {
            String textToWrite = code;
            ApplicationClass.addActivityToDoneArray(textToWrite);
            StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, ExcelEventWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
            Helper.snackBar(loadTextView, getString(R.string.success_write_event));
            selectedButtonText = code;
            setAllButtonsUnselected(foundButtonByCode(code));
            canExport = false;
            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).canExport = false;
            }
        } else {
            if (selectedButtonText.equals(code)) {
                String textToWrite = code;
                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, ExcelEventWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
                Helper.snackBar(loadTextView, getString(R.string.success_write_event));
                loadTextView.setText("");
                selectedButtonText = "";
                setAllButtonsUnselected(null);
                ApplicationClass.addActivityToDoneArray(textToWrite);
                canExport = true;
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).canExport = true;
                }
            } else {
                String textToWrite = selectedButtonText;
                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, ExcelEventWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");

                textToWrite = code;
                ApplicationClass.addActivityToDoneArray(textToWrite);
                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, ExcelEventWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
                Helper.snackBar(loadTextView, getString(R.string.success_write_event));
                selectedButtonText = code;
                setAllButtonsUnselected(foundButtonByCode(code));
                canExport = false;
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).canExport = false;
                }
            }
        }
    }

    private class FontChangeTimerTask extends TimerTask {

        @Override
        public void run() {
            if (getActivity() != null) {
                getActivity().runOnUiThread(new ChangeFont());
            }
        }

        private class ChangeFont implements Runnable {

            @Override
            public void run() {
                if (!ApplicationClass.connected) {
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        rootView.setBackgroundDrawable(getResources().getDrawable(R.drawable.frg_attention_red_border));
                    } else {
                        rootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_red_border));
                    }
                } else {
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        rootView.setBackgroundDrawable(getResources().getDrawable(R.drawable.frg_attention_usual));
                    } else {
                        rootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_usual));
                    }
                }
            }
        }
    }

    private int currentMusicIndex = 1;
    private MediaPlayer mediaPlayer;

    private void setUpMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
                playButton.setVisibility(View.GONE);
                pauseButton.setVisibility(View.VISIBLE);
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
                RealTimeAttentionFragment.this.mediaPlayer = null;
            }
        });
    }

    private void setUpPlayer() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer != null) {
                    mediaPlayer.start();
                    playButton.setVisibility(View.GONE);
                    pauseButton.setVisibility(View.VISIBLE);
                    return;
                }
                if (5 >= currentMusicIndex) {
                    try {
                        setUpMediaPlayer();
                        AssetFileDescriptor afd = getAssetFileDescriptor(currentMusicIndex);
                        if (afd == null) {
                            Snackbar snackbar = Snackbar.make(rootView, getString(R.string.cant_play), Snackbar.LENGTH_LONG);
                            snackbar.show();
                            return;
                        }
                        mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        afd.close();
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Snackbar snackbar = Snackbar.make(rootView, getString(R.string.cant_play), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(rootView, getString(R.string.cant_play), Snackbar.LENGTH_LONG);
                    snackbar.show();
                }
            }
        });
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMusicIndex ++;
                if (5 < currentMusicIndex) {
                    currentMusicIndex = 1;
                }
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                    playButton.setVisibility(View.VISIBLE);
                    pauseButton.setVisibility(View.GONE);
                }
                playButton.performClick();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentMusicIndex --;
                if (currentMusicIndex < 1) {
                    currentMusicIndex = 1;
                }
                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer = null;
                    playButton.setVisibility(View.VISIBLE);
                    pauseButton.setVisibility(View.GONE);
                }
                playButton.performClick();
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.pause();
                playButton.setVisibility(View.VISIBLE);
                pauseButton.setVisibility(View.GONE);
            }
        });
    }

    private AssetFileDescriptor getAssetFileDescriptor(int index) {
        switch (index) {
            case 1:
                return getActivity().getResources().openRawResourceFd(R.raw.zvuk1);
            case 2:
                return getActivity().getResources().openRawResourceFd(R.raw.zvuk2);
            case 3:
                return getActivity().getResources().openRawResourceFd(R.raw.zvuk3);
            case 4:
                return getActivity().getResources().openRawResourceFd(R.raw.zvuk4);
            default:
                return getActivity().getResources().openRawResourceFd(R.raw.zvuk5);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        try {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            mediaPlayer = null;
        } catch (Exception ex) {
            ex.printStackTrace();
            mediaPlayer = null;
        }
    }
}
