package com.remnev.verbatoriamini;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;
import com.remnev.verbatoriamini.callbacks.INeuroInterfaceCallback;
import com.remnev.verbatoriamini.databases.BCIDatabase;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.model.ActionID;
import com.remnev.verbatoriamini.model.RezhimID;
import com.remnev.verbatoriamini.sharedpreferences.SettingsSharedPrefs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nikitaremnev on 07.02.16.
 */
public class ApplicationClass extends Application {

    public static final int BLUETOOTH_NOT_STARTED = -13432;

    private TgStreamReader tgStreamReader;
    private BluetoothAdapter btAdapter = null;
    private static INeuroInterfaceCallback sINeurointerfaceCallback;
    private static View rootView;
    private static Context mContext = null;

    private static boolean writeBCIToDatabase = true;
    public static boolean connected = false;

    private static int T = 2;
    private static int Fa = 2;
    private static Snackbar connectionSnackbar;

    private static ArrayList<Integer> queue;

    private static Set<String> doneActivitiesArray;

    @Override
    public void onCreate() {
        super.onCreate();
        doneActivitiesArray = new HashSet<>();
    }

    public static Set<String>  getDoneActivitiesArray() {
        return doneActivitiesArray;
    }

    public static void clearDoneActivities() {
        doneActivitiesArray.clear();
    }

