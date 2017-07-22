package com.remnev.verbatoriamini;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Message;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;
//import com.remnev.verbatoriamini.callbacks.INeuroInterfaceCallback;
import com.verbatoria.business.session.processor.AttentionValueProcessor;
/**
 * Created by nikitaremnev on 07.02.16.
 */
public class NeuroApplicationClass extends Application {

    public static final int BLUETOOTH_NOT_STARTED = -13432;

    private BluetoothAdapter mBluetoothAdapter;

    private static TgStreamReader tgStreamReader;

    private static View rootView;
    private static Context mContext;

    private static Snackbar connectionSnackbar;

    private static boolean sConnected;
    private static TgStreamHandler sStreamHandler;
//    private static INeuroInterfaceCallback sINeurointerfaceCallback;

    static {
        sStreamHandler = new NeuroStreamProcessor();
        sConnected = false;
    }

//    public void setOnBCIConnectionCallback(INeuroInterfaceCallback callback) {
//        sINeurointerfaceCallback = callback;
//    }

    public static boolean isConnected() {
        return sConnected;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setRootView(View rootView) {
        NeuroApplicationClass.rootView = rootView;
    }

    public void connectToBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            onBluetoothConnected();
        } else {
            onBluetoothNotStarted();
        }
    }

    public void startBCIConnection() {
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            tgStreamReader = new TgStreamReader(mBluetoothAdapter, sStreamHandler);
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
        if (rootView != null) {
            Message message = new Message();
            message.what = BLUETOOTH_NOT_STARTED;
            message.arg1 = BLUETOOTH_NOT_STARTED;
//            if (sINeurointerfaceCallback != null) {
//                sINeurointerfaceCallback.onNeuroInterfaceStateChanged(33);
//            }
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
    }

    private static class NeuroStreamProcessor implements TgStreamHandler {

        @Override
        public void onStatesChanged(int connectionStates) {
            switch (connectionStates) {
                case ConnectionStates.STATE_CONNECTING:
                    sConnected = false;
//                    sINeurointerfaceCallback.onNeuroInterfaceStateChanged(connectionStates);
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
//                            message(mContext.getString(R.string.bci_started));
                        }
//                        sINeurointerfaceCallback.onNeuroInterfaceStateChanged(ConnectionStates.STATE_CONNECTED);
//                        StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.CONNECT_ID, RezhimID.ANOTHER_MODE, -1, -1);
//                        StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.RECORD_START_ID, RezhimID.ANOTHER_MODE, -1, -1);
                    }
                    sConnected = true;
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
//                    sINeurointerfaceCallback.onNeuroInterfaceStateChanged(connectionStates);
                    if (mContext != null) {
                        if (connectionSnackbar != null) {
                            connectionSnackbar.dismiss();
                        }
//                        message(mContext.getString(R.string.no_device));
                        try {
//                            NeuroDataDatabase.getInstance(mContext).close();
//                            NeuroDataDatabase.getMyWritableDatabase(mContext).close();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
            }
        }

        @Override
        public void onChecksumFail(byte[] bytes, int i, int i1) {

        }

        @Override
        public void onRecordFail(int i) {

        }

        @Override
        public void onDataReceived(int datatype, final int data, Object obj) {
            switch (datatype) {
                case MindDataType.CODE_ATTENTION:
//                    NeuroDataDatabase.addBCIAttentionToDatabase(mContext, System.currentTimeMillis(), data);
                    int attentionValue = AttentionValueProcessor.processValue(data);
//                    if (attentionValue != -1 && sINeurointerfaceCallback != null) {
//                        sINeurointerfaceCallback.onNeuroDataReceived(datatype, attentionValue);
//                    }
                    break;
                case MindDataType.CODE_MEDITATION:
//                    NeuroDataDatabase.addBCIMediationToDatabase(mContext, System.currentTimeMillis(), data);
                    break;
                case MindDataType.CODE_EEGPOWER:
                    EEGPower ep = (EEGPower) obj;
//                    NeuroDataDatabase.addBCITGEEGPowerToDatabase(mContext, ep.delta, ep.theta, ep.lowAlpha, ep.highAlpha, ep.lowBeta, ep.highBeta, ep.lowGamma, ep.middleGamma, System.currentTimeMillis());
                    break;
                default:
                    break;
            }
        }
    }

}
