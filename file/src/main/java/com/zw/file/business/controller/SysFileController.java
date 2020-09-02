package com.zw.file.business.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.zw.file.business.entity.SysFileEntity;
import com.zw.file.business.service.SysFileService;
import com.zw.file.properties.MinioData;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.net.URLEncoder;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author zw
 * @since 2020-09-01
 */
@RestController
@Api(value = "SysFile", tags = "文件上传下载")
@RequestMapping("/SysFile")
@Slf4j
public class SysFileController {

    @Resource
    MinioClient minioClient;
    @Resource
    private MinioData minioData;
    @Resource
    private SysFileService fileService;


    @PostMapping("/upload")
    @ApiOperation(value = "", httpMethod = "GET", response = R.class, notes = "")
    public R<?> success(@RequestParam("file") final MultipartFile file) {
        if (file.isEmpty()) {
            throw new RuntimeException("上传文件不能为空");
        } else {
            //得到文件流
            try (final InputStream inputStream = file.getInputStream()) {
                final String originalFilename = file.getOriginalFilename();
                final SysFileEntity fileEntity = new SysFileEntity(originalFilename, minioData.getBucketName() + "/" + originalFilename);
                // minioClient.putObject(minioData.getBucketName(),originalFilename,inputStream, new PutObjectOptions(inputStream.available(), -1));
                minioClient.putObject(PutObjectArgs.builder().bucket(minioData.getBucketName()).object(originalFilename).stream(inputStream, inputStream.available(), -1).build());
                fileService.save(fileEntity);
                return R.ok(fileEntity.getFileId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    
    @GetMapping("/download")
    @ApiOperation(value = "下载文件", httpMethod = "POST", response = R.class, notes = "")
    public void success(@RequestParam("fileName") String fileName,final HttpServletResponse response) {
        try (InputStream stream = minioClient.getObject(GetObjectArgs.builder().bucket(minioData.getBucketName()).object(fileName).build())) {
            // response.setContentType(stat.contentType());
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            IOUtils.copy(stream,response.getOutputStream());
        } catch (Exception e) {
            log.error("下载文件异常：{}", e.toString());
        }
    }

}
