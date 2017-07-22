package com.remnev.verbatoriamini.activities;

import android.content.Context;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.remnev.verbatoriamini.NeuroApplicationClass;
import com.remnev.verbatoriamini.R;
//import com.remnev.verbatoriamini.callbacks.IClearButtonsCallback;
//import com.remnev.verbatoriamini.databases.NeuroDataDatabase;
//import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.fragment.QuestionaryDialogFragment;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private View mRootView;

    public Fragment pendingFragment;
    private Context mContext;

    public TextView titleTextView;
    private TextView specialistTextView;
    public BottomNavigationView bottomNavigationView;

//    private IClearButtonsCallback clearButtons;

    public boolean isExportPossible = true;

    private QuestionaryDialogFragment mQuestionaryDialogFragment;

    private Timer mConnectionCheckTimer;

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
        mContext = this;
        mConnectionCheckTimer = new Timer();
        mConnectionCheckTimer.schedule(new CheckConnectionTimerTask(), 0, 1000);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mConnectionCheckTimer.cancel();
        mConnectionCheckTimer = null;
    }

//    private void moveFile(String inputPath, String outputPath) {
//        InputStream in;
//        OutputStream out;
//        try {
//            File dir = new File(outputPath);
//            if (!dir.exists()) {
//                dir.createNewFile();
//            }
//            in = new FileInputStream(inputPath);
//            out = new FileOutputStream(outputPath);
//
//            byte[] buffer = new byte[1024];
//            int read;
//            while ((read = in.read(buffer)) != -1) {
//                out.write(buffer, 0, read);
//            }
//            in.close();
//            out.flush();
//            out.close();
//
//            NeuroDataDatabase.removeAll(mContext);
//            StatisticsDatabase.removeAll(mContext);
//        }
//        catch (FileNotFoundException fnfe1) {
//            Log.e("tag", fnfe1.getMessage());
//        }
//        catch (Exception e) {
//            Log.e("tag", e.getMessage());
//        }
//    }

//    private void preCheckExportToExcel() {
//        String undoneActivities = NeuroApplicationClass.getAllUndoneActivities();
//        if (TextUtils.isEmpty(undoneActivities)) {
//            exportToExcel();
//        } else {
//            final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//            dialog.setMessage(String.format(getString(R.string.not_done_some_activities), undoneActivities));
//            dialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    exportToExcel();
//                }
//            });
//            dialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialogInterface, int i) {
//                    dialogInterface.dismiss();
//
//                    bottomNavigationView.getMenu().getItem(2).setChecked(false);
//                    if (pendingFragment instanceof ConnectionFragment) {
//                        bottomNavigationView.getMenu().getItem(0).setChecked(true);
//                        titleTextView.setText(getString(R.string.CONNECT_BOTTOM_NAVIGATION_BAR));
//                    } else if (pendingFragment instanceof AttentionFragment) {
//                        bottomNavigationView.getMenu().getItem(1).setChecked(true);
//                        titleTextView.setText(getString(R.string.ATTENTION_BOTTOM_NAVIGATION_BAR));
//                    } else if (pendingFragment instanceof WriteCertificateFragment) {
//                        bottomNavigationView.getMenu().getItem(4).setChecked(true);
//                        titleTextView.setText(getString(R.string.CERTIFICATES_BOTTOM_NAVIGATION_BAR));
//                    } else if (pendingFragment instanceof WriteCodesFragment) {
//                        bottomNavigationView.getMenu().getItem(3).setChecked(true);
//                        titleTextView.setText(getString(R.string.CODES_BOTTOM_NAVIGATION_BAR));
//                    }
//                }
//            });
//            dialog.setCancelable(false);
//            dialog.show();
//        }
//    }

