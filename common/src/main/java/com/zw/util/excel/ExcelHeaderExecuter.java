package com.zw.util.excel;

import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.List;

public interface ExcelHeaderExecuter {
    List<XSSFRow> createHeader(XSSFWorkbook var1, XSSFSheet var2, XSSFCellStyle var3);
}
