package com.verbatoria.infrastructure;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.remnev.verbatoria.R;
import com.verbatoria.utils.LocaleHelper;
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

        LocaleHelper.updateLocaleToSaved(this);

        if (mBasePresenter != null) {
            mBasePresenter.onRestoreInstanceState(savedInstanceState);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        ContextWrapper localeContextWrapper  = LocaleHelper.getLocaleContextWrapper(newBase);
        ContextWrapper calligraphyContextWrapper = CalligraphyContextWrapper.wrap(localeContextWrapper);
        super.attachBaseContext(calligraphyContextWrapper);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mBasePresenter != null) {
            mBasePresenter.onStart();
        }
        Logger.e(TAG, "onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBasePresenter != null) {
            mBasePresenter.onStop();
        }
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
        if (mBasePresenter != null) {
            mBasePresenter.onSaveInstanceState(outState);
        }
        Logger.e(TAG, "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (mBasePresenter != null) {
            mBasePresenter.onRestoreInstanceState(savedInstanceState);
        }
        Logger.e(TAG, "onRestoreInstanceState");
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Logger.e(TAG, "onUserInteraction");
//        VerbatoriaKtApplication.on();
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
