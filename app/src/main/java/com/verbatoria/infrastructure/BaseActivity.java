package com.verbatoria.infrastructure;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.remnev.verbatoriamini.R;
import com.verbatoria.utils.Logger;

import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity {

    private static final String TAG = BaseActivity.class.getSimpleName();

    private BasePresenter mBasePresenter;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        setUpViews();

        Logger.e(TAG, "onCreate");
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
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
        stopProgress();
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

    protected void startProgress() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage(getString(R.string.loading_please_wait));
        mProgressDialog.show();
    }

    protected void stopProgress() {
        if (mProgressDialog != null) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    protected void setPresenter(BasePresenter basePresenter) {
        mBasePresenter = basePresenter;
    }

    abstract protected void setUpViews();
}
