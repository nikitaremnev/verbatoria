package com.remnev.verbatoriamini.util;

import com.remnev.verbatoriamini.model.ExcelBCI;
import com.remnev.verbatoriamini.model.ExcelColumnID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

/**
 * Created by nikitaremnev on 27.03.16.
 */
public class BCIExcelWriter {

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

}
