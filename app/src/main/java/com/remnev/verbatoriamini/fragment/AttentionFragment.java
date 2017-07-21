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
import com.remnev.verbatoriamini.callbacks.IFragmentsMovingCallback;
import com.remnev.verbatoriamini.util.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.callbacks.IClearButtonsCallback;
import com.remnev.verbatoriamini.callbacks.INeuroInterfaceCallback;
import com.remnev.verbatoriamini.callbacks.INFCCallback;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.model.Code;
import com.remnev.verbatoriamini.util.NeuroExcelWriter;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class AttentionFragment extends Fragment  {


    private String selectedButtonText;

    private Button button99;
    private Button button11;
    private Button button21;
    private Button button31;
    private Button button41;
    private Button button51;
    private Button button61;
    private Button button71;

    private Timer mConnectionCheckTimer;

    private IExportPossibleCallback mExportPossibleCallback;
    private IFragmentsMovingCallback mFragmentsMovingCallback;

    public AttentionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IExportPossibleCallback) {
            mExportPossibleCallback = (IExportPossibleCallback) context;
        }
        if (context instanceof IFragmentsMovingCallback) {
            mFragmentsMovingCallback = (IFragmentsMovingCallback) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        selectedButtonText = "";

        NeuroApplicationClass mNeuroApplicationClass = (NeuroApplicationClass) getActivity().getApplicationContext();
//        mNeuroApplicationClass.setOnBCIConnectionCallback(this);
        mNeuroApplicationClass.setContext(getActivity());

//        setAllButtonsUnselected(null);

        mConnectionCheckTimer = new Timer();
        mConnectionCheckTimer.schedule(new CheckConnectionTimerTask(), 5000, 5000);
return null;
     }



    public void changeExportValue(boolean changedValue) {
        if (mExportPossibleCallback != null) {
            mExportPossibleCallback.exportPossibleValueChanged(changedValue);
        } else if (getActivity() != null && getActivity() instanceof IExportPossibleCallback) {
            mExportPossibleCallback = (IExportPossibleCallback) getActivity();
            changeExportValue(changedValue);
        }
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
                try {
                    if (!NeuroApplicationClass.isConnected()) {
                        if (mFragmentsMovingCallback != null) {
                            mFragmentsMovingCallback.moveToConnectionFragment();
                        } else {
                            if (getActivity() != null && getActivity() instanceof IFragmentsMovingCallback) {
                                mFragmentsMovingCallback = (IFragmentsMovingCallback) getActivity();
                                mFragmentsMovingCallback.moveToConnectionFragment();
                            }
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();

                }
            }
        }
    }
}