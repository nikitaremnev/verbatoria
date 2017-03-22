package com.remnev.verbatoriamini;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.neurosky.thinkgear.TGDevice;
import com.neurosky.thinkgear.TGEegPower;
import com.remnev.verbatoriamini.callbacks.OnBCIConnectionCallback;
import com.remnev.verbatoriamini.databases.BCIDatabase;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.model.ActionID;
import com.remnev.verbatoriamini.model.RezhimID;
import com.remnev.verbatoriamini.sharedpreferences.SettingsSharedPrefs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by nikitaremnev on 07.02.16.
 */
public class ApplicationClass extends Application {

    public static final int BLUETOOTH_NOT_STARTED = -13432;

    private TGDevice tgDevice = null;
    private BluetoothAdapter btAdapter = null;
    private static OnBCIConnectionCallback onBCIConnectionCallback;
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

    public void setOnBCIConnectionCallback(OnBCIConnectionCallback callback) {
        onBCIConnectionCallback = callback;
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
            tgDevice = new TGDevice( btAdapter, handler );
            tgDevice.connect(true);
        } else {
            onBluetoothNotStarted();

        }
    }

    public void startBCI() {
        if (tgDevice != null) {
            tgDevice.start();
        }
    }

    public void dropBCIConnection() {
        if (tgDevice != null) {
            tgDevice.close();
        }
    }

    private static Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            if (onBCIConnectionCallback != null) {
                try {
                    switch( msg.what ) {
                        case TGDevice.MSG_STATE_CHANGE:
                            switch (msg.arg1) {
                                case TGDevice.STATE_IDLE:
                                    break;
                                case TGDevice.STATE_ERR_BT_OFF:

                                    break;
                                case TGDevice.STATE_CONNECTING:
                                    connected = false;
                                    onBCIConnectionCallback.onMessageReceived(msg);
                                    if (mContext != null) {
                                        connectionSnackbar = Snackbar.make(rootView, mContext.getString(R.string.connecting), Snackbar.LENGTH_INDEFINITE);
                                        connectionSnackbar.show();
//                                        /message(mContext.getString(R.string.connecting));
                                    }
                                    break;
                                case TGDevice.STATE_ERR_NO_DEVICE:
                                    connected = false;
                                    onBCIConnectionCallback.onMessageReceived(msg);
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
                                case TGDevice.STATE_NOT_FOUND:
                                    connected = false;
                                    onBCIConnectionCallback.onMessageReceived(msg);
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
                                case TGDevice.STATE_CONNECTED:
                                    connected = true;
                                    onBCIConnectionCallback.onMessageReceived(msg);
                                    if (mContext != null) {
                                        if (connectionSnackbar != null) {
                                            connectionSnackbar.dismiss();
                                        }
                                        //message(mContext.getString(R.string.connected));
                                        if (msg != null) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                                                SettingsSharedPrefs.setBciID(mContext, "" + msg.sendingUid);
                                            } else {
                                                SettingsSharedPrefs.setBciID(mContext, "Неизвестно");
                                            }
                                        }
                                        writeBCIToDatabase = true;
                                        message(mContext.getString(R.string.bci_started));
                                        StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.CONNECT_ID, RezhimID.ANOTHER_MODE, -1, -1);
                                        StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.RECORD_START_ID, RezhimID.ANOTHER_MODE, -1, -1);
                                    }
                                    break;
                                case TGDevice.STATE_DISCONNECTED:
                                    connected = false;
                                    onBCIConnectionCallback.onMessageReceived(msg);
                                    if (mContext != null) {
                                        message(mContext.getString(R.string.connection_lost));
                                        try {
                                            BCIDatabase.getInstance(mContext).close();
                                            BCIDatabase.getMyWritableDatabase(mContext).close();
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                        StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.DISCONNECT_ID, RezhimID.ANOTHER_MODE, -1, -1);
                                    }
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case TGDevice.MSG_POOR_SIGNAL:

                            break;
                        case TGDevice.MSG_ATTENTION:
                            final int attention = msg.arg1;
                            final long timestamp = System.currentTimeMillis();
                            if (writeBCIToDatabase) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        BCIDatabase.addBCIAttentionToDatabase(mContext, timestamp, attention);
                                    }
                                }).start();
                            }
                            msg.arg1 = processValue(msg.arg1);
                            if (msg.arg1 != -1) {
                                onBCIConnectionCallback.onMessageReceived(msg);
                            }
                            break;
                        case TGDevice.MSG_RAW_DATA:
                            break;
                        case TGDevice.MSG_EEG_POWER:
                            if (writeBCIToDatabase) {
                                final TGEegPower ep = (TGEegPower) msg.obj;
                                final long timestampEEg = System.currentTimeMillis();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        BCIDatabase.addBCITGEEGPowerToDatabase(mContext, ep.delta, ep.theta, ep.lowAlpha, ep.highAlpha, ep.lowBeta, ep.highBeta, ep.lowGamma, ep.midGamma, timestampEEg);
                                    }
                                }).start();
                            }
                            break;

                        case TGDevice.MSG_MEDITATION:
                            final int mediation = msg.arg1;
                            final long timestampMed = System.currentTimeMillis();
                            if (writeBCIToDatabase) {
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        BCIDatabase.addBCIMediationToDatabase(mContext, timestampMed, mediation);
                                    }
                                }).start();
                            }
                        default:
                            break;
                    }


                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    public void onBluetoothConnected() {
        startBCIConnection();
    }

    public void onBluetoothNotStarted() {
        if (rootView == null) return;
        Message message = new Message();
        message.what = BLUETOOTH_NOT_STARTED;
        message.arg1 = BLUETOOTH_NOT_STARTED;
        if (onBCIConnectionCallback != null) {
            Log.e("event", "event send to callback");
            onBCIConnectionCallback.onMessageReceived(message);
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

/*

 */
}
