package com.remnev.verbatoriamini.fragment;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.design.internal.BottomNavigationItemView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.neurosky.thinkgear.TGDevice;
import com.remnev.verbatoriamini.ApplicationClass;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.activities.MainActivity;
import com.remnev.verbatoriamini.activities.NavigationDrawerActivity;
import com.remnev.verbatoriamini.callbacks.OnBCIConnectionCallback;

/**
 * A simple {@link Fragment} subclass.
 */
public class ConnectionFragment extends Fragment implements OnBCIConnectionCallback {

    public ImageView statusImageView;
    public ImageView bciButton;
    public TextView connectionTextView;
    ApplicationClass applicationClass;
    public boolean allowToConnect;

    public ConnectionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (ApplicationClass.connected) {
            bciButton.setImageResource(R.drawable.connected_bci);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_connection, container, false);
        statusImageView = (ImageView) rootView.findViewById(R.id.attention_status);
        connectionTextView = (TextView) rootView.findViewById(R.id.connection_title);
        applicationClass = (ApplicationClass) getActivity().getApplicationContext();
        applicationClass.setOnBCIConnectionCallback(this);
        applicationClass.setMContext(getActivity());
        applicationClass.setRootView(rootView);
        bciButton = (ImageView) rootView.findViewById(R.id.bci);
        setOnClickListeners();
        allowToConnect = true;

        if (ApplicationClass.connected) {
            connectionTextView.setText(getString(R.string.connection_done));
        } else {
            connectionTextView.setText(getString(R.string.connection_no));
        }

