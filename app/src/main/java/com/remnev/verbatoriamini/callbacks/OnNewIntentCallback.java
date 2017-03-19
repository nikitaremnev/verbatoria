package com.remnev.verbatoriamini.callbacks;

import android.nfc.Tag;

public interface OnNewIntentCallback {
    void promptForContent(Tag msg);
}
