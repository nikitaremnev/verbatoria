package com.verbatoria;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.neurosky.connection.ConnectionStates;
import com.neurosky.connection.DataType.MindDataType;
import com.neurosky.connection.EEGPower;
import com.neurosky.connection.TgStreamHandler;
import com.neurosky.connection.TgStreamReader;
import com.remnev.verbatoria.R;
import com.verbatoria.business.session.ISessionInteractor;
import com.verbatoria.business.session.activities.ActivitiesTimerTask;
import com.verbatoria.business.session.activities.UserInteractionTimerTask;
import com.verbatoria.di.DaggerInjector;
import com.verbatoria.di.DependencyHolder;
import com.verbatoria.di.Injector;

import com.verbatoria.utils.Logger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ConcurrentHashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Application-класс. Инициализирует даггер-компонент для построения всех зависимостей.
 *
 * @author nikitaremnev
 */

public class VerbatoriaApplication extends MultiDexApplication implements DependencyHolder<Object> {

    private static final String TAG = VerbatoriaApplication.class.getSimpleName();

    /*
        Вспомогательные переменные для подключения к устройству
     */
    private static BluetoothAdapter sBluetoothAdapter;
    private static TgStreamReader sTgStreamReader;
    private static TgStreamHandler sStreamHandler;

    private static ISessionInteractor.ISessionCallback sSessionInteractorCallback;

    private static ActivitiesTimerTask activitiesTimerTask;

    private static UserInteractionTimerTask userInteractionTimerTask;

    private static Injector injector;

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
        injector = DaggerInjector.builder()
            .context(this)
            .build();
        dependenciesContainer = new ConcurrentHashMap<>();
        MultiDex.install(this);
        overrideFonts();
    }

    @NonNull
    public static Injector getInjector() {
        return injector;
    }

    public static void setSessionInteractorCallback(ISessionInteractor.ISessionCallback sessionInteractorCallback) {
        sSessionInteractorCallback = sessionInteractorCallback;
    }

    public static ActivitiesTimerTask getActivitiesTimer(ISessionInteractor.IActivitiesCallback activitiesCallback) {
        if (activitiesTimerTask == null) {
            activitiesTimerTask = new ActivitiesTimerTask(activitiesCallback);
        } else {
            activitiesTimerTask.cancel();
            activitiesTimerTask = activitiesTimerTask.copy(activitiesCallback);
        }
        return activitiesTimerTask;
    }

    public static void dropActivitiesTimer() {
        activitiesTimerTask = null;
    }

    public static void onUserInteraction() {
        if (userInteractionTimerTask == null) {
            userInteractionTimerTask = new UserInteractionTimerTask();
        } else {
            userInteractionTimerTask.updateLastInteractionTime();
        }
    }

    public static void onSmsConfirmationPassed() {
        if (userInteractionTimerTask == null) {
            userInteractionTimerTask = new UserInteractionTimerTask();
        } else {
            userInteractionTimerTask.dropTimerTaskState();
        }
    }

    public static void tryToConnect() {
        sBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (sBluetoothAdapter != null && sBluetoothAdapter.isEnabled()) {
            startConnection();
        } else {
            sSessionInteractorCallback.onBluetoothDisabled();
        }
    }

    public static void dropConnection() {
        if (sTgStreamReader != null && sTgStreamReader.isBTConnected()) {
            sTgStreamReader.stop();
            sTgStreamReader.close();
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

    private static void startWriting() {
        if (sTgStreamReader != null) {
            sTgStreamReader.start();
        }
    }

    private void overrideFonts() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Roboto-Light.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

    //region DependencyHolder

    private static ConcurrentHashMap<String, Object> dependenciesContainer;

    @Override
    public void put(@NotNull String key, @NotNull Object dependency) {
        dependenciesContainer.put(key, dependency);
    }

    @Nullable
    @Override
    public <D> D pop(@NotNull String key) {
        return (D) dependenciesContainer.remove(key);
    }

    @Override
    public Void getComponent() {
        return null;
    }

    //endregion

    private static class DefaultTgStreamHandler implements TgStreamHandler {

        @Override
        public void onStatesChanged(int connectionState) {
            Logger.e(TAG, "onStatesChanged: " + connectionState);
            if (connectionState == ConnectionStates.STATE_CONNECTED ||
                    connectionState == ConnectionStates.STATE_WORKING ||
                    connectionState == ConnectionStates.STATE_RECORDING_START) {
                startWriting();
            }
            sSessionInteractorCallback.onConnectionStateChanged(connectionState);
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
