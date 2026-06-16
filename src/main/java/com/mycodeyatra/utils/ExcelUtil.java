package com.mycodeyatra.utils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
public class ExcelUtil {
    public static Object[][] getSheetData(String filePath, String sheetName) {
        Object[][] data = null;
        try (FileInputStream fis = new FileInputStream(filePath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {
            XSSFSheet sheet = workbook.getSheet(sheetName);
            if (sheet == null) {
                throw new IllegalArgumentException("Sheet " + sheetName + " does not exist in the workbook.");
            }
            int rowCount = sheet.getPhysicalNumberOfRows();
            if (rowCount <= 1) {
                return new Object[0][0];
            }
            XSSFRow headerRow = sheet.getRow(0);
            int colCount = headerRow.getLastCellNum();
            // Allocate memory for data (excluding header row)
            data = new Object[rowCount - 1][colCount];
            DataFormatter formatter = new DataFormatter();
            for (int r = 1; r < rowCount; r++) {
                XSSFRow row = sheet.getRow(r);
                if (row == null) continue;
                for (int c = 0; c < colCount; c++) {
                    XSSFCell cell = row.getCell(c);
                    data[r - 1][c] = formatter.formatCellValue(cell);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading Excel file: " + e.getMessage());
        }
        return data;
    }
}