package com.verbatoria.infrastructure.connection;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;

import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;
import com.verbatoria.utils.Logger;

import static com.verbatoria.infrastructure.connection.AttentionBroadcastReceiver.ATTENTION_BROADCAST;
import static com.verbatoria.infrastructure.connection.ConnectionBroadcastReceiver.BLUETOOTH_DISABLED_BROADCAST;
import static com.verbatoria.infrastructure.connection.ConnectionBroadcastReceiver.STATE_CONNECTED_BROADCAST;
import static com.verbatoria.infrastructure.connection.ConnectionBroadcastReceiver.STATE_CONNECTING_BROADCAST;
import static com.verbatoria.infrastructure.connection.ConnectionBroadcastReceiver.STATE_DISCONNECTED_BROADCAST;
import static com.verbatoria.infrastructure.connection.ConnectionBroadcastReceiver.STATE_ERROR_BROADCAST;
import static com.verbatoria.infrastructure.connection.ConnectionBroadcastReceiver.STATE_FAILED_BROADCAST;
import static com.verbatoria.infrastructure.connection.ConnectionBroadcastReceiver.STATE_GET_DATA_TIME_OUT_BROADCAST;

/**
 * Сервис соединения с нейроинтерфейсом
 *
 * @author nikitaremnev
 */
public class ConnectionService extends Service implements TgStreamHandler {

    private static final String TAG = ConnectionService.class.getSimpleName();

    public static final String ACTION_START_CONNECTION = "com.verbatoria.ACTION_START_CONNECTION";
    public static final String ACTION_DROP_CONNECTION = "com.verbatoria.ACTION_DROP_CONNECTION";

    /*
       Вспомогательные переменные для подключения к устройству
    */
    private BluetoothAdapter mBluetoothAdapter;
    private TgStreamReader mTgStreamReader;

    private LocalBroadcastManager mLocalBroadcastManager;

    private boolean mHasConnection = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            switch (intent.getAction()) {
                case ACTION_START_CONNECTION:
                    if (!mHasConnection) {
                        tryToConnect();
                    }
                    break;
                case ACTION_DROP_CONNECTION:
                    mHasConnection = false;
                    stopSelf();
                    break;
            }
        }
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        dropConnection();
        mTgStreamReader = null;
        mBluetoothAdapter = null;
        mLocalBroadcastManager = null;
    }

    @Override
    public void onStatesChanged(int connectionState) {
        Logger.e(TAG, "onStatesChanged: connectionState");
        switch (connectionState) {
            case ConnectionStates.STATE_CONNECTING:
                mLocalBroadcastManager.sendBroadcast(new Intent(STATE_CONNECTING_BROADCAST));
                break;
            case ConnectionStates.STATE_CONNECTED:
                startWriting();
                mLocalBroadcastManager.sendBroadcast(new Intent(STATE_CONNECTED_BROADCAST));
                break;
            case ConnectionStates.STATE_DISCONNECTED:
                mLocalBroadcastManager.sendBroadcast(new Intent(STATE_DISCONNECTED_BROADCAST));
                break;
            case ConnectionStates.STATE_GET_DATA_TIME_OUT:
                mLocalBroadcastManager.sendBroadcast(new Intent(STATE_GET_DATA_TIME_OUT_BROADCAST));
                break;
            case ConnectionStates.STATE_ERROR:
                mLocalBroadcastManager.sendBroadcast(new Intent(STATE_ERROR_BROADCAST));
                break;
            case ConnectionStates.STATE_FAILED:
                mLocalBroadcastManager.sendBroadcast(new Intent(STATE_FAILED_BROADCAST));
                break;
        }
    }

    @Override
    public void onChecksumFail(byte[] bytes, int i, int i1) {
        Logger.e(TAG, "onChecksumFail");
    }

    @Override
    public void onRecordFail(int i) {
        Logger.e(TAG, "onRecordFail");
    }

    @Override
    public void onDataReceived(int dataCode, final int data, Object object) {
        switch (dataCode) {
            case MindDataType.CODE_ATTENTION:
                mLocalBroadcastManager.sendBroadcast(new Intent(ATTENTION_BROADCAST));
                break;
            case MindDataType.CODE_MEDITATION:
//                mSessionInteractorCallback.onDataReceivedCallback(dataCode, data);
                break;
            case MindDataType.CODE_EEGPOWER:
                EEGPower eegPower = (EEGPower) object;
//                mSessionInteractorCallback.onEEGDataReceivedCallback(eegPower);
                break;
            default:
                break;
        }
    }

    public void tryToConnect() {
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            startConnection();
        } else {
            mLocalBroadcastManager.sendBroadcast(new Intent(BLUETOOTH_DISABLED_BROADCAST));
        }
    }

    private void startConnection() {
        mTgStreamReader = new TgStreamReader(mBluetoothAdapter, this);
        mTgStreamReader.connect();
    }

    public void dropConnection() {
        if (mTgStreamReader != null
                && mTgStreamReader.isBTConnected()) {
            mTgStreamReader.stop();
            mTgStreamReader.close();
        }
    }

    private void startWriting() {
        if (mTgStreamReader != null) {
            mTgStreamReader.start();
        }
        mHasConnection = true;
    }
}