    public static String getAllUndoneActivities() {
        String result = "";
        if (!doneActivitiesArray.contains("11")) {
            result += "11, ";
        }
        if (!doneActivitiesArray.contains("21")) {
            result += "21, ";
        }
        if (!doneActivitiesArray.contains("31")) {
            result += "31, ";
        }
        if (!doneActivitiesArray.contains("41")) {
            result += "41, ";
        }
        if (!doneActivitiesArray.contains("51")) {
            result += "51, ";
        }
        if (!doneActivitiesArray.contains("61")) {
            result += "61, ";
        }
        if (!doneActivitiesArray.contains("71")) {
            result += "71, ";
        }
        if (!doneActivitiesArray.contains("99")) {
            result += "99, ";
        }
        if (!TextUtils.isEmpty(result)) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static boolean addActivityToDoneArray(String string) {
        return doneActivitiesArray.add(string);
    }

    public static boolean containsDoneActivity(String string) {
        return doneActivitiesArray.contains(string);
    }

    public static boolean removeActivityFromDoneArray(String string) {
        return doneActivitiesArray.remove(string);
    }

    public void setOnBCIConnectionCallback(INeuroInterfaceCallback callback) {
        sINeurointerfaceCallback = callback;
    }

    public void setMContext(Context context) {
        mContext = context;
    }

    public void setRootView(View rootView) {
        this.rootView = rootView;
    }

    public void connectToBluetooth() {
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        if (btAdapter != null && btAdapter.isEnabled()) {
            onBluetoothConnected();
        } else {
            onBluetoothNotStarted();
        }
    }

    public void startBCIConnection() {
        if (btAdapter != null && btAdapter.isEnabled()) {
            tgStreamReader = new TgStreamReader(btAdapter, mStreamHandler);
            if (tgStreamReader.isBTConnected()) {
                // Prepare for connecting
                tgStreamReader.stop();
                tgStreamReader.close();
            }
            tgStreamReader.connect();
        } else {
            onBluetoothNotStarted();
        }
    }

    public void startBCI() {
        if (tgStreamReader != null) {
            tgStreamReader.start();
        }
    }

    public void dropBCIConnection() {
        if (tgStreamReader != null) {
            tgStreamReader.stop();
            tgStreamReader.close();
        }
    }

    public void onBluetoothConnected() {
        startBCIConnection();
    }

    public void onBluetoothNotStarted() {
        if (rootView == null) return;
        Message message = new Message();
        message.what = BLUETOOTH_NOT_STARTED;
        message.arg1 = BLUETOOTH_NOT_STARTED;
        if (sINeurointerfaceCallback != null) {
            Log.e("event", "event send to mStreamHandler");
            sINeurointerfaceCallback.onNeuroInterfaceStateChanged(33);
        }
        Snackbar snackbar = Snackbar.make(rootView, getString(R.string.bluetoothDisabled), Snackbar.LENGTH_SHORT);
        snackbar.setAction(getString(R.string.settings), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    private static void message(String message) {
        if (rootView == null) {
            return;
        } else {
            Helper.snackBar(rootView, message);
        }
    }

    private static int processValue(int value) {
        if (queue == null) {
            queue = new ArrayList<>();
            queue.add(value);
            return -1;
        }
        if (queue.size() == Fa * T) {
            queue.remove(0);
            queue.add(value);
            return meanValue();
        } else {
            queue.add(value);
            if (queue.size() == Fa * T) {
                return meanValue();
            }
            return -1;
        }
    }

    private static int meanValue() {
        int sum = 0;
        for (int i = 0; i < queue.size(); i ++) {
            sum += queue.get(i);
        }
        return sum / queue.size();
    }

    public static boolean changeStateOfWriting() {
        writeBCIToDatabase = !writeBCIToDatabase;
        return writeBCIToDatabase;
    }

    public static boolean getStateOfWriting() {
        return writeBCIToDatabase;
    }

    private TgStreamHandler mStreamHandler = new TgStreamHandler() {

        @Override
        public void onStatesChanged(int connectionStates) {
            switch (connectionStates) {
                case ConnectionStates.STATE_CONNECTING:
                    Log.e("test", "STATE_CONNECTING");
                    // Do something when connecting
                    connected = false;
                    sINeurointerfaceCallback.onNeuroInterfaceStateChanged(connectionStates);
                    if (mContext != null) {
                        connectionSnackbar = Snackbar.make(rootView, mContext.getString(R.string.connecting), Snackbar.LENGTH_INDEFINITE);
                        connectionSnackbar.show();
                    }
                    break;
                case ConnectionStates.STATE_CONNECTED:
                    Log.e("test", "STATE_CONNECTED");
                    // Do something when connected
                    connected = true;
                    sINeurointerfaceCallback.onNeuroInterfaceStateChanged(connectionStates);
                    if (mContext != null) {
                        if (connectionSnackbar != null) {
                            connectionSnackbar.dismiss();
                        }
                        //message(mContext.getString(R.string.connected));
                        SettingsSharedPrefs.setBciID(mContext, "Неизвестно");
                        writeBCIToDatabase = true;
                        message(mContext.getString(R.string.bci_started));
                        StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.CONNECT_ID, RezhimID.ANOTHER_MODE, -1, -1);
                        StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.RECORD_START_ID, RezhimID.ANOTHER_MODE, -1, -1);
                    }

                    break;
                case ConnectionStates.STATE_WORKING:
                    Log.e("test", "STATE_WORKING");
                    // Do something when working
                    break;
                case ConnectionStates.STATE_COMPLETE:
                    Log.e("test", "STATE_COMPLETE");
                    break;
                case ConnectionStates.STATE_GET_DATA_TIME_OUT:
                    Log.e("test", "STATE_GET_DATA_TIME_OUT");
                    break;
                case ConnectionStates.STATE_INIT:
                    Log.e("test", "STATE_INIT");
                    break;
                case ConnectionStates.STATE_RECORDING_END:
                    Log.e("test", "STATE_RECORDING_END");
                    break;
                case ConnectionStates.STATE_RECORDING_START:
                    Log.e("test", "STATE_RECORDING_START");
                    break;
                case ConnectionStates.STATE_STOPPED:
                    Log.e("test", "STATE_STOPPED");
                    break;
                case ConnectionStates.STATE_DISCONNECTED:
                case ConnectionStates.STATE_ERROR:
                case ConnectionStates.STATE_FAILED:
                    connected = false;
                    sINeurointerfaceCallback.onNeuroInterfaceStateChanged(connectionStates);
                    if (mContext != null) {
                        if (connectionSnackbar != null) {
                            connectionSnackbar.dismiss();
                        }
                        message(mContext.getString(R.string.no_device));
                        try {
                            BCIDatabase.getInstance(mContext).close();
                            BCIDatabase.getMyWritableDatabase(mContext).close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
            }
        }

        @Override
        public void onRecordFail(int flag) {
            // You can handle the record error message here

        }

        @Override
        public void onChecksumFail(byte[] payload, int length, int checksum) {
            // You can handle the bad packets here.
        }

        @Override
        public void onDataReceived(int datatype, final int data, Object obj) {
            switch (datatype) {
                case MindDataType.CODE_ATTENTION:
                    final long timestamp = System.currentTimeMillis();
                    if (writeBCIToDatabase) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BCIDatabase.addBCIAttentionToDatabase(mContext, timestamp, data);
                            }
                        }).start();
                    }
                    int attentionValue = processValue(data);
                    if (attentionValue != -1) {
                        sINeurointerfaceCallback.onNeuroDataReceived(datatype, attentionValue);
                    }
                    break;
                case MindDataType.CODE_MEDITATION:
                    final long timestampMed = System.currentTimeMillis();
                    if (writeBCIToDatabase) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BCIDatabase.addBCIMediationToDatabase(mContext, timestampMed, data);
                            }
                        }).start();
                    }
                    break;
                case MindDataType.CODE_EEGPOWER:
                    if (writeBCIToDatabase) {
                        final EEGPower ep = (EEGPower) obj;
                        final long timestampEEg = System.currentTimeMillis();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                BCIDatabase.addBCITGEEGPowerToDatabase(mContext, ep.delta, ep.theta, ep.lowAlpha, ep.highAlpha, ep.lowBeta, ep.highBeta, ep.lowGamma, ep.middleGamma, timestampEEg);
                            }
                        }).start();
                    }
                    break;
                default:
                    break;
            }
        }

    };

}
