package com.remnev.verbatoriamini.callbacks;

import android.os.Message;

/**
 * Created by nikitaremnev on 07.02.16.
 */
public interface INeuroInterfaceCallback {

    void onNeuroInterfaceStateChanged(int code);
    void onNeuroDataReceived(int code, int attention);

}
