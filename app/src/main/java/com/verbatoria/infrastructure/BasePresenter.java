package com.verbatoria.infrastructure;

import android.os.Bundle;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * @author nikitaremnev
 */

public abstract class BasePresenter {

    private CompositeSubscription mSubscriptions;

    protected void onStart() {
        mSubscriptions = new CompositeSubscription();
    }

    protected void onStop() {
        mSubscriptions.clear();
    }

    protected void onSaveInstanceState(Bundle outState) {
        saveState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        restoreState(savedInstanceState);
    }

    protected void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

    abstract public void saveState(Bundle outState);
    abstract public void restoreState(Bundle savedInstanceState);

}
