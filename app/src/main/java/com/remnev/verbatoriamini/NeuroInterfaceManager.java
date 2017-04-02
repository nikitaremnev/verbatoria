package com.remnev.verbatoriamini;

import android.bluetooth.BluetoothAdapter;

import com.neurosky.connection.TgStreamReader;

/**
 * Created by nikitaremnev on 31.03.17.
 */

public class NeuroInterfaceManager {

    private static boolean mConnected;
    private static TgStreamReader mTgStreamReader;
    private BluetoothAdapter mBluetoothAdapter;

    public void connectToBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            startConnection();
        } else {
//            onBluetoothNotStarted();
        }
    }

    public void startConnection() {
        if (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()) {
            mTgStreamReader = new TgStreamReader(mBluetoothAdapter, new NeuroInterfaceHandler());
            stopRecordingAndDropConnection();
            mTgStreamReader.connect();
        } else {
//            onBluetoothNotStarted();
        }
    }

    public void startRecording() {
        if (mTgStreamReader != null) {
            mTgStreamReader.start();
        }
    }

    public void stopRecordingAndDropConnection() {
        if (mTgStreamReader != null && mTgStreamReader.isBTConnected()){
            mTgStreamReader.stop();
            mTgStreamReader.close();
        }
    }

}
