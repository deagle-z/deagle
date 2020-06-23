package com.zw.util.excel;

import org.apache.poi.hssf.usermodel.*;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ExcelExportHelper {
    private String DATE_PATTERN = "yyyy-MM-dd";
    private int IMAGE_WIDTH = 30;
    private int IMAGE_HEIGHT = 5;
    private int[] maxWidth;
    private int maxRowCount = 2500;
    private String MORE_EXCEL_FLAG = "0001";
    private String MORE_SHEET_FLAG = "0002";

    public ExcelExportHelper() {
    }

    public ExcelExportHelper(String datePattern) {
        this.DATE_PATTERN = datePattern;
    }

    public ExcelExportHelper(int imageWidth, int imageHeight) {
        this.IMAGE_HEIGHT = imageHeight;
        this.IMAGE_WIDTH = imageWidth;
    }

    public ExcelExportHelper(String datePatter, int imageWidht, int imageHeight) {
        this.DATE_PATTERN = datePatter;
        this.IMAGE_HEIGHT = imageHeight;
        this.IMAGE_WIDTH = imageWidht;
    }

    public HSSFWorkbook exportExcel(String[] header, List<Object> excelList, String sheetTitle) {
        HSSFWorkbook book = new HSSFWorkbook();
        sheetTitle = this.getSheetTitle(sheetTitle);
        HSSFSheet sheet = book.createSheet(sheetTitle);
        this.setExcelContentData(book, sheet, header, excelList);
        System.out.println("——————————————————ExcelExportHelper:Excel生成成功...");
        return book;
    }

    public HSSFWorkbook exportExcel(String[] header, String[] properties, List<Object> excelList, String sheetTitle) {
        HSSFWorkbook book = new HSSFWorkbook();
        sheetTitle = this.getSheetTitle(sheetTitle);
        HSSFSheet sheet = book.createSheet(sheetTitle);
        this.setExcelContentData(book, sheet, header, properties, excelList);
        System.out.println("——————————————————ExcelExportHelper:Excel生成成功...");
        return book;
    }

    public void exportExcelAndSave(String[] header, List<Object> excelList, String sheetTitle, String filePath, String fileName) {
        HSSFWorkbook book = this.exportExcel(header, excelList, sheetTitle);
        this.saveExcel(book, filePath, fileName);
    }

    public void exportExcelAndSave(String[] header, String[] properties, List<Object> excelList, String sheetTitle, String filePath, String fileName) {
        HSSFWorkbook book = this.exportExcel(header, properties, excelList, sheetTitle);
        this.saveExcel(book, filePath, fileName);
    }

    public void exportExcelAndZip(String[] header, List<Object> excelList, String sheetTitle, String filePath, String excelName, String zipName) {
        HSSFWorkbook book = this.exportExcel(header, excelList, sheetTitle);
        List<HSSFWorkbook> books = new ArrayList();
        books.add(book);
        this.zipExcelAndSave(books, filePath, zipName, excelName);
    }

    public void exportExcelAndZip(String[] header, String[] properties, List<Object> excelList, String sheetTitle, String filePath, String excelName, String zipName) {
        HSSFWorkbook book = this.exportExcel(header, properties, excelList, sheetTitle);
        List<HSSFWorkbook> books = new ArrayList();
        books.add(book);
        this.zipExcelAndSave(books, filePath, zipName, excelName);
    }

    public List<HSSFWorkbook> exportExcelForBigData(String[] header, List<Object> excelList, String sheetTitle, String flag) {
        List<HSSFWorkbook> list = new ArrayList();
        int num = excelList.size() % this.maxRowCount == 0 ? excelList.size() / this.maxRowCount : excelList.size() / this.maxRowCount + 1;
        HSSFWorkbook book = new HSSFWorkbook();
        List<Object> newList = null;
        String newTitle = null;

        for(int i = 0; i < num; ++i) {
            int beginRowNum = this.maxRowCount * i;
            int endRowNum = this.maxRowCount * (i + 1) > excelList.size() ? excelList.size() : this.maxRowCount * (i + 1);
            newList = excelList.subList(beginRowNum, endRowNum);
            newTitle = this.getSheetTitle(sheetTitle) + "_" + i;
            if (this.MORE_EXCEL_FLAG.equals(flag)) {
                book = this.exportExcel(header, newList, newTitle);
                list.add(book);
            } else if (this.MORE_SHEET_FLAG.equals(flag)) {
                HSSFSheet sheet = book.createSheet(newTitle);
                this.setExcelContentData(book, sheet, header, newList);
            }
        }

        if (this.MORE_SHEET_FLAG.equals(flag)) {
            list.add(book);
        }

        return list;
    }

    public List<HSSFWorkbook> exportExcelForBigData(String[] header, String[] properties, List<Object> excelList, String sheetTitle, String flag) {
        List<HSSFWorkbook> list = new ArrayList();
        int num = excelList.size() % this.maxRowCount == 0 ? excelList.size() / this.maxRowCount : excelList.size() / this.maxRowCount + 1;
        HSSFWorkbook book = new HSSFWorkbook();
        List<Object> newList = null;
        String newTitle = null;

        for(int i = 0; i < num; ++i) {
            int beginRowNum = this.maxRowCount * i;
            int endRowNum = this.maxRowCount * (i + 1) > excelList.size() ? excelList.size() : this.maxRowCount * (i + 1);
            newList = excelList.subList(beginRowNum, endRowNum);
            newTitle = this.getSheetTitle(sheetTitle) + "_" + i;
            if (this.MORE_EXCEL_FLAG.equals(flag)) {
                book = this.exportExcel(header, properties, newList, newTitle);
                list.add(book);
            } else if (this.MORE_SHEET_FLAG.equals(flag)) {
                HSSFSheet sheet = book.createSheet(newTitle);
                this.setExcelContentData(book, sheet, header, properties, newList);
            }
        }

        if (this.MORE_SHEET_FLAG.equals(flag)) {
            list.add(book);
        }

        return list;
    }

    public void exportExcelForBigDataAndSave(String[] header, List<Object> excelList, String sheetTitle, String flag, String filePath, String fileName) {
        List<HSSFWorkbook> books = this.exportExcelForBigData(header, excelList, sheetTitle, flag);
        String _fileName = "";

        for(int i = 0; i < books.size(); ++i) {
            HSSFWorkbook book = (HSSFWorkbook)books.get(i);
            _fileName = this.getFileName(fileName) + "_0" + i;
            this.saveExcel(book, filePath, _fileName);
        }

    }

    public void exportExcelForBigDataAndSave(String[] header, String[] properties, List<Object> excelList, String sheetTitle, String flag, String filePath, String fileName) {
        List<HSSFWorkbook> books = this.exportExcelForBigData(header, properties, excelList, sheetTitle, flag);
        String _fileName = "";

        for(int i = 0; i < books.size(); ++i) {
            HSSFWorkbook book = (HSSFWorkbook)books.get(i);
            _fileName = this.getFileName(fileName) + "_0" + i;
            this.saveExcel(book, filePath, _fileName);
        }

    }

    public void exportExcelForBigDataAndZipAndSave(String[] header, List<Object> excelList, String sheetTitle, String flag, String filePath, String excelName, String zipName) {
        List<HSSFWorkbook> books = this.exportExcelForBigData(header, excelList, sheetTitle, flag);
        this.zipExcelAndSave(books, filePath, zipName, excelName);
    }

    public void exportExcelForBigDataAndZipAndSave(String[] header, String[] properties, List<Object> excelList, String sheetTitle, String flag, String filePath, String excelName, String zipName) {
        List<HSSFWorkbook> books = this.exportExcelForBigData(header, properties, excelList, sheetTitle, flag);
        this.zipExcelAndSave(books, filePath, zipName, excelName);
    }

    private void setExcelContentData(HSSFWorkbook book, HSSFSheet sheet, String[] header, List<Object> excelList) {
        HSSFCellStyle headerStyle = book.createCellStyle();
        this.setHeaderStyle(headerStyle, book);
        HSSFCellStyle cellStyle = book.createCellStyle();
        this.setCellStyle(cellStyle, book);
        this.createHeader(sheet, headerStyle, header);
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        int index = 0;
        Object t = null;
        HSSFCell cell = null;
        Field field = null;
        String fieldName = null;
        String getMethodName = null;
        Class tCls = null;
        Method getMethod = null;
        Object value = null;
        Iterator<Object> it = excelList.iterator();
        this.maxWidth = new int[header.length];

        while(it.hasNext()) {
            ++index;
            HSSFRow row = sheet.createRow(index);
            t = it.next();
            Field[] fields = t.getClass().getDeclaredFields();

            for(short i = 0; i < fields.length; ++i) {
                cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                field = fields[i];
                fieldName = field.getName();
                getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);

                try {
                    tCls = t.getClass();
                    getMethod = tCls.getMethod(getMethodName);
                    value = getMethod.invoke(t);
                    this.setCellData(row, index, i, value, cell, sheet, patriarch, book);
                } catch (NoSuchMethodException var22) {
                    var22.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var22.getMessage());
                } catch (SecurityException var23) {
                    var23.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var23.getMessage());
                } catch (IllegalAccessException var24) {
                    var24.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var24.getMessage());
                } catch (IllegalArgumentException var25) {
                    var25.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var25.getMessage());
                } catch (InvocationTargetException var26) {
                    var26.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var26.getMessage());
                }
            }
        }

        System.out.println("-------------------------填充Excel数据成功..........");
    }

    private void setExcelContentData(HSSFWorkbook book, HSSFSheet sheet, String[] header, String[] properties, List<Object> excelList) {
        HSSFCellStyle headerStyle = book.createCellStyle();
        this.setHeaderStyle(headerStyle, book);
        HSSFCellStyle cellStyle = book.createCellStyle();
        this.setCellStyle(cellStyle, book);
        this.createHeader(sheet, headerStyle, header);
        HSSFPatriarch patriarch = sheet.createDrawingPatriarch();
        int index = 0;
        Object t = null;
        HSSFCell cell = null;
        Object o = null;
        Class clazz = null;
        PropertyDescriptor pd = null;
        Method getMethod = null;
        Iterator<Object> it = excelList.iterator();
        this.maxWidth = new int[header.length];

        while(it.hasNext()) {
            ++index;
            HSSFRow row = sheet.createRow(index);
            t = it.next();

            for(int i = 0; i < header.length; ++i) {
                cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                o = null;

                try {
                    clazz = t.getClass();
                    pd = new PropertyDescriptor(properties[i], clazz);
                    getMethod = pd.getReadMethod();
                    if (pd != null) {
                        o = getMethod.invoke(t);
                    }

                    this.setCellData(row, index, i, o, cell, sheet, patriarch, book);
                } catch (IntrospectionException var20) {
                    var20.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var20.getMessage());
                } catch (IllegalAccessException var21) {
                    var21.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var21.getMessage());
                } catch (IllegalArgumentException var22) {
                    var22.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var22.getMessage());
                } catch (InvocationTargetException var23) {
                    var23.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var23.getMessage());
                }
            }
        }

        System.out.println("——————————————————填充Excel数据成功..........");
    }

    private String getSheetTitle(String sheetTitle) {
        String title = null;
        if (sheetTitle != null && !"".equals(sheetTitle)) {
            title = sheetTitle;
        } else {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
            title = sdf.format(date);
        }

        return title;
    }

    private void setHeaderStyle(HSSFCellStyle headerStyle, HSSFWorkbook book) {
        headerStyle.setAlignment((short)2);
        headerStyle.setVerticalAlignment((short)1);
        HSSFFont font = book.createFont();
        font.setFontHeightInPoints((short)12);
        font.setBoldweight((short)700);
        font.setColor((short)12);
        headerStyle.setFont(font);
    }

    private void setCellStyle(HSSFCellStyle cellStyle, HSSFWorkbook book) {
        cellStyle.setAlignment((short)2);
        cellStyle.setVerticalAlignment((short)1);
        HSSFFont font = book.createFont();
        font.setFontHeightInPoints((short)12);
        cellStyle.setFont(font);
    }

    private HSSFRow createHeader(HSSFSheet sheet, HSSFCellStyle headerStyle, String[] header) {
        HSSFRow headRow = sheet.createRow(0);
        headRow.setHeightInPoints(20.0F);
        HSSFCell cell = null;

        for(int i = 0; i < header.length; ++i) {
            cell = headRow.createCell(i);
            cell.setCellStyle(headerStyle);
            HSSFRichTextString text = new HSSFRichTextString(header[i]);
            cell.setCellValue(text);
        }

        return headRow;
    }

    private void setCellData(HSSFRow row, int index, int i, Object value, HSSFCell cell, HSSFSheet sheet, HSSFPatriarch patriarch, HSSFWorkbook book) {
        String textValue = null;
        if (value instanceof Date) {
            Date date = (Date)value;
            SimpleDateFormat sdf = new SimpleDateFormat(this.DATE_PATTERN);
            textValue = sdf.format(date);
        }

        if (value instanceof byte[]) {
            row.setHeightInPoints((float)((short)(this.IMAGE_HEIGHT * 10)));
            sheet.setColumnWidth(i, this.IMAGE_WIDTH * 256);
            HSSFClientAnchor anchor = new HSSFClientAnchor(0, 0, 1023, 255, (short)i, index, (short)i, index);
            anchor.setAnchorType(3);
            byte[] bsValue = (byte[])((byte[])value);
            patriarch.createPicture(anchor, book.addPicture(bsValue, 5));
        } else if (value != null) {
            textValue = String.valueOf(value);
        } else {
            textValue = "";
        }

        if (textValue != null) {
            Pattern p = Pattern.compile("^//d+(//.//d+)?$");
            Matcher matcher = p.matcher(textValue);
            this.setCellMaxWidth(textValue, i);
            sheet.setColumnWidth(i, this.maxWidth[i]);
            row.setHeightInPoints(20.0F);
            if (matcher.matches()) {
                cell.setCellValue(Double.parseDouble(textValue));
            } else {
                cell.setCellValue(textValue);
            }
        }

    }

    private String getFileName(String fileName) {
        if (fileName == null || "".equals(fileName)) {
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHH24mmss");
            Random random = new Random();
            fileName = sdf.format(date) + String.valueOf(Math.abs(random.nextInt() * 1000000));
        }

        return fileName;
    }

    private void setCellMaxWidth(String textValue, int i) {
        int size = textValue.length();
        int width = (size + 6) * 256;
        if (this.maxWidth[i] <= width) {
            this.maxWidth[i] = width;
        }

    }

    private void saveExcel(HSSFWorkbook book, String filePath, String fileName) {
        this.checkFilePathIsExist(filePath);
        fileName = this.getFileName(fileName);
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(filePath + "\\" + fileName + ".xls");
            book.write(out);
            System.out.println("——————————————————保存Excel文件成功，保存路径：" + filePath + "\\" + fileName + ".xls");
        } catch (Exception var14) {
            var14.printStackTrace();
            System.out.println("——————————————————保存Excel文件失败。exportExcelForListAndSave,message：" + var14.getMessage());
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException var13) {
                    var13.printStackTrace();
                }
            }

        }

    }

    private void zipExcelAndSave(List<HSSFWorkbook> books, String filePath, String zipName, String excelName) {
        this.checkFilePathIsExist(filePath);
        zipName = this.getFileName(zipName);
        excelName = this.getFileName(excelName);
        FileOutputStream out = null;
        ZipOutputStream zip = null;

        try {
            out = new FileOutputStream(filePath + "\\" + zipName + ".zip");
            zip = new ZipOutputStream(out);
            HSSFWorkbook book = null;
            String _excelName = "";

            for(int i = 0; i < books.size(); ++i) {
                book = (HSSFWorkbook)books.get(i);
                _excelName = this.getFileName(excelName) + "_0" + i;
                ZipEntry entry = new ZipEntry(_excelName + ".xls");
                zip.putNextEntry(entry);
                book.write(zip);
            }

            System.out.println("——————————————————保存Excel文件成功，保存路径：" + filePath + "\\" + zipName + ".xls");
        } catch (FileNotFoundException var26) {
            var26.printStackTrace();
            System.out.println("——————————————————保存Excel文件失败。method:exportExcelForBigDataAndSave,message：" + var26.getMessage());
        } catch (IOException var27) {
            var27.printStackTrace();
            System.out.println("——————————————————保存Excel文件失败。method:exportExcelForBigDataAndSave,message：" + var27.getMessage());
        } finally {
            if (zip != null) {
                try {
                    zip.flush();
                    zip.close();
                } catch (IOException var25) {
                    var25.printStackTrace();
                }
            }

            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException var24) {
                    var24.printStackTrace();
                }
            }

        }

    }

    private void checkFilePathIsExist(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdirs();
        }

    }
}
