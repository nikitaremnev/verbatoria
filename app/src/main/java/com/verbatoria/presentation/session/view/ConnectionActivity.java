package com.verbatoria.presentation.session.view;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.di.session.SessionModule;
import com.verbatoria.presentation.session.presenter.IConnectionPresenter;
import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Экран соединения с BCI
 *
 * @author nikitaremnev
 */
public class ConnectionActivity extends AppCompatActivity implements IConnectionView {

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

    private static int[] sConnectingDrawables = new int[]{
            R.drawable.connecting_bci1,
            R.drawable.connecting_bci2,
            R.drawable.connecting_bci3
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //initialize views
        setContentView(R.layout.activity_connection);
        ButterKnife.bind(this);
        setUpViews();
        setUpNavigation();
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
    public void showConnectingState() {
        mConnectionStatusTextView.setText(getString(R.string.session_connection_in_progress));
    }

    @Override
    public void showConnectedState() {
        mConnectionStatusImageView.setImageResource(R.drawable.connected_bci);
        mConnectionStatusTextView.setText(getString(R.string.session_connected));
        setStartButtonEnabled(true);
    }

    @Override
    public void showDisconnectedState() {
        mConnectionStatusImageView.setImageResource(R.drawable.connect_bci);
        mConnectionStatusTextView.setText(getString(R.string.session_not_connected));
        setStartButtonEnabled(false);
    }

    @Override
    public void showErrorConnectionState() {
        mConnectionStatusImageView.setImageResource(R.drawable.error_bci);
        mConnectionStatusTextView.setText(getString(R.string.session_not_connected));
        setStartButtonEnabled(false);
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
        mStartButton.setOnClickListener(v -> {
            //TODO: start activity with attention fragment
        });
        mConnectButton.setOnClickListener(v -> mConnectionPresenter.connect());
    }

    private void setUpNavigation() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.session_connection));
    }

    private void setStartButtonEnabled(boolean enabled) {
        mStartButton.setEnabled(enabled);
        mConnectButton.setEnabled(!enabled);
    }
}
