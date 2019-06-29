package com.verbatoria.ui.session.view.connection;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.remnev.verbatoria.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.models.EventModel;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.infrastructure.BaseActivity;
import com.verbatoria.infrastructure.BasePresenter;
import com.verbatoria.ui.session.presenter.connection.IConnectionPresenter;
import com.verbatoria.ui.session.view.writing.WritingActivity;
import com.verbatoria.utils.Helper;
import com.verbatoria.utils.Logger;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Экран соединения с BCI
 *
 * @author nikitaremnev
 */
public class ConnectionActivity extends BaseActivity implements IConnectionView {

    private static final String TAG = ConnectionActivity.class.getSimpleName();

    public static final String EXTRA_EVENT_MODEL = "com.verbatoria.presentation.session.view.connection.EXTRA_EVENT_MODEL";

    private static final int LOADING_CHANGE_STATE_PAUSE = 500;
    private static final int DISCONNECTED_STATE_DELAY = 2000;

    @Inject
    IConnectionPresenter mConnectionPresenter;

    @BindView(R.id.connection_status_text_view)
    public TextView mConnectionStatusTextView;

    @BindView(R.id.connection_status_image_view)
    public ImageView mConnectionStatusImageView;

    @BindView(R.id.start_button)
    public Button mStartButton;

    @BindView(R.id.connect_button)
    public Button mConnectButton;

    @BindView(R.id.progress_layout)
    public View mLoadingView;

    /*
        Handler and runnables for updating UI
    */
    private Handler mUiHandler;
    private Runnable mConnectingRunnable;
    private Runnable mDisconnectedRunnable;

    /*
        Loading drawables and counter state
     */
    private int mCurrentLoadingDrawable;
    private boolean mLoading;
    private static int[] sConnectingDrawables = new int[]{
            R.drawable.ic_neurointerface_connecting_first,
            R.drawable.ic_neurointerface_connecting_second,
            R.drawable.ic_neurointerface_connecting_third,
            R.drawable.ic_neurointerface_connecting_fourth
    };

    public static Intent newInstance(Context mContext, EventModel eventModel) {
        Logger.e(TAG, "eventModel: " + eventModel.toString());
        Intent intent = new Intent(mContext, ConnectionActivity.class);
        intent.putExtra(EXTRA_EVENT_MODEL, eventModel);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        VerbatoriaApplication.getInjector().addModule(new SessionModule()).inject(this);

        setContentView(R.layout.activity_connection);

        mConnectionPresenter.bindView(this);
        mConnectionPresenter.obtainEvent(getIntent());
        setPresenter((BasePresenter) mConnectionPresenter);

        super.onCreate(savedInstanceState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mConnectionPresenter.unbindView();
    }

    @Override
    public void showProgress() {
        mLoadingView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mLoadingView.setVisibility(View.GONE);
    }

    @Override
    public void startLoading() {
        mLoading = true;
        showConnectingState();
    }

    @Override
    public void showConnectingState() {
        mConnectionStatusTextView.setText(getString(R.string.session_connection_in_progress));
        int currentDrawableIndex = mCurrentLoadingDrawable ++ % sConnectingDrawables.length;
        mConnectionStatusImageView.setImageResource(sConnectingDrawables[currentDrawableIndex]);
        mUiHandler.postDelayed(mConnectingRunnable, LOADING_CHANGE_STATE_PAUSE);
        setButtonsDisabled();
    }

    @Override
    public void showConnectedState() {
        Logger.e(TAG, "showConnectedState");
        mLoading = false;
        mUiHandler.removeCallbacks(mConnectingRunnable);
        mUiHandler.removeCallbacks(mDisconnectedRunnable);
        mUiHandler.post(() -> updateConnectionState(R.drawable.ic_neurointerface_connected,
                getString(R.string.session_connected),
                true));
    }

    @Override
    public void showDisconnectedState() {
        Logger.e(TAG, "showDisconnectedState");
        mLoading = false;
        mUiHandler.removeCallbacks(mConnectingRunnable);
        mUiHandler.post(() -> updateConnectionState(R.drawable.ic_neurointerface_disconnected,
                getString(R.string.session_not_connected),
                false));
    }

    @Override
    public void showErrorConnectionState() {
        Logger.e(TAG, "showErrorConnectionState");
        mLoading = false;
        mUiHandler.post(() -> updateConnectionState(R.drawable.ic_neurointerface_error,
                getString(R.string.session_not_connected),
                false));
        mUiHandler.postDelayed(mDisconnectedRunnable, DISCONNECTED_STATE_DELAY);
    }

    private void updateConnectionState(@DrawableRes int drawable,
                                       String statusText,
                                       boolean startButtonEnabled) {
        mConnectionStatusImageView.setImageResource(drawable);
        mConnectionStatusTextView.setText(statusText);
        setStartButtonEnabled(startButtonEnabled);
    }


    @Override
    public void showBluetoothDisabled() {
        Snackbar snackbar = Snackbar.make(mConnectionStatusTextView, getString(R.string.session_bluetooth_disabled), Snackbar.LENGTH_SHORT);
        snackbar.getView().setBackgroundResource(R.color.error_color);
        snackbar.setAction(getString(R.string.session_settings), view -> {
            Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    @Override
    public void startWriting() {
        Intent intent = WritingActivity.newInstance(this, mConnectionPresenter.getEvent());
        startActivity(intent);
        finish();
    }

    @Override
    public void showError(String error) {
        Helper.showErrorSnackBar(mLoadingView, error);
    }

    @Override
    protected void setUpViews() {
        setUpNavigation();
        setUpHandler();
        showDisconnectedState();

        mConnectButton.setOnClickListener(v -> mConnectionPresenter.connect());
        mStartButton.setOnClickListener(v -> mConnectionPresenter.startWriting());
    }

    private void setUpNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.session_connection));
    }

    private void setUpHandler() {
        mUiHandler = new Handler();
        mConnectingRunnable = () -> {
            if (mLoading) {
                showConnectingState();
            }
        };
        mDisconnectedRunnable = this::showDisconnectedState;
    }

    private void setStartButtonEnabled(boolean enabled) {
        mStartButton.setEnabled(enabled);
        mConnectButton.setEnabled(!enabled);
    }

    private void setButtonsDisabled() {
        mStartButton.setEnabled(false);
        mConnectButton.setEnabled(false);
    }
}
