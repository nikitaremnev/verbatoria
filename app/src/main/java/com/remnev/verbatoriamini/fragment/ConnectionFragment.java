package com.remnev.verbatoriamini.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.neurosky.connection.ConnectionStates;
import com.remnev.verbatoriamini.NeuroApplicationClass;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.activities.MainActivity;
import com.remnev.verbatoriamini.callbacks.IFragmentsMovingCallback;
import com.remnev.verbatoriamini.callbacks.INeuroInterfaceCallback;

public class ConnectionFragment extends Fragment implements INeuroInterfaceCallback {

    public ImageView mStatusImageView;
    public ImageView mNeuroInterfaceStatusButton;
    public TextView mConnectionTextView;

    public boolean mAllowToConnect;
    public boolean mIsAlive = true;

    private Thread mChangePictureToInitialThread;
    private Thread mChangePictureThread;

    private NeuroApplicationClass mNeuroApplicationClass;

    private IFragmentsMovingCallback mFragmentsMovingCallback;

    private static int[] sConnectingDrawables = new int[] {
            R.drawable.connecting_bci1,
            R.drawable.connecting_bci2,
            R.drawable.connecting_bci3
    };

    public ConnectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof IFragmentsMovingCallback) {
            mFragmentsMovingCallback = (IFragmentsMovingCallback) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connection, container, false);

        initViews(rootView);
        setUpApplicationClass(rootView);
        setOnClickListeners();

        mAllowToConnect = true;

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkStateAndUpdate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mFragmentsMovingCallback = null;
    }

    @Override
    public void onNeuroInterfaceStateChanged(int connectionStates) {
        switch (connectionStates) {
            case ConnectionStates.STATE_CONNECTING:
                mAllowToConnect = false;
                startChangePictureThread();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mConnectionTextView.setText(getString(R.string.connection_in_progress));
                    }
                });
                break;
            case ConnectionStates.STATE_CONNECTED:
                mAllowToConnect = false;
                mIsAlive = false;
                mNeuroApplicationClass.startBCI();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stateConnected();
                    }
                });
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (mFragmentsMovingCallback != null) {
                                    mFragmentsMovingCallback.moveToAttentionFragment();
                                } else {
                                    if (getActivity() != null && getActivity() instanceof IFragmentsMovingCallback) {
                                        mFragmentsMovingCallback = (IFragmentsMovingCallback) getActivity();
                                        mFragmentsMovingCallback.moveToAttentionFragment();
                                    }
                                }
                            }
                        });
                    }
                }).start();
                break;
            case ConnectionStates.STATE_WORKING:
            case ConnectionStates.STATE_COMPLETE:
                break;
            case NeuroApplicationClass.BLUETOOTH_NOT_STARTED:
                mAllowToConnect = true;
                mIsAlive = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        checkStateAndUpdate();
                    }
                });
                break;
            case ConnectionStates.STATE_DISCONNECTED:
            case ConnectionStates.STATE_GET_DATA_TIME_OUT:
            case ConnectionStates.STATE_ERROR:
            case ConnectionStates.STATE_FAILED:
                mAllowToConnect = true;
                mIsAlive = false;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startNewThreadChangeLogo();
                        mNeuroInterfaceStatusButton.setImageResource(R.drawable.error_bci);
                        mConnectionTextView.setText(getString(R.string.connection_no));
                    }
                });
                break;
        }
    }

    @Override
    public void onNeuroDataReceived(int code, int attention) {

    }

    private void initViews(View rootView) {
        mStatusImageView = (ImageView) rootView.findViewById(R.id.attention_status);
        mConnectionTextView = (TextView) rootView.findViewById(R.id.connection_title);
        mNeuroInterfaceStatusButton = (ImageView) rootView.findViewById(R.id.bci);
    }

    private void setOnClickListeners() {
        mNeuroInterfaceStatusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAllowToConnect) {
                    mNeuroApplicationClass.connectToBluetooth();
                }
            }
        });
    }

    private void setUpApplicationClass(View rootView) {
        mNeuroApplicationClass = (NeuroApplicationClass) getActivity().getApplicationContext();
        mNeuroApplicationClass.setOnBCIConnectionCallback(this);
        mNeuroApplicationClass.setContext(getActivity());
        mNeuroApplicationClass.setRootView(rootView);
    }

    private void runOnUiThread(Runnable runnable) {
        if (getActivity() != null) {
            getActivity().runOnUiThread(runnable);
        }
    }

    private void stateConnected() {
        mConnectionTextView.setText(getString(R.string.connection_done));
        mNeuroInterfaceStatusButton.setImageResource(R.drawable.connected_bci);
    }

    private void stateDisconnected() {
        mConnectionTextView.setText(getString(R.string.connection_no));
        mNeuroInterfaceStatusButton.setImageResource(R.drawable.connect_bci);
    }

    private void checkStateAndUpdate() {
        if (NeuroApplicationClass.isConnected()) {
            stateConnected();
        } else {
            stateDisconnected();
        }
    }

    private void startNewThreadChangeLogo() {
        if (mChangePictureToInitialThread == null || !mChangePictureToInitialThread.isAlive()) {
            mChangePictureToInitialThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mNeuroInterfaceStatusButton != null) {
                                mNeuroInterfaceStatusButton.setImageResource(R.drawable.connect_bci);
                            }
                        }
                    });
                }
            });
            mChangePictureToInitialThread.start();
        }
    }

    private void startChangePictureThread() {
        if (mChangePictureThread == null || !mChangePictureThread.isAlive()) {
            mIsAlive = true;
            mChangePictureThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int currentImage = 0;
                    while (mIsAlive) {
                        final int currentImageDiv = currentImage % 3;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mNeuroInterfaceStatusButton.setImageResource(sConnectingDrawables[currentImageDiv]);
                            }
                        });
                        currentImage ++;
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            mChangePictureThread.start();
        }
    }

}