        return rootView;
    }

    public void setOnClickListeners() {
        bciButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (allowToConnect) {
                    connectToDevice();
                }
            }
        });
    }

    private void connectToDevice() {
        bciButton.setImageResource(R.drawable.connecting_bci1);
        applicationClass.connectToBluetooth();
    }

    @Override
    public void onMessageReceived(Message msg) {
        Log.e("event", "get message");
        switch( msg.what ) {
            case TGDevice.MSG_STATE_CHANGE:
                switch (msg.arg1) {
                    case TGDevice.STATE_IDLE:
                        allowToConnect = true;
                        Log.e("was", "in STATE_IDLE");
                        isAlive = false;
                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.playFloatingActionButton.hide();
                            mainActivity.pauseFloatingActionButton.hide();
                        }
                        connectionTextView.setText(getString(R.string.connection_no));
                        break;
                    case TGDevice.STATE_ERR_BT_OFF:
                        allowToConnect = true;
                        Log.e("was", "in STATE_ERR_BT_OFF");
                        isAlive = false;
                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.playFloatingActionButton.hide();
                            mainActivity.pauseFloatingActionButton.hide();
                        }
                        connectionTextView.setText(getString(R.string.connection_no));
                        break;
                    case TGDevice.STATE_CONNECTING:
                        allowToConnect = false;
                        Log.e("was", "in connecting");
                        startChangePictureThread();
                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.playFloatingActionButton.hide();
                            mainActivity.pauseFloatingActionButton.hide();
                        }
                        connectionTextView.setText(getString(R.string.connection_in_progress));
                        break;
                    case TGDevice.STATE_ERR_NO_DEVICE:
                        allowToConnect = true;
                        Log.e("was", "in STATE_ERR_NO_DEVICE");
                        isAlive = false;
                        bciButton.setImageResource(R.drawable.error_bci);
                        startNewThreadChangeLogo();
                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.playFloatingActionButton.hide();
                            mainActivity.pauseFloatingActionButton.hide();
                        }
                        connectionTextView.setText(getString(R.string.connection_no));
                        break;
                    case TGDevice.STATE_NOT_FOUND:
                        allowToConnect = true;
                        Log.e("was", "in STATE_NOT_FOUND");
                        isAlive = false;
                        bciButton.setImageResource(R.drawable.error_bci);
                        startNewThreadChangeLogo();
                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.playFloatingActionButton.hide();
                            mainActivity.pauseFloatingActionButton.hide();
                        }
                        connectionTextView.setText(getString(R.string.connection_no));
                        break;
                    case TGDevice.STATE_CONNECTED:
                        applicationClass.startBCI();
                        allowToConnect = false;
                        Log.e("was", "in STATE_CONNECTED");
                        isAlive = false;
                        bciButton.setImageResource(R.drawable.connected_bci);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                long time = System.currentTimeMillis();
                                while (System.currentTimeMillis() - time < 1000) {
                                }
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            if (getActivity().getSupportFragmentManager().getFragments().get(0) instanceof ConnectionFragment) {
                                                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                                                RealTimeAttentionFragment realTimeAttentionFragment = new RealTimeAttentionFragment();
                                                if (getActivity() instanceof NavigationDrawerActivity) {
                                                    NavigationDrawerActivity navigationDrawerActivity = (NavigationDrawerActivity) getActivity();
                                                    navigationDrawerActivity.callback = realTimeAttentionFragment;
                                                    navigationDrawerActivity.navigationView.getMenu().findItem(R.id.drawer_item_bci_stop).setTitle(getString(R.string.stop_writing));
                                                    navigationDrawerActivity.setTitle(getString(R.string.nav_drawer_raw));
                                                }
                                                if (getActivity() instanceof MainActivity) {
                                                    MainActivity mainActivity = (MainActivity) getActivity();
                                                    mainActivity.callback = realTimeAttentionFragment;
                                                    mainActivity.pendingFragment = realTimeAttentionFragment;
                                                    mainActivity.bottomNavigationView.getMenu().getItem(1).setChecked(true);
                                                    mainActivity.titleTextView.setText(getString(R.string.ATTENTION_BOTTOM_NAVIGATION_BAR));
                                                    if (ApplicationClass.getStateOfWriting()) {
                                                        mainActivity.pauseFloatingActionButton.show();
                                                        mainActivity.playFloatingActionButton.hide();
                                                    } else {
                                                        mainActivity.playFloatingActionButton.show();
                                                        mainActivity.pauseFloatingActionButton.hide();
                                                    }

                                                }

                                                fragmentManager.beginTransaction()
                                                        .replace(R.id.container, realTimeAttentionFragment)
                                                        .commit();


                                            }
                                        } catch (Exception ex) {
                                            ex.printStackTrace();
                                        }
                                    }
                                });

                            }
                        }).start();
                        connectionTextView.setText(getString(R.string.connection_done));
                        break;
                    case TGDevice.STATE_DISCONNECTED:
                        allowToConnect = true;
                        Log.e("was", "in STATE_DISCONNECTED");
                        isAlive = false;
                        bciButton.setImageResource(R.drawable.error_bci);
                        startNewThreadChangeLogo();
                        if (getActivity() instanceof MainActivity) {
                            MainActivity mainActivity = (MainActivity) getActivity();
                            mainActivity.playFloatingActionButton.hide();
                            mainActivity.pauseFloatingActionButton.hide();
                        }
                        connectionTextView.setText(getString(R.string.connection_no));
                        break;
                    default:
                        break;
                }
                break;
            case TGDevice.MSG_ATTENTION:
                allowToConnect = false;
                Log.e("was", "in MSG_ATTENTION");
                isAlive = false;
                bciButton.setImageResource(R.drawable.connected_bci);
                animateStatusChanged(msg.arg1);
                connectionTextView.setText(getString(R.string.connection_done));
                break;
            case ApplicationClass.BLUETOOTH_NOT_STARTED:
                Log.e("was", "in BLUETOOTH_NOT_STARTED");
                allowToConnect = true;
                isAlive = false;
                bciButton.setImageResource(R.drawable.connect_bci);
                connectionTextView.setText(getString(R.string.connection_no));
                break;
            default:
                connectionTextView.setText(getString(R.string.connection_done));
                Log.e("was", "in default");
                break;
        }
    }

    Drawable firstDrawable;
    Drawable secondDrawable;

    @Override
    public void animateStatusChanged(int value) {
        if (secondDrawable != null) {
            firstDrawable = secondDrawable;
        }
        if (value < 20) {
            secondDrawable = getResources().getDrawable(R.drawable.status_lowest);
        } else if (value < 40) {
            secondDrawable = getResources().getDrawable(R.drawable.status_low);
        } else if (value < 60) {
            secondDrawable = getResources().getDrawable(R.drawable.status_middle);
        } else if (value < 80) {
            secondDrawable = getResources().getDrawable(R.drawable.status_high);
        } else if (value < 100) {
            secondDrawable = getResources().getDrawable(R.drawable.status_highest);
        }
        if (firstDrawable == null) {
            firstDrawable = getResources().getDrawable(R.drawable.status_middle);
        }
        TransitionDrawable transitionDrawable = new TransitionDrawable(new Drawable[] {
                firstDrawable,
                secondDrawable,
        });
        statusImageView.setImageDrawable(transitionDrawable);
        transitionDrawable.startTransition(700);
        //Helper.animateStatusChange(firstDrawable, secondDrawable, BCIConnectionActivity.this, statusImageView, value);
    }

    private long startTime;

    private Thread thread;

    private Thread changePictureThread;

    private void startNewThreadChangeLogo() {
        if (thread == null || !thread.isAlive()) {
            startTime = System.currentTimeMillis();
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (System.currentTimeMillis() - startTime < 2000) {
                    }
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (bciButton != null) {
                                    bciButton.setImageResource(R.drawable.connect_bci);
                                }
                            }
                        });
                    }
                }
            });
            thread.start();
        }
    }

    public boolean isAlive = true;

    private void startChangePictureThread() {
        if (changePictureThread == null || !changePictureThread.isAlive()) {
            isAlive = true;
            changePictureThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    int currentImage = 0;
                    while (isAlive) {
                        final int currentImageDiv = currentImage % 3;
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                switch (currentImageDiv) {
                                    case 0:
                                        bciButton.setImageResource(R.drawable.connecting_bci1);
                                        break;
                                    case 1:
                                        bciButton.setImageResource(R.drawable.connecting_bci2);
                                        break;
                                    case 2:
                                        bciButton.setImageResource(R.drawable.connecting_bci3);
                                        break;
                                }
                            }
                        });
                        currentImage ++;
                        long time = System.currentTimeMillis();
                        while (System.currentTimeMillis() - time < 500) {
                        }
                    }
                }
            });
            changePictureThread.start();
        }
    }

}

