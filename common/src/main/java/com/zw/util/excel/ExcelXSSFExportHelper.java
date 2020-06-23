package com.zw.util.excel;

import org.apache.poi.ss.usermodel.ClientAnchor;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.xssf.usermodel.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

public class ExcelXSSFExportHelper {
    private String DATE_PATTERN = "yyyy-MM-dd";
    private int IMAGE_WIDTH = 30;
    private int IMAGE_HEIGHT = 5;
    private int[] maxWidth;
    private int maxRowCount = 100000;
    private String MORE_EXCEL_FLAG = "0001";
    private String MORE_SHEET_FLAG = "0002";

    public ExcelXSSFExportHelper() {
    }

    public ExcelXSSFExportHelper(String datePattern) {
        this.DATE_PATTERN = datePattern;
    }

    public ExcelXSSFExportHelper(int imageWidth, int imageHeight) {
        this.IMAGE_HEIGHT = imageHeight;
        this.IMAGE_WIDTH = imageWidth;
    }

    public ExcelXSSFExportHelper(String datePatter, int imageWidht, int imageHeight) {
        this.DATE_PATTERN = datePatter;
        this.IMAGE_HEIGHT = imageHeight;
        this.IMAGE_WIDTH = imageWidht;
    }

    public XSSFWorkbook exportExcel(String[] header, List<Object> excelList, String sheetTitle) {
        XSSFWorkbook book = new XSSFWorkbook();
        sheetTitle = this.getSheetTitle(sheetTitle);
        XSSFSheet sheet = book.createSheet(sheetTitle);
        this.setExcelContentData(book, sheet, header, excelList);
        System.out.println("——————————————————ExcelXSSFExportHelper:Excel生成成功...");
        return book;
    }

    public XSSFWorkbook exportExcel(String[] header, String[] properties, List<Object> excelList, String sheetTitle) {
        XSSFWorkbook book = new XSSFWorkbook();
        sheetTitle = this.getSheetTitle(sheetTitle);
        XSSFSheet sheet = book.createSheet(sheetTitle);
        this.setExcelContentData(book, sheet, header, properties, excelList);
        System.out.println("——————————————————ExcelXSSFExportHelper:Excel生成成功...");
        return book;
    }

    public XSSFWorkbook exportExcel(String url, String[] properties, List<Object> excelList) throws Exception {
        URL excelUrl = new URL(url);
        XSSFWorkbook book = new XSSFWorkbook(excelUrl.openStream());
        XSSFSheet sheet = book.getSheetAt(0);
        String[] header = null;
        this.setExcelContentData(book, sheet, (String[])header, properties, excelList);
        System.out.println("——————————————————ExcelXSSFExportHelper:Excel生成成功...");
        return book;
    }

    public XSSFWorkbook exportExcel(String sheetTitle, ExcelHeaderExecuter headerExecuter, String[] properties, List<Object> excelList) {
        XSSFWorkbook book = new XSSFWorkbook();
        sheetTitle = this.getSheetTitle(sheetTitle);
        XSSFSheet sheet = book.createSheet(sheetTitle);
        this.setExcelContentData(book, sheet, headerExecuter, properties, excelList);
        System.out.println("——————————————————ExcelXSSFExportHelper:Excel生成成功...");
        return book;
    }

