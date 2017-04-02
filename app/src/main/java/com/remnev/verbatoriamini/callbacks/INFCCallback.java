package com.remnev.verbatoriamini.callbacks;

import android.nfc.Tag;

public interface INFCCallback {
    void onNFCTagReaded(Tag msg);
}
