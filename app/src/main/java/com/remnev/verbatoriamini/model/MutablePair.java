package com.remnev.verbatoriamini.model;

/**
 * Created by nikitaremnev on 22.03.17.
 */

public class MutablePair {

    private String first;
    private long second;

    public MutablePair(String first, int second) {
        this.first = first;
        this.second = second;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public long getSecond() {
        return second;
    }

    public void addSecond(long second) {
        this.second += second;
    }

    @Override
    public String toString() {
        return "first: " + first + " second: " + second;
    }
}