    public static void exportStream(String sheetTitle, String[] header, String[] properties, List<Object> excelList, HttpServletResponse response) throws Exception {
        ExcelXSSFExportHelper excelXSSFExportHelper = new ExcelXSSFExportHelper();
        XSSFWorkbook wb = excelXSSFExportHelper.exportExcel(header, properties, excelList, sheetTitle);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((sheetTitle + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];

            int bytesRead;
            while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (IOException var19) {
            var19.printStackTrace();
            throw var19;
        } finally {
            if (bis != null) {
                bis.close();
            }

            if (bos != null) {
                bos.close();
            }

        }

    }

    public static void exportStream(String sheetTitle, String[] properties, List<Object> excelList, HttpServletResponse response, ExcelHeaderExecuter headerExecuter) throws Exception {
        ExcelXSSFExportHelper excelXSSFExportHelper = new ExcelXSSFExportHelper();
        XSSFWorkbook wb = excelXSSFExportHelper.exportExcel(sheetTitle, headerExecuter, properties, excelList);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((sheetTitle + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];

            int bytesRead;
            while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (UnsupportedEncodingException var19) {
            var19.printStackTrace();
            throw var19;
        } catch (IOException var20) {
            var20.printStackTrace();
            throw var20;
        } finally {
            if (bis != null) {
                bis.close();
            }

            if (bos != null) {
                bos.close();
            }

        }

    }

    public static void exportStream(String url, String sheetTitle, String[] properties, List<Object> excelList, HttpServletResponse response) throws Exception {
        ExcelXSSFExportHelper excelXSSFExportHelper = new ExcelXSSFExportHelper();
        XSSFWorkbook wb = excelXSSFExportHelper.exportExcel(url, properties, excelList);
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            wb.write(os);
            byte[] content = os.toByteArray();
            InputStream is = new ByteArrayInputStream(content);
            response.reset();
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + new String((sheetTitle + ".xlsx").getBytes(), "iso-8859-1"));
            ServletOutputStream out = response.getOutputStream();
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];

            int bytesRead;
            while(-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (UnsupportedEncodingException var19) {
            var19.printStackTrace();
            throw var19;
        } catch (IOException var20) {
            var20.printStackTrace();
            throw var20;
        } finally {
            if (bis != null) {
                bis.close();
            }

            if (bos != null) {
                bos.close();
            }

        }

    }

    public List<XSSFWorkbook> exportExcelForBigData(String[] header, List<Object> excelList, String sheetTitle, String flag) {
        List<XSSFWorkbook> list = new ArrayList();
        int num = excelList.size() % this.maxRowCount == 0 ? excelList.size() / this.maxRowCount : excelList.size() / this.maxRowCount + 1;
        XSSFWorkbook book = new XSSFWorkbook();
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
                XSSFSheet sheet = book.createSheet(newTitle);
                this.setExcelContentData(book, sheet, header, newList);
            }
        }

        if (this.MORE_SHEET_FLAG.equals(flag)) {
            list.add(book);
        }

