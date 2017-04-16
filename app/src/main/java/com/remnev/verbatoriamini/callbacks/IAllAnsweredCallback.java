package com.remnev.verbatoriamini.callbacks;

/**
 * Created by nikitaremnev on 16.04.17.
 */
public interface IAllAnsweredCallback {

    void allAnswered(final String age, final String reportID, final long timeInMillis, final String directoryAbsPath);

}