//    private void exportToExcel() {
//        final ProgressDialog progressDialog = new ProgressDialog(mContext);
//        progressDialog.setMessage(getString(R.string.check_correctness));
//        progressDialog.show();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                final String result = checkNumberOfLines(getExcelEvents(mContext));
//                progressDialog.dismiss();
//                if (result != null) {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            String text;
//                            if (result.contains(",")) {
//                                text = String.format(getString(R.string.codes_error), result);
//                            } else {
//                                text = String.format(getString(R.string.codes_1_error), result);
//                            }
//
//                            final AlertDialog.Builder dialog = new AlertDialog.Builder(mContext);
//                            dialog.setMessage(text);
//                            dialog.setNegativeButton(getString(R.string.codes_error_restart), new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                    runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            if (clearButtons != null) {
//                                                clearButtons.clearRemovedButtons();
//                                            }
//                                        }
//                                    });
//                                    selectBottomNavigationItemAndSetTitle();
//                                }
//                            });
//                            dialog.setCancelable(false);
//                            dialog.show();
//                        }
//                    });
//                } else {
//                    runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            final android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(mContext).create();
//                            View layoutView = getLayoutInflater().inflate(R.layout.dialog_export_user, null);
//                            final TextView okTextView = (TextView) layoutView.findViewById(R.id.ok_button);
//                            final EditText randomLabel = (EditText) layoutView.findViewById(R.id.random_label);
//                            randomLabel.addTextChangedListener(new TextWatcher() {
//                                @Override
//                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                }
//
//                                @Override
//                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//                                }
//
//                                @Override
//                                public void afterTextChanged(Editable editable) {
//                                    String result = editable.toString().replaceAll(" ", "");
//                                    if (!editable.toString().equals(result)) {
//                                        randomLabel.setText(result);
//                                        randomLabel.setSelection(result.length());
//                                    }
//                                }
//                            });
//
//                            final Spinner ageSpinner = (Spinner) layoutView.findViewById(R.id.choose_years);
//                            okTextView.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View view) {
//                                    //проверки
//                                    if (TextUtils.isEmpty(randomLabel.getText().toString())) {
//                                        Helper.showSnackBar(findViewById(R.id.container), getString(R.string.no_random_label));
//                                        return;
//                                    }
//                                    final File directory = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "Export" + File.separator);
//                                    if (!directory.exists()) {
//                                        if (!directory.mkdirs()) {
//                                            Helper.showSnackBar(findViewById(R.id.container), getString(R.string.cannot_create_directory));
//                                        }
//                                    }
//                                    alertDialog.dismiss();
//
//                                    if (directory.exists()) {
////                                        mQuestionaryDialogFragment = new QuestionaryDialogFragment(ageSpinner.getSelectedItem().toString(), randomLabel.getText().toString(), directory.getAbsolutePath(), MainActivity.this);
////                                        mQuestionaryDialogFragment.show(getSupportFragmentManager(), QuestionaryDialogFragment.TAG);
//                                    }
//                                }
//                            });
//
//                            alertDialog.setView(layoutView);
//                            alertDialog.setCancelable(false);
//                            alertDialog.show();
//                        }
//                    });
//                }
//            }
//        }).start();
//    }