        return list;
    }

    public List<XSSFWorkbook> exportExcelForBigData(String[] header, String[] properties, List<Object> excelList, String sheetTitle, String flag) {
        List<XSSFWorkbook> list = new ArrayList();
        int num = excelList.size() % this.maxRowCount == 0 ? excelList.size() / this.maxRowCount : excelList.size() / this.maxRowCount + 1;
        XSSFWorkbook book = new XSSFWorkbook();
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
                XSSFSheet sheet = book.createSheet(newTitle);
                this.setExcelContentData(book, sheet, header, properties, newList);
            }
        }

        if (this.MORE_SHEET_FLAG.equals(flag)) {
            list.add(book);
        }

        return list;
    }

    private void setExcelContentData(XSSFWorkbook book, XSSFSheet sheet, String[] header, List<Object> excelList) {
        XSSFCellStyle headerStyle = book.createCellStyle();
        this.setHeaderStyle(headerStyle, book);
        XSSFCellStyle cellStyle = book.createCellStyle();
        this.setCellStyle(cellStyle, book);
        this.createHeader(sheet, headerStyle, header);
        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        int index = 0;
        Object t = null;
        XSSFCell cell = null;
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
            XSSFRow row = sheet.createRow(index);
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

    private void setExcelContentData(XSSFWorkbook book, XSSFSheet sheet, String[] header, String[] properties, List<Object> excelList) {
        XSSFCellStyle headerStyle = book.createCellStyle();
        this.setHeaderStyle(headerStyle, book);
        XSSFCellStyle cellStyle = book.createCellStyle();
        this.setCellStyle(cellStyle, book);
        XSSFRow row = null;
        if (header != null && header.length > 0) {
            this.createHeader(sheet, headerStyle, header);
        }

        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        int index = 0;
        Object t = null;
        XSSFCell cell = null;
        Object o = null;
        Class clazz = null;
        PropertyDescriptor pd = null;
        Method getMethod = null;
        Iterator<Object> it = excelList.iterator();
        this.maxWidth = new int[properties.length];

        while(it.hasNext()) {
            ++index;
            row = sheet.createRow(index);
            t = it.next();

            for(int i = 0; i < properties.length; ++i) {
                cell = row.createCell(i);
                cell.setCellStyle(cellStyle);
                o = null;

                try {
                    if (t instanceof Map) {
                        o = ((Map)t).get(properties[i]);
                    } else {
                        clazz = t.getClass();
                        pd = new PropertyDescriptor(properties[i], clazz);
                        getMethod = pd.getReadMethod();
                        if (pd != null) {
                            o = getMethod.invoke(t);
                        }
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

    private void setExcelContentData(XSSFWorkbook book, XSSFSheet sheet, ExcelHeaderExecuter headerExecuter, String[] properties, List<Object> excelList) {
        XSSFCellStyle headerStyle = book.createCellStyle();
        this.setHeaderStyle(headerStyle, book);
        XSSFCellStyle cellStyle = book.createCellStyle();
        this.setCellStyle(cellStyle, book);
        List<XSSFRow> headers = headerExecuter.createHeader(book, sheet, headerStyle);
        XSSFDrawing patriarch = sheet.createDrawingPatriarch();
        int index = headers.size();
        Object t = null;
        XSSFRow row = null;
        XSSFCell cell = null;
        Object o = null;
        Class clazz = null;
        PropertyDescriptor pd = null;
        Method getMethod = null;
        Iterator<Object> it = excelList.iterator();

        for(this.maxWidth = new int[properties.length]; it.hasNext(); ++index) {
            row = sheet.createRow(index);
            t = it.next();

            for(int i = 0; i < properties.length; ++i) {
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
                } catch (IntrospectionException var21) {
                    var21.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var21.getMessage());
                } catch (IllegalAccessException var22) {
                    var22.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var22.getMessage());
                } catch (IllegalArgumentException var23) {
                    var23.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var23.getMessage());
                } catch (InvocationTargetException var24) {
                    var24.printStackTrace();
                    System.out.println("——————————————————创建Excel数据列表时出错。method:setDataRow,message：" + var24.getMessage());
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

    private void setHeaderStyle(XSSFCellStyle headerStyle, XSSFWorkbook book) {
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = book.createFont();
        font.setFontHeightInPoints((short)11);
        font.setBold(true);
        font.setColor(new XSSFColor(Color.BLACK));
        headerStyle.setFont(font);
    }

    private void setCellStyle(XSSFCellStyle cellStyle, XSSFWorkbook book) {
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        XSSFFont font = book.createFont();
        font.setFontHeightInPoints((short)12);
        cellStyle.setFont(font);
    }

    private XSSFRow createHeader(XSSFSheet sheet, XSSFCellStyle headerStyle, String[] header) {
        XSSFRow headRow = sheet.createRow(0);
        headRow.setHeightInPoints(20.0F);
        if (header != null && header.length > 0) {
            XSSFCell cell = null;

            for(int i = 0; i < header.length; ++i) {
                cell = headRow.createCell(i);
                cell.setCellStyle(headerStyle);
                XSSFRichTextString text = new XSSFRichTextString(header[i]);
                cell.setCellValue(text);
            }
        }

        return headRow;
    }

    private void setCellData(XSSFRow row, int index, int i, Object value, XSSFCell cell, XSSFSheet sheet, XSSFDrawing patriarch, XSSFWorkbook book) {
        String textValue = null;
        if (value instanceof Date) {
            Date date = (Date)value;
            SimpleDateFormat sdf = new SimpleDateFormat(this.DATE_PATTERN);
            textValue = sdf.format(date);
        }

        if (value instanceof byte[]) {
            row.setHeightInPoints((float)((short)(this.IMAGE_HEIGHT * 10)));
            sheet.setColumnWidth(i, this.IMAGE_WIDTH * 256);
            XSSFClientAnchor anchor = new XSSFClientAnchor(0, 0, 1023, 255, (short)i, index, (short)i, index);
            anchor.setAnchorType(ClientAnchor.AnchorType.DONT_MOVE_AND_RESIZE);
            byte[] bsValue = (byte[])((byte[])value);
            patriarch.createPicture(anchor, book.addPicture(bsValue, 5));
        } else if (value != null) {
            textValue = String.valueOf(value);
        } else {
            textValue = "";
        }

        if (textValue != null) {
            Pattern pattern = compile("^//d+(//.//d+)?$");
            Matcher matcher = pattern.matcher(textValue);
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
        if (width >= 10000) {
            width = 10000;
        }

        if (this.maxWidth[i] <= width) {
            this.maxWidth[i] = width;
        }

    }
}
