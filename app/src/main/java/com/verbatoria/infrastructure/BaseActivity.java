package com.verbatoria.infrastructure;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BaseActivity extends AppCompatActivity {

    private CompositeSubscription mSubscriptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSubscriptions = new CompositeSubscription();
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//    }

    @Override
    protected void onStop() {
        super.onStop();
        mSubscriptions.clear();
    }

    protected void addSubscription(Subscription subscription) {
        mSubscriptions.add(subscription);
    }

}
