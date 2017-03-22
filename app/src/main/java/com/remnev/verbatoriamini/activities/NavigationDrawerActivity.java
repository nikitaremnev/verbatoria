package com.remnev.verbatoriamini.activities;

import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.Pair;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bluelinelabs.logansquare.LoganSquare;
import com.remnev.verbatoriamini.ApplicationClass;
import com.remnev.verbatoriamini.BuildConfig;
import com.remnev.verbatoriamini.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.callbacks.OnNewIntentCallback;
import com.remnev.verbatoriamini.databases.BCIDatabase;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.fragment.AuthorityFragment;
import com.remnev.verbatoriamini.fragment.ConnectionFragment;
import com.remnev.verbatoriamini.fragment.RealTimeAttentionFragment;
import com.remnev.verbatoriamini.fragment.WriteChildCardFragment;
import com.remnev.verbatoriamini.fragment.WriteCertificateFragment;
import com.remnev.verbatoriamini.fragment.WriteCodesFragment;
import com.remnev.verbatoriamini.model.ActionID;
import com.remnev.verbatoriamini.model.Child;
import com.remnev.verbatoriamini.model.ExcelBCI;
import com.remnev.verbatoriamini.model.ExcelColumnID;
import com.remnev.verbatoriamini.model.ExcelEvent;
import com.remnev.verbatoriamini.model.RezhimID;
import com.remnev.verbatoriamini.sharedpreferences.SettingsSharedPrefs;
import com.remnev.verbatoriamini.sharedpreferences.SpecialistSharedPrefs;
import com.remnev.verbatoriamini.util.BCIExcelWriter;
import com.remnev.verbatoriamini.util.ExcelEventWriter;
import com.remnev.verbatoriamini.util.ExcelEventsComparator;
import com.remnev.verbatoriamini.util.ExcelExportComparator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Random;

@Deprecated
public class NavigationDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnNewIntentCallback {

    private Fragment pendingFragment;
    public static NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private int selectedIndex;
    private Context mContext;

    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;
    IntentFilter[] mNdefExchangeFilters;
    public static OnNewIntentCallback callback;

