package com.remnev.verbatoriamini.model;

/**
 * Created by nikitaremnev on 09.04.16.
 */
public class SumNumber {

    private int sum;
    private int number;

    public int getSum() {
        return sum;
    }

    public int getNumber() {
        return number;
    }

    public void increaseNumber() {
        number ++;
    }

    public void increaseSum(int value) {
        sum += value;
    }

    public int getMean() {
        return sum / number;
    }

    public SumNumber(int value) {
        sum = value;
        number = 1;
    }
}
