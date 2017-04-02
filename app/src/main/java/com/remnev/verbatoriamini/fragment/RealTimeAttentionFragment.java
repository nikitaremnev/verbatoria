package com.remnev.verbatoriamini.fragment;


import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
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
import com.neurosky.connection.DataType.MindDataType;
import com.remnev.verbatoriamini.NeuroApplicationClass;
import com.remnev.verbatoriamini.util.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.activities.MainActivity;
import com.remnev.verbatoriamini.callbacks.IClearButtons;
import com.remnev.verbatoriamini.callbacks.INeuroInterfaceCallback;
import com.remnev.verbatoriamini.callbacks.INFCCallback;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.model.Code;
import com.remnev.verbatoriamini.util.NeuroExcelWriter;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class RealTimeAttentionFragment extends Fragment implements
        OnChartValueSelectedListener, INeuroInterfaceCallback, INFCCallback, IClearButtons {

    private LineChart mChart;
    private NeuroApplicationClass mNeuroApplicationClass;
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

    private TextView musicFileName;

    private Timer timer;

    private boolean canExport = true;

    private static int[] sMusicRaw = new int[] {
            R.raw.zvuk1,
            R.raw.zvuk2,
            R.raw.zvuk3,
            R.raw.zvuk4,
            R.raw.zvuk5
    };

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
        musicFileName = (TextView) rootView.findViewById(R.id.music_file_name);
        playerButtons.setVisibility(View.GONE);

        setUpPlayer();

        mChart = (LineChart) rootView.findViewById(R.id.line_chart);
        selectedButtonText = "";
        loadTextView = (TextView) rootView.findViewById(R.id.load_text);

        mNeuroApplicationClass = (NeuroApplicationClass) getActivity().getApplicationContext();
        mNeuroApplicationClass.setOnBCIConnectionCallback(this);
        mNeuroApplicationClass.setMContext(getActivity());

        setUpChart();
        setUpCodeButtons();
        setUpOnClickListeners();
        setAllButtonsUnselected(null);

        timer = new Timer();
        timer.schedule(new FontChangeTimerTask(), 0, 2000);

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
        if (!NeuroApplicationClass.containsDoneActivity("99")) {
            Log.e("test", "!ApplicationClass.containsDoneActivity(\"99\")");
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
            String textToWrite = code;
            NeuroApplicationClass.addActivityToDoneArray(textToWrite);
            StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
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
                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
                Helper.snackBar(loadTextView, getString(R.string.success_write_event));
                loadTextView.setText("");
                selectedButtonText = "";
                setAllButtonsUnselected(null);
                NeuroApplicationClass.addActivityToDoneArray(textToWrite);
                canExport = true;
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).canExport = true;
                }
            } else {
                String textToWrite = selectedButtonText;
                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");

                textToWrite = code;
                NeuroApplicationClass.addActivityToDoneArray(textToWrite);
                StatisticsDatabase.addEventToDatabase(getActivity(), textToWrite, NeuroExcelWriter.CUSTOM_ACTION_ID, -1, -1, -1, -1, "");
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
                musicFileName.setText(Integer.toString(currentMusicIndex) + ".mp3");
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
        return getActivity().getResources().openRawResourceFd(sMusicRaw[index]);
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
                final int sdk = android.os.Build.VERSION.SDK_INT;
                if (!NeuroApplicationClass.sConnected) {
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        rootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_red_border));
                    } else {
                        rootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_red_border));
                    }
                } else {
                    if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        rootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_usual));
                    } else {
                        rootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_usual));
                    }
                }
            }
        }
    }

}
