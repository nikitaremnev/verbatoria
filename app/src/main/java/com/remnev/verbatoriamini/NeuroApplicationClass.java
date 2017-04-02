package com.remnev.verbatoriamini;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;

import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;
import com.remnev.verbatoriamini.callbacks.INeuroInterfaceCallback;
import com.remnev.verbatoriamini.databases.NeuroDataDatabase;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.model.ActionID;
import com.remnev.verbatoriamini.model.RezhimID;
import com.remnev.verbatoriamini.sharedpreferences.SettingsSharedPrefs;
import com.remnev.verbatoriamini.util.Helper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by nikitaremnev on 07.02.16.
 */
public class NeuroApplicationClass extends Application {

    public static final int BLUETOOTH_NOT_STARTED = -13432;

    private BluetoothAdapter btAdapter = null;

    private static TgStreamReader tgStreamReader;

    private static INeuroInterfaceCallback sINeurointerfaceCallback;

    private static View rootView;
    private static Context mContext;

    private static int T = 2;
    private static int Fa = 2;
    private static Snackbar connectionSnackbar;

    private static boolean sWriteToDatabase;
    public static boolean sConnected;

    private static ArrayList<Integer> sAttentionQueue;
    private static Set<String> sDoneActivitiesArray;
    private static TgStreamHandler sStreamHandler;

    static {
        sStreamHandler = new TgStreamHandler() {

            @Override
            public void onStatesChanged(int connectionStates) {
                switch (connectionStates) {
                    case ConnectionStates.STATE_CONNECTING:
                        sConnected = false;
                        sINeurointerfaceCallback.onNeuroInterfaceStateChanged(connectionStates);
                        if (mContext != null) {
                            connectionSnackbar = Snackbar.make(rootView, mContext.getString(R.string.connecting), Snackbar.LENGTH_INDEFINITE);
                            connectionSnackbar.show();
                        }
                        break;
                    case ConnectionStates.STATE_CONNECTED:
                        if (!sConnected) {
                            if (mContext != null) {
                                if (connectionSnackbar != null) {
                                    connectionSnackbar.dismiss();
                                }
                                SettingsSharedPrefs.setBciID(mContext, mContext.getString(R.string.unknown));
                                message(mContext.getString(R.string.bci_started));
                            }
                            sINeurointerfaceCallback.onNeuroInterfaceStateChanged(ConnectionStates.STATE_CONNECTED);
                            StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.CONNECT_ID, RezhimID.ANOTHER_MODE, -1, -1);
                            StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.RECORD_START_ID, RezhimID.ANOTHER_MODE, -1, -1);
                        }
                        sConnected = true;
                        sWriteToDatabase = true;
                        break;
                    case ConnectionStates.STATE_WORKING:
                    case ConnectionStates.STATE_RECORDING_START:
                    case ConnectionStates.STATE_COMPLETE:
                    case ConnectionStates.STATE_INIT:
                    case ConnectionStates.STATE_RECORDING_END:
                    case ConnectionStates.STATE_STOPPED:
                        break;
                    case ConnectionStates.STATE_DISCONNECTED:
                    case ConnectionStates.STATE_ERROR:
                    case ConnectionStates.STATE_GET_DATA_TIME_OUT:
                    case ConnectionStates.STATE_FAILED:
                        sConnected = false;
                        sINeurointerfaceCallback.onNeuroInterfaceStateChanged(connectionStates);
                        if (mContext != null) {
                            if (connectionSnackbar != null) {
                                connectionSnackbar.dismiss();
                            }
                            message(mContext.getString(R.string.no_device));
                            try {
                                NeuroDataDatabase.getInstance(mContext).close();
                                NeuroDataDatabase.getMyWritableDatabase(mContext).close();
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
                        if (sWriteToDatabase) {
                            NeuroDataDatabase.addBCIAttentionToDatabase(mContext, System.currentTimeMillis(), data);
                        }
                        int attentionValue = processValue(data);
                        if (attentionValue != -1 && sINeurointerfaceCallback != null) {
                            sINeurointerfaceCallback.onNeuroDataReceived(datatype, attentionValue);
                        }
                        break;
                    case MindDataType.CODE_MEDITATION:
                        if (sWriteToDatabase) {
                            NeuroDataDatabase.addBCIMediationToDatabase(mContext, System.currentTimeMillis(), data);
                        }
                        break;
                    case MindDataType.CODE_EEGPOWER:
                        if (sWriteToDatabase) {
                            EEGPower ep = (EEGPower) obj;
                            NeuroDataDatabase.addBCITGEEGPowerToDatabase(mContext, ep.delta, ep.theta, ep.lowAlpha, ep.highAlpha, ep.lowBeta, ep.highBeta, ep.lowGamma, ep.middleGamma, System.currentTimeMillis());
                        }
                        break;
                    default:
                        break;
                }
            }

        };

        sWriteToDatabase = true;
        sConnected = false;
        sDoneActivitiesArray = new HashSet<>();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static Set<String> getDoneActivitiesArray() {
        return sDoneActivitiesArray;
    }

    public static void clearDoneActivities() {
        sDoneActivitiesArray.clear();
    }

    public static String getAllUndoneActivities() {
        String result = "";
        if (!sDoneActivitiesArray.contains("11")) {
            result += "11, ";
        }
        if (!sDoneActivitiesArray.contains("21")) {
            result += "21, ";
        }
        if (!sDoneActivitiesArray.contains("31")) {
            result += "31, ";
        }
        if (!sDoneActivitiesArray.contains("41")) {
            result += "41, ";
        }
        if (!sDoneActivitiesArray.contains("51")) {
            result += "51, ";
        }
        if (!sDoneActivitiesArray.contains("61")) {
            result += "61, ";
        }
        if (!sDoneActivitiesArray.contains("71")) {
            result += "71, ";
        }
        if (!sDoneActivitiesArray.contains("99")) {
            result += "99, ";
        }
        if (!TextUtils.isEmpty(result)) {
            result = result.substring(0, result.length() - 2);
        }
        return result;
    }

    public static boolean addActivityToDoneArray(String string) {
        return sDoneActivitiesArray.add(string);
    }

    public static boolean containsDoneActivity(String string) {
        return sDoneActivitiesArray.contains(string);
    }

    public static boolean removeActivityFromDoneArray(String string) {
        return sDoneActivitiesArray.remove(string);
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
            tgStreamReader = new TgStreamReader(btAdapter, sStreamHandler);
            dropBCIConnection();
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
        if (tgStreamReader != null && tgStreamReader.isBTConnected()) {
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
        if (sAttentionQueue == null) {
            sAttentionQueue = new ArrayList<>();
            sAttentionQueue.add(value);
            return -1;
        }
        if (sAttentionQueue.size() == Fa * T) {
            sAttentionQueue.remove(0);
            sAttentionQueue.add(value);
            return meanValue();
        } else {
            sAttentionQueue.add(value);
            if (sAttentionQueue.size() == Fa * T) {
                return meanValue();
            }
            return -1;
        }
    }

    private static int meanValue() {
        int sum = 0;
        for (int i = 0; i < sAttentionQueue.size(); i ++) {
            sum += sAttentionQueue.get(i);
        }
        return sum / sAttentionQueue.size();
    }

    public static boolean changeStateOfWriting() {
        sWriteToDatabase = !sWriteToDatabase;
        return sWriteToDatabase;
    }

    public static boolean getStateOfWriting() {
        return sWriteToDatabase;
    }

}
