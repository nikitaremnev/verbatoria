package com.verbatoria.infrastructure;

import android.os.Bundle;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * @author nikitaremnev
 */

public abstract class BasePresenter {

    private CompositeDisposable compositeDisposable;

    protected void onStart() {
        compositeDisposable = new CompositeDisposable();
    }

    protected void onStop() {
        compositeDisposable.clear();
    }

    protected void onSaveInstanceState(Bundle outState) {
        saveState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        restoreState(savedInstanceState);
    }

    protected void addSubscription(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    abstract public void saveState(Bundle outState);
    abstract public void restoreState(Bundle savedInstanceState);

}
