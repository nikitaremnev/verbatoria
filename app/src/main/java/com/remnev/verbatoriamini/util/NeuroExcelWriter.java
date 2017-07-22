package com.remnev.verbatoriamini.util;

import android.content.Context;

import com.remnev.verbatoriamini.model.ActionID;
import com.remnev.verbatoriamini.model.ExcelBCI;
import com.remnev.verbatoriamini.model.ExcelColumnID;
import com.remnev.verbatoriamini.model.ExcelEvent;
import com.remnev.verbatoriamini.model.RezhimID;
import com.remnev.verbatoriamini.sharedpreferences.ParentsAnswersSharedPrefs;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 *
 *
 * @author nikitaremnev
 */
public class NeuroExcelWriter {

    public static final String CUSTOM_ACTION_ID = "custom";

    public static void writeToRow(Row row, ExcelBCI excelBCI) {
        Cell c = row.createCell(ExcelColumnID.EXCEL_ATTENTION_CODE);
        c.setCellValue(excelBCI.getAttention());
        c = row.createCell(ExcelColumnID.EXCEL_MEDIATION_CODE);
        c.setCellValue(excelBCI.getMediation());
        c = row.createCell(ExcelColumnID.EXCEL_DELTA_CODE);
        c.setCellValue(excelBCI.getDelta());
        c = row.createCell(ExcelColumnID.EXCEL_THETA_CODE);
        c.setCellValue(excelBCI.getTheta());
        c = row.createCell(ExcelColumnID.EXCEL_LOW_ALPHA_CODE);
        c.setCellValue(excelBCI.getLowAlpha());
        c = row.createCell(ExcelColumnID.EXCEL_HIGH_ALPHA_CODE);
        c.setCellValue(excelBCI.getHighAlpha());
        c = row.createCell(ExcelColumnID.EXCEL_LOW_BETA_CODE);
        c.setCellValue(excelBCI.getLowBeta());
        c = row.createCell(ExcelColumnID.EXCEL_HIGH_BETA_CODE);
        c.setCellValue(excelBCI.getHighBeta());
        c = row.createCell(ExcelColumnID.EXCEL_LOW_GAMMA_CODE);
        c.setCellValue(excelBCI.getLowGamma());
        c = row.createCell(ExcelColumnID.EXCEL_MID_GAMMA_CODE);
        c.setCellValue(excelBCI.getMidGamma());
    }

    public static void writeToRow(Row row, ExcelEvent excelEvent) {
        Cell c;
        if (excelEvent.getRezhimID() != -1) {
            c = row.createCell(ExcelColumnID.EXCEL_EVENT_ID_CODE);
            switch (excelEvent.getRezhimID()) {
                case RezhimID.LEARN_MODE:
                    c.setCellValue(RezhimID.LEARN_MODE_STRING);
                    break;
                case RezhimID.MODULES_MODE:
                    c.setCellValue(RezhimID.MODULES_MODE_STRING);
                    break;
                case RezhimID.TEACHER_MODE:
                    c.setCellValue(RezhimID.TEACHER_MODE_STRING);
                    break;
                case RezhimID.ANOTHER_MODE:
                    c.setCellValue(RezhimID.ANOTHER_MODE_STRING);
                    break;
            }
            c.setCellValue(excelEvent.getRezhimID());
        }
        if (excelEvent.getLogopedModeID() != -1) {
            c = row.createCell(ExcelColumnID.EXCEL_LOGOPED_MODE_ID_CODE);
            c.setCellValue(excelEvent.getLogopedModeID());
        }
        if (excelEvent.getActionID() != -1) {
            c = row.createCell(ExcelColumnID.EXCEL_ACTION_CODE);
            switch (excelEvent.getActionID()) {
                case ActionID.RECORD_START_ID:
                    c.setCellValue(ActionID.RECORD_START_STRING);
                    break;
                case ActionID.RECORD_END_ID:
                    c.setCellValue(ActionID.RECORD_END_STRING);
                    break;
                case ActionID.CONNECT_ID:
                    c.setCellValue(ActionID.CONNECT_STRING);
                    break;
                case ActionID.DISCONNECT_ID:
                    c.setCellValue(ActionID.DISCONNECT_STRING);
                    break;
                case ActionID.WORD_START_ID:
                    c.setCellValue(ActionID.WORD_START_STRING);
                    break;
                case ActionID.WORD_END_ID:
                    c.setCellValue(ActionID.WORD_END_STRING);
                    break;
                case ActionID.WORD_FAIL_ID:
                    c.setCellValue(ActionID.WORD_FAIL_STRING);
                    break;
                case ActionID.WORD_SKIP_ID:
                    c.setCellValue(ActionID.WORD_SKIP_STRING);
                    break;
                case ActionID.WORD_SUCCESS_ID:
                    c.setCellValue(ActionID.WORD_SUCCESS_STRING);
                    break;
                case ActionID.WORD_BACK_ID:
                    c.setCellValue(ActionID.BACK_STRING);
                    break;

            }
        }
        if (excelEvent.getMistake() != null) {
            c = row.createCell(ExcelColumnID.EXCEL_MISTAKE_CODE);
            c.setCellValue(excelEvent.getMistake());
        }
        if (excelEvent.getModule() != null && excelEvent.getModule().equals(CUSTOM_ACTION_ID)) {
            c = row.createCell(ExcelColumnID.EXCEL_ACTION_CODE);
            c.setCellValue(excelEvent.getWord());
        } else {
            if (excelEvent.getWord() != null) {
                c = row.createCell(ExcelColumnID.EXCEL_WORD_CODE);
                c.setCellValue(excelEvent.getWord());
            }
            if (excelEvent.getModule() != null) {
                c = row.createCell(ExcelColumnID.EXCEL_BLOCK_CODE);
                c.setCellValue(excelEvent.getModule());
            }
        }
    }

    public static void writeStaticEvents(Context mContext, Row row) {
        Cell c;
        c = row.createCell(ExcelColumnID.EXCEL_REPORT_ID_CODE);
        c.setCellValue(ExcelEvent.reportID);
        c = row.createCell(ExcelColumnID.EXCEL_CHILD_ID_CODE);
        c.setCellValue(ExcelEvent.childID);
        c = row.createCell(ExcelColumnID.EXCEL_VERBATOLOG_ID_CODE);
        c.setCellValue(ExcelEvent.verbatologID);
        c = row.createCell(ExcelColumnID.EXCEL_DEVICE_ID_CODE);
        c.setCellValue(ExcelEvent.deviceID);
        c = row.createCell(ExcelColumnID.EXCEL_BCI_ID_CODE);
        c.setCellValue(ExcelEvent.bciID);
        if (ExcelEvent.expired != false) {
            c = row.createCell(ExcelColumnID.EXCEL_RESERVE_2_CODE);
            c.setCellValue("Срок действия сертификата истек");
        }
        if (row.getRowNum() < 8) {
            c = row.createCell(ExcelColumnID.EXCEL_RESERVE_3_CODE);
            c.setCellValue(ParentsAnswersSharedPrefs.getValue(mContext, Integer.toString(row.getRowNum() - 1)));
        }
    }
}
