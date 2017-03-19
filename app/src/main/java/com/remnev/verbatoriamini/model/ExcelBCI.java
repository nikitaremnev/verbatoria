package com.remnev.verbatoriamini.model;

/**
 * Created by nikitaremnev on 28.03.16.
 */
public class ExcelBCI {

    private int attention;
    private int mediation;
    private int delta;
    private int theta;
    private int lowAlpha;
    private int highAlpha;
    private int lowBeta;
    private int highBeta;
    private int lowGamma;
    private int midGamma;
    private String childID;

    private long timestamp;

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public int getMediation() {
        return mediation;
    }

    public void setMediation(int mediation) {
        this.mediation = mediation;
    }

    public int getDelta() {
        return delta;
    }

    public void setDelta(int delta) {
        this.delta = delta;
    }

    public int getTheta() {
        return theta;
    }

    public void setTheta(int theta) {
        this.theta = theta;
    }

    public int getLowAlpha() {
        return lowAlpha;
    }

    public void setLowAlpha(int lowAlpha) {
        this.lowAlpha = lowAlpha;
    }

    public int getLowBeta() {
        return lowBeta;
    }

    public void setLowBeta(int lowBeta) {
        this.lowBeta = lowBeta;
    }

    public int getLowGamma() {
        return lowGamma;
    }

    public void setLowGamma(int lowGamma) {
        this.lowGamma = lowGamma;
    }

    public int getMidGamma() {
        return midGamma;
    }

    public void setMidGamma(int midGamma) {
        this.midGamma = midGamma;
    }

    public int getHighBeta() {
        return highBeta;
    }

    public void setHighBeta(int highBeta) {
        this.highBeta = highBeta;
    }

    public int getHighAlpha() {
        return highAlpha;
    }

    public void setHighAlpha(int highAlpha) {
        this.highAlpha = highAlpha;
    }

    public String getChildID() {
        return childID;
    }

    public void setChildID(String childID) {
        this.childID = childID;
    }
}
