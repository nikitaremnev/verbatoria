package com.verbatoria.presentation.session.view.connection;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.DrawableRes;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.token.processor.TokenProcessor;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.presentation.session.presenter.connection.IConnectionPresenter;
import com.verbatoria.presentation.session.view.writing.WritingActivity;
import com.verbatoria.utils.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Экран соединения с BCI
 *
 * @author nikitaremnev
 */
public class ConnectionActivity extends AppCompatActivity implements IConnectionView {

    private static final String TAG = ConnectionActivity.class.getSimpleName();

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
            R.drawable.ic_neurointerface_connecting_fourth,
            R.drawable.ic_neurointerface_connecting_fourth,
            R.drawable.ic_neurointerface_connecting_fourth,
            R.drawable.ic_neurointerface_connecting_fourth
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize views
        setContentView(R.layout.activity_connection);
        ButterKnife.bind(this);
        setUpViews();
        setUpNavigation();
        setUpHandler();
        //bind views
        VerbatoriaApplication.getApplicationComponent().addModule(new SessionModule()).inject(this);
        mConnectionPresenter.bindView(this);
        showDisconnectedState();
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
        mUiHandler.post(() -> updateConnectionState(R.drawable.ic_neurointerface_connected,
                getString(R.string.session_connected),
                true));
    }

    @Override
    public void showDisconnectedState() {
        Logger.e(TAG, "showDisconnectedState");
        mLoading = false;
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
        snackbar.setAction(getString(R.string.session_settings), view -> {
            Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        });
        snackbar.setActionTextColor(getResources().getColor(R.color.white));
        snackbar.show();
    }

    private void setUpViews() {
        mConnectButton.setOnClickListener(v -> mConnectionPresenter.connect());
        mStartButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, WritingActivity.class);
            startActivity(intent);
            finish();
            //TODO: start activity with attention fragment
        });
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
        mDisconnectedRunnable = () -> showDisconnectedState();
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
