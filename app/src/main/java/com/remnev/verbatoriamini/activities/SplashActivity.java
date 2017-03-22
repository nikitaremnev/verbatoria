package com.remnev.verbatoriamini.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.crashlytics.android.Crashlytics;
import com.remnev.verbatoriamini.BuildConfig;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.sharedpreferences.SettingsSharedPrefs;

import java.io.File;

import io.fabric.sdk.android.Fabric;

public class SplashActivity extends AppCompatActivity {

    public final static String FILES_DIR = "Verbatoria Mini";
    public final static String MUSIC_FILES_DIR = "Verbatoria Mini" + File.separator + "Music";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();

        if (!SettingsSharedPrefs.getUpdateFlag1(this)) {
            StatisticsDatabase.removeAll(this);
            SettingsSharedPrefs.setUpdateFlag1(this);
        }

        final long currentTime = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (System.currentTimeMillis() - currentTime < 1000) {}
//                if (!BuildConfig.FLAVOR.contains("master")) {
                    Intent intent = new Intent(SplashActivity.this, AuthorityActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    SplashActivity.this.finish();
                    startActivity(intent);
//                } else {
//                    Intent intent = new Intent(SplashActivity.this, NavigationDrawerActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                    SplashActivity.this.finish();
//                    startActivity(intent);
//                }
            }
        });
        thread.start();


        hideSystemUI();
    }

    private void hideSystemUI() {
        if(Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else {
            //for lower api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

}
