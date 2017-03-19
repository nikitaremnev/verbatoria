package com.remnev.verbatoriamini.callbacks;

import android.os.Message;

/**
 * Created by nikitaremnev on 07.02.16.
 */
public interface OnBCIConnectionCallback {

    void onMessageReceived(Message message);

    void animateStatusChanged(int value);

}
