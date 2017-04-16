package com.remnev.verbatoriamini.model;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Created by nikitaremnev on 27.03.16.
 */
public class ExcelEvent {

    public static String reportID;
    public static String childID;
    public static String verbatologID;
    public static String deviceID;
    public static String bciID;
    public static boolean expired;

    public  int rezhimID = -1;
    public  int logopedModeID = -1;
    public String word;
    public String module;
    public String mistake;
    public  int actionID = -1;
//    public  int gameMode = -1;
    public  int gameSubMode = -1;
    public  long timestamp = -1;

//    public int getGameMode() {
//        return gameMode;
//    }
//
//    public void setGameMode(int gameMode) {
//        this.gameMode = gameMode;
//    }


    public String getMistake() {
        return mistake;
    }

    public void setMistake(String mistake) {
        this.mistake = mistake;
    }

    public int getGameSubMode() {
        return gameSubMode;
    }

    public void setGameSubMode(int gameSubMode) {
        this.gameSubMode = gameSubMode;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getRezhimID() {
        return rezhimID;
    }

    public void setRezhimID(int rezhimID) {
        this.rezhimID = rezhimID;
    }

    public int getLogopedModeID() {
        return logopedModeID;
    }

    public void setLogopedModeID(int logopedModeID) {
        this.logopedModeID = logopedModeID;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getActionID() {
        return actionID;
    }

    public void setActionID(int actionID) {
        this.actionID = actionID;
    }

    public static void createHeaderAndSetWidth(Workbook wb, Sheet sheet) {
        // Generate column headings
        //Cell style for header row

        Cell c = null;
        Row row = sheet.createRow(0);

        // Create column titles
        c = row.createCell(ExcelColumnID.EXCEL_REPORT_ID_CODE);
        c.setCellValue("Report ID");
        c = row.createCell(ExcelColumnID.EXCEL_CHILD_ID_CODE);
        c.setCellValue("Child ID");
        c = row.createCell(ExcelColumnID.EXCEL_VERBATOLOG_ID_CODE);
        c.setCellValue("Verbatolog ID");
        c = row.createCell(ExcelColumnID.EXCEL_DEVICE_ID_CODE);
        c.setCellValue("Device ID");
        c = row.createCell(ExcelColumnID.EXCEL_BCI_ID_CODE);
        c.setCellValue("BCI ID");
        c = row.createCell(ExcelColumnID.EXCEL_EVENT_ID_CODE);
        c.setCellValue("Event ID");
        c = row.createCell(ExcelColumnID.EXCEL_LOGOPED_MODE_ID_CODE);
        c.setCellValue("Logoped Mode ID");

        c = row.createCell(ExcelColumnID.EXCEL_ACTION_CODE);
        c.setCellValue("Action ID");

        c = row.createCell(ExcelColumnID.EXCEL_WORD_CODE);
        c.setCellValue("Word");

        c = row.createCell(ExcelColumnID.EXCEL_BLOCK_CODE);
        c.setCellValue("Block");

        c = row.createCell(ExcelColumnID.EXCEL_MISTAKE_CODE);
        c.setCellValue("Mistake");

        c = row.createCell(ExcelColumnID.EXCEL_RESERVE_2_CODE);
        c.setCellValue("Reserve Blank");

        c = row.createCell(ExcelColumnID.EXCEL_RESERVE_3_CODE);
        c.setCellValue("Reserve Blank");

        c = row.createCell(ExcelColumnID.EXCEL_ID_CODE);
        c.setCellValue("ID");

        c = row.createCell(ExcelColumnID.EXCEL_TIMESTAMP_CODE);
        c.setCellValue("Timestamp");

        c = row.createCell(ExcelColumnID.EXCEL_ATTENTION_CODE);
        c.setCellValue("Attention");

        c = row.createCell(ExcelColumnID.EXCEL_MEDIATION_CODE);
        c.setCellValue("Mediation");

        c = row.createCell(ExcelColumnID.EXCEL_DELTA_CODE);
        c.setCellValue("Delta");

        c = row.createCell(ExcelColumnID.EXCEL_THETA_CODE);
        c.setCellValue("Theta");

        c = row.createCell(ExcelColumnID.EXCEL_LOW_ALPHA_CODE);
        c.setCellValue("Low Alpha");

        c = row.createCell(ExcelColumnID.EXCEL_HIGH_ALPHA_CODE);
        c.setCellValue("High Alpha");

        c = row.createCell(ExcelColumnID.EXCEL_LOW_BETA_CODE);
        c.setCellValue("Low Beta");

        c = row.createCell(ExcelColumnID.EXCEL_HIGH_BETA_CODE);
        c.setCellValue("High Beta");

        c = row.createCell(ExcelColumnID.EXCEL_LOW_GAMMA_CODE);
        c.setCellValue("Low Gamma");

        c = row.createCell(ExcelColumnID.EXCEL_MID_GAMMA_CODE);
        c.setCellValue("Mid Gamma");

        sheet.setColumnWidth(0, (5 * 500));
        sheet.setColumnWidth(1, (5 * 500));
        sheet.setColumnWidth(2, (5 * 500));
        sheet.setColumnWidth(3, (5 * 500));
        sheet.setColumnWidth(4, (5 * 500));
        sheet.setColumnWidth(5, (5 * 500));
        sheet.setColumnWidth(6, (5 * 500));
        sheet.setColumnWidth(7, (5 * 500));
        sheet.setColumnWidth(8, (5 * 500));
        sheet.setColumnWidth(9, (5 * 500));
        sheet.setColumnWidth(10, (5 * 500));
        sheet.setColumnWidth(11, (5 * 500));
        sheet.setColumnWidth(12, (5 * 500));
        sheet.setColumnWidth(13, (5 * 500));
        sheet.setColumnWidth(14, (5 * 500));
        sheet.setColumnWidth(15, (5 * 500));
        sheet.setColumnWidth(16, (5 * 500));
        sheet.setColumnWidth(17, (5 * 500));
        sheet.setColumnWidth(18, (5 * 500));
        sheet.setColumnWidth(19, (5 * 500));
        sheet.setColumnWidth(20, (5 * 500));
        sheet.setColumnWidth(21, (5 * 500));
        sheet.setColumnWidth(22, (5 * 500));
        sheet.setColumnWidth(23, (5 * 500));
        sheet.setColumnWidth(24, (5 * 500));
    }

    @Override
    public String toString() {
        return "ExcelEvent{" +
                "rezhimID=" + rezhimID +
                ", logopedModeID=" + logopedModeID +
                ", word='" + word + '\'' +
                ", module='" + module + '\'' +
                ", mistake='" + mistake + '\'' +
                ", actionID=" + actionID +
                ", gameSubMode=" + gameSubMode +
                ", timestamp=" + timestamp +
                '}';
    }
}
