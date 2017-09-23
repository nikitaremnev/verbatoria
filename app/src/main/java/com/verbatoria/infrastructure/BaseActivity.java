package com.verbatoria.infrastructure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.verbatoria.presentation.login.presenter.recovery.RecoveryPresenter;
import com.verbatoria.utils.Logger;

import butterknife.ButterKnife;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private BasePresenter mBasePresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        setUpViews();

        Logger.e(TAG, "onCreate");
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBasePresenter.onStart();
        Logger.e(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBasePresenter.onStop();
        Logger.e(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Logger.e(TAG, "onDestroy");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mBasePresenter.onSaveInstanceState(outState);
        Logger.e(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mBasePresenter.onRestoreInstanceState(savedInstanceState);
        Logger.e(TAG, "onRestoreInstanceState");
    }

    protected void setPresenter(BasePresenter basePresenter) {
        mBasePresenter = basePresenter;
    }

    abstract protected void setUpViews();
}