    @Override
    public void onNewIntent(Intent intent) {
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) && callback != null) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            promptForContent(tagFromIntent);
            callback.promptForContent(tagFromIntent);
        } else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            promptForContent(tagFromIntent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mNdefExchangeFilters = new IntentFilter[] { tagDetected };
        enableNFC();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(this.getIntent().getAction()) && callback != null) {
            Tag detectedTag = this.getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            promptForContent(detectedTag);
            callback.promptForContent(detectedTag);
        } else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(this.getIntent().getAction())) {
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

    private boolean clickedFlag = false;

    private void setUpToolbarButton() {
        ImageView imageView = (ImageView) findViewById(R.id.exit);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                System.exit(0);

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContext = this;

        initViews();
        setUpViews();
        setUpDrawerLayout();

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        ndefDetected.addCategory(Intent.CATEGORY_DEFAULT);
        mNdefExchangeFilters = new IntentFilter[] { ndefDetected };

        if (BuildConfig.FLAVOR.contains("master")) {
            pendingFragment = new WriteCertificateFragment();
            callback = (OnNewIntentCallback) pendingFragment;
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, pendingFragment)
                    .commit();
            setTitle(getString(R.string.nav_drawer_item_write_specialist));
        } else {
            pendingFragment = new ConnectionFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, pendingFragment)
                    .commit();
            setTitle(getString(R.string.nav_drawer_item_main));
        }

        File cacheDir = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        setUpToolbarButton();
    }

    private void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setUpViews() {
        navigationView.setNavigationItemSelectedListener(this);

        setSupportActionBar(toolbar);

        if (BuildConfig.FLAVOR.contains("master")) {
            navigationView.getMenu().findItem(R.id.drawer_item_write_specialist).setVisible(true);
//            navigationView.getMenu().findItem(R.id.drawer_item_authority).setVisible(false);
        } else {
            navigationView.getMenu().findItem(R.id.drawer_item_write_specialist).setVisible(false);
        }

        navigationView.getMenu().findItem(R.id.drawer_item_export_address).setVisible(false);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.drawer_item_authority:
//                pendingFragment = new AuthorityFragment();
//                callback = (OnNewIntentCallback) pendingFragment;
//                selectedIndex = -1;
//                clickedFlag = true;
//                break;
            case R.id.drawer_item_main:
                pendingFragment = new ConnectionFragment();
                selectedIndex = 0;
                clickedFlag = true;
                break;
            case R.id.drawer_item_bci_raw:
                pendingFragment = new RealTimeAttentionFragment();
                callback = (OnNewIntentCallback) pendingFragment;
                selectedIndex = 1;
                clickedFlag = true;
                break;
            case R.id.drawer_item_bci_stop:
                selectedIndex = 2;
                clickedFlag = true;
                break;
            case R.id.drawer_item_export_excel:
                selectedIndex = 3;
                clickedFlag = true;
                break;
            case R.id.drawer_item_export_address:
                selectedIndex = 4;
                clickedFlag = true;
                break;
            case R.id.drawer_item_write_child:
                pendingFragment = new WriteChildCardFragment();
                callback = (OnNewIntentCallback) pendingFragment;
                selectedIndex = 5;
                clickedFlag = true;
                break;
            case R.id.drawer_item_write_specialist:
                pendingFragment = new WriteCertificateFragment();
                callback = (OnNewIntentCallback) pendingFragment;
                selectedIndex = 6;
                clickedFlag = true;
                break;
            case R.id.drawer_item_codes:
                pendingFragment = new WriteCodesFragment();
                callback = (OnNewIntentCallback) pendingFragment;
                selectedIndex = 7;
                clickedFlag = true;
                break;
        }
        item.setChecked(true);
        if (selectedIndex < 2 || selectedIndex >= 5) {
            setTitle(item.getTitle());
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUpDrawerLayout() {
        final ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!clickedFlag) {
                    return;
                }
                clickedFlag = false;
                if (pendingFragment != null && (selectedIndex < 2 || selectedIndex >= 5)) {
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, pendingFragment)
                            .commit();
                } else if (selectedIndex == 2) {
                    changeStateOfWriting();
                } else if (selectedIndex == 3) {
                    exportToExcel(false);
                } else {
                    final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                    View layoutView = getLayoutInflater().inflate(R.layout.dialog_export_address, null);
                    final EditText addressEditText = (EditText) layoutView.findViewById(R.id.email_address);
                    addressEditText.setText(SettingsSharedPrefs.getEmailToSend(mContext));
                    alertDialog.setView(layoutView);
                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.save), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            SettingsSharedPrefs.setEmailToSend(mContext, addressEditText.getText().toString());
                            Helper.snackBar(toolbar, getString(R.string.address_saved));
                        }
                    });
                    alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    alertDialog.show();
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };

        drawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
        drawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void cleanDatabase(final File fromFileStatistics, final File toFileStatistics,
                               final File fromFileBCI, final File toFileBCI, final String path, final String reportID) {
//        if (BuildConfig.FLAVOR.contains("master")) {
//            AlertDialog.Builder askToDelete = new AlertDialog.Builder(mContext);
//            askToDelete.setMessage(getString(R.string.master_cleaned));
//            askToDelete.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    moveFile(fromFileStatistics.getAbsolutePath(), toFileStatistics.getAbsolutePath(), true);
//                    moveFile(fromFileBCI.getAbsolutePath(), toFileBCI.getAbsolutePath(), true);
//                    share(path, reportID);
//                }
//            });
//            askToDelete.setCancelable(false);
//            askToDelete.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialogInterface) {
////                moveFile(fromFileStatistics.getAbsolutePath(), toFileStatistics.getAbsolutePath(), false);
////                moveFile(fromFileBCI.getAbsolutePath(), toFileBCI.getAbsolutePath(), false);
//                    //share("file://" + directory.getAbsolutePath() + File.separator + fileName, reportID);
//
//                }
//            });
//            askToDelete.show();
////        } else {
//            AlertDialog.Builder askToDelete = new AlertDialog.Builder(mContext);
//            askToDelete.setMessage(getString(R.string.clean_database_question));
//            askToDelete.setPositiveButton(getString(R.string.clean_database), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
////                                                    moveFile(fromFileStatistics.getAbsolutePath(), toFileStatistics.getAbsolutePath(), true);
////                                                    moveFile(fromFileBCI.getAbsolutePath(), toFileBCI.getAbsolutePath(), true);
////                                                    share("file://" + directory.getAbsolutePath() + File.separator + fileName);
//                    moveFile(fromFileStatistics.getAbsolutePath(), toFileStatistics.getAbsolutePath(), true);
//                    moveFile(fromFileBCI.getAbsolutePath(), toFileBCI.getAbsolutePath(), true);
//                }
//            });
//            askToDelete.setNegativeButton(getString(R.string.continue_writing), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    moveFile(fromFileStatistics.getAbsolutePath(), toFileStatistics.getAbsolutePath(), false);
//                    moveFile(fromFileBCI.getAbsolutePath(), toFileBCI.getAbsolutePath(), false);
////                                                    moveFile(fromFileStatistics.getAbsolutePath(), toFileStatistics.getAbsolutePath(), false);
////                                                    moveFile(fromFileBCI.getAbsolutePath(), toFileBCI.getAbsolutePath(), false);
//                }
//            });
//            askToDelete.setCancelable(false);
//            askToDelete.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                @Override
//                public void onDismiss(DialogInterface dialogInterface) {
////                moveFile(fromFileStatistics.getAbsolutePath(), toFileStatistics.getAbsolutePath(), false);
////                moveFile(fromFileBCI.getAbsolutePath(), toFileBCI.getAbsolutePath(), false);
//                    //share("file://" + directory.getAbsolutePath() + File.separator + fileName, reportID);
//                    share(path, reportID);
//                }
//            });
//            askToDelete.show();
//        }
    }

    private void moveFile(String inputPath, String outputPath, boolean delete) {
        InputStream in = null;
        OutputStream out = null;
        try {
            File dir = new File(outputPath);
            if (!dir.exists()) {
                dir.createNewFile();
            }
            in = new FileInputStream(inputPath);
            out = new FileOutputStream(outputPath);

            byte[] buffer = new byte[1024];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
            in.close();
            // write the output file
            out.flush();
            out.close();
            // delete the original file
            if (delete) {
                BCIDatabase.removeAll(mContext);
            }
        }
        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private void exportToExcel(final boolean stopBCI) {
        final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(mContext).create();
        View layoutView = getLayoutInflater().inflate(R.layout.dialog_export_user, null);
//        final EditText namePatient = (EditText) layoutView.findViewById(R.id.patient_name);
//                    final EditText nameVerbatolog = (EditText) layoutView.findViewById(R.id.verbatolog_name);
        final EditText randomLabel = (EditText) layoutView.findViewById(R.id.random_label);
        randomLabel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String result = editable.toString().replaceAll(" ", "");
                if (!editable.toString().equals(result)) {
                    randomLabel.setText(result);
                    randomLabel.setSelection(result.length());
                    // alert the user
                }
            }
        });

        final Spinner ageSpinner = (Spinner) layoutView.findViewById(R.id.choose_years);
        final Calendar calendar = Calendar.getInstance();

        alertDialog.setView(layoutView);
        alertDialog.setCancelable(false);
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //check correctness
//                if (TextUtils.isEmpty(namePatient.getText().toString())) {
//                    Helper.snackBar(toolbar, getString(R.string.no_name));
//                    return;
//                }
//                            if (TextUtils.isEmpty(nameVerbatolog.getText().toString())) {
//                                Helper.snackBar(toolbar, getString(R.string.no_verbatolog_name));
//                                return;
//                            }
                if (TextUtils.isEmpty(randomLabel.getText().toString())) {
                    Helper.snackBar(toolbar, getString(R.string.no_random_label));
                    return;
                }
