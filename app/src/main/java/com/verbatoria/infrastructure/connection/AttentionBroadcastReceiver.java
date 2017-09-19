package com.verbatoria.infrastructure.connection;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Ресивер для внимания
 *
 * @author nikitaremnev
 */
public class AttentionBroadcastReceiver extends BroadcastReceiver {

    public static final String ATTENTION_BROADCAST = "com.verbatoria.infrastructure.connection.ATTENTION_BROADCAST";

    @Override
    public void onReceive(Context context, Intent intent) {

    }
}