//    private static String saveExcelFile(Context context, File file, String reportID, String rebenokID, String verbatologID) {
//        // check if available and not read only
//        if (!Helper.isExternalStorageAvailable() || Helper.isExternalStorageReadOnly()) {
//            return "false";
//        }
//
//        //New Workbook
//        Workbook wb = new HSSFWorkbook();
//
//        ExcelEvent.reportID = reportID;
//        ExcelEvent.childID = rebenokID;
//        ExcelEvent.verbatologID = verbatologID;
//        ExcelEvent.deviceID = android.provider.Settings.Secure.getString(context.getContentResolver(),
//                android.provider.Settings.Secure.ANDROID_ID);
//        ExcelEvent.bciID = SettingsSharedPrefs.getBciID(context);
//        ExcelEvent.expired = SpecialistSharedPrefs.getCertificateExpired(context);
//
//        ExcelEvent currentExcelEvent = new ExcelEvent();
//        SimpleDateFormat timeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
//        String currentWord = "";
//        String currentModule = "";
//        String learnWord = "";
//        int rowIndex = 1;
//
//        //New Sheet
//        Sheet sheet1 = wb.createSheet("EXPORT");
//
//        ExcelEvent.createHeaderAndSetWidth(wb, sheet1);
//
//        ArrayList<ExcelEvent> excelEvents = getExcelEvents(context);
//        ArrayList<ExcelBCI> excelBCIs = getExcelBCIs(getBCIItems(context));
//
//        for (int i = 0; i < excelBCIs.size(); i ++) {
//            long timestampBCI = excelBCIs.get(i).getTimestamp();
//            if (timestampBCI == 0) {
//                continue;
//            }
//            Row row = sheet1.createRow(rowIndex);
//            Cell c = row.createCell(ExcelColumnID.EXCEL_ID_CODE);
//            c.setCellValue(rowIndex);
//            c = row.createCell(ExcelColumnID.EXCEL_TIMESTAMP_CODE);
//            c.setCellValue(timeFormat.format(timestampBCI * 1000));
//            rowIndex ++;
//            NeuroExcelWriter.writeToRow(row, excelBCIs.get(i));
//            NeuroExcelWriter.writeStaticEvents(context, row);
//            int j = 0;
//            boolean rawAlreadyWritten = false;
//            while (j < excelEvents.size()) {
//                if (excelEvents.get(j).timestamp == timestampBCI) {
//                    if (TextUtils.isEmpty(learnWord) && (excelEvents.get(j).getRezhimID() == RezhimID.LEARN_MODE)) {
//                        learnWord = excelEvents.get(j).getWord();
//                    } else if (excelEvents.get(j).getRezhimID() == RezhimID.LEARN_MODE) {
//                        learnWord = "";
//                    }
//                    if (excelEvents.get(j).getActionID() == ActionID.WORD_START_ID || (excelEvents.get(j).getModule().equals(NeuroExcelWriter.CUSTOM_ACTION_ID) && !currentWord.equals(excelEvents.get(j).getWord()))) {
//                        currentWord = excelEvents.get(j).getWord();
//                        currentModule = excelEvents.get(j).getModule();
//                        currentExcelEvent.setRezhimID(excelEvents.get(j).getRezhimID());
//                        currentExcelEvent.setGameSubMode(excelEvents.get(j).getGameSubMode());
//                        currentExcelEvent.setLogopedModeID(excelEvents.get(j).getLogopedModeID());
//                        learnWord = "";
//                    } else if (excelEvents.get(j).getActionID() == ActionID.WORD_END_ID
//                            || excelEvents.get(j).getActionID() == ActionID.WORD_SKIP_ID
//                            || excelEvents.get(j).getActionID() == ActionID.WORD_BACK_ID
//                            || excelEvents.get(j).getActionID() == ActionID.WORD_SUCCESS_ID || (excelEvents.get(j).getModule().equals(NeuroExcelWriter.CUSTOM_ACTION_ID) && currentWord.equals(excelEvents.get(j).getWord()))) {
//                        currentModule = "";
//                        currentWord = "";
//                        currentExcelEvent.setRezhimID(-1);
//                        currentExcelEvent.setGameSubMode(-1);
//                        currentExcelEvent.setLogopedModeID(-1);
//                    }
//                    if (rawAlreadyWritten) {
//                        row = sheet1.createRow(rowIndex);
//                        c = row.createCell(ExcelColumnID.EXCEL_ID_CODE);
//                        c.setCellValue(rowIndex);
//                        c = row.createCell(ExcelColumnID.EXCEL_TIMESTAMP_CODE);
//                        c.setCellValue(timeFormat.format(timestampBCI * 1000));
//                        NeuroExcelWriter.writeToRow(row, excelBCIs.get(i));
//                        NeuroExcelWriter.writeStaticEvents(context, row);
//                        rowIndex ++;
//                    }
//                    NeuroExcelWriter.writeToRow(row, excelEvents.get(j));
//                    rawAlreadyWritten = true;
//                    j ++;
//                    continue;
//                }
//                if (!rawAlreadyWritten && !TextUtils.isEmpty(learnWord)) {
//                    ExcelEvent excelEvent = new ExcelEvent();
//                    excelEvent.setRezhimID(RezhimID.LEARN_MODE);
//                    excelEvent.setWord(learnWord);
//                    if (rawAlreadyWritten) {
//                        row = sheet1.createRow(rowIndex);
//                        c = row.createCell(ExcelColumnID.EXCEL_ID_CODE);
//                        c.setCellValue(rowIndex);
//                        c = row.createCell(ExcelColumnID.EXCEL_TIMESTAMP_CODE);
//                        c.setCellValue(timeFormat.format(timestampBCI * 1000));
//                        NeuroExcelWriter.writeToRow(row, excelBCIs.get(i));
//                        NeuroExcelWriter.writeStaticEvents(context, row);
//                        rowIndex ++;
//                    }
//                    NeuroExcelWriter.writeToRow(row, excelEvent);
//                    rawAlreadyWritten = true;
//                }
//                if (!rawAlreadyWritten && (!TextUtils.isEmpty(currentWord) || !TextUtils.isEmpty(currentModule))) {
//                    ExcelEvent excelEvent = new ExcelEvent();
//                    excelEvent.setWord(currentWord);
//                    excelEvent.setModule(currentModule);
//                    excelEvent.setLogopedModeID(currentExcelEvent.getLogopedModeID());
//                    excelEvent.setRezhimID(currentExcelEvent.getRezhimID());
//                    excelEvent.setGameSubMode(currentExcelEvent.getGameSubMode());
//                    if (rawAlreadyWritten) {
//                        row = sheet1.createRow(rowIndex);
//                        c = row.createCell(ExcelColumnID.EXCEL_ID_CODE);
//                        c.setCellValue(rowIndex);
//                        c = row.createCell(ExcelColumnID.EXCEL_TIMESTAMP_CODE);
//                        c.setCellValue(timeFormat.format(timestampBCI * 1000));
//                        NeuroExcelWriter.writeToRow(row, excelBCIs.get(i));
//                        NeuroExcelWriter.writeStaticEvents(context, row);
//                        rowIndex ++;
//                    }
//                    NeuroExcelWriter.writeToRow(row, excelEvent);
//                    rawAlreadyWritten = true;
//                }
//                if (excelEvents.get(j).timestamp > timestampBCI) {
//                    int index = 0;
//                    while (excelEvents.get(index).timestamp <= timestampBCI) {
//                        excelEvents.remove(index);
//                    }
//                    break;
//                }
//                j ++;
//            }
//        }
//
//
//        // Create a path where we will place our List of objects on external storage
//        FileOutputStream os = null;
//        try {
//            os = new FileOutputStream(file);
//            wb.write(os);
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (null != os)
//                    os.close();
//            } catch (Exception ex) {
//                ex.printStackTrace();
//            }
//        }
//        return "true";
//    }

