package com.remnev.verbatoriamini.model;

import android.util.Log;

import com.bluelinelabs.logansquare.annotation.JsonField;
import com.bluelinelabs.logansquare.annotation.JsonObject;

import java.util.Random;

/**
 * Created by nikitaremnev on 27.05.16.
 */
@JsonObject
public class Code {

    @JsonField(name = "load_code")
    private int code;

    public Code() {

    }

    public Code(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