//                if (chooseDate.getText().toString().equals(getString(R.string.date_of_birth))) {
//                    Helper.snackBar(toolbar, getString(R.string.no_date_chosen));
//                    return;
//                }
                dialogInterface.dismiss();
                final File directory = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "Export" + File.separator);
                if (!directory.exists()) {
                    if (!directory.mkdirs()) {
                        Helper.snackBar(toolbar, getString(R.string.cannot_create_directory));
                    }
                }
                if (directory.exists()) {
                    final ProgressDialog progressDialog = new ProgressDialog(mContext);
                    progressDialog.setMessage(getString(R.string.generate_xls_file));
//                    progressDialog.setMessage(getString(R.string.generating_xls_message));
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
                            String rebenokID = "Возраст: " + (String) ageSpinner.getSelectedItem();
                            final String fileName = "Возраст: " + (String) ageSpinner.getSelectedItem()
                                    + "_" + sdf.format(calendar.getTimeInMillis()) + "_" + randomLabel.getText().toString() + ".xls";
                            File excelFile = new File(directory.getAbsolutePath() + File.separator + fileName);
                            if (excelFile.exists()) {
                                excelFile.delete();
                            }
                            final String reportID = randomLabel.getText().toString();
                            final String specialist = SpecialistSharedPrefs.getCurrentSpecialist(NavigationDrawerActivity.this).getSpecialistName();
                            if (saveExcelFile(mContext, excelFile, reportID, rebenokID, specialist)) {
                                Helper.snackBar(toolbar, getString(R.string.generate_xls_file_success));
                                if (!ApplicationClass.getStateOfWriting()) {
                                    Helper.snackBar(toolbar, getString(R.string.bci_notify));
                                }
                            } else {
                                Helper.snackBar(toolbar, getString(R.string.generate_xls_file_fail));
                            }
                            final File fromFileStatistics = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "statistics.db");
                            final File toFileStatistics = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "Export" + File.separator + "statistics_" + "test" + "_" + sdf.format(calendar.getTimeInMillis()) + ".db");
                            final File fromFileBCI = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "bci.db");
                            final File toFileBCI = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "Export" + File.separator + "bci_" + "test" + "_" + sdf.format(calendar.getTimeInMillis()) + ".db");
                            if (toFileStatistics.exists()) {
                                toFileStatistics.delete();
                            }
                            if (toFileBCI.exists()) {
                                toFileBCI.delete();
                            }
                            progressDialog.dismiss();
                            NavigationDrawerActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    final AlertDialog alertDialog = new AlertDialog.Builder(mContext).create();
                                    alertDialog.setMessage(getString(R.string.generating_xls_message));
                                    alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            if (stopBCI) {
                                                ApplicationClass.changeStateOfWriting();
                                                Helper.snackBar(toolbar, getString(R.string.bci_stopped));
                                                StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.RECORD_END_ID, RezhimID.ANOTHER_MODE, -1, -1);
                                                navigationView.getMenu().findItem(R.id.drawer_item_bci_stop).setTitle("Возобновить запись");

                                                moveFile(fromFileStatistics.getAbsolutePath(), toFileStatistics.getAbsolutePath(), true);
                                                moveFile(fromFileBCI.getAbsolutePath(), toFileBCI.getAbsolutePath(), true);
                                                share("file://" + directory.getAbsolutePath() + File.separator + fileName, reportID);
                                            }
                                        }
                                    });
                                    alertDialog.setCancelable(false);
