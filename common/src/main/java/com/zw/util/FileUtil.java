package com.chinamobile.cmss.cpms.common.utils.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

@SuppressWarnings("nls")
public class FileUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

    public static final String FILE_SEPARATOR = "/";

    public static final String DEFAULT_MODULE = "default";

    public static final String UTF_8 = "UTF-8";

    public static final String ISO_8859_1 = "ISO-8859-1";

    public static final String ZIP_SUFFIX = ".zip";

    public static final String POINT = ".";

    /**
     * 字符集
     */
    private static final char[] CODE_OPTION = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D',
            'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y',
            'Z' };

    /**
     * 生成相对路径
     *
     * @return path
     */
    public static String generateRelativeDir() {

        return new SimpleDateFormat("/yyyy/MM/dd").format(new Date());
    }

    /**
     * 生成FileId
     *
     * @return fileId
     */
    public static String generateFileId() {

        final StringBuffer path = new StringBuffer();
        final Date date = new Date();
        final DateFormat format = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        final String time = format.format(date);
        path.append(time);
        path.append(generateVerifyCode(7));

        return path.toString();
    }

    /**
     * 生成随机码
     *
     * @param codeCount
     * @return 随机码
     */
    public static String generateVerifyCode(final int codeCount) {

        final Random random = new Random();

        final StringBuffer randomCode = new StringBuffer();
        for (int i = 0; i < codeCount; i++) {
            final String randomStr = String.valueOf(CODE_OPTION[random.nextInt(CODE_OPTION.length)]);
            randomCode.append(randomStr);
        }
        return randomCode.toString();
    }

    /**
     * 获取文件后缀
     *
     * @param fileName
     * @return 后缀
     */
    public static final String getFileSuffix(final String fileName) {

        if (fileName == null) {
            return null;
        }
        final int suffixIndex = fileName.lastIndexOf(POINT);
        if (suffixIndex == -1) {
            return null;
        } else {
            return fileName.substring(suffixIndex);
        }

    }

    public static final String insertBeforeSuffix(final String fileName, final String insertStr) {

        if (fileName == null) {
            return null;
        }
        final int suffixIndex = fileName.lastIndexOf(POINT);
        if (suffixIndex == -1) {
            return fileName + insertStr;
        } else {
            return (suffixIndex > 0 ? fileName.substring(0, suffixIndex) : "") + insertStr
                    + fileName.substring(suffixIndex);

        }

    }

    /**
     * 封装下载返回
     *
     * @param response
     * @param fileName
     */
    public static final void wrapDownloadResponse(final HttpServletResponse response, final String fileName) {

        FileUtil.wrapDownloadResponse(response, fileName, -1);
    }

    /**
     * 封装下载返回
     *
     * @param response
     * @param fileName
     * @param fileLength
     */
    public static final void wrapDownloadResponse(final HttpServletResponse response, final String fileName,
                                                  final int fileLength) {

        response.setContentType("application/octet-stream;charset=UTF-8");
        if (fileLength > 0) {
            response.setContentLength(fileLength);
        }
        String headerValue = "attachment;";
        headerValue += " filename=\"" + encodeURIComponent(fileName) + "\";";
        headerValue += " filename*=utf-8''" + encodeURIComponent(fileName);
        response.setHeader("Content-Disposition", headerValue);
        response.setCharacterEncoding(FileUtil.UTF_8);
    }

    /**
     * <pre>
     * 符合 RFC 3986 标准的“百分号URL编码”
     * 在这个方法里，空格会被编码成%20，而不是+
     * 和浏览器的encodeURIComponent行为一致
     * </pre>
     *
     * @param value
     * @return
     */
    public static String encodeURIComponent(final String value) {

        try {
            return URLEncoder.encode(value, UTF_8).replaceAll("\\+", "%20");
        } catch (final UnsupportedEncodingException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public static String resolveOriginalFilename(String name) {

        String s = name;
        if (s != null) {
            final int startIndex = s.lastIndexOf("\\");
            if (startIndex != -1) {
                s = s.substring(startIndex + 1, s.length());
            }
            if (!StringUtils.isBlank(s)) {
                name = s;
            }
        }
        return name;

    }

}