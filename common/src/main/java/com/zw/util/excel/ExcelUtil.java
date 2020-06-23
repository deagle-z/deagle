//package com.zw.util.excel;
//
//import com.zw.exception.BusinessException;
//import com.zw.util.FileUtil;
//import lombok.extern.slf4j.Slf4j;
//import net.sf.jxls.reader.ReaderBuilder;
//import net.sf.jxls.reader.XLSReadStatus;
//import net.sf.jxls.reader.XLSReader;
//import net.sf.jxls.transformer.XLSTransformer;
//import org.apache.commons.io.IOUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.xml.sax.SAXException;
//import sun.misc.BASE64Encoder;
//
//import javax.servlet.http.HttpServletResponse;
//import java.io.*;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//@Slf4j
//public class ExcelUtil {
//
//    private static final Logger LOGGER = LoggerFactory.getLogger(ExcelUtil.class);
//
//    public static <T> XLSReadStatus importExcel(final String templateName, final InputStream fileStream,
//                                                final String itemsName, final List<T> importList) {
//
//        final File template;
//        InputStream inputXLS = null;
//        InputStream inputXML = null;
//        XLSReadStatus status;
//        try {
//            //            template = ResourceUtils.getFile("" + templateName);
//            template = new File(templateName);
//
//            inputXML = new BufferedInputStream(new FileInputStream(template));
//            final XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
//            inputXLS = new BufferedInputStream(fileStream);
//            final Map<String, Object> beans = new HashMap<>();
//            beans.put(itemsName, importList);
//            status = mainReader.read(inputXLS, beans);
//        } catch (final IOException | InvalidFormatException | SAXException e) {
//            LOGGER.error(e.getMessage());
//            throw new BusinessException("excel导入失败");
//        } finally {
//            IOUtils.closeQuietly(inputXML);
//            IOUtils.closeQuietly(inputXLS);
//        }
//        return status;
//    }
//
//    public static void exportListFile(final String templetFilePath, final String fileName, final String sheetName,
//                                      final List<?> dataList, final HttpServletResponse response) {
//
//        FileUtil.wrapDownloadResponse(response, fileName);
//        final ArrayList<List<?>> objects = new ArrayList<>();
//        objects.add(dataList);
//        InputStream in = null;
//        try {
//            final List<String> sheetNames = new ArrayList<>();
//            sheetNames.add(sheetName != null ? sheetName : "Sheet1");
//            final XLSTransformer transformer = new XLSTransformer();
//            in = ExcelUtil.class.getClassLoader().getResourceAsStream(templetFilePath);
//            final Workbook workbook = transformer.transformMultipleSheetsList(in, objects, sheetNames, "list",
//                    new HashMap<>(), 0);
//            workbook.write(response.getOutputStream());
//        } catch (final Exception e) {
//            LOGGER.error("导出模板失败", e);
//        } finally {
//            IOUtils.closeQuietly(in);
//        }
//    }
//
//    public static void exportFile(final String templetFilePath, final String fileName,
//                                  final Map<String, Object> dataMap, final HttpServletResponse response) {
//
//        exportFile(templetFilePath, fileName, null, dataMap, response);
//    }
//
////    public static MultipartFile exportFileSave(final String templetFilePath, final String fileName, final String sheetName, final String title, final Map<String, Object> dataMap) {
////
//////        FileUtil.wrapDownloadResponse(response, fileName);
////        InputStream in = null;
////        try {
////            final XLSTransformer transformer = new XLSTransformer();
////            in = ExcelUtil.class.getClassLoader().getResourceAsStream(templetFilePath);
////            final Workbook workbook = transformer.transformXLS(in, dataMap);
////            if (StringUtils.isNotBlank(sheetName)) {
////                workbook.setSheetName(0, sheetName);
////            }
////            workbook.setForceFormulaRecalculation(true);
////            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
////            workbook.write(outputStream);
//////            workbook.write(response.getOutputStream());
////            final MultipartFile file = OutStreamTomultipartFile(outputStream, title);
////            return file;
////        } catch (final Exception e) {
////            LOGGER.error("导出xls失败", e);
////        } finally {
////            IOUtils.closeQuietly(in);
////        }
////        return null;
////    }
//
//
//    public static void exportFile(final String templetFilePath, final String fileName, final String sheetName,
//                                  final Map<String, Object> dataMap, final HttpServletResponse response) {
//
//        FileUtil.wrapDownloadResponse(response, fileName);
//        InputStream in = null;
//        try {
//            final XLSTransformer transformer = new XLSTransformer();
//            in = ExcelUtil.class.getClassLoader().getResourceAsStream(templetFilePath);
//            final Workbook workbook = transformer.transformXLS(in, dataMap);
//            if (StringUtils.isNotBlank(sheetName)) {
//                workbook.setSheetName(0, sheetName);
//            }
//            workbook.setForceFormulaRecalculation(true);
//            workbook.write(response.getOutputStream());
//        } catch (final Exception e) {
//            LOGGER.error("导出xls失败", e);
//        } finally {
//            IOUtils.closeQuietly(in);
//        }
//    }
//
//    public static Workbook exportFileWithWorkBook(final String templetFilePath, final String fileName,
//                                                  final Map<String, Object> dataMap, final HttpServletResponse response) {
//
//        FileUtil.wrapDownloadResponse(response, fileName);
//        return exportFileWithWorkBookNoResponse(templetFilePath, dataMap);
//    }
//
//    public static Workbook exportFileWithWorkBookNoResponse(final String templetFilePath,
//                                                            final Map<String, Object> dataMap) {
//
//        InputStream in = null;
//        try {
//            final XLSTransformer transformer = new XLSTransformer();
//            in = ExcelUtil.class.getClassLoader().getResourceAsStream(templetFilePath);
//            final Workbook workbook = transformer.transformXLS(in, dataMap);
//            return workbook;
//        } catch (final Exception e) {
//            LOGGER.error("生成excel失败!", e);
//            throw new BusinessException("导出失败");
//        } finally {
//            IOUtils.closeQuietly(in);
//        }
//    }
////
////    public static MultipartFile OutStreamTomultipartFile(final ByteArrayOutputStream outputStream, final String title) {
////        final ByteArrayInputStream bais = new ByteArrayInputStream(outputStream.toByteArray());
////        final String strBase64 = ioToBase64(bais);
////        return base64ToMultipart(strBase64, title, bais);
////    }
//
//    private static String ioToBase64(final InputStream in) {
//        String strBase64 = null;
//        try {
//            //in.available()//返回文件的字节长度
//            final byte[] bytes = new byte[in.available()];
//            in.read(bytes);
//            // 将文件中的内容读入到数组中
//            strBase64 = new BASE64Encoder().encode(bytes);   //将字节流数组转换为字符串
//            in.close();
//        } catch (IOException e) {
//            log.error("将字节流数组转换为字符串出错!", e);
//        }
//        return strBase64;
//    }
//
////    private static MultipartFile base64ToMultipart(final String base64, final String title, final InputStream in) {
////        byte[] b = null;
////        try {
////            final BASE64Decoder decoder = new BASE64Decoder();
////            b = decoder.decodeBuffer(base64);
////            for (int i = 0; i < b.length; ++i) {
////                if (b[i] < 0) {
////                    b[i] += 256;
////                }
////            }
////        } catch (Exception e) {
////            log.error("base64转byte数组失败!", e);
////        }
////        return new BASE64DecodedMultipartFileDto(b, title, in);
////    }
//}
