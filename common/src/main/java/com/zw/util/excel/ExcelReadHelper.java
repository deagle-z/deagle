package com.zw.util.excel;

import com.zw.util.DateFormatUtils;
import com.zw.util.DateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelReadHelper {

    public ExcelReadHelper() {
    }

        public static List<Object> excelRead(File file, String[] properties, Class obj) throws Exception {
        Object book = null;

        try {
            book = new XSSFWorkbook(new FileInputStream(file));
        } catch (Exception var5) {
            book = new HSSFWorkbook(new FileInputStream(file));
        }

        return getExcelContent((Workbook)book, properties, obj);
    }

        public static List<Object> excelRead(String filePath, String[] properties, Class obj) throws Exception {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new Exception("指定的文件不存在");
        } else {
            return excelRead(file, properties, obj);
        }
    }

        private static List<Object> getExcelContent(Workbook book, String[] properties, Class obj) throws Exception {
        List<Object> resultList = new ArrayList();
        Map<String, Method> methodMap = getObjectSetterMethod(obj);
        Map<String, Field> fieldMap = getObjectField(obj);

        for(int numSheet = 0; numSheet < book.getNumberOfSheets(); ++numSheet) {
            Sheet sheet = book.getSheetAt(numSheet);
            if (sheet != null) {
                for(int numRow = 1; numRow < sheet.getLastRowNum(); ++numRow) {
                    Row row = sheet.getRow(numRow);
                    if (row != null) {
                        resultList.add(getObject(row, properties, methodMap, fieldMap, obj));
                    }
                }
            }
        }

        return resultList;
    }

        private static Object getObject(Row row, String[] properties, Map<String, Method> methodMap, Map<String, Field> fieldMap, Class obj) throws Exception {
        Object object = obj.newInstance();

        for(int numCell = 0; numCell < row.getLastCellNum(); ++numCell) {
            Cell cell = row.getCell(numCell);
            if (cell != null) {
                String cellValue = getValue(cell);
                String property = properties[numCell].toLowerCase();
                Field field = (Field)fieldMap.get(property);
                Method method = (Method)methodMap.get(property);
                setObjectPropertyValue(object, field, method, cellValue);
            }
        }

        return object;
    }

        private static void setObjectPropertyValue(Object obj, Field field, Method method, String value) throws Exception {
        Object[] oo = new Object[1];
        String type = field.getType().getName();
        if (!"java.lang.String".equals(type) && !"String".equals(type)) {
            if (!"java.lang.Integer".equals(type) && !"java.lang.int".equals(type) && !"Integer".equals(type) && !"int".equals(type)) {
                if (!"java.lang.Float".equals(type) && !"java.lang.float".equals(type) && !"Float".equals(type) && !"float".equals(type)) {
                    if (!"java.lang.Double".equals(type) && !"java.lang.double".equals(type) && !"Double".equals(type) && !"double".equals(type)) {
                        if (!"java.math.BigDecimal".equals(type) && !"BigDecimal".equals(type)) {
                            if (!"java.util.Date".equals(type) && !"Date".equals(type)) {
                                if ("java.sql.Timestamp".equals(type)) {
                                    if (value.length() > 0) {
                                        oo[0] = DateFormatUtils.formatDate(value, "yyyyMMddHH24mmss");
                                    }
                                } else if (!"java.lang.Boolean".equals(type) && !"Boolean".equals(type)) {
                                    if (("java.lang.Long".equals(type) || "java.lang.long".equals(type) || "Long".equals(type) || "long".equals(type)) && value.length() > 0) {
                                        oo[0] = Long.valueOf(value);
                                    }
                                } else if (value.length() > 0) {
                                    oo[0] = Boolean.valueOf(value);
                                }
                            } else if (value.length() > 0) {
                                if (value.length() != 19 && value.length() != 14) {
                                    oo[0] = DateUtil.string2Date(value, "yyyyMMdd");
                                } else {
                                    oo[0] = DateUtil.string2Date(value, "yyyyMMddHH24mmss");
                                }
                            }
                        } else if (value.length() > 0) {
                            oo[0] = new BigDecimal(value);
                        }
                    } else if (value.length() > 0) {
                        oo[0] = Double.valueOf(value);
                    }
                } else if (value.length() > 0) {
                    oo[0] = Float.valueOf(value);
                }
            } else if (value.length() > 0) {
                oo[0] = Integer.valueOf(value);
            }
        } else {
            oo[0] = value;
        }

        try {
            method.invoke(obj, oo);
        } catch (Exception var7) {
            var7.printStackTrace();
            throw var7;
        }
    }

        private static String getValue(Cell cell) {
        if (cell.getCellType() == 4) {
            return String.valueOf(cell.getBooleanCellValue());
        } else {
            return cell.getCellType() == 0 ? NumberToTextConverter.toText(cell.getNumericCellValue()) : String.valueOf(cell.getStringCellValue());
        }
    }

        private static Map<String, Method> getObjectSetterMethod(Class object) {
        Field[] fields = object.getDeclaredFields();
        Method[] methods = object.getDeclaredMethods();
        Map<String, Method> methodMap = new HashMap();
        Field[] var4 = fields;
        int var5 = fields.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            Field field = var4[var6];
            String attri = field.getName();
            Method[] var9 = methods;
            int var10 = methods.length;

            for(int var11 = 0; var11 < var10; ++var11) {
                Method method = var9[var11];
                String meth = method.getName();
                if (meth != null && "set".equals(meth.substring(0, 3)) && Modifier.isPublic(method.getModifiers()) && ("set" + Character.toUpperCase(attri.charAt(0)) + attri.substring(1)).equals(meth)) {
                    methodMap.put(attri.toLowerCase(), method);
                    break;
                }
            }
        }

        return methodMap;
    }

        private static Map<String, Field> getObjectField(Class object) {
        Field[] fields = object.getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap();
        Field[] var3 = fields;
        int var4 = fields.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            String attri = field.getName();
            fieldMap.put(attri.toLowerCase(), field);
        }

        return fieldMap;
    }

}
