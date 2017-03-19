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
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.bluelinelabs.logansquare.LoganSquare;
import com.bluelinelabs.logansquare.annotation.JsonField;
import com.crashlytics.android.Crashlytics;
import com.remnev.verbatoriamini.BuildConfig;
import com.remnev.verbatoriamini.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.callbacks.OnNewIntentCallback;
import com.remnev.verbatoriamini.model.Certificate;
import com.remnev.verbatoriamini.sharedpreferences.SettingsSharedPrefs;
import com.remnev.verbatoriamini.sharedpreferences.SpecialistSharedPrefs;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import io.fabric.sdk.android.Fabric;

public class AuthorityActivity extends AppCompatActivity implements OnNewIntentCallback {

    public static final long WEEK_MILLIS = 1000 * 60 * 60 * 24 * 7;

    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;
    IntentFilter[] mNdefExchangeFilters;

    @Override
    public void onNewIntent(Intent intent) {
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            promptForContent(tagFromIntent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        read = true;

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mNdefExchangeFilters = new IntentFilter[] { tagDetected };
        enableNFC();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(this.getIntent().getAction())) {
            Tag detectedTag = this.getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            promptForContent(detectedTag);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        disableNFC();
    }


    private void disableNFC() {
        if (mAdapter != null) {
            mAdapter.disableForegroundDispatch(this);
        }
    }

    private void enableNFC() {
        if (mAdapter != null) {
            mAdapter.enableForegroundDispatch(this, mPendingIntent, mNdefExchangeFilters, null);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_authority);

        hideSystemUI();

        mAdapter = NfcAdapter.getDefaultAdapter(this);

        if (mAdapter == null) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.imageView), getString(R.string.no_nfc_support) , Snackbar.LENGTH_LONG);
            snackbar.show();
        } else if (!mAdapter.isEnabled()) {
            Snackbar snackbar = Snackbar.make(findViewById(R.id.imageView), getString(R.string.start_nfc) , Snackbar.LENGTH_LONG);
            snackbar.show();
            startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
        }

        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        ndefDetected.addCategory(Intent.CATEGORY_DEFAULT);
        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };

        preCheckAndStart();
    }


    private void preCheckAndStart() {
        final long currentTime = System.currentTimeMillis();
        final long lastCheckTime = SpecialistSharedPrefs.getLastCertificateCheckDate(AuthorityActivity.this);
        Log.e("certificate", "lastCheckTime: " + lastCheckTime);
        if (currentTime - lastCheckTime < WEEK_MILLIS) {
            Certificate certificate = SpecialistSharedPrefs.getCurrentSpecialist(this);
            if (checkDate(certificate)) {
                showExpiryDialog();
                read = true;
                return;
            }

            if (!SettingsSharedPrefs.getIsFirstTime(this)) {
                dialogGetAddress(certificate);
            } else {
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (System.currentTimeMillis() - currentTime < 1000) {}
                        Intent intent = new Intent(AuthorityActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        AuthorityActivity.this.finish();
                        startActivity(intent);
                    }
                });
                thread.start();
            }
        }
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

    public static boolean read = true;

    @Override
    public void promptForContent(Tag msg) {
        if (msg != null && read) {
            read = false;
            String readedText = Helper.readTag(msg, AuthorityActivity.this);
            if (TextUtils.isEmpty(readedText)) {
                showNotCertificate();
                read = true;
                return;
            } else {
                try {
                    Certificate certificate = new Certificate();
                    if (certificate.parseCertificate(readedText)) {
                        Certificate currentCertificate = SpecialistSharedPrefs.getCurrentSpecialist(AuthorityActivity.this);
                        if (currentCertificate == null) {
                            if (checkDate(certificate)) {
                                showExpiryDialog();
                                read = true;
                                return;
                            }
                            SpecialistSharedPrefs.setCurrentSpecialist(AuthorityActivity.this, certificate);
                            startApplication(certificate);
                            return;
                        } else {//cvc is correct //if (specialist.getCvc().equals(SpecialistSharedPrefs.getCurrentSpecialist(AuthorityActivity.this).getCvc())) {
                            if (checkDate(certificate)) {
                                showExpiryDialog();
                                read = true;
                                return;
                            }
                            SpecialistSharedPrefs.setCurrentSpecialist(AuthorityActivity.this, certificate);
                            startApplication(certificate);
                            return;
                        }
//                        } else {
//                            showCvcNotEqual(certificate);
//                            read = true;
//                            return;
//                        }
                    } else {
                        showNotCertificate();
                        read = true;
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    showNotCertificate();
                    read = true;
                    return;
                }
            }

        }
    }

    private boolean checkDate(Certificate certificate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = simpleDateFormat.format(System.currentTimeMillis());
        Log.e("currentDate", "currentDate: " + currentDate);
        try {
            if (simpleDateFormat.parse(certificate.getExpiry()).before(simpleDateFormat.parse(currentDate))) {
                return true;
            } else {
                return false;
            }
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

    private void showNotCertificate() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.tag_not_specialist));
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
                SettingsSharedPrefs.setFirstTime(AuthorityActivity.this);
                SettingsSharedPrefs.setEmailToSend(AuthorityActivity.this, addressEditText.getText().toString());
                SpecialistSharedPrefs.setLastCertificateCheckDate(AuthorityActivity.this, System.currentTimeMillis());
                Snackbar snackbar = Snackbar.make(findViewById(R.id.imageView), (String.format(getString(R.string.authority_success), certificate.getSpecialistName()) + " " + certificate.getExpiry()), Snackbar.LENGTH_LONG);
                snackbar.show();
                final long currentTime = System.currentTimeMillis();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (System.currentTimeMillis() - currentTime < 1000) {}
                        Intent intent = new Intent(AuthorityActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        AuthorityActivity.this.finish();
                        startActivity(intent);
                    }
                });
                thread.start();
            }
        });
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void startApplication(Certificate certificate) {
        if (SpecialistSharedPrefs.getLastCertificateCheckDate(AuthorityActivity.this) == 0) {
            dialogGetAddress(certificate);
            return;
        }
        SpecialistSharedPrefs.setLastCertificateCheckDate(AuthorityActivity.this, System.currentTimeMillis());
        Snackbar snackbar = Snackbar.make(findViewById(R.id.imageView), (String.format(getString(R.string.authority_success), certificate.getSpecialistName()) + " " + certificate.getExpiry()), Snackbar.LENGTH_LONG);
        snackbar.show();
        final long currentTime = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (System.currentTimeMillis() - currentTime < 1000) {}
                Intent intent = new Intent(AuthorityActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                AuthorityActivity.this.finish();
                startActivity(intent);
            }
        });
        thread.start();
    }

    private void startApplicationWithError(Certificate certificate) {
        Snackbar snackbar = Snackbar.make(findViewById(R.id.imageView), (String.format(getString(R.string.authority_some_success), certificate.getSpecialistName()) + " " + certificate.getExpiry()), Snackbar.LENGTH_LONG);
        snackbar.show();
        final long currentTime = System.currentTimeMillis();
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (System.currentTimeMillis() - currentTime < 1000) {}
                Intent intent = new Intent(AuthorityActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                AuthorityActivity.this.finish();
                startActivity(intent);
            }
        });
        thread.start();
    }


}
