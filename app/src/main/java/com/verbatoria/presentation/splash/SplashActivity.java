package com.verbatoria.presentation.splash;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import com.crashlytics.android.Crashlytics;
import com.remnev.verbatoriamini.R;
import com.verbatoria.presentation.login.view.LoginActivity;
import com.verbatoria.utils.FileUtils;
import io.fabric.sdk.android.Fabric;

/**
 * Splash-экран
 *
 * @author nikitaremnev
 */
public class SplashActivity extends AppCompatActivity {

    private final static int START_LOGIN_ACTIVITY_DELAY = 2000;
    private static final int REQUEST_PERMISSION_CODE = 2444;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Fabric.with(this, new Crashlytics());

        FileUtils.createApplicationDirectory();
        askPermissions();
    }

    private void setUpHandler() {
        Runnable loginActivityRunnable = () -> startLoginActivity();
        new Handler().postDelayed(loginActivityRunnable, START_LOGIN_ACTIVITY_DELAY);
    }

    private void startLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void askPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CODE);
        } else {
            setUpHandler();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    setUpHandler();
                } else {
                    askPermissions();
                }
            }
        }
    }
}