//    @Override
//    public void allAnswered(final String age, final String reportID, final long timeInMillis, final String directoryAbsPath) {
//        if (ParentsAnswersSharedPrefs.isAllQuestionsAnswered(mContext)) {
//            mQuestionaryDialogFragment.dismiss();
//            mQuestionaryDialogFragment = null;
//
//            final ProgressDialog progressDialog = new ProgressDialog(mContext);
//            progressDialog.setMessage(getString(R.string.generate_xls_file));
//            progressDialog.setCancelable(false);
//            progressDialog.show();
//            new Thread(() -> {
//                SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
//                String rebenokID = "Возраст: " + age;
//                final String fileName = "Возраст: " + age
//                        + "_" + sdf.format(timeInMillis) + "_" + reportID + ".xls";
//                File excelFile = new File(directoryAbsPath + File.separator + fileName);
//                if (excelFile.exists()) {
//                    excelFile.delete();
//                }
//
//                final String specialist = SpecialistSharedPrefs.getCurrentSpecialist(MainActivity.this).getSpecialistName();
//                final String save = saveExcelFile(mContext, excelFile, reportID, rebenokID, specialist);
//                if (save.equals("true")) {
//                    Helper.showSnackBar(findViewById(R.id.container), getString(R.string.generate_xls_file_success));
//                } else if (save.equals("false")) {
//                    Helper.showSnackBar(findViewById(R.id.container), getString(R.string.generate_xls_file_fail));
//                    return;
//                }
//
//                final File fromFileStatistics = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "statistics.db");
//                final File toFileStatistics = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "Export" + File.separator + "statistics_" + "test" + "_" + sdf.format(timeInMillis) + ".db");
//                final File fromFileBCI = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "bci.db");
//                final File toFileBCI = new File(Environment.getExternalStorageDirectory(), SplashActivity.FILES_DIR + File.separator + "Export" + File.separator + "bci_" + "test" + "_" + sdf.format(timeInMillis) + ".db");
//                if (toFileStatistics.exists()) {
//                    toFileStatistics.delete();
//                }
//                if (toFileBCI.exists()) {
//                    toFileBCI.delete();
//                }
//                progressDialog.dismiss();
//                runOnUiThread(() -> {
//                    Helper.showSnackBar(findViewById(R.id.container), getString(R.string.generating_xls_message));
////                    StatisticsDatabase.addEventToDatabase(mContext, "", "", ActionID.RECORD_END_ID, RezhimID.ANOTHER_MODE, -1, -1);
//                    moveFile(fromFileStatistics.getAbsolutePath(), toFileStatistics.getAbsolutePath());
//                    moveFile(fromFileBCI.getAbsolutePath(), toFileBCI.getAbsolutePath());
//                    selectBottomNavigationItemAndSetTitle();
//                    share("file://" + directoryAbsPath + File.separator + fileName, reportID);
//                });
//            }).start();
//        } else {
//            Helper.showSnackBar(bottomNavigationView, getString(R.string.fill_all_questions));
//        }
//    }

    private class CheckConnectionTimerTask extends TimerTask {

        @Override
        public void run() {
            runOnUiThread(new ChangeFont());
        }

        private class ChangeFont implements Runnable {

            @Override
            public void run() {
                try {
                    final int sdk = android.os.Build.VERSION.SDK_INT;
                    if (!NeuroApplicationClass.isConnected()) {
                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            mRootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_red_border));
                        } else {
                            mRootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_red_border));
                        }
                    } else {
                        if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                            mRootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_usual));
                        } else {
                            mRootView.setBackground(getResources().getDrawable(R.drawable.frg_attention_usual));
                        }
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }
}
