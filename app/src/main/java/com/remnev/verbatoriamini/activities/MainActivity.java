package com.remnev.verbatoriamini.activities;

import android.Manifest;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import com.remnev.verbatoriamini.NeuroApplicationClass;
import com.remnev.verbatoriamini.callbacks.IAllAnsweredCallback;
import com.remnev.verbatoriamini.callbacks.IExportPossibleCallback;
import com.remnev.verbatoriamini.callbacks.IFragmentsMovingCallback;
import com.remnev.verbatoriamini.util.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.callbacks.IClearButtonsCallback;
import com.remnev.verbatoriamini.callbacks.INFCCallback;
import com.remnev.verbatoriamini.databases.NeuroDataDatabase;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.fragment.ConnectionFragment;
import com.remnev.verbatoriamini.fragment.QuestionaryDialogFragment;
import com.remnev.verbatoriamini.fragment.AttentionFragment;
import com.remnev.verbatoriamini.fragment.WriteCertificateFragment;
import com.remnev.verbatoriamini.fragment.WriteCodesFragment;
import com.remnev.verbatoriamini.model.ActionID;
import com.remnev.verbatoriamini.model.Certificate;
import com.remnev.verbatoriamini.model.ExcelBCI;
import com.remnev.verbatoriamini.model.ExcelColumnID;
import com.remnev.verbatoriamini.model.ExcelEvent;
import com.remnev.verbatoriamini.model.MutablePair;
import com.remnev.verbatoriamini.model.RezhimID;
import com.remnev.verbatoriamini.sharedpreferences.ParentsAnswersSharedPrefs;
import com.remnev.verbatoriamini.sharedpreferences.SettingsSharedPrefs;
import com.remnev.verbatoriamini.sharedpreferences.SpecialistSharedPrefs;
import com.remnev.verbatoriamini.util.NeuroExcelWriter;
import com.remnev.verbatoriamini.util.comparators.ExcelEventsComparator;
import com.remnev.verbatoriamini.util.comparators.ExcelExportComparator;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity
        implements INFCCallback,
        IAllAnsweredCallback,
        IExportPossibleCallback,
        IFragmentsMovingCallback {

    private static final int REQUEST_PERMISSION_CODE = 2444;

    public Fragment pendingFragment;
    private Context mContext;

    public TextView titleTextView;
    private TextView specialistTextView;
    public BottomNavigationView bottomNavigationView;

    private IClearButtonsCallback clearButtons;

    public boolean isExportPossible = true;

    private QuestionaryDialogFragment mQuestionaryDialogFragment;

    NfcAdapter mAdapter;
    PendingIntent mPendingIntent;
    IntentFilter[] mNdefExchangeFilters;

    public static INFCCallback callback;

    @Override
    public void onNewIntent(Intent intent) {
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) && callback != null) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            onNFCTagReaded(tagFromIntent);
        } else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction())) {
            Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
            onNFCTagReaded(tagFromIntent);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        mNdefExchangeFilters = new IntentFilter[] { tagDetected };
        enableNFC();

        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(this.getIntent().getAction())) {
            Tag detectedTag = this.getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            onNFCTagReaded(detectedTag);
            callback.onNFCTagReaded(detectedTag);
        } else if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(this.getIntent().getAction())) {
            Tag detectedTag = this.getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            onNFCTagReaded(detectedTag);
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

    private void setUpToolbarButton() {
        findViewById(R.id.exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.exit(0);
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mContext = this;

        initViews();
        setUpBottomNavigationView();

        mAdapter = NfcAdapter.getDefaultAdapter(this);
        mPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this,
                getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        ndefDetected.addCategory(Intent.CATEGORY_DEFAULT);
        mNdefExchangeFilters = new IntentFilter[]{ndefDetected};

        pendingFragment = new ConnectionFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, pendingFragment)
                .commit();

        setUpView();

        File cacheDir = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR);
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();
        }

        bottomNavigationView.getMenu().removeItem(R.id.bottom_navigation_item_certificates);
        bottomNavigationView.getMenu().removeItem(R.id.bottom_navigation_item_codes);

        setUpToolbarButton();

        if (ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(mContext,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE},
                    REQUEST_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_PERMISSION_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    Helper.showSnackBar(bottomNavigationView, getString(R.string.app_unstable));
                }
            }
        }
    }

    private void initViews() {
        titleTextView = (TextView) findViewById(R.id.title);
        specialistTextView = (TextView) findViewById(R.id.specialist);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
    }

    private void setUpView() {
        titleTextView.setText(getString(R.string.CONNECT_BOTTOM_NAVIGATION_BAR));
        specialistTextView.setText(SpecialistSharedPrefs.getCurrentSpecialist(mContext).getSpecialistName());
    }

    private void setUpBottomNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                if (!isExportPossible) {
                    Helper.showSnackBar(findViewById(R.id.container), getString(R.string.please_stop_action));
                    return false;
                }
                boolean flag = true;
                clearButtons = null;
                switch (menuItem.getItemId()) {
                    case R.id.bottom_navigation_item_connect:
                        pendingFragment = new ConnectionFragment();
                        titleTextView.setText(getString(R.string.CONNECT_BOTTOM_NAVIGATION_BAR));
                        callback = null;
                        break;
                    case R.id.bottom_navigation_item_attention:
                        if (!NeuroApplicationClass.isConnected()) {
                            Helper.showSnackBar(findViewById(R.id.container), getString(R.string.please_connect_neuro));
                            return false;
                        }
                        pendingFragment = new AttentionFragment();
                        clearButtons = (IClearButtonsCallback) pendingFragment;
                        titleTextView.setText(getString(R.string.ATTENTION_BOTTOM_NAVIGATION_BAR));
                        callback = (INFCCallback) pendingFragment;
                        break;
                    case R.id.bottom_navigation_item_mail:
                        if (!NeuroApplicationClass.isConnected()) {
                            Helper.showSnackBar(findViewById(R.id.container), getString(R.string.please_connect_neuro));
                            return false;
                        }
                        preCheckExportToExcel(false);
                        flag = false;
                        break;
                    case R.id.bottom_navigation_item_certificates:
                        pendingFragment = new WriteCertificateFragment();
                        callback = (INFCCallback) pendingFragment;
                        titleTextView.setText(getString(R.string.CERTIFICATES_BOTTOM_NAVIGATION_BAR));
                        break;
                    case R.id.bottom_navigation_item_codes:
                        pendingFragment = new WriteCodesFragment();
                        callback = (INFCCallback) pendingFragment;
                        titleTextView.setText(getString(R.string.CODES_BOTTOM_NAVIGATION_BAR));
                        break;
                }
                if (flag) {
                    beginFragmentManagerTransaction(pendingFragment);
                }
                return true;
            }
        });
    }

    private void moveFile(String inputPath, String outputPath) {
        InputStream in;
        OutputStream out;
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
            out.flush();
            out.close();

            NeuroDataDatabase.removeAll(mContext);
            StatisticsDatabase.removeAll(mContext);
        }
        catch (FileNotFoundException fnfe1) {
            Log.e("tag", fnfe1.getMessage());
        }
        catch (Exception e) {
            Log.e("tag", e.getMessage());
        }
    }

    private void preCheckExportToExcel(final boolean stopBCI) {
        String undoneActivities = NeuroApplicationClass.getAllUndoneActivities();
        if (TextUtils.isEmpty(undoneActivities)) {
            exportToExcel();
        } else {
            final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
            dialog.setMessage(String.format(getString(R.string.not_done_some_activities), undoneActivities));
            dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    exportToExcel();
                }
            });
            dialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();

                    bottomNavigationView.getMenu().getItem(2).setChecked(false);
                    if (pendingFragment instanceof ConnectionFragment) {
                        bottomNavigationView.getMenu().getItem(0).setChecked(true);
                        titleTextView.setText(getString(R.string.CONNECT_BOTTOM_NAVIGATION_BAR));
                    } else if (pendingFragment instanceof AttentionFragment) {
                        bottomNavigationView.getMenu().getItem(1).setChecked(true);
                        titleTextView.setText(getString(R.string.ATTENTION_BOTTOM_NAVIGATION_BAR));
                    } else if (pendingFragment instanceof WriteCertificateFragment) {
                        bottomNavigationView.getMenu().getItem(4).setChecked(true);
                        titleTextView.setText(getString(R.string.CERTIFICATES_BOTTOM_NAVIGATION_BAR));
                    } else if (pendingFragment instanceof WriteCodesFragment) {
                        bottomNavigationView.getMenu().getItem(3).setChecked(true);
                        titleTextView.setText(getString(R.string.CODES_BOTTOM_NAVIGATION_BAR));
                    }
                }
            });
            dialog.setCancelable(false);
            dialog.show();
        }
    }

    private void exportToExcel() {
        final ProgressDialog progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(getString(R.string.check_correctness));
        progressDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                final String result = checkNumberOfLines(getExcelEvents(mContext));
                progressDialog.dismiss();
                if (result != null) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            String text;
                            if (result.contains(",")) {
                                text = String.format(getString(R.string.codes_error), result);
                            } else {
                                text = String.format(getString(R.string.codes_1_error), result);
                            }

                            final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
                            dialog.setMessage(text);
                            dialog.setNegativeButton(getString(R.string.codes_error_restart), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (clearButtons != null) {
                                                clearButtons.clearRemovedButtons();
                                            }
                                        }
                                    });
                                    selectBottomNavigationItemAndSetTitle();
                                }
                            });
                            dialog.setCancelable(false);
                            dialog.show();
                        }
                    });
                } else {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(mContext).create();
                            View layoutView = getLayoutInflater().inflate(R.layout.dialog_export_user, null);
                            final TextView okTextView = (TextView) layoutView.findViewById(R.id.ok_button);
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
                                    }
                                }
                            });

                            final Spinner ageSpinner = (Spinner) layoutView.findViewById(R.id.choose_years);
                            okTextView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    //проверки
                                    if (TextUtils.isEmpty(randomLabel.getText().toString())) {
                                        Helper.showSnackBar(findViewById(R.id.container), getString(R.string.no_random_label));
                                        return;
                                    }
                                    final File directory = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "Export" + File.separator);
                                    if (!directory.exists()) {
                                        if (!directory.mkdirs()) {
                                            Helper.showSnackBar(findViewById(R.id.container), getString(R.string.cannot_create_directory));
                                        }
                                    }
                                    alertDialog.dismiss();

                                    if (directory.exists()) {
                                        mQuestionaryDialogFragment = new QuestionaryDialogFragment(ageSpinner.getSelectedItem().toString(), randomLabel.getText().toString(), directory.getAbsolutePath(), MainActivity.this);
                                        mQuestionaryDialogFragment.show(getSupportFragmentManager(), QuestionaryDialogFragment.TAG);
                                    }
                                }
                            });

                            alertDialog.setView(layoutView);
                            alertDialog.setCancelable(false);
                            alertDialog.show();
                        }
                    });
                }
            }
        }).start();
    }

    private static String saveExcelFile(Context context, File file, String reportID, String rebenokID, String verbatologID) {
        // check if available and not read only
        if (!Helper.isExternalStorageAvailable() || Helper.isExternalStorageReadOnly()) {
            return "false";
        }

        //New Workbook
        Workbook wb = new HSSFWorkbook();

        ExcelEvent.reportID = reportID;
        ExcelEvent.childID = rebenokID;
        ExcelEvent.verbatologID = verbatologID;
        ExcelEvent.deviceID = android.provider.Settings.Secure.getString(context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        ExcelEvent.bciID = SettingsSharedPrefs.getBciID(context);
        ExcelEvent.expired = SpecialistSharedPrefs.getCertificateExpired(context);

        ExcelEvent currentExcelEvent = new ExcelEvent();
        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentWord = "";
        String currentModule = "";
        String learnWord = "";
        int rowIndex = 1;

        //New Sheet
        Sheet sheet1 = wb.createSheet("EXPORT");

        ExcelEvent.createHeaderAndSetWidth(wb, sheet1);

        ArrayList<ExcelEvent> excelEvents = getExcelEvents(context);
        ArrayList<ExcelBCI> excelBCIs = getExcelBCIs(getBCIItems(context));

        for (int i = 0; i < excelBCIs.size(); i ++) {
            long timestampBCI = excelBCIs.get(i).getTimestamp();
            if (timestampBCI == 0) {
                continue;
            }
            Row row = sheet1.createRow(rowIndex);
            Cell c = row.createCell(ExcelColumnID.EXCEL_ID_CODE);
            c.setCellValue(rowIndex);
            c = row.createCell(ExcelColumnID.EXCEL_TIMESTAMP_CODE);
            c.setCellValue(timeFormat.format(timestampBCI * 1000));
            rowIndex ++;
            NeuroExcelWriter.writeToRow(row, excelBCIs.get(i));
            NeuroExcelWriter.writeStaticEvents(context, row);
            int j = 0;
            boolean rawAlreadyWritten = false;
            while (j < excelEvents.size()) {
                if (excelEvents.get(j).timestamp == timestampBCI) {
                    if (TextUtils.isEmpty(learnWord) && (excelEvents.get(j).getRezhimID() == RezhimID.LEARN_MODE)) {
                        learnWord = excelEvents.get(j).getWord();
                    } else if (excelEvents.get(j).getRezhimID() == RezhimID.LEARN_MODE) {
                        learnWord = "";
                    }
                    if (excelEvents.get(j).getActionID() == ActionID.WORD_START_ID || (excelEvents.get(j).getModule().equals(NeuroExcelWriter.CUSTOM_ACTION_ID) && !currentWord.equals(excelEvents.get(j).getWord()))) {
                        currentWord = excelEvents.get(j).getWord();
                        currentModule = excelEvents.get(j).getModule();
                        currentExcelEvent.setRezhimID(excelEvents.get(j).getRezhimID());
                        currentExcelEvent.setGameSubMode(excelEvents.get(j).getGameSubMode());
                        currentExcelEvent.setLogopedModeID(excelEvents.get(j).getLogopedModeID());
                        learnWord = "";
                    } else if (excelEvents.get(j).getActionID() == ActionID.WORD_END_ID
                            || excelEvents.get(j).getActionID() == ActionID.WORD_SKIP_ID
                            || excelEvents.get(j).getActionID() == ActionID.WORD_BACK_ID
                            || excelEvents.get(j).getActionID() == ActionID.WORD_SUCCESS_ID || (excelEvents.get(j).getModule().equals(NeuroExcelWriter.CUSTOM_ACTION_ID) && currentWord.equals(excelEvents.get(j).getWord()))) {
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
                        c.setCellValue(timeFormat.format(timestampBCI * 1000));
                        NeuroExcelWriter.writeToRow(row, excelBCIs.get(i));
                        NeuroExcelWriter.writeStaticEvents(context, row);
                        rowIndex ++;
                    }
                    NeuroExcelWriter.writeToRow(row, excelEvents.get(j));
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
                        c.setCellValue(timeFormat.format(timestampBCI * 1000));
                        NeuroExcelWriter.writeToRow(row, excelBCIs.get(i));
                        NeuroExcelWriter.writeStaticEvents(context, row);
                        rowIndex ++;
                    }
                    NeuroExcelWriter.writeToRow(row, excelEvent);
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
                        c.setCellValue(timeFormat.format(timestampBCI * 1000));
                        NeuroExcelWriter.writeToRow(row, excelBCIs.get(i));
                        NeuroExcelWriter.writeStaticEvents(context, row);
                        rowIndex ++;
                    }
                    NeuroExcelWriter.writeToRow(row, excelEvent);
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                wb.close();
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
        return "true";
    }

    private static ArrayList<ExcelEvent> getExcelEvents(Context context) {
        StatisticsDatabase statisticsDatabase = StatisticsDatabase.getInstance(context);
        SQLiteDatabase sqdb = statisticsDatabase.getMyWritableDatabase();

        ArrayList<ExcelEvent> excelEvents = new ArrayList<>();
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
        return excelEvents;
    }

    private static ArrayList<Pair<Long, String>> getBCIItems(Context context) {
        NeuroDataDatabase sqh = NeuroDataDatabase.getInstance(context);
        SQLiteDatabase sqdb = sqh.getMyWritableDatabase(context);

        ArrayList<Pair<Long, String>> bciItems = new ArrayList<>();

        try {
            Cursor cursor = sqdb.query(NeuroDataDatabase.BCI_ATTENTION_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                bciItems.add(new Pair<Long, String>(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(NeuroDataDatabase.TIMESTAMP))),
                        "A" + Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.ATTENTION))) + ";" +
                                cursor.getString(cursor.getColumnIndex(NeuroDataDatabase.CHILD_ID))));
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            Cursor cursor = sqdb.query(NeuroDataDatabase.BCI_EEG_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                bciItems.add(new Pair<Long, String>(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(NeuroDataDatabase.TIMESTAMP))),
                        "E" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.DELTA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.THETA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.LOW_ALPHA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.HIGH_ALPHA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.LOW_BETA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.HIGH_BETA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.LOW_GAMMA))) + ";" +
                                Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.MID_GAMMA))) + ";" +
                                cursor.getString(cursor.getColumnIndex(NeuroDataDatabase.CHILD_ID))
                ));
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        try {
            Cursor cursor = sqdb.query(NeuroDataDatabase.BCI_MEDIATION_TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            do {
                bciItems.add(new Pair<Long, String>(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(NeuroDataDatabase.TIMESTAMP))),
                        "M" + Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.MEDIATION))) + ";" +
                                cursor.getString(cursor.getColumnIndex(NeuroDataDatabase.CHILD_ID))));
            } while (cursor.moveToNext());
            cursor.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Collections.sort(bciItems, new ExcelExportComparator());
        return bciItems;
    }

    private static ArrayList<ExcelBCI> getExcelBCIs(ArrayList<Pair<Long, String>> bciItems) {
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
        return excelBCIs;
    }

    private static String checkNumberOfLines(ArrayList<ExcelEvent> excelEvents) {
        MutablePair[] checkingArray = new MutablePair[]{new MutablePair("99", 0), new MutablePair("11", 0),
                new MutablePair("21", 0), new MutablePair("31", 0), new MutablePair("41", 0),
                new MutablePair("51", 0), new MutablePair("61", 0), new MutablePair("71", 0)};
        //count 15
        for (int i = 0; i < excelEvents.size() - 1; i ++) {
            ExcelEvent firstEvent = excelEvents.get(i);
            ExcelEvent secondEvent = excelEvents.get(i + 1);
            for (int j = 0; j < checkingArray.length; j ++) {
                MutablePair pair = checkingArray[j];
                if (firstEvent.getWord().equals(pair.getFirst()) && secondEvent.getWord().equals(pair.getFirst())
                        && NeuroApplicationClass.containsDoneActivity(pair.getFirst())) {
                    pair.addSecond(secondEvent.getTimestamp() - firstEvent.getTimestamp());
                    break;
                }
            }
        }
        Log.e("test", Arrays.toString(checkingArray));

        StringBuilder stringBuilder = new StringBuilder();

        for (int j = 0; j < checkingArray.length; j ++) {
            MutablePair pair = checkingArray[j];
            if (pair.getSecond() < 15 && NeuroApplicationClass.containsDoneActivity(pair.getFirst())) {
                stringBuilder.append(pair.getFirst()).append(", ");
                NeuroApplicationClass.removeActivityFromDoneArray(pair.getFirst());
            }
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.setLength(stringBuilder.length() - 2);
            return stringBuilder.toString();
        }
        return null;
    }

    public void share(String fileAndLocation, String letterSubject) {
        NeuroApplicationClass.clearDoneActivities();

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] {SettingsSharedPrefs.getEmailToSend(mContext)});
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, letterSubject);
        Log.e("uri", "uri: " + Uri.parse(fileAndLocation));
        emailIntent.putExtra(android.content.Intent.EXTRA_STREAM, Uri.parse(fileAndLocation));
        mContext.startActivity(emailIntent);
    }

    private boolean checkDate(Certificate certificate) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = simpleDateFormat.format(System.currentTimeMillis());
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

    private void selectBottomNavigationItemAndSetTitle() {
        if (pendingFragment != null) {
            if (pendingFragment instanceof ConnectionFragment) {
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                titleTextView.setText(getString(R.string.CONNECT_BOTTOM_NAVIGATION_BAR));
            } else if (pendingFragment instanceof AttentionFragment) {
                ((AttentionFragment) pendingFragment).clearRemovedButtons();
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                titleTextView.setText(getString(R.string.ATTENTION_BOTTOM_NAVIGATION_BAR));
            } else if (pendingFragment instanceof WriteCertificateFragment) {
                bottomNavigationView.getMenu().getItem(4).setChecked(true);
                titleTextView.setText(getString(R.string.CERTIFICATES_BOTTOM_NAVIGATION_BAR));
            } else if (pendingFragment instanceof WriteCodesFragment) {
                bottomNavigationView.getMenu().getItem(3).setChecked(true);
                titleTextView.setText(getString(R.string.CODES_BOTTOM_NAVIGATION_BAR));
            }
        }
    }

    public void beginFragmentManagerTransaction(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public void onNFCTagReaded(Tag msg) {
        if (pendingFragment instanceof WriteCertificateFragment) {
            if (callback != null) {
                callback.onNFCTagReaded(msg);
            }
            return;
        }
        if (msg != null) {
            String readedText = Helper.readTag(msg, MainActivity.this);
            if (TextUtils.isEmpty(readedText)) {
                if (callback != null) {
                    callback.onNFCTagReaded(msg);
                }
            } else {
                try {
                    Certificate certificate = new Certificate();
                    if (certificate.parseCertificate(readedText)) {
                        Certificate currentCertificate = SpecialistSharedPrefs.getCurrentSpecialist(MainActivity.this);
                        if (currentCertificate == null) {
                            if (checkDate(certificate)) {
                                showExpiryDialog();
                                return;
                            }
                            SpecialistSharedPrefs.setCurrentSpecialist(MainActivity.this, certificate);
                            specialistTextView.setText(SpecialistSharedPrefs.getCurrentSpecialist(mContext).getSpecialistName());
                            SpecialistSharedPrefs.setLastCertificateCheckDate(MainActivity.this, System.currentTimeMillis());
                            Helper.showSnackBar(bottomNavigationView, (String.format(getString(R.string.authority_success), certificate.getSpecialistName()) + " " + certificate.getExpiry()));
                        } else {//cvc is correct //if (specialist.getCvc().equals(SpecialistSharedPrefs.getCurrentSpecialist(AuthorityActivity.this).getCvc())) {
                            if (checkDate(certificate)) {
                                showExpiryDialog();
                                return;
                            }
                            SpecialistSharedPrefs.setCurrentSpecialist(MainActivity.this, certificate);
                            specialistTextView.setText(SpecialistSharedPrefs.getCurrentSpecialist(mContext).getSpecialistName());
                            SpecialistSharedPrefs.setLastCertificateCheckDate(MainActivity.this, System.currentTimeMillis());
                            Helper.showSnackBar(bottomNavigationView, (String.format(getString(R.string.authority_success), certificate.getSpecialistName()) + " " + certificate.getExpiry()));
                        }
                    } else {
                        if (callback != null) {
                            callback.onNFCTagReaded(msg);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (callback != null) {
                        callback.onNFCTagReaded(msg);
                    }
                }
            }

        }

    }

    @Override
    public void allAnswered(final String age, final String reportID, final long timeInMillis, final String directoryAbsPath) {
        if (ParentsAnswersSharedPrefs.isAllQuestionsAnswered(mContext)) {
            mQuestionaryDialogFragment.dismiss();
            mQuestionaryDialogFragment = null;

            final ProgressDialog progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage(getString(R.string.generate_xls_file));
            progressDialog.setCancelable(false);
            progressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
                    String rebenokID = "Возраст: " + age;
                    final String fileName = "Возраст: " + age
                            + "_" + sdf.format(timeInMillis) + "_" + reportID + ".xls";
                    File excelFile = new File(directoryAbsPath + File.separator + fileName);
                    if (excelFile.exists()) {
                        excelFile.delete();
                    }

                    final String specialist = SpecialistSharedPrefs.getCurrentSpecialist(MainActivity.this).getSpecialistName();
                    final String save = saveExcelFile(mContext, excelFile, reportID, rebenokID, specialist);
                    if (save.equals("true")) {
                        Helper.showSnackBar(findViewById(R.id.container), getString(R.string.generate_xls_file_success));
                    } else if (save.equals("false")) {
                        Helper.showSnackBar(findViewById(R.id.container), getString(R.string.generate_xls_file_fail));
                        return;
                    }

                    final File fromFileStatistics = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "statistics.db");
                    final File toFileStatistics = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "Export" + File.separator + "statistics_" + "test" + "_" + sdf.format(timeInMillis) + ".db");
                    final File fromFileBCI = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "bci.db");
                    final File toFileBCI = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "Export" + File.separator + "bci_" + "test" + "_" + sdf.format(timeInMillis) + ".db");
                    if (toFileStatistics.exists()) {
                        toFileStatistics.delete();
                    }
                    if (toFileBCI.exists()) {
                        toFileBCI.delete();
                    }
                    progressDialog.dismiss();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Helper.showSnackBar(findViewById(R.id.container), getString(R.string.generating_xls_message));
                            StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.RECORD_END_ID, RezhimID.ANOTHER_MODE, -1, -1);
                            moveFile(fromFileStatistics.getAbsolutePath(), toFileStatistics.getAbsolutePath());
                            moveFile(fromFileBCI.getAbsolutePath(), toFileBCI.getAbsolutePath());
                            selectBottomNavigationItemAndSetTitle();
                            share("file://" + directoryAbsPath + File.separator + fileName, reportID);
                        }
                    });
                }
            }).start();
        } else {
            Helper.showSnackBar(bottomNavigationView, getString(R.string.fill_all_questions));
        }
    }

    @Override
    public void exportPossibleValueChanged(boolean isPossible) {
        isExportPossible = isPossible;
    }

    @Override
    public void moveToAttentionFragment() {
        Log.e("test", "moveToAttentionFragment");
        pendingFragment = new ConnectionFragment();
        selectBottomNavigationItemAndSetTitle();
        callback = null;
        beginFragmentManagerTransaction(pendingFragment);
    }

    @Override
    public void moveToConnectionFragment() {
        pendingFragment = new AttentionFragment();
        selectBottomNavigationItemAndSetTitle();
        callback = (INFCCallback) pendingFragment;
        beginFragmentManagerTransaction(pendingFragment);
    }

}
