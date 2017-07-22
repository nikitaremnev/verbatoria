package com.verbatoria;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.support.annotation.NonNull;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;

import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.di.application.ApplicationComponent;
import com.verbatoria.di.application.ApplicationModule;
import com.verbatoria.di.application.DaggerApplicationComponent;
import com.verbatoria.utils.FontsOverride;

/**
 * Application-класс. Инициализирует даггер-компонент для построения всех зависимостей.
 *
 * @author nikitaremnev
 */

public class VerbatoriaApplication extends MultiDexApplication {

    /*
        Вспомогательные переменные для подключения к устройству
     */
    private static BluetoothAdapter sBluetoothAdapter;
    private static TgStreamReader sTgStreamReader;
    private static TgStreamHandler sStreamHandler;

    private static ISessionInteractor.IApplicationSessionInteractorCallback sSessionInteractorCallback;

    @NonNull
    private static ApplicationComponent mApplicationComponent;

    static {
        sStreamHandler = new DefaultTgStreamHandler();
    }

    @NonNull
    public static VerbatoriaApplication get(@NonNull Context context) {
        return (VerbatoriaApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(new ApplicationModule(getApplicationContext()))
            .build();
        MultiDex.install(this);
        overrideFonts();
    }

    @NonNull
    public static ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public static void setSessionInteractorCallback(ISessionInteractor.IApplicationSessionInteractorCallback sessionInteractorCallback) {
        sSessionInteractorCallback = sessionInteractorCallback;
    }

    public static void tryToConnect() {
        sBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (sBluetoothAdapter != null && sBluetoothAdapter.isEnabled()) {
            startConnection();
        } else {
            sSessionInteractorCallback.onBluetoothDisabled();
        }
    }

    private static void startConnection() {
        if (sTgStreamReader != null && sTgStreamReader.isBTConnected()) {
            sTgStreamReader.stop();
            sTgStreamReader.close();
        }
        sTgStreamReader = new TgStreamReader(sBluetoothAdapter, sStreamHandler);
        sTgStreamReader.connect();
    }

    private void overrideFonts() {
        FontsOverride.setDefaultFont(this, "DEFAULT", "Lato-Regular.ttf");
        FontsOverride.setDefaultFont(this, "MONOSPACE", "Lato-Regular.ttf");
        FontsOverride.setDefaultFont(this, "SERIF", "Lato-Regular.ttf");
        FontsOverride.setDefaultFont(this, "SANS_SERIF", "Lato-Regular.ttf");
    }

    private static class DefaultTgStreamHandler implements TgStreamHandler {

        @Override
        public void onStatesChanged(int connectionState) {
            sSessionInteractorCallback.onConnectionStateChanged(connectionState);
        }

        @Override
        public void onChecksumFail(byte[] bytes, int i, int i1) {

        }

        @Override
        public void onRecordFail(int i) {

        }

        @Override
        public void onDataReceived(int dataCode, final int data, Object object) {
            switch (dataCode) {
                case MindDataType.CODE_ATTENTION:
                case MindDataType.CODE_MEDITATION:
                    sSessionInteractorCallback.onDataReceivedCallback(dataCode, data);
                    break;
                case MindDataType.CODE_EEGPOWER:
                    EEGPower eegPower = (EEGPower) object;
                    sSessionInteractorCallback.onEEGDataReceivedCallback(eegPower);
                    break;
                default:
                    break;
            }
        }
    }
}