//                                    alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                                        @Override
//                                        public void onDismiss(DialogInterface dialogInterface) {
//                                            cleanDatabase(fromFileStatistics, toFileStatistics, fromFileBCI, toFileBCI,
//                                                    "file://" + directory.getAbsolutePath() + File.separator + fileName, reportID);
//
//                                        }
//                                    });
                                    alertDialog.show();
                                }
                            });

                        }
                    }).start();

                }
            }
        });
        alertDialog.show();
    }

    private void changeStateOfWriting() {
        if (!ApplicationClass.getStateOfWriting()) {
            ApplicationClass.changeStateOfWriting();
            Helper.snackBar(toolbar, getString(R.string.bci_resumed));
            StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.RECORD_START_ID, RezhimID.ANOTHER_MODE, -1, -1);
            navigationView.getMenu().findItem(R.id.drawer_item_bci_stop).setTitle(getString(R.string.stop_writing));
            return;
        }
        stopBCIDialog();

//        if (currentState) {
//            Helper.snackBar(toolbar, getString(R.string.bci_resumed));
//            StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.RECORD_START_ID, RezhimID.ANOTHER_MODE, -1, -1);
//            navigationView.getMenu().findItem(R.id.drawer_item_bci_stop).setTitle("Остановить запись");
//        } else {
//
//        }
    }

    private void stopBCIDialog() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
        dialog.setMessage(getString(R.string.send_immediately));
        dialog.setPositiveButton(getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                exportToExcel(true);
            }
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    private static boolean saveExcelFile(Context context, File file, String reportID, String rebenokID, String verbatologID) {
        // check if available and not read only
        if (!Helper.isExternalStorageAvailable() || Helper.isExternalStorageReadOnly()) {
            Log.e("hello", "Storage not available or read only");
            return false;
        }

        boolean success = false;

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        //New Sheet
        Sheet sheet1 = null;
        sheet1 = wb.createSheet(reportID);

        ExcelEvent.createHeaderAndSetWidth(wb, sheet1);

        ExcelEvent.reportID = reportID;
        ExcelEvent.childID = rebenokID;
        ExcelEvent.verbatologID = verbatologID;
        ExcelEvent.deviceID = android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        ExcelEvent.bciID = SettingsSharedPrefs.getBciID(context);
        ExcelEvent.expired = SpecialistSharedPrefs.getCertificateExpired(context);

        ArrayList<Pair<Long, String>> bciItems = new ArrayList<>();

        ArrayList<ExcelEvent> excelEvents = new ArrayList<>();

        BCIDatabase sqh = BCIDatabase.getInstance(context);
        SQLiteDatabase sqdb = sqh.getMyWritableDatabase(context);
//        if (true) {

        try {
            Cursor cursor = sqdb.query(BCIDatabase.BCI_ATTENTION_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                bciItems.add(new Pair<Long, String>(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(BCIDatabase.TIMESTAMP))),
                        "A" + Integer.toString(cursor.getInt(cursor.getColumnIndex(BCIDatabase.ATTENTION))) + ";" +
                                cursor.getString(cursor.getColumnIndex(BCIDatabase.CHILD_ID))));
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
//        }

        try {
//        if (true) {

            Cursor cursor = sqdb.query(BCIDatabase.BCI_EEG_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                bciItems.add(new Pair<Long, String>(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(BCIDatabase.TIMESTAMP))),
                        "E" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(BCIDatabase.DELTA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(BCIDatabase.THETA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(BCIDatabase.LOW_ALPHA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(BCIDatabase.HIGH_ALPHA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(BCIDatabase.LOW_BETA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(BCIDatabase.HIGH_BETA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(BCIDatabase.LOW_GAMMA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(BCIDatabase.MID_GAMMA))) + ";" +
                                cursor.getString(cursor.getColumnIndex(BCIDatabase.CHILD_ID))
                ));
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
//        if (true) {
            Cursor cursor = sqdb.query(BCIDatabase.BCI_MEDIATION_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                bciItems.add(new Pair<Long, String>(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(BCIDatabase.TIMESTAMP))),
                        "M" + Integer.toString(cursor.getInt(cursor.getColumnIndex(BCIDatabase.MEDIATION))) + ";" +
                                cursor.getString(cursor.getColumnIndex(BCIDatabase.CHILD_ID))));
            } while (cursor.moveToNext());
            cursor.close();
      //  }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        StatisticsDatabase statisticsDatabase = StatisticsDatabase.getInstance(context);
        sqdb = statisticsDatabase.getMyWritableDatabase();

        try {
            Cursor cursor = sqdb.query(StatisticsDatabase.EVENTS_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                ExcelEvent excelEvent = new ExcelEvent();
                excelEvent.setTimestamp((Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(StatisticsDatabase.TIMESTAMP)))));
                excelEvent.setLogopedModeID((cursor.getInt(cursor.getColumnIndex(StatisticsDatabase.GAME_SUBMODE))));
                excelEvent.setActionID((cursor.getInt(cursor.getColumnIndex(StatisticsDatabase.ACTION))));
                excelEvent.setRezhimID((cursor.getInt(cursor.getColumnIndex(StatisticsDatabase.EVENT_MODE))));
                excelEvent.setWord((cursor.getString(cursor.getColumnIndex(StatisticsDatabase.WORD))));
                excelEvent.setModule(cursor.getString(cursor.getColumnIndex(StatisticsDatabase.MODULE)));
                excelEvent.setMistake(cursor.getString(cursor.getColumnIndex(StatisticsDatabase.MISTAKE)));
                excelEvents.add(excelEvent);
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Collections.sort(excelEvents, new ExcelEventsComparator());
        Collections.sort(bciItems, new ExcelExportComparator());

        ArrayList<ExcelBCI> excelBCIs = new ArrayList<>();

        int k = 0;
        while (k < bciItems.size() - 2) {
            ExcelBCI excelBCI = new ExcelBCI();
            if (k + 1 > bciItems.size() - 1) {
                long timestampFirst = bciItems.get(k).first;
                String firstString = bciItems.get(k).second;
                if (firstString.substring(0, 1).equals("A")) {
                    excelBCI.setAttention(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (firstString.substring(0, 1).equals("M")) {
                    excelBCI.setMediation(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (firstString.substring(0, 1).equals("E")) {
                    String[] array = firstString.substring(1).split(";");
                    excelBCI.setDelta(Integer.parseInt(array[0]));
                    excelBCI.setTheta(Integer.parseInt(array[1]));
                    excelBCI.setLowAlpha(Integer.parseInt(array[2]));
                    excelBCI.setHighAlpha(Integer.parseInt(array[3]));
                    excelBCI.setLowBeta(Integer.parseInt(array[4]));
                    excelBCI.setHighBeta(Integer.parseInt(array[5]));
                    excelBCI.setLowGamma(Integer.parseInt(array[6]));
                    excelBCI.setMidGamma(Integer.parseInt(array[7]));
                    excelBCI.setChildID(array[8]);
                }
                excelBCI.setTimestamp(timestampFirst);
                k += 1;
                excelBCIs.add(excelBCI);
                continue;
            }
            if (k + 1 > bciItems.size() - 2) {
                long timestampFirst = bciItems.get(k).first;
                long timestampSecond = bciItems.get(k + 1).first;
                String firstString = bciItems.get(k).second;
                String secondString = bciItems.get(k + 1).second;
                if (timestampFirst == timestampSecond) {
                    if (firstString.substring(0, 1).equals("A")) {
                        excelBCI.setAttention(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                        excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                    } else if (firstString.substring(0, 1).equals("M")) {
                        excelBCI.setMediation(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                        excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                    } else if (firstString.substring(0, 1).equals("E")) {
                        String[] array = firstString.substring(1).split(";");
                        excelBCI.setDelta(Integer.parseInt(array[0]));
                        excelBCI.setTheta(Integer.parseInt(array[1]));
                        excelBCI.setLowAlpha(Integer.parseInt(array[2]));
                        excelBCI.setHighAlpha(Integer.parseInt(array[3]));
                        excelBCI.setLowBeta(Integer.parseInt(array[4]));
                        excelBCI.setHighBeta(Integer.parseInt(array[5]));
                        excelBCI.setLowGamma(Integer.parseInt(array[6]));
                        excelBCI.setMidGamma(Integer.parseInt(array[7]));
                        excelBCI.setChildID(array[8]);
                    }
                    if (secondString.substring(0, 1).equals("E")) {
                        String[] array = secondString.substring(1).split(";");
                        excelBCI.setDelta(Integer.parseInt(array[0]));
                        excelBCI.setTheta(Integer.parseInt(array[1]));
                        excelBCI.setLowAlpha(Integer.parseInt(array[2]));
                        excelBCI.setHighAlpha(Integer.parseInt(array[3]));
                        excelBCI.setLowBeta(Integer.parseInt(array[4]));
                        excelBCI.setHighBeta(Integer.parseInt(array[5]));
                        excelBCI.setLowGamma(Integer.parseInt(array[6]));
                        excelBCI.setMidGamma(Integer.parseInt(array[7]));
                        excelBCI.setChildID(array[8]);
                    } else if (secondString.substring(0, 1).equals("A")) {
                        excelBCI.setAttention(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                        excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                    } else if (secondString.substring(0, 1).equals("M")) {
                        excelBCI.setMediation(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                        excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                    }
                    excelBCI.setTimestamp(timestampFirst);
                    k += 2;
                } else {
                    if (firstString.substring(0, 1).equals("A")) {
                        excelBCI.setAttention(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                        excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                    } else if (firstString.substring(0, 1).equals("M")) {
                        excelBCI.setMediation(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                        excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                    } else if (firstString.substring(0, 1).equals("E")) {
                        String[] array = firstString.substring(1).split(";");
                        excelBCI.setDelta(Integer.parseInt(array[0]));
                        excelBCI.setTheta(Integer.parseInt(array[1]));
                        excelBCI.setLowAlpha(Integer.parseInt(array[2]));
                        excelBCI.setHighAlpha(Integer.parseInt(array[3]));
                        excelBCI.setLowBeta(Integer.parseInt(array[4]));
                        excelBCI.setHighBeta(Integer.parseInt(array[5]));
                        excelBCI.setLowGamma(Integer.parseInt(array[6]));
                        excelBCI.setMidGamma(Integer.parseInt(array[7]));
                        excelBCI.setChildID(array[8]);
                    }
                    excelBCI.setTimestamp(timestampFirst);
                    k += 1;
                }
                excelBCIs.add(excelBCI);
                continue;
            }
            long timestampFirst = bciItems.get(k).first;
            long timestampSecond = bciItems.get(k + 1).first;
            long timestampThird = bciItems.get(k + 2).first;
            String firstString = bciItems.get(k).second;
            String secondString = bciItems.get(k + 1).second;
            String thirdString = bciItems.get(k + 2).second;
            if (timestampFirst == timestampSecond && timestampSecond == timestampThird) {
                if (firstString.substring(0, 1).equals("A")) {
                    excelBCI.setAttention(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (firstString.substring(0, 1).equals("M")) {
                    excelBCI.setMediation(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (firstString.substring(0, 1).equals("E")) {
                    String[] array = firstString.substring(1).split(";");
                    excelBCI.setDelta(Integer.parseInt(array[0]));
                    excelBCI.setTheta(Integer.parseInt(array[1]));
                    excelBCI.setLowAlpha(Integer.parseInt(array[2]));
                    excelBCI.setHighAlpha(Integer.parseInt(array[3]));
                    excelBCI.setLowBeta(Integer.parseInt(array[4]));
                    excelBCI.setHighBeta(Integer.parseInt(array[5]));
                    excelBCI.setLowGamma(Integer.parseInt(array[6]));
                    excelBCI.setMidGamma(Integer.parseInt(array[7]));
                    excelBCI.setChildID(array[8]);
                }
                if (secondString.substring(0, 1).equals("E")) {
                    String[] array = secondString.substring(1).split(";");
                    excelBCI.setDelta(Integer.parseInt(array[0]));
                    excelBCI.setTheta(Integer.parseInt(array[1]));
                    excelBCI.setLowAlpha(Integer.parseInt(array[2]));
                    excelBCI.setHighAlpha(Integer.parseInt(array[3]));
                    excelBCI.setLowBeta(Integer.parseInt(array[4]));
                    excelBCI.setHighBeta(Integer.parseInt(array[5]));
                    excelBCI.setLowGamma(Integer.parseInt(array[6]));
                    excelBCI.setMidGamma(Integer.parseInt(array[7]));
                    excelBCI.setChildID(array[8]);
                } else if (secondString.substring(0, 1).equals("A")) {
                    excelBCI.setAttention(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (secondString.substring(0, 1).equals("M")) {
                    excelBCI.setMediation(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                }
                if (thirdString.substring(0, 1).equals("M")) {
                    excelBCI.setMediation(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (thirdString.substring(0, 1).equals("A")) {
                    excelBCI.setAttention(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (thirdString.substring(0, 1).equals("E")) {
                    String[] array = thirdString.substring(1).split(";");
                    excelBCI.setDelta(Integer.parseInt(array[0]));
                    excelBCI.setTheta(Integer.parseInt(array[1]));
                    excelBCI.setLowAlpha(Integer.parseInt(array[2]));
                    excelBCI.setHighAlpha(Integer.parseInt(array[3]));
                    excelBCI.setLowBeta(Integer.parseInt(array[4]));
                    excelBCI.setHighBeta(Integer.parseInt(array[5]));
                    excelBCI.setLowGamma(Integer.parseInt(array[6]));
                    excelBCI.setMidGamma(Integer.parseInt(array[7]));
                    excelBCI.setChildID(array[8]);
                }
                excelBCI.setTimestamp(timestampFirst);
                k += 3;
            } else if (timestampFirst == timestampSecond) {
                if (firstString.substring(0, 1).equals("A")) {
                    excelBCI.setAttention(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (firstString.substring(0, 1).equals("M")) {
                    excelBCI.setMediation(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (firstString.substring(0, 1).equals("E")) {
                    String[] array = firstString.substring(1).split(";");
                    excelBCI.setDelta(Integer.parseInt(array[0]));
                    excelBCI.setTheta(Integer.parseInt(array[1]));
                    excelBCI.setLowAlpha(Integer.parseInt(array[2]));
                    excelBCI.setHighAlpha(Integer.parseInt(array[3]));
                    excelBCI.setLowBeta(Integer.parseInt(array[4]));
                    excelBCI.setHighBeta(Integer.parseInt(array[5]));
                    excelBCI.setLowGamma(Integer.parseInt(array[6]));
                    excelBCI.setMidGamma(Integer.parseInt(array[7]));
                    excelBCI.setChildID(array[8]);
                }
                if (secondString.substring(0, 1).equals("E")) {
                    String[] array = secondString.substring(1).split(";");
                    excelBCI.setDelta(Integer.parseInt(array[0]));
                    excelBCI.setTheta(Integer.parseInt(array[1]));
                    excelBCI.setLowAlpha(Integer.parseInt(array[2]));
                    excelBCI.setHighAlpha(Integer.parseInt(array[3]));
                    excelBCI.setLowBeta(Integer.parseInt(array[4]));
                    excelBCI.setHighBeta(Integer.parseInt(array[5]));
                    excelBCI.setLowGamma(Integer.parseInt(array[6]));
                    excelBCI.setMidGamma(Integer.parseInt(array[7]));
                    excelBCI.setChildID(array[8]);
                } else if (secondString.substring(0, 1).equals("A")) {
                    excelBCI.setAttention(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (secondString.substring(0, 1).equals("M")) {
                    excelBCI.setMediation(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                }
                excelBCI.setTimestamp(timestampFirst);
                k += 2;
            } else {
                if (firstString.substring(0, 1).equals("A")) {
                    excelBCI.setAttention(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (firstString.substring(0, 1).equals("M")) {
                    excelBCI.setMediation(Integer.parseInt(firstString.substring(1, firstString.indexOf(";"))));
                    excelBCI.setChildID(firstString.substring(firstString.indexOf(";") + 1, firstString.length()));
                } else if (firstString.substring(0, 1).equals("E")) {
                    String[] array = firstString.substring(1).split(";");
                    excelBCI.setDelta(Integer.parseInt(array[0]));
                    excelBCI.setTheta(Integer.parseInt(array[1]));
                    excelBCI.setLowAlpha(Integer.parseInt(array[2]));
                    excelBCI.setHighAlpha(Integer.parseInt(array[3]));
                    excelBCI.setLowBeta(Integer.parseInt(array[4]));
                    excelBCI.setHighBeta(Integer.parseInt(array[5]));
                    excelBCI.setLowGamma(Integer.parseInt(array[6]));
                    excelBCI.setMidGamma(Integer.parseInt(array[7]));
                    excelBCI.setChildID(array[8]);
                }
                excelBCI.setTimestamp(timestampFirst);
                k += 1;
            }
            excelBCIs.add(excelBCI);

        }
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentWord = "";
        String currentModule = "";
        ExcelEvent currentExcelEvent = new ExcelEvent();
        String learnWord = "";
        int rowIndex = 1;
        for (int i = 0; i < excelBCIs.size(); i ++) {
            long timestampBCI = excelBCIs.get(i).getTimestamp();
            if (timestampBCI == 0) {
                continue;
            }
            Row row = sheet1.createRow(rowIndex);
            Cell c = row.createCell(ExcelColumnID.EXCEL_ID_CODE);
            c.setCellValue(rowIndex);
            c = row.createCell(ExcelColumnID.EXCEL_TIMESTAMP_CODE);
            c.setCellValue(sdf.format(timestampBCI * 1000));
            rowIndex ++;
            BCIExcelWriter.writeToRow(row, excelBCIs.get(i));
            ExcelEventWriter.writeStaticEvents(context, row);
            int j = 0;
            boolean rawAlreadyWritten = false;
            while (j < excelEvents.size()) {
                if (excelEvents.get(j).timestamp == timestampBCI) {
                    if (TextUtils.isEmpty(learnWord) && (excelEvents.get(j).getRezhimID() == RezhimID.LEARN_MODE)) {
                        learnWord = excelEvents.get(j).getWord();
                    } else if (excelEvents.get(j).getRezhimID() == RezhimID.LEARN_MODE) {
                        learnWord = "";
                    }
                    if (excelEvents.get(j).getActionID() == ActionID.WORD_START_ID || (excelEvents.get(j).getModule().equals(ExcelEventWriter.CUSTOM_ACTION_ID) && !currentWord.equals(excelEvents.get(j).getWord()))) {
                        currentWord = excelEvents.get(j).getWord();
                        currentModule = excelEvents.get(j).getModule();
                        currentExcelEvent.setRezhimID(excelEvents.get(j).getRezhimID());
                        currentExcelEvent.setGameSubMode(excelEvents.get(j).getGameSubMode());
                        currentExcelEvent.setLogopedModeID(excelEvents.get(j).getLogopedModeID());
                        learnWord = "";
                    } else
                    if (excelEvents.get(j).getActionID() == ActionID.WORD_END_ID
                            || excelEvents.get(j).getActionID() == ActionID.WORD_SKIP_ID
                            || excelEvents.get(j).getActionID() == ActionID.WORD_BACK_ID
                            || excelEvents.get(j).getActionID() == ActionID.WORD_SUCCESS_ID || (excelEvents.get(j).getModule().equals(ExcelEventWriter.CUSTOM_ACTION_ID) && currentWord.equals(excelEvents.get(j).getWord()))) {
                        currentModule = "";
                        currentWord = "";
                        currentExcelEvent.setRezhimID(-1);
                        currentExcelEvent.setGameSubMode(-1);
                        currentExcelEvent.setLogopedModeID(-1);
                    }
                    if (rawAlreadyWritten) {
                        row = sheet1.createRow(rowIndex);
                        c = row.createCell(ExcelColumnID.EXCEL_ID_CODE);
                        c.setCellValue(rowIndex);
                        c = row.createCell(ExcelColumnID.EXCEL_TIMESTAMP_CODE);
                        c.setCellValue(sdf.format(timestampBCI * 1000));
                        BCIExcelWriter.writeToRow(row, excelBCIs.get(i));
                        ExcelEventWriter.writeStaticEvents(context, row);
                        rowIndex ++;
                    }
                    ExcelEventWriter.writeToRow(row, excelEvents.get(j));
                    rawAlreadyWritten = true;
                    j ++;
                    continue;
                }
                if (!rawAlreadyWritten && !TextUtils.isEmpty(learnWord)) {
                    ExcelEvent excelEvent = new ExcelEvent();
                    excelEvent.setRezhimID(RezhimID.LEARN_MODE);
                    excelEvent.setWord(learnWord);
                    if (rawAlreadyWritten) {
                        row = sheet1.createRow(rowIndex);
                        c = row.createCell(ExcelColumnID.EXCEL_ID_CODE);
                        c.setCellValue(rowIndex);
                        c = row.createCell(ExcelColumnID.EXCEL_TIMESTAMP_CODE);
                        c.setCellValue(sdf.format(timestampBCI * 1000));
                        BCIExcelWriter.writeToRow(row, excelBCIs.get(i));
                        ExcelEventWriter.writeStaticEvents(context, row);
                        rowIndex ++;
                    }
                    ExcelEventWriter.writeToRow(row, excelEvent);
                    rawAlreadyWritten = true;
                }
                if (!rawAlreadyWritten && (!TextUtils.isEmpty(currentWord) || !TextUtils.isEmpty(currentModule))) {
                    ExcelEvent excelEvent = new ExcelEvent();
                    excelEvent.setWord(currentWord);
                    excelEvent.setModule(currentModule);
                    excelEvent.setLogopedModeID(currentExcelEvent.getLogopedModeID());
                    excelEvent.setRezhimID(currentExcelEvent.getRezhimID());
                    excelEvent.setGameSubMode(currentExcelEvent.getGameSubMode());
                    if (rawAlreadyWritten) {
                        row = sheet1.createRow(rowIndex);
                        c = row.createCell(ExcelColumnID.EXCEL_ID_CODE);
                        c.setCellValue(rowIndex);
                        c = row.createCell(ExcelColumnID.EXCEL_TIMESTAMP_CODE);
                        c.setCellValue(sdf.format(timestampBCI * 1000));
                        BCIExcelWriter.writeToRow(row, excelBCIs.get(i));
                        ExcelEventWriter.writeStaticEvents(context, row);
                        rowIndex ++;
                    }
                    ExcelEventWriter.writeToRow(row, excelEvent);
                    rawAlreadyWritten = true;
                }
                if (excelEvents.get(j).timestamp > timestampBCI) {
                    int index = 0;
                    while (excelEvents.get(index).timestamp <= timestampBCI) {
                        excelEvents.remove(index);
                    }
                    break;
                }
                j ++;
            }
        }

        // Create a path where we will place our List of objects on external storage
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return success;
    }

    public void share(String fileAndLocation, String letterSubject) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("application/excel");
//        emailIntent.setClassName("com.google.android.gm", "com.google.android.gm.ComposeActivityGmail");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {SettingsSharedPrefs.getEmailToSend(mContext)});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, letterSubject);
        emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse(fileAndLocation));
        mContext.startActivity(emailIntent);
    }

    @Override
    public void promptForContent(Tag msg) {

    }
}
