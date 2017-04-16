package com.remnev.verbatoriamini.activities;

import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.adapters.CertificatesAdapter;
import com.remnev.verbatoriamini.callbacks.INFCCallback;
import com.remnev.verbatoriamini.databases.CertificatesDatabase;
import com.remnev.verbatoriamini.model.Certificate;
import com.remnev.verbatoriamini.sharedpreferences.SettingsSharedPrefs;
import com.remnev.verbatoriamini.sharedpreferences.SpecialistSharedPrefs;
import com.remnev.verbatoriamini.util.Helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class CheckCertificateActivity extends AppCompatActivity {

    public static final long WEEK_MILLIS = 1000 * 60 * 60 * 24 * 7;

    private List<Certificate> mCertificateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_check_certificate);

        mCertificateList = CertificatesDatabase.readAllCertificates();

        if (mCertificateList.size() == 1) {
            SpecialistSharedPrefs.setCurrentSpecialist(CheckCertificateActivity.this, mCertificateList.get(0));
            findViewById(R.id.progressBar).setVisibility(View.VISIBLE);
            findViewById(R.id.textView2).setVisibility(View.GONE);
            preCheckAndStart();
        } else {
            final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
            final TextView textView = (TextView) findViewById(R.id.textView2);
            CertificatesAdapter certificatesAdapter = new CertificatesAdapter(this, mCertificateList, new CertificatesAdapter.ICertificateClick() {
                @Override
                public void onCertificateClick(int position) {
                    recyclerView.setVisibility(View.GONE);
                    textView.setVisibility(View.GONE);
                    findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

                    SpecialistSharedPrefs.setCurrentSpecialist(CheckCertificateActivity.this, mCertificateList.get(position));
                    preCheckAndStart();
                }
            });
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(certificatesAdapter);
        }
        hideSystemUI();
    }


    private void preCheckAndStart() {
        long currentTime = System.currentTimeMillis();
        long lastCheckTime = SpecialistSharedPrefs.getLastCertificateCheckDate(CheckCertificateActivity.this);
        if (currentTime - lastCheckTime < WEEK_MILLIS) {
            Certificate certificate = SpecialistSharedPrefs.getCurrentSpecialist(this);
            if (checkDate(certificate)) {
                showExpiryDialog();
            } else if (!SettingsSharedPrefs.getIsFirstTime(this)) {
                dialogGetAddress(certificate);
            } else {
                startApplication(certificate);
            }
        }
    }

    private void hideSystemUI() {
        if(Build.VERSION.SDK_INT < 19) {
            View decorView = this.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.GONE);
        } else {
            //for lower api versions.
            View decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private boolean checkDate(Certificate certificate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = simpleDateFormat.format(System.currentTimeMillis());
        try {
            return simpleDateFormat.parse(certificate.getExpiry()).before(simpleDateFormat.parse(currentDate));
        } catch (ParseException e) {
            return false;
        }
    }

    private void showExpiryDialog() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.authority_expiry));
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog.show();
    }

    private void dialogGetAddress(final Certificate certificate) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View layoutView = getLayoutInflater().inflate(R.layout.dialog_export_address, null);
        final EditText addressEditText = (EditText) layoutView.findViewById(R.id.email_address);
        addressEditText.setText(SettingsSharedPrefs.getEmailToSend(this));
        alertDialog.setTitle(getString(R.string.email_dialog_title));
        alertDialog.setView(layoutView);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.save), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SettingsSharedPrefs.setFirstTime(CheckCertificateActivity.this);
                SettingsSharedPrefs.setEmailToSend(CheckCertificateActivity.this, addressEditText.getText().toString());
                SpecialistSharedPrefs.setLastCertificateCheckDate(CheckCertificateActivity.this, System.currentTimeMillis());
                Helper.showSnackBar(findViewById(R.id.imageView), (String.format(getString(R.string.authority_success), certificate.getSpecialistName()) + " " + certificate.getExpiry()));
                startMainActivity();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void startApplication(Certificate certificate) {
        if (SpecialistSharedPrefs.getLastCertificateCheckDate(CheckCertificateActivity.this) == 0) {
            dialogGetAddress(certificate);
            return;
        }
        SpecialistSharedPrefs.setLastCertificateCheckDate(CheckCertificateActivity.this, System.currentTimeMillis());
        Helper.showSnackBar(findViewById(R.id.imageView), (String.format(getString(R.string.authority_success), certificate.getSpecialistName()) + " " + certificate.getExpiry()));
        startMainActivity();
    }

    private void startMainActivity() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }                Intent intent = new Intent(CheckCertificateActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                CheckCertificateActivity.this.finish();
                startActivity(intent);
            }
        });
        thread.start();
    }
}
