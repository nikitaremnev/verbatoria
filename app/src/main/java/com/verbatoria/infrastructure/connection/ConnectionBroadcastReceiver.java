package com.verbatoria.infrastructure.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Ресивер для соединения с устройством
 *
 * @author nikitaremnev
 */
public class ConnectionBroadcastReceiver extends BroadcastReceiver {

    public static final String BLUETOOTH_DISABLED_BROADCAST = "com.verbatoria.infrastructure.connection.BLUETOOTH_DISABLED_BROADCAST";
    public static final String STATE_CONNECTING_BROADCAST = "com.verbatoria.infrastructure.connection.STATE_CONNECTING_BROADCAST";
    public static final String STATE_CONNECTED_BROADCAST = "com.verbatoria.infrastructure.connection.STATE_CONNECTED_BROADCAST";
    public static final String STATE_DISCONNECTED_BROADCAST = "com.verbatoria.infrastructure.connection.STATE_DISCONNECTED_BROADCAST";
    public static final String STATE_GET_DATA_TIME_OUT_BROADCAST = "com.verbatoria.infrastructure.connection.STATE_GET_DATA_TIME_OUT_BROADCAST";
    public static final String STATE_ERROR_BROADCAST = "com.verbatoria.infrastructure.connection.STATE_ERROR_BROADCAST";
    public static final String STATE_FAILED_BROADCAST = "com.verbatoria.infrastructure.connection.STATE_FAILED_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
