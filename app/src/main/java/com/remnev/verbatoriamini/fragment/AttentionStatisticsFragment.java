package com.remnev.verbatoriamini.fragment;


import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.remnev.verbatoriamini.util.Helper;
import com.remnev.verbatoriamini.R;
import com.remnev.verbatoriamini.databases.NeuroDataDatabase;
import com.remnev.verbatoriamini.databases.StatisticsDatabase;
import com.remnev.verbatoriamini.model.ActionID;
import com.remnev.verbatoriamini.model.ExcelBCI;
import com.remnev.verbatoriamini.model.ExcelEvent;
import com.remnev.verbatoriamini.model.RezhimID;
import com.remnev.verbatoriamini.model.SumNumber;
import com.remnev.verbatoriamini.util.comparators.ExcelEventsComparator;
import com.remnev.verbatoriamini.util.comparators.ExcelExportComparator;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A simple {@link Fragment} subclass.
 */
public class AttentionStatisticsFragment extends Fragment implements OnChartValueSelectedListener {

    public HorizontalBarChart barChart;
    private Context mContext;
    private ProgressBar progressBar;
    private static ArrayList<Pair<String, SumNumber>> wordValue;

    public AttentionStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_attention_statistics, null, false);
        barChart = (HorizontalBarChart) rootView.findViewById(R.id.chart);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mContext = getActivity();
        wordValue = new ArrayList<>();
        setUpChart();
        return rootView;
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void setUpChart() {
        barChart.setOnChartValueSelectedListener(this);
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        barChart.setDescription("");
        barChart.setMaxVisibleValueCount(100);
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(false);
        barChart.setNoDataText(getString(R.string.noData));
        XAxis xl = barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(true);
        xl.setGridLineWidth(0.3f);

        YAxis yl = barChart.getAxisLeft();
        yl.setDrawAxisLine(true);
        yl.setDrawGridLines(true);
        yl.setGridLineWidth(0.5f);

        YAxis yr = barChart.getAxisRight();
        yr.setDrawAxisLine(true);
        yr.setDrawGridLines(false);

        barChart.getLegend().setEnabled(false);
//        setData(12, 100);
//
        progressBar.setVisibility(View.VISIBLE);
        barChart.setVisibility(View.GONE);
        analyze();
    }

    private void analyze() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ArrayList<Pair<Long, String>> bciItems = new ArrayList<>();

                ArrayList<ExcelEvent> excelEvents = new ArrayList<>();

                NeuroDataDatabase sqh = NeuroDataDatabase.getInstance(mContext);
                SQLiteDatabase sqdb = sqh.getMyWritableDatabase(mContext);
                try {
                    Cursor cursor = sqdb.query(NeuroDataDatabase.BCI_ATTENTION_TABLE_NAME, null, null, null, null, null, null);
                    cursor.moveToFirst();
                    do {
                        bciItems.add(new Pair<Long, String>(Helper.processTimestamp(cursor.getLong(cursor.getColumnIndex(NeuroDataDatabase.TIMESTAMP))),
                                "A" + Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.ATTENTION)))));
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
                                        Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.MID_GAMMA)))
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
                                "M" + Integer.toString(cursor.getInt(cursor.getColumnIndex(NeuroDataDatabase.MEDIATION)))));
                    } while (cursor.moveToNext());
                    cursor.close();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                StatisticsDatabase statisticsDatabase = StatisticsDatabase.getInstance(mContext);
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
                            excelBCI.setAttention(Integer.parseInt(firstString.substring(1)));
                        } else if (firstString.substring(0, 1).equals("M")) {
                            excelBCI.setMediation(Integer.parseInt(firstString.substring(1)));
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
                        }
                        excelBCI.setTimestamp(timestampFirst);
                        k += 1;
                        excelBCIs.add(excelBCI);
                        continue;
                    }
                    if (k + 1 > bciItems.size() - 1) {
                        long timestampFirst = bciItems.get(k).first;
                        long timestampSecond = bciItems.get(k + 1).first;
                        String firstString = bciItems.get(k).second;
                        String secondString = bciItems.get(k + 1).second;
                        if (timestampFirst == timestampSecond) {
                            if (firstString.substring(0, 1).equals("A")) {
                                excelBCI.setAttention(Integer.parseInt(firstString.substring(1)));
                            } else if (firstString.substring(0, 1).equals("M")) {
                                excelBCI.setMediation(Integer.parseInt(firstString.substring(1)));
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
                            } else if (secondString.substring(0, 1).equals("A")) {
                                excelBCI.setAttention(Integer.parseInt(secondString.substring(1)));
                            } else if (secondString.substring(0, 1).equals("M")) {
                                excelBCI.setMediation(Integer.parseInt(secondString.substring(1)));
                            }
                            k += 2;
                        } else {
                            if (firstString.substring(0, 1).equals("A")) {
                                excelBCI.setAttention(Integer.parseInt(firstString.substring(1)));
                            } else if (firstString.substring(0, 1).equals("M")) {
                                excelBCI.setMediation(Integer.parseInt(firstString.substring(1)));
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
                            }
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
                            excelBCI.setAttention(Integer.parseInt(firstString.substring(1)));
                        } else if (firstString.substring(0, 1).equals("M")) {
                            excelBCI.setMediation(Integer.parseInt(firstString.substring(1)));
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
                        } else if (secondString.substring(0, 1).equals("A")) {
                            excelBCI.setAttention(Integer.parseInt(secondString.substring(1)));
                        } else if (secondString.substring(0, 1).equals("M")) {
                            excelBCI.setMediation(Integer.parseInt(secondString.substring(1)));
                        }
                        if (thirdString.substring(0, 1).equals("M")) {
                            excelBCI.setMediation(Integer.parseInt(thirdString.substring(1)));
                        } else if (thirdString.substring(0, 1).equals("A")) {
                            excelBCI.setAttention(Integer.parseInt(thirdString.substring(1)));
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
                        }
                        excelBCI.setTimestamp(timestampFirst);
                        k += 3;
                    } else if (timestampFirst == timestampSecond) {
                        if (firstString.substring(0, 1).equals("A")) {
                            excelBCI.setAttention(Integer.parseInt(firstString.substring(1)));
                        } else if (firstString.substring(0, 1).equals("M")) {
                            excelBCI.setMediation(Integer.parseInt(firstString.substring(1)));
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
                        } else if (secondString.substring(0, 1).equals("A")) {
                            excelBCI.setAttention(Integer.parseInt(secondString.substring(1)));
                        } else if (secondString.substring(0, 1).equals("M")) {
                            excelBCI.setMediation(Integer.parseInt(secondString.substring(1)));
                        }
                        k += 2;
                    } else {
                        if (firstString.substring(0, 1).equals("A")) {
                            excelBCI.setAttention(Integer.parseInt(firstString.substring(1)));
                        } else if (firstString.substring(0, 1).equals("M")) {
                            excelBCI.setMediation(Integer.parseInt(firstString.substring(1)));
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
                        }
                        k += 1;
                    }
                    excelBCIs.add(excelBCI);

                }
                String currentWord = "";
                String currentModule = "";
                ExcelEvent currentExcelEvent = new ExcelEvent();
                String learnWord = "";
                int rowIndex = 1;
                for (int i = 0; i < excelBCIs.size(); i ++) {
                    long timestampBCI = excelBCIs.get(i).getTimestamp();
                    rowIndex ++;
                    int j = 0;
                    boolean rawAlreadyWritten = false;
                    while (j < excelEvents.size()) {
                        if (excelEvents.get(j).timestamp == timestampBCI) {
                            if (TextUtils.isEmpty(learnWord) && excelEvents.get(j).getRezhimID() == RezhimID.LEARN_MODE) {
                                learnWord = excelEvents.get(j).getWord();
                            } else if (excelEvents.get(j).getRezhimID() == RezhimID.LEARN_MODE) {
                                learnWord = "";
                            }
                            if (excelEvents.get(j).getActionID() == ActionID.WORD_START_ID) {
                                currentWord = excelEvents.get(j).getWord();
                                currentModule = excelEvents.get(j).getModule();
                                currentExcelEvent.setRezhimID(excelEvents.get(j).getRezhimID());
                                currentExcelEvent.setGameSubMode(excelEvents.get(j).getGameSubMode());
                                currentExcelEvent.setLogopedModeID(excelEvents.get(j).getLogopedModeID());
                                learnWord = "";
                            }
                            if (excelEvents.get(j).getActionID() == ActionID.WORD_END_ID
                                    || excelEvents.get(j).getActionID() == ActionID.WORD_SKIP_ID
                                    || excelEvents.get(j).getActionID() == ActionID.WORD_BACK_ID
                                    || excelEvents.get(j).getActionID() == ActionID.WORD_SUCCESS_ID) {
                                currentModule = "";
                                currentWord = "";
                                currentExcelEvent.setRezhimID(-1);
                                currentExcelEvent.setGameSubMode(-1);
                                currentExcelEvent.setLogopedModeID(-1);
                            }
                            if (rawAlreadyWritten) {
                                rowIndex ++;
                            }
                            addValue(excelEvents.get(j).getWord(), excelBCIs.get(i).getAttention());
                            rawAlreadyWritten = true;
                            j ++;
                            continue;
                        }
                        if (!rawAlreadyWritten && !TextUtils.isEmpty(learnWord)) {
                            ExcelEvent excelEvent = new ExcelEvent();
                            excelEvent.setRezhimID(RezhimID.LEARN_MODE);
                            excelEvent.setWord(learnWord);
                            if (rawAlreadyWritten) {
                                rowIndex ++;
                            }
                            addValue(learnWord, excelBCIs.get(i).getAttention());
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
                                rowIndex ++;
                            }
                            addValue(currentWord, excelBCIs.get(i).getAttention());
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

                setData();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        barChart.animateY(1000);
                        progressBar.setVisibility(View.GONE);
                        barChart.setVisibility(View.VISIBLE);
                    }
                });

            }
        }).start();
    }

    public static void addValue(String word, int attention) {
        if (word.isEmpty()) {
            return;
        }
        boolean found = false;
        for (int i = 0; i < wordValue.size(); i ++) {
            if (wordValue.get(i).first.equals(word)) {
                wordValue.get(i).second.increaseSum(attention);
                wordValue.get(i).second.increaseNumber();
                found = true;
                break;
            }
        }
        if (!found) {
            wordValue.add(new Pair<String, SumNumber>(word, new SumNumber(attention)));
        }
    }

    private void setData() {
        if (wordValue.isEmpty()) {
            return;
        }
        ArrayList<BarEntry> yVals = new ArrayList<BarEntry>();
        ArrayList<String> xVals = new ArrayList<String>();
        String[] xAxisValues = new String[wordValue.size()];
        for (int i = 0; i < xAxisValues.length; i ++) {
            xAxisValues[i] = wordValue.get(i).first;
        }
        int[] yAxisValues = new int[wordValue.size()];
        for (int i = 0; i < xAxisValues.length; i ++) {
            yAxisValues[i] = wordValue.get(i).second.getMean();
        }
        for (int i = 0; i < wordValue.size(); i++) {
            xVals.add(xAxisValues[i]);
            yVals.add(new BarEntry((int) yAxisValues[i], i));
        }
        BarDataSet set = new BarDataSet(yVals, null);
        set.setColor(getResources().getColor(R.color.main));
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set);
        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(12f);
        barChart.setData(data);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
